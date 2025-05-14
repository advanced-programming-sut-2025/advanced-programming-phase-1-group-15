package models.farming.GeneralPlants;

import models.App;
import models.RandomGenerator;
import models.Result;
import models.artisanry.ArtisanItem;
import models.artisanry.ArtisanItemType;
import models.farming.*;
import models.map.*;
import models.map.Map;
import models.time.DateAndTime;
import models.time.TimeObserver;
import models.weather.WeatherObserver;
import models.weather.WeatherOption;

import java.util.*;
import java.util.stream.Collectors;

public class PloughedPlace implements TimeObserver , Tilable , WeatherObserver {
    protected Tile tile;
    protected Fertilizer fertilizer = null;

    int lastUpdateOfDay = 0;

    public void setHarvestable(Harvestable harvestable) {
        this.harvestable = harvestable;
    }

    public Fertilizer getFertilizer() {
        return fertilizer;
    }

    protected Harvestable harvestable;

    protected PlantState currentState = new PloughedState(this);
    protected SeedType seed;
    protected CropSeeds cropSeed;

    protected boolean isInGreenHouse(){
        return tile.getAreaType() == AreaType.GREENHOUSE;
    }

    public Result seed(SeedType seed) {
        if (harvestable != null) {
            return new Result(false, "This plot is already occupied.");
        }

        TreeType treeType = SeedType.getTreeOfSeedType(seed);

        if (treeType == null) {
            return new Result(false, "This seed cannot grow into a tree.");
        }

        Result seedResult = currentState.seed(seed);
        if (!seedResult.isSuccessFull()) {
            return seedResult;
        }

        Tree tree = new Tree(treeType);
        tree.setDaysUntilHarvest(treeType.getTotalHarvestTime());
        this.harvestable = tree;
        this.seed = seed;

        return new Result(true, "Tile seeded successfully.");
    }

    public void setState(PlantState currentState) {
        this.currentState = currentState;
    }

    public DateAndTime lastUpdate(){
        return App.currentGame.getDateAndTime();
    }

    public Result seed(CropSeeds seed){
        if (harvestable != null) {
            return new Result(false, "already there is a crop or tree here!");
        }

        Crops crop = CropSeeds.cropOfThisSeed(seed);

        if(crop == null)  throw new IllegalArgumentException("crop seed cannot be null");

        if (!crop.canGrowInThisSeason(lastUpdate().getSeason()) && !isInGreenHouse()) {
            return new Result(false, "this is not a suitable season for this seed!");
        }

        Result seedResult = this.currentState.seed(seed);
        if(!seedResult.isSuccessFull()) {
            return seedResult;
        }



        this.cropSeed = seed;
        this.harvestable = new Crop(crop);

        if (crop.canBecomeGiant()) {
            List<List<Position>> giantPositions = getAll2x2Groups();
            for(List<Position> giantPosition : giantPositions) {
                if (canBecomeGiant(giantPosition)) {
                    List<Tile> giantTiles = new LinkedList<>();
                    for(Position position : giantPosition) {
                        giantTiles.add(App.currentGame.getTile(position));
                    }
                    GiantPlant giantPlant = new GiantPlant(giantTiles);
                    for (Tile part : giantTiles) {
                        part.setObjectInTile(giantPlant);
                    }
                }
            }
            return new Result(true,"Giant plant seeded!");
        } else {
            return new Result(true,"Crop seeded!");
        }
    }

    public void setFertilizer(Fertilizer fertilizer) {
        this.fertilizer = fertilizer;
    }

    public void setCropSeed(CropSeeds cropSeed) {
        this.cropSeed = cropSeed;
    }

    public void setSeed(SeedType seed) {
        this.seed = seed;
    }

    public PloughedPlace() {
        App.currentGame.getDateAndTime().addObserver(this);
        updateDay();
    }

    public void updateDay(){
        lastUpdateOfDay = App.currentGame.getDateAndTime().getDay();
    }

    public PloughedPlace(Tile tile) {
        this.tile = tile;
        currentState = new PloughedState(this);
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(harvestable != null) {
            harvestable.update(dateAndTime);
        }
        if(lastUpdateOfDay != dateAndTime.getDay()){
            currentState.updateByTime();
            tile.setWatered(false);
            attackedByCrow--;
            updateDay();
        }
    }

    public boolean hasTreeOrCrop() {
        return harvestable != null;
    }

    public void unPlough(){
        this.tile.unplow();
    }

    private List<List<Position>> getAll2x2Groups() {
        Position pos = tile.getPosition();
        return Arrays.asList(
                Arrays.asList(pos, pos.getRight(), pos.getDown(), pos.getRight().getDown()),
                Arrays.asList(pos, pos.getLeft(), pos.getDown(), pos.getDown().getLeft()),
                Arrays.asList(pos, pos.getRight(), pos.getUp(), pos.getRight().getUp()),
                Arrays.asList(pos, pos.getLeft(), pos.getUp(), pos.getLeft().getUp())
        );
    }

    private boolean canBecomeGiant(List<Position> positions) {
        Crops previousCrops = getCropTypeOfPos(positions.get(0));
        TreeType previousTree = getTreeTypeOfPos(positions.get(0));

        for(int i=1;i<positions.size()-1;i++){
            Position nextPos = positions.get(i);
            Position previousPos = positions.get(i-1);
            if(App.currentGame.getTile(previousPos.x,previousPos.y).getAreaType() == AreaType.GREENHOUSE){
                return false;
            }
            
            if(getCropTypeOfPos(nextPos)!= null && getCropTypeOfPos(previousPos)!= null){
                if(!getCropTypeOfPos(nextPos).equals(getCropTypeOfPos(previousPos))){
                    return false;
                }
            }
            else if(getTreeTypeOfPos(nextPos)!= null && getTreeTypeOfPos(previousPos)!= null){
                if(!getTreeTypeOfPos(nextPos).equals(getTreeTypeOfPos(previousPos))){
                    return false;
                }
            }
            else{
                return false;
            }
        }
        return true;
    }

    private Crops getCropTypeOfPos(Position pos){
        Tile tileOfPos = App.currentGame.getTile(pos);
        if(tileOfPos.getObjectInTile() == null){
            return null;
        }
        if(tileOfPos.getObjectInTile() instanceof PloughedPlace ploughedPlace){
            if(ploughedPlace.getHarvestable() instanceof Crop crop){
                return crop.getCropType();
            }
            else{
                return null;
            }
        }
        else return null;
    }

    private TreeType getTreeTypeOfPos(Position pos){
        Tile tileOfPos = App.currentGame.getTile(pos);
        if(tileOfPos.getObjectInTile() == null){
            return null;
        }
        if(tileOfPos.getObjectInTile() instanceof PloughedPlace ploughedPlace){
            if(ploughedPlace.getHarvestable() instanceof Tree tree){
                return tree.getTreeType();
            }
            else{
                return null;
            }
        }
        else return null;
    }

    public PlantState getCurrentState() {
        return currentState;
    }
    public Harvestable getHarvestable() {
        return harvestable;
    }

    public void harvest(){
        harvestable.harvest(1);
    }

    public List<Integer> getDaysUntilHarvests(List<PloughedPlace> tiles) {
        return tiles.stream()
                .map(t -> t.getHarvestable() != null
                        ? t.getHarvestable().getDaysUntilHarvest()
                        : null)
                .collect(Collectors.toList());
    }

    public String printInfo() {
        if (harvestable == null) {
            return "Empty ploughed place.";
        }

        int growth = 0;
        if(currentState instanceof WateredState) growth = 4;
        if(currentState instanceof RestState) growth = 4;
        if(currentState instanceof SeededState) {
            SeededState seededState = (SeededState) currentState;
            growth = seededState.getGrowthLevel() +1;
        }

        return "Name: " + harvestable.getName() + "\n" +
                "Days Until Harvest: " + harvestable.getDaysUntilHarvest() + "\n" +
                "growth Level: "+ growth + "\n" +
                "Current State: " + currentState.getClass().getSimpleName() + "\n" +
                "Watered today: " + (isWatered() ? "Yes" : "No") + "\n" +
                "Fertilized: " + (isFertilized() ? "Yes" : "No");
    }

    private boolean isWatered() {
        return tile.isWatered();
    }

    private boolean isFertilized() {
        return fertilizer != null;
    }

    @Override
    public void update(WeatherOption weatherOption) {
        if(tile.getAreaType() == AreaType.GREENHOUSE){
            return;
        }
        if(weatherOption.equals(WeatherOption.RAINY)){
            this.currentState.water();
        }
        if(weatherOption.equals(WeatherOption.STORM)){
            currentState.water();
        }
    }

    public void setAttackedByCrow(int attackedByCrow) {
        this.attackedByCrow = attackedByCrow;
    }

    int attackedByCrow = 0;

    public void thor(){
        this.unPlough();
        if(this.harvestable instanceof Tree){
            tile.setObjectInTile(new ArtisanItem(ArtisanItemType.COAL));
        }
    }




}



package models.farming.GeneralPlants;

import models.App;
import models.Result;
import models.farming.*;
import models.map.Map;
import models.map.Position;
import models.map.Tile;
import models.time.DateAndTime;
import models.time.TimeObserver;

import java.util.*;
import java.util.stream.Collectors;

public class PloughedPlace implements TimeObserver {
    protected Tile tile;
    protected Harvestable harvestable;
    protected DateAndTime lastUpdate;
    protected PlantState currentState = new PloughedState(this);
    protected SeedType seed;
    protected CropSeeds cropSeed;


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

    public Result seed(CropSeeds seed){
        if (harvestable != null) {
            return new Result(false, "already there is a crop or tree here!");
        }

        Crops crop = CropSeeds.cropOfThisSeed(seed);

        if(crop == null)  throw new IllegalArgumentException("crop seed cannot be null");

        if (!crop.canGrowInThisSeason(lastUpdate.getSeason())) {
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
                    // TODO : Update info of giant Plant based on participants
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

    public void fertilize() {
        currentState.fertilize();
    }

    public PloughedPlace() {
    }

    public PloughedPlace(Tile tile) {
        this.tile = tile;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(harvestable != null) {
            harvestable.update(dateAndTime);
        }
        if(lastUpdate.getDay() != dateAndTime.getDay()){
            currentState.updateByTime();
        }
        // other changes should be added
        lastUpdate = dateAndTime;
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
        if(tileOfPos.getObjectInTile() instanceof PloughedTile ploughedTile){
            if(ploughedTile.getHarvestable() instanceof Crop crop){
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
        if(tileOfPos.getObjectInTile() instanceof PloughedTile ploughedTile){
            if(ploughedTile.getHarvestable() instanceof Tree tree){
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

    public List<Integer> getDaysUntilHarvests(List<PloughedPlace> tiles) {
        return tiles.stream()
                .map(t -> t.getHarvestable() != null
                        ? t.getHarvestable().getDaysUntilHarvest()
                        : null)
                .collect(Collectors.toList());
    }


}



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

public class PloughedPlace implements TimeObserver {
    protected Tile tile;
    protected Harvestable harvestable;
    protected DateAndTime lastUpdate;
    protected PlantState currentState = new PloughedState(this);
    protected SeedType seed;
    protected CropSeeds cropSeed;


    public Result seed(SeedType seed) {
        //return currentState.seed(this, seed);
        //handle for trees later
        return null;
    }

    public void setState(PlantState currentState) {
        this.currentState = currentState;
    }

    public Result seed(CropSeeds seed){
        Crops crop = CropSeeds.cropOfThisSeed(seed);

        if(crop == null)  throw new IllegalArgumentException("crop seed cannot be null");

        if (!crop.canGrowInThisSeason(lastUpdate.getSeason())) {
            return new Result(false, "this is not a suitable season for this seed!");
        }

        this.currentState.seed(seed);

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



        for (Position position : positions) {
            if(getCropTypeOfPos(position) != null){
                if(getCropTypeOfPos(position).equals(previousCrops)){
                    continue;
                }
                else{
                    return false;
                }
            }
            else if(getTreeTypeOfPos(position) != null){
                if(getTreeTypeOfPos(position).equals(previousTree)){
                    continue;
                }
                else{
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

}



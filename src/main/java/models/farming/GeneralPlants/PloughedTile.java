package models.farming.GeneralPlants;

import models.Result;
import models.farming.*;
import models.map.Map;
import models.map.Position;
import models.map.Tilable;
import models.map.Tile;
import models.time.DateAndTime;
import models.time.TimeObserver;

public class PloughedTile implements Tilable, TimeObserver {
    Position position;
    Map map;
    Tile tile;
    DateAndTime lastUpdate;



    private PlantState currentState = new PloughedState();

    public Harvestable getHarvestable() {
        return harvestable;
    }

    private Harvestable harvestable;

    public void setState(PlantState state) {
        this.currentState = state;
    }

    public Result seed(SeedType seed) {
        return currentState.seed(this, seed);
    }

    public void fertilize() {
        currentState.fertilize(this);
    }

    public PloughedTile(Map map, Position position) {
        tile = map.getTile(position);
    }

    public Result seed(CropSeeds seed){
        // TODO: check for getting Giant
        return null;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(lastUpdate.getDay() != dateAndTime.getDay()){
            currentState.updateByTime(this);
        }
        // other changes should be added
        lastUpdate = dateAndTime;
    }

    public boolean hasTreeOrCrop() {
        return harvestable != null;
    }

    public void unPlough(){
        this.tile.empty();
    }
}

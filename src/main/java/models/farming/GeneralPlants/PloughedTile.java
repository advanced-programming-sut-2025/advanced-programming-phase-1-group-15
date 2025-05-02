package models.farming.GeneralPlants;

import models.App;
import models.Result;
import models.farming.*;
import models.map.Map;
import models.map.Position;
import models.map.Tilable;
import models.map.Tile;
import models.time.DateAndTime;
import models.time.TimeObserver;

public class PloughedTile implements Tilable, TimeObserver {
    Tile tile;
    DateAndTime lastUpdate;
    private PloughedPlace ploughedPlace;

    public PloughedTile(Position position) {
        tile = App.currentGame.getMap().getTile(position);
    }

    public Harvestable getHarvestable() {
        return ploughedPlace.getHarvestable();
    }


    public Result seed(SeedType seed) {
        return ploughedPlace.seed(seed);
    }


    public Result seed(CropSeeds seed){
        // TODO: check for getting Giant
        return null;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(lastUpdate.getDay() != dateAndTime.getDay()){
            //ploughedPlace.getCurrentState().updateByTime(dateAndTime);
        }
        // other changes should be added
        lastUpdate = dateAndTime;
    }

    public boolean hasTreeOrCrop() {
        return ploughedPlace.getHarvestable() != null;
    }

    public void unPlough(){
        this.tile.unplow();
    }
}

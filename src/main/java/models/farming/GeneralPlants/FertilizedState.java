package models.farming.GeneralPlants;

import models.Result;
import models.farming.SeedType;

public class FertilizedState implements PlantState {
    private int daysNotWatered = 0 ;

    @Override
    public Result seed(PloughedTile tile, SeedType seed) {
        return new Result(false,"this tile is already seeded");
    }

    @Override
    public Result fertilize(PloughedTile tile) {
        return new Result(false,"this tile is already fertilized");
    }

    @Override
    public Result water(PloughedTile tile) {
        return null;
    }

    @Override
    public Result harvest(PloughedTile tile) {
        return new Result(false,"you should fertilize this tile first");
    }

    @Override
    public Result updateByTime(PloughedTile tile) {
        daysNotWatered++;
        if(daysNotWatered >= 2){
            tile.unPlough();
        }
        return null;
    }
}

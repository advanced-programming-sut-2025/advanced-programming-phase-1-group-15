package models.farming.GeneralPlants;

import models.Result;
import models.farming.CropSeeds;
import models.farming.SeedType;

public class FertilizedState implements PlantState {
    private int daysNotWatered = 0 ;
    int daysWatered = 0;
    int growthLevel = 0;

    @Override
    public Result seed(PloughedPlace tile, CropSeeds seed) {
        return new Result(false,"this tile is already seeded");
    }

    @Override
    public Result fertilize(PloughedPlace tile) {
        return new Result(false,"this tile is already fertilized");
    }

    @Override
    public Result water(PloughedPlace tile) {
        daysWatered++;
        daysNotWatered = 0;
        if(daysWatered > tile.getHarvestable().getStages().get(growthLevel)){
            daysWatered = 0;
            growthLevel++;
        }
        return new Result(true,"successfully watered this plant!");
    }

    @Override
    public Result harvest(PloughedPlace tile) {
        return new Result(false,"you should fertilize this tile first");
    }

    @Override
    public Result updateByTime(PloughedPlace tile) {
        daysNotWatered++;
        if(daysNotWatered >= 2){
            tile.unPlough();
        }
        return null;
    }
}

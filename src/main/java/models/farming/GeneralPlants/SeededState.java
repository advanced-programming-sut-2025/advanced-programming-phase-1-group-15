package models.farming.GeneralPlants;

import models.Result;
import models.farming.CropSeeds;
import models.farming.Fertilizer;
import models.farming.SeedType;
import models.farming.Seedable;

public class SeededState implements PlantState {
    PloughedPlace tile;

    public SeededState(PloughedPlace tile) {
        this.tile = tile;
    }
    private int daysNotWatered = 0 ;
    int daysWatered = 0;
    int growthLevel = 0;


    @Override
    public Result fertilize(Fertilizer fertilizer) {
        return new Result(false,"this tile is already fertilized");
    }

    @Override
    public Result water() {
        daysWatered++;
        daysNotWatered = 0;
        if(daysWatered > tile.getHarvestable().getStages().get(growthLevel)){
            daysWatered = 0;
            growthLevel++;
        }
        return new Result(true,"successfully watered this plant!");
    }

    @Override
    public Result harvest() {
        return new Result(false,"you should water this tile first");
    }

    @Override
    public Result updateByTime() {
        daysNotWatered++;
        if(daysNotWatered >= 2){
            tile.unPlough();
        }
        return null;
    }

    @Override
    public Result takeRest() {
        return null;
    }

    @Override
    public Result seed(Seedable seed) {
        return new Result(false,"this tile is already seeded");
    }
}

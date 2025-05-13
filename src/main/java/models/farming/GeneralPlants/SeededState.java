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
        if(tile.getFertilizer() != null){
            if(tile.getFertilizer().equals(Fertilizer.Growth)){
                daysWatered ++;
            }
        }
    }
    private int daysNotWatered = 0 ;
    private int daysWatered = 0;
    private int growthLevel = 0;

    @Override
    public Result fertilize(Fertilizer fertilizer) {
        return new Result(false,"this tile is already fertilized");
    }

    @Override
    public Result water() {
        daysWatered++;
        daysNotWatered = 0;
        if(growthLevel <= 3) {
            if (daysWatered > tile.getHarvestable().getStages().get(growthLevel)) {
                daysWatered = 0;
                growthLevel++;
            }
        }
        else{
            daysWatered = 0;
        }
        return new Result(true,"successfully watered this plant!");
    }

    @Override
    public Result harvest() {
        if(growthLevel > 3 ){
            tile.harvest();
        }
        return new Result(false,"you should water this tile first");
    }

    @Override
    public Result updateByTime() {
        daysNotWatered++;
        Fertilizer fertilizer = tile.getFertilizer();
        if(fertilizer != null){
            if(fertilizer.equals(Fertilizer.Water)){
                return null;
            }
        }
        if(daysNotWatered >= 2 && growthLevel > 3){
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

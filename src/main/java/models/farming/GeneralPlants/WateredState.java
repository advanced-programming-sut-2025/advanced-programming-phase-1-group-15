package models.farming.GeneralPlants;

import models.Result;
import models.farming.CropSeeds;
import models.farming.SeedType;

public class WateredState implements PlantState {

    @Override
    public Result seed(PloughedPlace tile, CropSeeds seed) {
        return new Result(false,"this tile is already watered");
    }

    @Override
    public Result fertilize(PloughedPlace tile) {
        return new Result(false,"this tile is already fertilized");
    }

    @Override
    public Result updateByTime(PloughedPlace tile) {
        return null;
    }

    @Override
    public Result water(PloughedPlace tile) {
        return new Result(false,"this tile is already watered");
    }

    @Override
    public Result harvest(PloughedPlace tile) {
        return null;
    }
}

package models.farming.GeneralPlants;

import models.Result;
import models.farming.SeedType;

public class WateredState implements PlantState {
    @Override
    public Result seed(PloughedTile tile, SeedType seed) {
        return new Result(false,"this tile is already watered");
    }

    @Override
    public Result fertilize(PloughedTile tile) {
        return new Result(false,"this tile is already fertilized");
    }

    @Override
    public Result water(PloughedTile tile) {
        return new Result(false,"this tile is already watered");
    }

    @Override
    public Result harvest(PloughedTile tile) {
        return null;
    }
}

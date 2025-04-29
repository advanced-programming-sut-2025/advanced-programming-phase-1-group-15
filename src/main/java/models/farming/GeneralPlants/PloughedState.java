package models.farming.GeneralPlants;

import models.Result;
import models.farming.SeedType;

public class PloughedState implements PlantState {

    @Override
    public Result seed(PloughedTile tile, SeedType seed) {
        tile.setState(new SeededState());
        return new Result(true,"congratulations! you seeded this tile!");
    }

    @Override
    public Result fertilize(PloughedTile tile) {
        return new Result(false,"you should seed this tile first");
    }

    @Override
    public Result water(PloughedTile tile) {
        return new Result(false,"you should seed this tile first");
    }

    @Override
    public Result harvest(PloughedTile tile) {
        return new Result(false,"you should seed this tile first");
    }
}

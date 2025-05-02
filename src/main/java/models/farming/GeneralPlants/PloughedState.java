package models.farming.GeneralPlants;

import models.Result;
import models.farming.CropSeeds;
import models.farming.SeedType;

public class PloughedState implements PlantState {

    @Override
    public Result updateByTime(PloughedPlace tile) {
        return null;
    }

    @Override
    public Result seed(PloughedPlace tile, CropSeeds seed) {
        tile.setState(new SeededState());
        return new Result(true,"congratulations! you seeded this tile!");
    }

    @Override
    public Result fertilize(PloughedPlace tile) {
        return new Result(false,"you should seed this tile first");
    }

    @Override
    public Result water(PloughedPlace tile) {
        return new Result(false,"you should seed this tile first");
    }

    @Override
    public Result harvest(PloughedPlace tile) {
        return new Result(false,"you should seed this tile first");
    }
}

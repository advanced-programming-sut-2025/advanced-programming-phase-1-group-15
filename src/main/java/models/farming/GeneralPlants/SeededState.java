package models.farming.GeneralPlants;

import models.Result;
import models.farming.SeedType;

public class SeededState implements PlantState {
    @Override
    public Result updateByTime(PloughedTile tile) {
        return null;
    }

    @Override
    public Result fertilize(PloughedTile tile) {
        tile.setState(new FertilizedState());
        return null;
    }

    @Override
    public Result seed(PloughedTile tile, SeedType seed) {
        return new Result(false,"this tile is already seeded");
    }

    @Override
    public Result water(PloughedTile tile) {
        return new Result(false,"you should fertilize this tile first");
    }

    @Override
    public Result harvest(PloughedTile tile) {
        return new Result(false,"you should fertilize this tile first");
    }
}

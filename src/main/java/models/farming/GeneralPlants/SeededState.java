package models.farming.GeneralPlants;

import models.Result;
import models.farming.CropSeeds;
import models.farming.SeedType;

public class SeededState implements PlantState {
    @Override
    public Result updateByTime(PloughedPlace tile) {
        return null;
    }

    @Override
    public Result fertilize(PloughedPlace tile) {
        tile.setState(new FertilizedState());
        return null;
    }

    @Override
    public Result seed(PloughedPlace tile, CropSeeds seed) {
        return new Result(false,"this tile is already seeded");
    }

    @Override
    public Result water(PloughedPlace tile) {
        return new Result(false,"you should fertilize this tile first");
    }

    @Override
    public Result harvest(PloughedPlace tile) {
        return new Result(false,"you should fertilize this tile first");
    }
}

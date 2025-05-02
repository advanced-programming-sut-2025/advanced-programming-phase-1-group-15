package models.farming.GeneralPlants;

import models.Result;
import models.farming.CropSeeds;
import models.farming.SeedType;

public class SeededState implements PlantState {
    PloughedPlace tile;

    public SeededState(PloughedPlace tile) {
        this.tile = tile;
    }


    @Override
    public Result updateByTime() {
        return null;
    }

    @Override
    public Result fertilize() {
        tile.setState(new FertilizedState(tile));
        return null;
    }

    @Override
    public Result seed(CropSeeds seed) {
        return new Result(false,"this tile is already seeded");
    }

    @Override
    public Result water() {
        return new Result(false,"you should fertilize this tile first");
    }

    @Override
    public Result harvest() {
        return new Result(false,"you should fertilize this tile first");
    }

    @Override
    public Result takeRest() {
        return null;
    }

    @Override
    public Result seed(SeedType seed) {
        return new Result(false,"this tile is already seeded");
    }
}

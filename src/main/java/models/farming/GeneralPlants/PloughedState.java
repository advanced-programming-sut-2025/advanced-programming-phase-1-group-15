package models.farming.GeneralPlants;

import models.Result;
import models.farming.CropSeeds;
import models.farming.SeedType;

public class PloughedState implements PlantState {

    PloughedPlace tile;

    public PloughedState(PloughedPlace tile) {
        this.tile = tile;
    }


    @Override
    public Result updateByTime() {
        return null;
    }

    @Override
    public Result seed(CropSeeds seed) {
        tile.setState(new SeededState(tile));
        return new Result(true,"congratulations! you seeded this tile!");
    }

    @Override
    public Result fertilize() {
        return new Result(false,"you should seed this tile first");
    }

    @Override
    public Result water() {
        return new Result(false,"you should seed this tile first");
    }

    @Override
    public Result harvest() {
        return new Result(false,"you should seed this tile first");
    }

    @Override
    public Result takeRest() {
        return null;
    }

    @Override
    public Result seed(SeedType seed) {
        tile.setState(new SeededState(tile));
        return new Result(true,"congratulations! you seeded this tile!");
    }
}

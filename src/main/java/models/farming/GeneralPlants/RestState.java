package models.farming.GeneralPlants;

import models.Result;
import models.farming.Crop;
import models.farming.CropSeeds;
import models.farming.Crops;

public class RestState implements PlantState{

    private PloughedPlace tile;
    private int restDaysRemaining;

    public RestState(PloughedPlace tile) {
        this.tile = tile;
        Crop crop = (Crop) tile.getHarvestable();
        Crops cropType = crop.getCropType();
        this.restDaysRemaining= cropType.getRegrowthTime();
    }

    @Override
    public Result updateByTime() {
        restDaysRemaining--;
        if (restDaysRemaining <= 0) {
            tile.setState(new WateredState(tile));
            return new Result(true, "Withdrawal period over. Ready to harvest again.");
        }
        return new Result(true, restDaysRemaining + " withdrawal days left.");
    }

    @Override
    public Result harvest() {
        return new Result(false, "Cannot harvest during withdrawal.");
    }

    @Override
    public Result takeRest() {
        return new Result(false,"Already in rest mode");
    }

    @Override
    public Result seed(CropSeeds seed) {
        return new Result(false, "Cannot seed during withdrawal.");
    }

    @Override
    public Result fertilize() {
        return new Result(false, "Cannot fertilize during withdrawal.");
    }

    @Override
    public Result water() {
        return new Result(false, "Already watered (in withdrawal).");
    }
}

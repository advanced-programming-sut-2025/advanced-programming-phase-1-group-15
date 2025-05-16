package models.farming.GeneralPlants;

import models.Result;
import models.farming.*;

public class RestState implements PlantState{

    private PloughedPlace tile;
    private int restDaysRemaining;

    public RestState(PloughedPlace tile) {
        this.tile = tile;
        Harvestable h = tile.getHarvestable();

        if (h instanceof Crop crop) {
            Crops ct = crop.getCropType();
            this.restDaysRemaining = ct.getRegrowthTime();
        }
        else if (h instanceof Tree tree) {
            this.restDaysRemaining = tree.getTreeType().getTotalHarvestTime();
        }
        else {
            throw new IllegalStateException("RestState on non-crop/tree");
        }
    }

    @Override
    public Result updateByTime() {
        restDaysRemaining--;
        if (restDaysRemaining <= 0) {
            tile.setState(new WateredState(tile));
            return new Result(true, "rest Period period over. Ready to harvest again.");
        }
        return new Result(true, restDaysRemaining + " withdrawal days left.");
    }

    @Override
    public Result harvest() {
        return new Result(false, "Cannot harvest during rest Period.");
    }

    @Override
    public Result takeRest() {
        return new Result(false,"Already in rest mode");
    }

    @Override
    public Result fertilize(Fertilizer fertilizer) {
        return new Result(false, "Cannot fertilize during rest Period.");
    }

    @Override
    public Result water() {
        return new Result(false, "Already watered (in rest Period).");
    }

    @Override
    public Result seed(Seedable seed) {
        return new Result(false,"this tile is already seeded");
    }
}

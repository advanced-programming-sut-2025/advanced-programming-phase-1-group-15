package models.farming.GeneralPlants;

import models.Result;
import models.farming.*;

public class SeededState implements PlantState {
    PloughedPlace tile;
    boolean isWatered = false;

    public SeededState(PloughedPlace tile) {
        this.tile = tile;
        if (tile.getFertilizer() != null) {
            if (tile.getFertilizer().equals(Fertilizer.Growth)) {
                daysWatered++;
            }
        }
    }

    private int daysNotWatered = 0;
    private int daysWatered = 0;
    private int growthLevel = 0;

    public int getGrowthLevel() {
        return growthLevel;
    }

    private int totalDaysWatered = 0;

    @Override
    public Result fertilize(Fertilizer fertilizer) {
        return new Result(false, "this tile is already fertilized");
    }

    @Override
    public Result water() {
        isWatered = true;
        daysWatered++;
        daysNotWatered = 0;

        int maxGrowth = tile.getHarvestable().getStages().size() - 1;

        if (growthLevel <= maxGrowth) {
            if (tile.getHarvestable() instanceof Crop) {
                Crop crop = (Crop) tile.getHarvestable();
                if (daysWatered > crop.getStages().get(growthLevel)) {
                    daysWatered = 0;
                    growthLevel++;
                }
            } else if (tile.getHarvestable() instanceof Tree tree) {
                if (daysWatered > tree.getStages().get(growthLevel)) {
                    daysWatered = 0;
                    growthLevel++;
                }
            }
        } else {
            tile.setState(new WateredState(tile));
        }
        return new Result(true, "successfully watered this plant!");
    }

    @Override
    public Result harvest() {
        int finalStage = tile.getHarvestable().getStages().size();
        if (growthLevel >= finalStage) {
            tile.harvest();
            return new Result(true, "successfully harvested this plant!");
        }
        return new Result(false, "you should water this tile first");
    }

    @Override
    public Result updateByTime() {
        daysNotWatered++;
        totalDaysWatered++;
        isWatered = false;

        if (tile.getFertilizer() == Fertilizer.Water) {
            return new Result(true, "");
        }

//        if (totalDaysWatered >= tile.getHarvestable().getDaysUntilHarvest()) {
//            tile.setState(new WateredState(tile));
//            return new Result(true, "Plant fully grown and ready to harvest.");
//        }
        if (tile.getHarvestable().getDaysUntilHarvest() <= 0) {
            tile.setState(new WateredState(tile));
            return new Result(true, "Plant fully grown and ready to harvest.");
        }

        if (daysNotWatered >= 3) {
            tile.unPlough();
        }
        return new Result(true, "");
    }

    @Override
    public Result takeRest() {
        return new Result(false, "this tile is already seeded");
    }

    @Override
    public Result seed(Seedable seed) {
        return new Result(false, "this tile is already seeded");
    }
}

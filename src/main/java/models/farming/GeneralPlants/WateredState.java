package models.farming.GeneralPlants;

import models.Result;
import models.farming.*;

public class WateredState implements PlantState {

    PloughedPlace tile;


    public WateredState(PloughedPlace tile) {
        this.tile = tile;
    }

    @Override
    public Result seed(Seedable seed) {
        return new Result(false,"this tile is already seeded");
    }

    @Override
    public Result fertilize(Fertilizer fertilizer) {

        return new Result(false,"this tile is already watered");
    }

    @Override
    public Result updateByTime() {
        return null;
    }

    @Override
    public Result water() {
        return new Result(false,"this tile is already watered");
    }

    @Override
    public Result harvest() {
        Harvestable harvestable = tile.getHarvestable();
        if (harvestable == null) {
            return new Result(false, "Nothing to harvest.");
        }

        if (harvestable.getDaysUntilHarvest() > 0) {
            return new Result(false, "Crop isn't ready.");
        }

        int amount = ( tile instanceof GiantPlant ) ? 10 : 1;

        harvestable.harvest(amount);

        if (harvestable.isOneTime()) {
            tile.unPlough();
            return new Result(true, "Harvested. Crop removed.");
        } else {
            tile.setState(new RestState(tile));
            return new Result(true, "Harvested. rest mode started.");
        }
    }

    @Override
    public Result takeRest() {
        return null;
    }

}

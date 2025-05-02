package models.farming.GeneralPlants;

import models.Result;
import models.farming.Crop;
import models.farming.CropSeeds;
import models.farming.Harvestable;
import models.farming.SeedType;

public class WateredState implements PlantState {

    PloughedPlace tile;


    public WateredState(PloughedPlace tile) {
        this.tile = tile;
    }

    @Override
    public Result seed(CropSeeds seed) {
        return new Result(false,"this tile is already watered");
    }

    @Override
    public Result fertilize() {
        return new Result(false,"this tile is already fertilized");
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

        if (harvestable instanceof Crop) {
            Crop crop = (Crop) harvestable;
        }

        harvestable.harvest();

        if (harvestable instanceof Crop) {
            Crop crop = (Crop) harvestable;
            if (crop.getCropType().isOneTime()) {
                tile.unPlough();
                // TODO: check if deleting plowed tile effect other things
                return new Result(true, "Harvested. Crop removed.");
            } else {
                tile.setState(new RestState(tile));
                return new Result(true, "Harvested. Withdrawal started.");
            }
        }

        return new Result(true, "Harvested.");
    }

    @Override
    public Result takeRest() {
        return null;
    }
}

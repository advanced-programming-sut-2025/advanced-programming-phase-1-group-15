package models.farming;

import models.map.Tilable;
import models.tools.BackPackable;

public class Crop implements Tilable, BackPackable, Harvestable {
    private Crops cropType;

    private GiantProducts giantProducts;

    public Crop(Crops cropType) {
        this.cropType = cropType;
    }


    @Override
    public void harvest() {

    }

    @Override
    public String getName() {
        return cropType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getPrice() {
        return cropType.getBasePrice();
    }
}

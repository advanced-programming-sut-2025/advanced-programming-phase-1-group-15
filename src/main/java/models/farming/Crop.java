package models.farming;

import models.map.Tilable;

public class Crop implements Tilable, Harvestable {
    private Crops cropType;

    private GiantProducts giantProducts;

    public Crop(Crops cropType) {
        this.cropType = cropType;
    }


    @Override
    public void harvest() {

    }
}

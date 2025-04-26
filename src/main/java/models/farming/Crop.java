package models.farming;

import models.map.Tilable;

public class Crop implements Tilable {
    Crops cropType;

    public Crop(Crops cropType) {
        this.cropType = cropType;
    }


}

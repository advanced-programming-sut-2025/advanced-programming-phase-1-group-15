package com.example.common.farming;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.tools.BackPackable;

public class CropProduct implements BackPackable {

    private final Crops cropType;
    private final Crop sourceCrop;


    public CropProduct(Crop sourceCrop) {
        this.sourceCrop = sourceCrop;
        this.cropType   = sourceCrop.getCropType();
    }

    public Crops getCropType() {
        return cropType;
    }

    public Crop getSourceCrop() {
        return sourceCrop;
    }

    @Override
    public String toString() {
        return String.format("%s", getName());
    }

    @Override
    public String getName() {
        return cropType.name().toLowerCase();
    }

    @Override
    public String getDescription() {
        return cropType.getName().toLowerCase() + " crop";
    }

    @Override
    public int getPrice() {
        return cropType.getBasePrice(); // TODO : should check effect of quality and other things on price
    }

    public Sprite getSprite(){
        return null;
    }
}

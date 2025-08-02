package com.example.common.farming;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.client.models.ClientApp;
import com.example.common.enums.Quality;
import com.example.common.map.Tilable;
import com.example.common.tools.BackPackable;

import java.util.ArrayList;

public class Crop extends Harvestable implements Tilable, BackPackable {


    private final Crops cropType;
    int daysNotWatered;
    double sellPrice;
    Quality quality;

    public Crop(Crops cropType) {
        this.cropType = cropType;
        daysUntilHarvest = cropType.getTotalHarvestTime();
        sellPrice = cropType.getBasePrice(); // check effect of fertilizer later
    }

    @Override
    public void harvest(int number) {
        ClientApp.currentGame.getCurrentPlayer().addToBackPack(new CropProduct(this),number);
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

    @Override
    public ArrayList<Integer> getStages(){
        return cropType.getStages();
    }

    public Crops getCropType() {
        return cropType;
    }

    @Override
    public boolean isOneTime(){
        return cropType.isOneTime();
    }

    @Override
    public Sprite getSprite(){
        return null;
    }

}

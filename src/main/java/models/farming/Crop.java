package models.farming;

import models.enums.Quality;
import models.map.Tilable;
import models.tools.BackPackable;

import java.util.ArrayList;

public class Crop implements Tilable, BackPackable, Harvestable {
    public Crops getCropType() {
        return cropType;
    }

    private final Crops cropType;
    int daysUntilHarvest;
    int daysNotWatered;
    double sellPrice;
    Quality quality;

    public int getDaysUntilHarvest(){
        return daysUntilHarvest;
    }

    private GiantProducts giantProducts;

    public Crop(Crops cropType) {
        this.cropType = cropType;
        daysUntilHarvest = cropType.getTotalHarvestTime();
        sellPrice = cropType.getBasePrice();
    }


    @Override
    public void harvest() {

    }

    @Override
    public String printInfo(){
        return "plant info: \n"+
                "name: "+ cropType+"\n"+
                "days Until Harvest: " + daysUntilHarvest + "\n"+
                "is today watered? : " + (((daysNotWatered==0) ? "yes" : "no")) + "\n"+
                "";
        // TODO: should be completed and fixed . there are some paradoxes in doc

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
}

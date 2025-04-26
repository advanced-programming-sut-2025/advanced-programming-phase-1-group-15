package models.farming;

import models.map.Area;

public class GiantProducts {
    Harvestable[] harvestables;
    private int daysUntilHarvest = 0 ;
    private int daysNotWatered;
    private boolean isFeritilized;

    public Harvestable[] getProducts(){
        return harvestables;

    }

    public GiantProducts(Harvestable[] harvestables) {
        this.harvestables = harvestables;
        for(Harvestable h: harvestables){
            daysUntilHarvest = Math.max(daysUntilHarvest,h.getDaysUntilHarvest());
        }
        for(Harvestable h: harvestables){
            daysNotWatered = Math.min(daysNotWatered,h.getDaysUntilHarvest());
        }
    }
}

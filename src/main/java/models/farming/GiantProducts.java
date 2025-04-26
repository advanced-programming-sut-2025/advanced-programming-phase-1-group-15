package models.farming;

import models.map.Area;

public class GiantProducts {
    Harvestable[] harvestables;
    public Harvestable[] getProducts(){
        return harvestables;
    }

    public GiantProducts(Harvestable[] harvestables) {
        this.harvestables = harvestables;
    }
}

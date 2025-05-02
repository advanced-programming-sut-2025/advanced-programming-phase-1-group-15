package models.farming.GeneralPlants;

import models.farming.Harvestable;
import models.map.Tilable;
import models.map.Tile;
import models.time.DateAndTime;

import java.util.List;

public class GiantPlant extends PloughedPlace implements Tilable {
    private List<Tile> parts;
    private int daysUntilHarvest;

    public GiantPlant(List<Tile> parts) {
        super();
        this.parts= parts;
    }


    @Override
    public void update(DateAndTime dateAndTime) {
    }

}

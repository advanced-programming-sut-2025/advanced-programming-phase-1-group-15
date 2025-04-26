package models.farming;

import models.map.Tilable;
import models.time.DateAndTime;
import models.time.TimeObserver;
import models.tools.BackPackable;

public class ForagingCrop implements Tilable, BackPackable, TimeObserver {
    private ForagingCropsType foragingCropsType;

    public ForagingCropsType getForagingCropsType() {
        return foragingCropsType;
    }

    public void setForagingCropsType(ForagingCropsType foragingCropsType) {
        this.foragingCropsType = foragingCropsType;
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }

    @Override
    public String getName() {
        return foragingCropsType.name().toLowerCase().replace("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getPrice() {
        return foragingCropsType.basePrice;
    }
}
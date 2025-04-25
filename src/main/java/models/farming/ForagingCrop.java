package models.farming;

import models.map.Tilable;
import models.time.DateAndTime;
import models.time.TimeObserver;

public class ForagingCrop implements Tilable, TimeObserver {
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
}
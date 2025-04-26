package models.farming;

import models.map.Tilable;
import models.time.DateAndTime;
import models.time.TimeObserver;
import models.tools.BackPackable;

public class ForagingSeeds implements Tilable, BackPackable, TimeObserver {
    ForagingSeedsType foragingSeedsType;
    boolean fertilized;
    int notWateredDays;

    public void water() {

    }
    public void fertilize() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }

    @Override
    public String getName() {
        return foragingSeedsType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getPrice() {
        return 0;
    }
}

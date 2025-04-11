package models.farming;

import models.map.Tilable;
import models.time.DateAndTime;
import models.time.TimeObserver;

public class ForagingSeeds implements Tilable, TimeObserver {
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
}

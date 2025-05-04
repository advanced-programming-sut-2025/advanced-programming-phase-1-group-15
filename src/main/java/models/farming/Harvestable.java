package models.farming;

import models.enums.Quality;
import models.time.DateAndTime;
import models.time.TimeObserver;

import java.util.ArrayList;

public abstract class Harvestable implements TimeObserver {
    protected int daysUntilHarvest;
    protected Quality quality;
    protected DateAndTime lastUpdate;

    public void update(DateAndTime dt) {
        if(lastUpdate.getDay() != dt.getDay()){
            if(daysUntilHarvest > 0)
                daysUntilHarvest--;
        }
        lastUpdate = dt;
    }

    public abstract void harvest();
    public abstract ArrayList<Integer> getStages();
    public abstract String printInfo();

    public int getDaysUntilHarvest() {
        return daysUntilHarvest;
    }

    public void setDaysUntilHarvest(int days) {
        this.daysUntilHarvest = days;
    }

    public abstract boolean isOneTime();

}

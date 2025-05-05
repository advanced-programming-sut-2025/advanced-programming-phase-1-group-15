package models.farming;

import models.enums.Quality;
import models.time.DateAndTime;
import models.time.TimeObserver;

import java.util.ArrayList;
import java.util.List;

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

    public abstract void harvest(int number);
    public abstract ArrayList<Integer> getStages();

    public int getDaysUntilHarvest() {
        return daysUntilHarvest;
    }

    public void setDaysUntilHarvest(int days) {
        this.daysUntilHarvest = days;
    }

    public abstract boolean isOneTime();

    public abstract String getName();

    public static List<Integer> getDaysUntilHarvest(List<Harvestable> harvestables) {
        ArrayList<Integer> daysUntilHarvest = new ArrayList<>();
        for (Harvestable harvestable : harvestables) {
            daysUntilHarvest.add(harvestable.getDaysUntilHarvest());
        }
        return daysUntilHarvest;
    }


}

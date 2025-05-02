package models.farming;

import models.enums.Quality;

import java.util.ArrayList;

public abstract class Harvestable {
    protected int daysUntilHarvest;
    protected Quality quality;

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

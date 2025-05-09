package models.stores;

import models.map.Area;
import models.time.DateAndTime;

public abstract class Store extends Area {
    protected Runner runner;
    protected int opensAt;
    protected int closesAt;

    public abstract String displayItems();
    public abstract void resetSoldItems();
    public boolean isOpen(int hour) {
        return hour >= opensAt && hour <= closesAt;
    }
    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == opensAt) {
            resetSoldItems();
        }
    }
}
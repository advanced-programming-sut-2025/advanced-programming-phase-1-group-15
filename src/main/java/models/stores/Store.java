package models.stores;

import models.map.Area;
import models.time.DateAndTime;

public abstract class Store extends Area {
    protected Runner runner;
    protected int opensAt;
    protected int closesAt;

    public abstract String displayItems();
    public abstract String displayAvailableItems();
    public abstract void resetSoldItems();
    public abstract boolean checkAvailable(String productName);
    public abstract boolean checkAmount(String productName, int amount);

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
package com.example.models.stores;

import com.example.models.Player;
import com.example.models.map.Area;
import com.example.models.map.Tile;
import com.example.models.time.DateAndTime;

import java.util.ArrayList;

public abstract class Store extends Area {
    protected Runner runner;
    protected int opensAt;
    protected int closesAt;

    public Store() {

    }

    public Store(ArrayList<ArrayList<Tile>> storeTiles) {
        super(storeTiles);
    }

    public abstract String displayItems();
    public abstract String displayAvailableItems();
    public abstract void resetSoldItems();
    public abstract boolean checkAvailable(String productName);
    public abstract boolean checkAmount(String productName, int amount);
    public abstract String sell(Player buyer, String productName, int amount);

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

package com.example.common.stores;

import com.example.common.Player;
import com.example.common.Result;
import com.example.common.map.Area;
import com.example.common.map.Tile;
import com.example.common.time.DateAndTime;

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
    public abstract Result sell(Player buyer, String productName, int amount);

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

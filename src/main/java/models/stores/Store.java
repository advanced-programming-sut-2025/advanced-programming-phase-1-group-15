package models.stores;

import models.map.Area;
import models.map.Tile;
import models.time.DateAndTime;
import models.tools.BackPackable;

import java.util.ArrayList;

public class Store extends Area {
    protected String runner;
    protected int opensAt;
    protected int closesAt;

    @Override
    public void build() {
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}
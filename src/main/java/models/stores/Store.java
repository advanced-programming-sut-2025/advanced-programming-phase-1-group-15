package models.stores;

import models.map.Area;
import models.time.DateAndTime;

public abstract class Store extends Area {
    protected Runner runner;
    protected int opensAt;
    protected int closesAt;
}
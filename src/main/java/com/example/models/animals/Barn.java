package com.example.models.animals;

import com.example.models.map.Area;
import com.example.models.map.AreaType;
import com.example.models.time.DateAndTime;

public class Barn extends Area {
    private int animalCount = 0;
    private int capacity = 4;
    private boolean Big = false;
    private boolean Deluxe = false;

    public Barn() {
        this.areaType = AreaType.BARN;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isBig() {
        return Big;
    }
    public void setBig() {
        Big = true;
        this.capacity = 8;
    }

    public boolean isDeluxe() {
        return Deluxe;
    }
    public void setDeluxe() {
        Deluxe = true;
        this.capacity = 12;
    }

    @Override
    public void build() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

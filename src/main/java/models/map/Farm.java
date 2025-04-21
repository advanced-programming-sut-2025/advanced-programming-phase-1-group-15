package models.map;

import models.foraging.Stone;
import models.farming.Tree;

import java.util.ArrayList;

public class Farm extends Area {
    private final int number;
    public Farm(int number) {
        this.number = number;
    }



    public void build() {
        innerAreas = new ArrayList<>();
        innerAreas.add(new Lake());
        innerAreas.add(new GreenHouse());
        innerAreas.add(new House());
        innerAreas.add(new Quarry());
    }
}

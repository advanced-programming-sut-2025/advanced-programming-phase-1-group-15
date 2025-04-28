package models.stores;

import models.foraging.ForagingSeeds;

import java.util.ArrayList;

public class JojaMart extends Store {
    private ArrayList<ForagingSeeds> foragingSeeds = new ArrayList<>();


    public JojaMart() {
        runner = "Robin";
        opensAt = 9;
        closesAt = 16;
    }

    @Override
    public void build() {

    }
}

package models.stores;

import models.animals.Animal;
import models.farming.ForagingSeeds;

import java.util.ArrayList;

public class MarnieRanch extends Store {
    private ArrayList<Animal> animals = new ArrayList<>();


    public MarnieRanch() {
        runner = "Robin";
        opensAt = 9;
        closesAt = 16;
    }

    @Override
    public void build() {

    }
}

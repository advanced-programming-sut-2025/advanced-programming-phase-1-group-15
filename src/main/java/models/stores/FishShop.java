package models.stores;

import models.animals.Fish;
import models.tools.FishingPole;

import java.util.ArrayList;

public class FishShop extends Store {
    private ArrayList<FishingPole> fishingPoles = new ArrayList<>();
    private ArrayList<Fish> fishes = new ArrayList<>();

    public FishShop() {
        runner = "Robin";
        opensAt = 9;
        closesAt = 16;
    }

    @Override
    public void build() {

    }
}

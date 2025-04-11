package models.stores;

import models.animals.Barn;

import java.util.ArrayList;

public class CarpenterShop extends Store {
    private ArrayList<Barn> barns = new ArrayList<>();

    public CarpenterShop() {
        runner = "Robin";
        opensAt = 9;
        closesAt = 16;
    }

    @Override
    public void build() {

    }
}

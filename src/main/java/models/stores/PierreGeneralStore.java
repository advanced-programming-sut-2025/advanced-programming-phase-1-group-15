package models.stores;

import models.tools.BackPackable;

import java.util.ArrayList;

public class PierreGeneralStore extends Store {
    private ArrayList<BackPackable> items = new ArrayList<>();


    public PierreGeneralStore() {
        runner = "Robin";
        opensAt = 9;
        closesAt = 16;
    }

    @Override
    public void build() {

    }
}

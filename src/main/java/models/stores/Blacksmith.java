package models.stores;

import models.tools.Tool;

import java.util.ArrayList;

public class Blacksmith extends Store {
    private ArrayList<Tool> products = new ArrayList<>();

    public Blacksmith() {
        runner = "Clint";
        opensAt = 9;
        closesAt = 16;
    }

    @Override
    public void build() {

    }
}

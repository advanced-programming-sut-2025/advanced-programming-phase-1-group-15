package models.map;

import models.foraging.Stone;
import models.farming.Tree;

import java.util.ArrayList;

public class Farm extends Area {
    public Farm() {
        innerAreas = new ArrayList<>();
        innerAreas.add(new Lake());
        innerAreas.add(new GreenHouse());
        innerAreas.add(new House());
        innerAreas.add(new Quarry());
    }

    ArrayList<Tree> trees = new ArrayList<>();
    ArrayList<Stone> stones = new ArrayList<>();

    public void build() {

    }
}

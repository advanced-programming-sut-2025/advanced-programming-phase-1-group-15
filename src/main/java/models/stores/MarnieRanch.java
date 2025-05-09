package models.stores;

import models.map.AreaType;
import models.map.Tile;
import models.time.DateAndTime;

import java.util.ArrayList;
import java.util.HashMap;

public class MarnieRanch extends Store {
    public static int[] coordinates = {46, 50, 21, 25};


    private HashMap<MarnieRanchItems, Integer> Sold = new HashMap<>();

    public MarnieRanch(ArrayList<ArrayList<Tile>> storeTiles) {
        runner = Runner.MARNIE;
        opensAt = 9;
        closesAt = 16;

        this.areaType = AreaType.STORE;
        this.tiles = storeTiles;

        for(ArrayList<Tile> row : storeTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }

    @Override
    public void build() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getDay() == opensAt) {

        }
    }

    @Override
    public String displayItems() {
        StringBuilder display = new StringBuilder();

        display.append("Name    Description    Price\n");
        for(BlackSmithItems item : BlackSmithItems.values()) {
            display.append(item.getName()).append("\t");
            display.append(item.foragingMineralType.description).append("\t");
            display.append(item.price).append("\n");
        }

        display.append("and animals: \n");
        display.append("""
                Chicken\tWell cared-for chickens lay eggs every day. Lives in the coop.\t800g
                Cow\tCan be milked daily. A milk pail is required to harvest the milk. Lives in the barn.\t1,500g
                Goat\tHappy provide goat milk every other day. A milk pail is required to harvest the milk. Lives in the barn.\t4,000g
                Duck\tHappy lay duck eggs every other day. Lives in the coop.\t1,200g
                Sheep\tCan be shorn for wool. A pair of shears is required to harvest the wool. Lives in the barn.\t8,000g
                Rabbit\tThese are wooly rabbits! They shed precious wool every few days. Lives in the coop.\t8,000g
                Dinosaur\tThe Dinosaur is a farm animal that lives in a Big Coop\t14,000g
                Pig\tThese pigs are trained to find truffles! Lives in the barn.\t16,000g""");

        return display.toString();
    }
}

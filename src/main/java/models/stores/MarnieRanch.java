package models.stores;

import models.Player;
import models.foraging.ForagingMineral;
import models.map.AreaType;
import models.map.Tile;
import models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class MarnieRanch extends Store {
    public static int[] coordinates = {46, 50, 21, 25};

    private HashMap<MarnieRanchItems, Integer> sold = new HashMap<>();

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

        for(MarnieRanchItems item : MarnieRanchItems.values()) {
            sold.put(item, 0);
        }
    }

    @Override
    public void build() {

    }

    @Override
    public void resetSoldItems() {
        sold.replaceAll((i, v) -> 0);
    }

    @Override
    public boolean checkAvailable(String productName) {
        for(MarnieRanchItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkAmount(String productName, int amount) {
        for(MarnieRanchItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return amount + sold.get(item) <= item.dailyLimit;
            }
        }
        return false;
    }

    @Override
    public String sell(Player buyer, String productName, int amount) {
        return "";
    }

    @Override
    public String displayItems() {
        StringBuilder display = new StringBuilder();

        display.append("Name    Price\n");
        for(MarnieRanchItems item : MarnieRanchItems.values()) {
            display.append(item.getName()).append("\t");
            display.append(item.price).append("\n");
        }

        display.append("and animals: \n");
        display.append("""
                Chicken\t"Well cared-for chickens lay eggs every day. Lives in the coop."\t800g
                Cow\t"Can be milked daily. A milk pail is required to harvest the milk. Lives in the barn."\t1,500g
                Goat\t"Happy provide goat milk every other day. A milk pail is required to harvest the milk. Lives in the barn."\t4,000g
                Duck\t"Happy lay duck eggs every other day. Lives in the coop."\t1,200g
                Sheep\t"Can be shorn for wool. A pair of shears is required to harvest the wool. Lives in the barn."\t8,000g
                Rabbit\t"These are wooly rabbits! They shed precious wool every few days. Lives in the coop."\t8,000g
                Dinosaur\t"The Dinosaur is a farm animal that lives in a Big Coop."\t14,000g
                Pig\t"These pigs are trained to find truffles! Lives in the barn."\t16,000g
                """);


        return display.toString();
    }

    @Override
    public String displayAvailableItems() {
        StringBuilder display = new StringBuilder();

        display.append("Name    Price\n");
        for(MarnieRanchItems item : sold.keySet()) {
            if(sold.get(item) == item.dailyLimit) {
                display.append(item.getName()).append("\t");
                display.append(item.price).append("\n");
            }
        }

        display.append("and animals: \n");
        display.append("""
                Chicken\t"Well cared-for chickens lay eggs every day. Lives in the coop."\t800g
                Cow\t"Can be milked daily. A milk pail is required to harvest the milk. Lives in the barn."\t1,500g
                Goat\t"Happy provide goat milk every other day. A milk pail is required to harvest the milk. Lives in the barn."\t4,000g
                Duck\t"Happy lay duck eggs every other day. Lives in the coop."\t1,200g
                Sheep\t"Can be shorn for wool. A pair of shears is required to harvest the wool. Lives in the barn."\t8,000g
                Rabbit\t"These are wooly rabbits! They shed precious wool every few days. Lives in the coop."\t8,000g
                Dinosaur\t"The Dinosaur is a farm animal that lives in a Big Coop."\t14,000g
                Pig\t"These pigs are trained to find truffles! Lives in the barn."\t16,000g
                """);


        return display.toString();
    }
}

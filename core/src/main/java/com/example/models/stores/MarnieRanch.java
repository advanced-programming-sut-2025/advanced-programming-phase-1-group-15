package com.example.models.stores;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.Player;
import com.example.models.map.AreaType;
import com.example.models.map.Tile;
import com.example.models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class MarnieRanch extends Store {
    public static int[] coordinates = {46, 50, 21, 25};

    private GeneralItem hay = new GeneralItem(GeneralItemsType.HAY);
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
    public TextureRegion getTexture() {
        return null;
    }

    @Override
    public void resetSoldItems() {
        sold.replaceAll((i, v) -> 0);
    }

    @Override
    public boolean checkAvailable(String productName) {
        if(productName.equalsIgnoreCase(hay.getName())) {
            return true;
        }
        for(MarnieRanchItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkAmount(String productName, int amount) {
        if(productName.equalsIgnoreCase(hay.getName())) {
            return true;
        }
        for(MarnieRanchItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return amount + sold.get(item) <= item.dailyLimit;
            }
        }
        return false;
    }

    @Override
    public String sell(Player buyer, String productName, int amount) {
        if(productName.equalsIgnoreCase("hay")) {
            if(amount * hay.getPrice() > buyer.getGold()) {
                return "not enough gold to buy " + amount + " " + hay.getName();
            }

            buyer.subtractGold(amount * hay.getPrice());
            buyer.addToBackPack(new GeneralItem(GeneralItemsType.HAY), amount);
            return "you've bought " + amount + " " + hay.getName() + " with price " + amount * hay.getPrice();
        }
        for(MarnieRanchItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                if(amount * item.price > buyer.getGold()) {
                    return "not enough gold to buy " + amount + " " + item.getName();
                }

                buyer.subtractGold(amount * item.price);
                buyer.addToBackPack(Tool.toolFactory(item.toolType), amount);
                sold.put(item, sold.get(item) + amount);
                return "you've bought " + amount + " " + item.getName() + " with price " + amount * item.price;
            }
        }

        return "";
    }

    @Override
    public String displayItems() {
        StringBuilder display = new StringBuilder();

        display.append("Name    Price\n");
        display.append(hay.getName()).append("\t");
        display.append(hay.getPrice()).append("\n");
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
        display.append(hay.getName()).append("\t");
        display.append(hay.getPrice()).append("\n");
        for(MarnieRanchItems item : sold.keySet()) {
            if(sold.get(item) < item.dailyLimit) {
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

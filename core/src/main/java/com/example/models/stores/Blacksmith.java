package com.example.models.stores;

import com.example.models.Player;
import com.example.models.foraging.ForagingMineral;
import com.example.models.map.AreaType;
import com.example.models.map.Tile;
import com.example.models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class Blacksmith extends Store {
    public static int[] coordinates = {47, 51, 40, 43};

    private HashMap<BlackSmithItems, Integer> sold = new HashMap<>();

    public Blacksmith(ArrayList<ArrayList<Tile>> storeTiles) {
        runner = Runner.CLINT;
        opensAt = 9;
        closesAt = 16;

        this.areaType = AreaType.STORE;
        this.tiles = storeTiles;

        for(ArrayList<Tile> row : storeTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }

        for(BlackSmithItems item : BlackSmithItems.values()) {
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
        for(BlackSmithItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkAmount(String productName, int amount) {
        for(BlackSmithItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return amount + sold.get(item) <= item.dailyLimit;
            }
        }
        return false;
    }

    @Override
    public String sell(Player buyer, String productName, int amount) {
        for(BlackSmithItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                if(amount * item.price > buyer.getGold()) {
                    return "not enough gold to buy " + amount + " " + item.getName();
                }

                buyer.subtractGold(amount * item.price);
                buyer.addToBackPack(new ForagingMineral(item.foragingMineralType), amount);
                sold.put(item, sold.get(item) + amount);
                return "you've bought " + amount + " " + item.getName() + " with price " + amount * item.price;
            }
        }

        return "";
    }

    @Override
    public String displayItems() {
        StringBuilder display = new StringBuilder();

        display.append("Name    Description    Price\n");
        for(BlackSmithItems item : BlackSmithItems.values()) {
            display.append(item.getName()).append("\t");
            display.append("\"").append(item.foragingMineralType.description).append("\"").append("\t");
            display.append(item.price).append("\n");
        }

        display.append("and different upgrades: \n");
        display.append("""
                Copper Tool\tCopper Bar(5)\t2,000g
                Steel Tool\tIron Bar(5)\t5,000g
                Gold Tool\tGold Bar(5)\t10,000g
                Iridium Tool\tIridium Bar(5)\t25,000g
                Copper Trash Can\tCopper Bar(5)\t1,000g
                Steel Trash Can\tIron Bar(5)\t2,500g
                Gold Trash Can\tGold Bar(5)\t5,000g
                Iridium Trash Can\tIridium Bar(5)\t12,500g
                """);

        return display.toString();
    }

    @Override
    public String displayAvailableItems() {
        return displayItems();
    }

    public void upgradeTool(Tool tool) {

    }
}

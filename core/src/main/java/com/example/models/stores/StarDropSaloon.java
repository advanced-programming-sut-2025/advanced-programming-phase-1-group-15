package com.example.models.stores;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.Player;
import com.example.models.Result;
import com.example.models.artisanry.ArtisanItem;
import com.example.models.artisanry.ArtisanItemType;
import com.example.models.cooking.Food;
import com.example.models.cooking.FoodType;
import com.example.models.map.AreaType;
import com.example.models.map.Tile;
import com.example.views.GameAssetManager;

import java.util.ArrayList;
import java.util.HashMap;

public class StarDropSaloon extends Store {
    public static int[] coordinates = {51, 54, 33, 36};

    private HashMap<StarDropSaloonItems, Integer> sold = new HashMap<>();

    public StarDropSaloon(ArrayList<ArrayList<Tile>> storeTiles) {
        super(storeTiles);
        runner = Runner.GUS;
        opensAt = 12;
        closesAt = 24;

        this.areaType = AreaType.STORE;
        this.tiles = storeTiles;

        for(ArrayList<Tile> row : storeTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }

        for(StarDropSaloonItems item : StarDropSaloonItems.values()) {
            sold.put(item, 0);
        }
    }

    @Override
    public void build() {

    }

    @Override
    public TextureRegion getTexture() {
        return GameAssetManager.StarDropSaloon;
    }

    @Override
    public void resetSoldItems() {
        sold.replaceAll((i, v) -> 0);
    }

    @Override
    public boolean checkAvailable(String productName) {
        if(productName.equalsIgnoreCase("beer") || productName.equalsIgnoreCase("coffee")) {
            return true;
        }
        for(StarDropSaloonItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkAmount(String productName, int amount) {
        if(productName.equalsIgnoreCase("beer") || productName.equalsIgnoreCase("coffee")) {
            return true;
        }
        for(StarDropSaloonItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return amount + sold.get(item) <= item.dailyLimit;
            }
        }
        return false;
    }

    @Override
    public Result sell(Player buyer, String productName, int amount) {
        if(productName.equalsIgnoreCase("beer")) {
            if(amount * 400 > buyer.getGold()) {
                return new Result(false,"not enough gold to buy " + amount + " beer");
            }

            buyer.subtractGold(amount * 400);
            buyer.addToBackPack(new ArtisanItem(ArtisanItemType.BEER), amount);
            return new Result(true,"you've bought " + amount + " beer with price " + amount * 400);
        }
        if(productName.equalsIgnoreCase("coffee")) {
            if(amount * 300 > buyer.getGold()) {
                return new Result(false,"not enough gold to buy " + amount + " coffee");
            }

            buyer.subtractGold(amount * 300);
            buyer.addToBackPack(new ArtisanItem(ArtisanItemType.COFFEE), amount);
            return new Result(true, "you've bought " + amount + " coffee with price " + amount * 300);
        }
        for(StarDropSaloonItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                if(amount * item.price > buyer.getGold()) {
                    return new Result(false,"not enough gold to buy " + amount + " " + item.getName());
                }

                buyer.subtractGold(amount * item.price);
                if(item.getName().contains("recipe")) {
                    buyer.addToAvailableFoods(new Food((FoodType) item.itemType));
                }
                else {
                    buyer.addToBackPack(new Food((FoodType) item.itemType), amount);
                }
                sold.put(item, sold.get(item) + amount);
                return new Result(true,"you've bought " + amount + " " + item.getName() + " with price " + amount * item.price);
            }
        }

        return new Result(false,"");
    }

    @Override
    public String displayItems() {
        StringBuilder display = new StringBuilder();

        display.append("Name    Description    Price\n");
        for(StarDropSaloonItems item : StarDropSaloonItems.values()) {
            display.append(item.getName()).append("\t");
            display.append("\"").append(item.description).append("\"").append("\t");
            display.append(item.price).append("\n");
        }

        return display.toString();
    }

    @Override
    public String displayAvailableItems() {
        StringBuilder display = new StringBuilder();

        display.append("Name    Description    Price\n");
        for(StarDropSaloonItems item : sold.keySet()) {
            if(sold.get(item) < item.dailyLimit) {
                display.append(item.getName()).append("\t");
                display.append("\"").append(item.description).append("\"").append("\t");
                display.append(item.price).append("\n");
            }
        }

        return display.toString();
    }
}

package com.example.common.stores;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.foraging.ForagingSeeds;
import com.example.common.map.AreaType;
import com.example.common.map.Tile;
import com.example.common.time.Season;
import com.example.client.views.GameAssetManager;

import java.util.ArrayList;
import java.util.HashMap;

public class JojaMart extends Store {
    public static int[] coordinates = {54, 57, 16, 19};

    private HashMap<GeneralItemsType, Integer> sold1 = new HashMap<>();
    private HashMap<JojaMartItems, Integer> sold2 = new HashMap<>();

    public JojaMart(ArrayList<ArrayList<Tile>> storeTiles) {
        super(storeTiles);
        this.areaType = AreaType.STORE;

        runner = Runner.MORRIS;
        opensAt = 9;
        closesAt = 23;

        for(ArrayList<Tile> row : storeTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }

        for(int i = 2; i <= 7; i++) {
            sold1.put(GeneralItemsType.values()[i], 0);
        }
        for(JojaMartItems item : JojaMartItems.values()) {
            sold2.put(item, 0);
        }
    }

    @Override
    public void build() {

    }

    @Override
    public TextureRegion getTexture() {
        return GameAssetManager.JojaMart;
    }

    @Override
    public void resetSoldItems() {
        sold1.replaceAll((i, v) -> 0);
        sold2.replaceAll((i, v) -> 0);
    }

    @Override
    public boolean checkAvailable(String productName) {
        Season season = ClientApp.currentGame.getDateAndTime().getSeason();

        for(GeneralItemsType item : sold1.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        for(JojaMartItems item : sold2.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return item.season.equals(Season.ALL) || item.season.equals(season);
            }
        }
        return false;
    }

    @Override
    public boolean checkAmount(String productName, int amount) {
        for(GeneralItemsType item : sold1.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        for(JojaMartItems item : sold2.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return amount + sold2.get(item) <= item.dailyLimit;
            }
        }
        return false;
    }

    @Override
    public Result sell(Player buyer, String productName, int amount) {
        for(GeneralItemsType item : sold1.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                if(amount * item.price > buyer.getGold()) {
                    return new Result(false,"not enough gold to buy " + amount + " " + item.getName());
                }

                buyer.subtractGold(amount * item.price);
                buyer.addToBackPack(new GeneralItem(item), amount);
                sold1.put(item, sold1.get(item) + amount);
                return new Result(true,"you've bought " + amount + " " + item.getName() + " with price " + amount * item.price);
            }
        }
        for(JojaMartItems item : sold2.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                if(amount * item.price > buyer.getGold()) {
                    return new Result(false,"not enough gold to buy " + amount + " " + item.getName());
                }

                buyer.subtractGold(amount * item.price);
                buyer.addToBackPack(new ForagingSeeds(item.foragingSeedsType), amount);
                sold2.put(item, sold2.get(item) + amount);
                return new Result(true,"you've bought " + amount + " " + item.getName() + " with price " + amount * item.price);
            }
        }

        return new Result(false,"");
    }

    @Override
    public String displayItems() {
        StringBuilder display = new StringBuilder();

        display.append("Name    Description    Price\n");
        for(GeneralItemsType item : sold1.keySet()) {
            display.append(item.getName()).append("\t");
            display.append("\"").append(item.description).append("\"").append("\t");
            display.append(item.price).append("\n");
        }
        for(JojaMartItems item : sold2.keySet()) {
            display.append(item.getName()).append("\t");
            display.append("\"").append(item.description).append("\"").append("\t");
            display.append(item.price).append("\n");
        }

        return display.toString();
    }

    @Override
    public String displayAvailableItems() {
        Season season = ClientApp.currentGame.getDateAndTime().getSeason();
        StringBuilder display = new StringBuilder();

        display.append("Name    Description    Price\n");
        for(GeneralItemsType item : sold1.keySet()) {
            display.append(item.getName()).append("\t");
            display.append("\"").append(item.description).append("\"").append("\t");
            display.append(item.price).append("\n");
        }
        for(JojaMartItems item : sold2.keySet()) {
            if(sold2.get(item) < item.dailyLimit && (item.season.equals(Season.ALL) || item.season.equals(season))) {
                display.append(item.getName()).append("\t");
                display.append("\"").append(item.description).append("\"").append("\t");
                display.append(item.price).append("\n");
            }
        }

        return display.toString();
    }
}

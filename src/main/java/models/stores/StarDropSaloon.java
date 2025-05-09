package models.stores;

import models.App;
import models.map.AreaType;
import models.map.Tile;
import models.time.Season;

import java.util.ArrayList;
import java.util.HashMap;

public class StarDropSaloon extends Store {
    public static int[] coordinates = {51, 54, 33, 36};

    private HashMap<StarDropSaloonItems, Integer> sold = new HashMap<>();

    public StarDropSaloon(ArrayList<ArrayList<Tile>> storeTiles) {
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
    public void resetSoldItems() {
        sold.replaceAll((i, v) -> 0);
    }

    @Override
    public boolean checkAvailable(String productName) {
        for(StarDropSaloonItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkAmount(String productName, int amount) {
        for(StarDropSaloonItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return amount + sold.get(item) <= item.dailyLimit;
            }
        }
        return false;
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

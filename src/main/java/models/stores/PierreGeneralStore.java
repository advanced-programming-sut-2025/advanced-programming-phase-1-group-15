package models.stores;

import models.App;
import models.map.AreaType;
import models.map.Tile;
import models.time.Season;

import java.util.ArrayList;
import java.util.HashMap;

public class PierreGeneralStore extends Store {
    public static int[] coordinates = {49, 53, 27, 30};

    private HashMap<PierreGeneralStoreItems, Integer> sold = new HashMap<>();

    public PierreGeneralStore(ArrayList<ArrayList<Tile>> storeTiles) {
        runner = Runner.PIERRE;
        opensAt = 9;
        closesAt = 17;

        this.areaType = AreaType.STORE;
        this.tiles = storeTiles;

        for(ArrayList<Tile> row : storeTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }

        for(PierreGeneralStoreItems item : PierreGeneralStoreItems.values()) {
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
        Season season = App.currentGame.getDateAndTime().getSeason();

        for(PierreGeneralStoreItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return item.season.equals(Season.ALL) || item.season.equals(season);
            }
        }
        return false;
    }

    @Override
    public boolean checkAmount(String productName, int amount) {
        for(PierreGeneralStoreItems item : sold.keySet()) {
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
        for(PierreGeneralStoreItems item : PierreGeneralStoreItems.values()) {
            display.append(item.getName()).append("\t");
            display.append("\"").append(item.description).append("\"").append("\t");
            display.append(item.price).append("\n");
        }

        return display.toString();
    }

    @Override
    public String displayAvailableItems() {
        Season season = App.currentGame.getDateAndTime().getSeason();
        StringBuilder display = new StringBuilder();

        display.append("Name    Description    Price\n");
        for(PierreGeneralStoreItems item : sold.keySet()) {
            if(sold.get(item) < item.dailyLimit && (item.season.equals(Season.ALL) || item.season.equals(season))) {
                display.append(item.getName()).append("\t");
                display.append("\"").append(item.description).append("\"").append("\t");
                display.append(item.price).append("\n");
            }
        }

        return display.toString();
    }
}

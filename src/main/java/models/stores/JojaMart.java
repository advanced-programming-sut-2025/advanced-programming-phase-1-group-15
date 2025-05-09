package models.stores;

import models.App;
import models.foraging.ForagingSeeds;
import models.map.AreaType;
import models.map.Tile;
import models.time.DateAndTime;
import models.time.Season;

import java.util.ArrayList;
import java.util.HashMap;

public class JojaMart extends Store {
    public static int[] coordinates = {54, 57, 16, 19};

    private HashMap<JojaMartItems, Integer> sold = new HashMap<>();

    public JojaMart(ArrayList<ArrayList<Tile>> storeTiles) {
        runner = Runner.MORRIS;
        opensAt = 9;
        closesAt = 23;

        this.areaType = AreaType.STORE;
        this.tiles = storeTiles;

        for(ArrayList<Tile> row : storeTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }

        for(JojaMartItems item : JojaMartItems.values()) {
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

        for(JojaMartItems item : sold.keySet()) {
            if(item.getName().equalsIgnoreCase(productName)) {
                return item.season.equals(Season.ALL) || item.season.equals(season);
            }
        }
        return false;
    }

    @Override
    public boolean checkAmount(String productName) {
        return false;
    }

    @Override
    public String displayItems() {
        StringBuilder display = new StringBuilder();

        display.append("Name    Description    Price\n");
        for(JojaMartItems item : JojaMartItems.values()) {
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
        for(JojaMartItems item : sold.keySet()) {
            if(sold.get(item) < item.dailyLimit && (item.season.equals(Season.ALL) || item.season.equals(season))) {
                display.append(item.getName()).append("\t");
                display.append("\"").append(item.description).append("\"").append("\t");
                display.append(item.price).append("\n");
            }
        }

        return display.toString();
    }
}

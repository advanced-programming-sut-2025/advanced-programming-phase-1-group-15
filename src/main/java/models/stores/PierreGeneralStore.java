package models.stores;

import models.map.AreaType;
import models.map.Tile;
import models.time.DateAndTime;
import models.tools.BackPackable;

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
}

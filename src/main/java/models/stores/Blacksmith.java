package models.stores;

import models.map.AreaType;
import models.map.Tile;
import models.time.DateAndTime;
import models.tools.Tool;

import java.util.ArrayList;

public class Blacksmith extends Store {
    public static int[] coordinates = {47, 51, 40, 43};

    private ArrayList<Tool> products = new ArrayList<>();

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
    }

    @Override
    public void build() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

package models.stores;

import models.map.AreaType;
import models.map.Tile;
import models.time.DateAndTime;

import java.util.ArrayList;

public class StarDropSaloon extends Store {
    public static int[] coordinates = {51, 54, 33, 36};
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
    }

    @Override
    public void build() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

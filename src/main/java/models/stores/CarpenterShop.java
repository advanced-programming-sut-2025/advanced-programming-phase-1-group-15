package models.stores;

import models.animals.Barn;
import models.map.AreaType;
import models.map.Tile;
import models.time.DateAndTime;

import java.util.ArrayList;

public class CarpenterShop extends Store {
    public static int[] coordinates = {51, 55, 6, 9};
    private ArrayList<Barn> barns = new ArrayList<>();

    public CarpenterShop(ArrayList<ArrayList<Tile>> storeTiles) {
        runner = Runner.ROBIN;
        opensAt = 9;
        closesAt = 20;

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

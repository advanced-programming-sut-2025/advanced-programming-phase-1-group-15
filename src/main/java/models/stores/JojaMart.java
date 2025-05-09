package models.stores;

import models.foraging.ForagingSeeds;
import models.map.AreaType;
import models.map.Tile;
import models.time.DateAndTime;

import java.util.ArrayList;

public class JojaMart extends Store {
    public static int[] coordinates = {54, 57, 16, 19};
    private ArrayList<ForagingSeeds> foragingSeeds = new ArrayList<>();

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
    }

    @Override
    public void build() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

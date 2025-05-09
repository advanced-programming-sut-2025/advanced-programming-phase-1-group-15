package models.stores;

import models.map.AreaType;
import models.map.Tile;
import models.time.DateAndTime;
import models.tools.BackPackable;

import java.util.ArrayList;

public class PierreGeneralStore extends Store {
    public static int[] coordinates = {49, 53, 27, 30};
    private ArrayList<BackPackable> items = new ArrayList<>();


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
    }

    @Override
    public void build() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

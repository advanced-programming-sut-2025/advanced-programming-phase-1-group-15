package models.stores;

import models.animals.Animal;
import models.map.AreaType;
import models.map.Tile;
import models.time.DateAndTime;

import java.util.ArrayList;

public class MarnieRanch extends Store {
    public static int[] coordinates = {46, 50, 21, 25};
    private ArrayList<Animal> animals = new ArrayList<>();


    public MarnieRanch(ArrayList<ArrayList<Tile>> storeTiles) {
        runner = Runner.MARNIE;
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

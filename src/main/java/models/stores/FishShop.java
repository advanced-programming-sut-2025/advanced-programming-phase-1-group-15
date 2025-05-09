package models.stores;

import models.animals.Fish;
import models.map.AreaType;
import models.map.Tile;
import models.time.DateAndTime;
import models.tools.FishingPole;

import java.util.ArrayList;

public class FishShop extends Store {
    public static int[] coordinates = {48, 51, 12, 15};

    private ArrayList<FishingPole> fishingPoles = new ArrayList<>();
    private ArrayList<Fish> fishes = new ArrayList<>();

    public FishShop(ArrayList<ArrayList<Tile>> storeTiles) {
        runner = Runner.WILLIE;
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

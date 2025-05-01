package models.map;

import models.App;
import models.Player;
import models.time.DateAndTime;

import java.util.ArrayList;
import java.util.Random;

public class Lake extends Area {
    public static int[][] coordinates = {
            {2, 13, 14, 19},   //MAP 1
            {25, 32, 1, 9}, //MAP 2
            {10, 15, 13, 18}, //MAP 3
            {5, 15, 10, 15}   //MAP 4
    };

    public Lake(ArrayList<ArrayList<Tile>> lakeTiles) {
        this.areaType = AreaType.LAKE;
        this.tiles = lakeTiles;

        for(ArrayList<Tile> row : lakeTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }

    public void build() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

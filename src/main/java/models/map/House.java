package models.map;

import models.time.DateAndTime;
import models.tools.BackPackable;

import java.util.ArrayList;

public class House extends Area {
    public static int[][] coordinates = {
            {3, 7, 5, 9},   //MAP 1
            {2, 6, 12, 16}, //MAP 2
            {5, 9, 3, 7}, //MAP 3
            {25, 29, 0, 4}   //MAP 4
    };
    public static int[][] playerCoordinates = {
            {5, 6},   //MAP 1
            {65, 14}, //MAP 2
            {67, 35}, //MAP 3
            {88, 33}   //MAP 4
    };
    public static int[][] fridgeCoordinates = {
            {7, 5},   //MAP 1
            {62, 13}, //MAP 2
            {69, 36}, //MAP 3
            {89, 30}   //MAP 4
    };

    public House(ArrayList<ArrayList<Tile>> houseTiles) {
        this.areaType = AreaType.HOUSE;
        this.tiles = houseTiles;

        for(ArrayList<Tile> row : houseTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }

    public void build() {
        for(ArrayList<Tile> row : tiles) {
            for(Tile tile : row) {
                if(owner != null && tile.getPosition().x == fridgeCoordinates[owner.getMapNumber() - 1][0] && tile.getPosition().y == fridgeCoordinates[owner.getMapNumber() - 1][1]) {
                    tile.put(owner.fridge());
                }
            }
        }
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

package models.map;

import models.cooking.Fridgable;

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

    private ArrayList<Fridgable> fridge = new ArrayList<>();

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

    }
}

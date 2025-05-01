package models.map;

import models.time.DateAndTime;

import java.util.ArrayList;

public class Quarry extends Area {
    public static int[][] coordinates = {
            {31, 37, 3, 7},   //MAP 1
            {12, 16, 16, 20}, //MAP 2
            {35, 39, 0, 5}, //MAP 3
            {27, 31, 13, 18}   //MAP 4
    };

    public Quarry(ArrayList<ArrayList<Tile>> quarryTiles) {
        this.areaType = AreaType.QUARRY;
        this.tiles = quarryTiles;

        for(ArrayList<Tile> row : quarryTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }

    public void build() {

    }
    public void repair() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

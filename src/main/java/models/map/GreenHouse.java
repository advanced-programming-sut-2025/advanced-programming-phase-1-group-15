package models.map;

import models.time.DateAndTime;

import java.util.ArrayList;

public class GreenHouse extends Area {
    public static int[][] coordinates = {
            {23, 28, 9, 15},   //MAP 1
            {15, 21, 7, 12}, //MAP 2
            {20, 26, 10, 15}, //MAP 3
            {33, 39, 15, 20}   //MAP 4
    };

    private boolean built = false;
    private Position waterBox;

    public boolean isBuilt() {
        return built;
    }
    public void buildGreenHouse() {
        built = true;
    }

    public GreenHouse(ArrayList<ArrayList<Tile>> greenhouseTiles) {
        this.areaType = AreaType.GREENHOUSE;
        this.tiles = greenhouseTiles;

        for(ArrayList<Tile> row : greenhouseTiles) {
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

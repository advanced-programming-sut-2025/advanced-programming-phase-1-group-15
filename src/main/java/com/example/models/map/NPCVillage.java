package com.example.models.map;

import com.example.models.time.DateAndTime;

import java.util.ArrayList;

public class NPCVillage extends Area {
    public static int[][] coordinates = {{5, 20, 22, 26},
        {71, 90, 24, 28}};

    public NPCVillage(ArrayList<ArrayList<Tile>> farmTiles) {
        this.areaType = AreaType.VILLAGE;
        this.tiles = farmTiles;

        for(ArrayList<Tile> row : farmTiles) {
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

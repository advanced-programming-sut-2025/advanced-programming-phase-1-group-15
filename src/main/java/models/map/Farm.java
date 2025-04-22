package models.map;

import models.foraging.Stone;
import models.farming.Tree;

import java.util.ArrayList;

public class Farm extends Area {
    public static int nextId =0 ;
    private int Id;

    public Farm(ArrayList<ArrayList<Tile>> FarmTiles , int firstCol, int lastCol, int firstRow, int lastRow) {
        this.Id = nextId;
        nextId ++;
        this.tiles = tiles;
        for(int row = firstRow; row <= lastRow; row++) {
            for(int col = firstCol; col <= lastCol; col++) {
                Tile tile = FarmTiles.get(col).get(row);
                tile.setArea(this);
            }
        }

    }



    public void build() {
        innerAreas = new ArrayList<>();
        innerAreas.add(new Lake());
        innerAreas.add(new GreenHouse());
        innerAreas.add(new House());
        innerAreas.add(new Quarry());
    }
}

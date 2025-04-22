package models.map;

import java.util.ArrayList;

public abstract class Area {
    protected ArrayList<ArrayList<Tile>> tiles;

    protected ArrayList<Area> innerAreas = new ArrayList<>();

    public Area(ArrayList<ArrayList<Tile>> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<ArrayList<Tile>> getSubArea(ArrayList<ArrayList<Tile>> mapTiles,int firstCol, int lastCol ,int firstRow, int lastRow){
        ArrayList<ArrayList<Tile>> subArea = new ArrayList<>();
        for(int row = firstRow; row <= lastRow; row++){
            subArea.add((ArrayList)mapTiles.subList(firstCol,lastCol));
        }
        return subArea;
    }

    public Area(){}

    //public abstract void build();
}

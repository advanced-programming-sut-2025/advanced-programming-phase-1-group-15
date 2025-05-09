package models.map;

import models.Player;
import models.time.TimeObserver;

import java.util.ArrayList;

public abstract class Area implements TimeObserver {
    protected AreaType areaType;


    protected ArrayList<ArrayList<Tile>> tiles;

    protected ArrayList<Area> innerAreas;
    protected Area parentArea;

    protected Player owner;
    protected int number;

    public Player getOwner() {
        if(parentArea == null) return null;

        if(owner == null) {
            return parentArea.getOwner();
        }
        return owner;
    }

    public void setParentArea(Area parentArea) {
        this.parentArea = parentArea;
    }

    public Area() {

    }
    public Area(ArrayList<ArrayList<Tile>> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<ArrayList<Tile>> getSubArea(ArrayList<ArrayList<Tile>> mapTiles, int firstCol, int lastCol, int firstRow, int lastRow) {
        ArrayList<ArrayList<Tile>> subArea = new ArrayList<>();

        for (int row = firstRow; row < lastRow; row++) {
            ArrayList<Tile> newRow = new ArrayList<>(mapTiles.get(row).subList(firstCol, lastCol));
            subArea.add(newRow);
        }

        return subArea;
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public abstract void build();
}

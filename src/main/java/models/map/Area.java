package models.map;

import models.Player;

import java.util.ArrayList;

public abstract class Area {
    protected AreaType areaType;
    protected ArrayList<ArrayList<Tile>> tiles;

    protected ArrayList<Area> innerAreas;
    protected Area parentArea;

    protected Player owner = null;

    public Player getOwner() {
        if(this instanceof Map) return null;

        if(this.owner == null) {
            return parentArea.getOwner();
        }
        return this.owner;
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


    public abstract void build();

    public AreaType getAreaType() {
        return areaType;
    }
}

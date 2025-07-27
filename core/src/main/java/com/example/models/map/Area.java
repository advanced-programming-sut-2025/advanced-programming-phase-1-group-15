package com.example.models.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.Player;
import com.example.models.time.TimeObserver;

import java.util.ArrayList;

public abstract class
Area implements TimeObserver {
    protected AreaType areaType;

    private int firstCol;
    private int firstRow;
    private int lastCol;
    private int lastRow;

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
        this.firstCol = tiles.get(0).get(0).getPosition().x;
        this.firstRow = tiles.get(0).get(0).getPosition().y;
        this.lastCol = tiles.get(tiles.size() - 1).get(tiles.get(tiles.size() - 1).size() - 1).getPosition().x;
        this.lastRow = tiles.get(tiles.size() - 1).get(tiles.get(tiles.size() - 1).size() - 1).getPosition().y;
    }

    public int getHeight() {
        return lastRow - firstRow + 1;
    }
    public int getWidth() {
        return lastCol - firstCol + 1;
    }
    public Position getBottomLeftCorner() {
        return new Position(firstCol, firstRow);
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

    abstract public TextureRegion getTexture();

    public Tile getTile(int row, int col) {
        return tiles.get(row).get(col);
    }

    public Tile getTile(Position pos) {
        return tiles.get(pos.getY()).get(pos.getX());
    }

    public ArrayList<Area> getInnerAreas() {
        return innerAreas;
    }
}

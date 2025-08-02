package com.example.common.animals;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.common.RandomGenerator;
import com.example.common.map.Area;
import com.example.common.map.AreaType;
import com.example.common.map.Tile;
import com.example.common.time.DateAndTime;
import com.example.client.views.GameAssetManager;

import java.util.ArrayList;

public class Barn extends Area implements Building {
    public static int ROWS = 10;
    public static int COLS = 12;

    private int animalCount = 0;
    private int capacity = 4;
    private boolean Big = false;
    private boolean Deluxe = false;

    public Barn(ArrayList<ArrayList<Tile>> barnTiles) {
        super(barnTiles);
        this.areaType = AreaType.BARN;

        for(ArrayList<Tile> row : barnTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isBig() {
        return Big;
    }
    public void setBig() {
        Big = true;
        this.capacity = 8;
    }

    public boolean isDeluxe() {
        return Deluxe;
    }
    public void setDeluxe() {
        Deluxe = true;
        this.capacity = 12;
    }

    @Override
    public void build() {

    }

    @Override
    public TextureRegion getTexture() {
        return GameAssetManager.barn;
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }

    public boolean place(Animal animal) {
        boolean filled = true;
        for(ArrayList<Tile> row : tiles) {
            for(Tile tile : row) {
                if(tile.isEmpty()) {
                    filled = false;
                    break;
                }
            }
        }
        if(filled) {
            return false;
        }

        while (true) {
            int randomRow = RandomGenerator.getInstance().randomInt(0, tiles.size() - 1);
            int randomCol = RandomGenerator.getInstance().randomInt(0, tiles.get(randomRow).size() - 1);
            Tile randomTile = tiles.get(randomRow).get(randomCol);

            if (randomTile.isEmpty()) {
                animal.setTile(randomTile);
                animal.setBuilding(this);
                return true;
            }
        }
    }
}

package com.example.models.animals;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.RandomGenerator;
import com.example.models.foraging.ForagingMineral;
import com.example.models.foraging.ForagingMineralType;
import com.example.models.map.Area;
import com.example.models.map.AreaType;
import com.example.models.map.Tile;
import com.example.models.time.DateAndTime;
import com.example.views.GameAssetManager;

import java.util.ArrayList;

public class Barn extends Area {
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
        while (true) {
            int randomRow = RandomGenerator.getInstance().randomInt(0, tiles.size() - 1);
            int randomCol = RandomGenerator.getInstance().randomInt(0, tiles.get(randomRow).size() - 1);
            Tile randomTile = tiles.get(randomRow).get(randomCol);

            if (randomTile.isEmpty()) {
                randomTile.put(animal);
                animal.setPosition(randomTile.getPosition());
                return true;
            }
        }
    }
}

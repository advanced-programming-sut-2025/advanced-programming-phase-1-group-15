package com.example.models.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.time.DateAndTime;
import com.example.models.tools.Fridge;
import com.example.views.GameAssetManager;

import java.util.ArrayList;

public class House extends Area {
    public static int[][] coordinates = {
            {30, 45, 70, 85},   //MAP 1
            {10, 25, 25, 40}, //MAP 2
            {25, 40, 35, 50}, //MAP 3
            {45, 60, 45, 60}   //MAP 4
    };
    public static int[][] playerCoordinates = {
            {40, 69},   //MAP 1
            {195, 24}, //MAP 2
            {30, 214}, //MAP 3
            {224, 230}   //MAP 4
    };
    public static int[][] fridgeCoordinates = {
            {3, 5},   //MAP 1
            {62, 13}, //MAP 2
            {68, 36}, //MAP 3
            {25, 33}   //MAP 4
    };

    private Fridge fridge;

    public House(ArrayList<ArrayList<Tile>> houseTiles) {
        super(houseTiles);
        this.areaType = AreaType.HOUSE;

        for(ArrayList<Tile> row : houseTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }

    public void build() {
        for(ArrayList<Tile> row : tiles) {
            for(Tile tile : row) {
                if(tile.getPosition().x == House.fridgeCoordinates[number - 1][0] && tile.getPosition().y == House.fridgeCoordinates[number - 1][1]) {
                    fridge = new Fridge();
                    tile.put(fridge);
                }
            }
        }
    }

    @Override
    public TextureRegion getTexture() {
        return GameAssetManager.house;
    }

    public Fridge getFridge() {
        return fridge;
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

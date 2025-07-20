package com.example.models.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.time.DateAndTime;
import com.example.views.GameAssetManager;

import java.util.ArrayList;

public class GreenHouse extends Area {
    public static int[][] coordinates = {
            {1, 12, 75, 85},   //MAP 1
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
        super(greenhouseTiles);
        this.areaType = AreaType.GREENHOUSE;

        for(ArrayList<Tile> row : greenhouseTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }

    public void build() {

    }

    @Override
    public TextureRegion getTexture() {
        if(built) {
            return GameAssetManager.greenhouse;
        }
        return GameAssetManager.broken_greenhouse;
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

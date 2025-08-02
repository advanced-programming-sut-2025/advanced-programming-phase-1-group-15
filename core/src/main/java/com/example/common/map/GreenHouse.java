package com.example.common.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.common.time.DateAndTime;
import com.example.client.views.GameAssetManager;

import java.util.ArrayList;

public class GreenHouse extends Area {
    public static int[][] coordinates = {
            {1, 12, 75, 85},   //MAP 1
            {76, 87, 2, 12}, //MAP 2
            {2, 13, 76, 86}, //MAP 3
            {70, 81, 70, 80}   //MAP 4
    };

    private boolean built = false;

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

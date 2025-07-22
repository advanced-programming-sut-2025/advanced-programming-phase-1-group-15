package com.example.models.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.time.DateAndTime;

import java.util.ArrayList;

public class Quarry extends Area {
    public static int[][] coordinates = {
            {65, 80, 3, 18},   //MAP 1
            {65, 80, 65, 80}, //MAP 2
            {65, 80, 10, 25}, //MAP 3
            {10, 25, 65, 80}   //MAP 4
    };

    public Quarry(ArrayList<ArrayList<Tile>> quarryTiles) {
        super(quarryTiles);
        this.areaType = AreaType.QUARRY;

        for(ArrayList<Tile> row : quarryTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }

    public void build() {

    }

    @Override
    public TextureRegion getTexture() {
        return null;
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

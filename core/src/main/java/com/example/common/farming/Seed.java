package com.example.common.farming;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.map.Tilable;
import com.example.common.tools.BackPackable;

public class Seed implements Tilable, BackPackable {
    SeedType seedType;

    public Seed(SeedType seedType) {
        this.seedType = seedType;
    }

    @Override
    public String getName() {
        return seedType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public Sprite getSprite(){
        return null;
    }
}

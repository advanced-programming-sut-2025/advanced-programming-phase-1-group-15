package com.example.models.foraging;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.views.GameAssetManager;

public class Stone extends ForagingMineral {
    public Stone() {

    }

    @Override
    public String getName() {
        return "stone";
    }

    @Override
    public String getDescription() {
        return "A common material with many uses in crafting and building.";
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public Sprite getSprite() {
        return GameAssetManager.stone;
    }
}

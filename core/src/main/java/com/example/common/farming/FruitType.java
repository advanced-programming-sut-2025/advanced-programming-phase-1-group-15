package com.example.common.farming;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.tools.BackPackable;

public enum FruitType implements BackPackable {
    APRICOT,
    CHERRY,
    BANANA,
    MANGO,
    ORANGE,
    PEACH,
    APPLE,
    POMEGRANATE,
    OAK_RESIN,
    MAPLE_SYRUP,
    PINE_TAR,
    SAP,
    COMMON_MUSHROOM,
    MYSTIC_SYRUP;

    @Override
    public String getName() {
        return this.name().toLowerCase().replaceAll("_", " ");
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

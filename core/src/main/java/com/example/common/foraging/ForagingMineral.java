package com.example.common.foraging;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.map.Tilable;
import com.example.common.tools.BackPackable;

public class ForagingMineral implements Tilable, BackPackable {
    ForagingMineralType foragingMineralType;
    public ForagingMineral(ForagingMineralType foragingMineralType) {
        this.foragingMineralType = foragingMineralType;
    }
    public ForagingMineral() {

    }

    @Override
    public String getName() {
        return foragingMineralType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return foragingMineralType.description;
    }

    @Override
    public int getPrice() {
        return foragingMineralType.sellPrice;
    }

    @Override
    public Sprite getSprite() {
        return foragingMineralType.sprite;
    }

    public ForagingMineralType getForagingMineralType() {
        return foragingMineralType;
    }
}

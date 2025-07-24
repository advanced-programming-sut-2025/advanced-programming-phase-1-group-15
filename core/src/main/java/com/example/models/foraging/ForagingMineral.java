package com.example.models.foraging;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.map.Tilable;
import com.example.models.tools.BackPackable;

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
}

package com.example.common.stores;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.tools.BackPackable;
import com.example.client.views.GameAssetManager;

public class GeneralItem implements BackPackable {
    GeneralItemsType generalItemsType;
    public GeneralItem(GeneralItemsType generalItemsType) {
        this.generalItemsType = generalItemsType;
    }

    @Override
    public String getName() {
        return generalItemsType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return generalItemsType.description;
    }

    @Override
    public int getPrice() {
        return generalItemsType.price;
    }

    @Override
    public Sprite getSprite(){
        switch (generalItemsType) {
            case WOOD -> {
                return GameAssetManager.wood;
            }
            default -> {
                return null;
            }
        }
    }
}

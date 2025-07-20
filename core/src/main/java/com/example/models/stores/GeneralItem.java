package com.example.models.stores;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.tools.BackPackable;
import com.example.views.GameAssetManager;

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

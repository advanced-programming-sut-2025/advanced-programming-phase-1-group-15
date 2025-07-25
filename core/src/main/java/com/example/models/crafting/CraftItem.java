package com.example.models.crafting;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.map.Tilable;
import com.example.models.tools.BackPackable;

public class CraftItem implements Tilable, BackPackable {
    private final CraftItemType craftItemType;
    private final Sprite sprite;
    private boolean available = false;
    public CraftItem(CraftItemType craftItemType) {
        this.craftItemType = craftItemType;
        this.sprite = new Sprite(craftItemType.texture);
    }

    public CraftItemType getCraftItemType() {
        return craftItemType;
    }

    @Override
    public String getName() {
        return craftItemType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getPrice() {
        return craftItemType.price;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

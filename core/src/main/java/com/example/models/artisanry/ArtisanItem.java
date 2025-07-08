package com.example.models.artisanry;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.tools.BackPackable;

public class ArtisanItem implements BackPackable {
    private ArtisanItemType artisanItemType;
    private int hour;
    private int day;
    public ArtisanItem(ArtisanItemType artisanItemType) {
        this.artisanItemType = artisanItemType;
    }
    @Override
    public String getName() {
        return artisanItemType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return artisanItemType.description;
    }

    @Override
    public int getPrice() {
        return artisanItemType.sellPrice;
    }

    public ArtisanItemType getArtisanItemType() {
        return artisanItemType;
    }

    public int getHour() {
        return hour;
    }
    public int getDay() {
        return day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
    public void setDay(int day) {
        this.day = day;
    }

    public Sprite getSprite() {
        return null;
    }
}

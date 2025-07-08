package com.example.models.foraging;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.map.Tilable;
import com.example.models.time.DateAndTime;
import com.example.models.time.TimeObserver;
import com.example.models.tools.BackPackable;

public class ForagingCrop implements Tilable, BackPackable, TimeObserver {
    private ForagingCropsType foragingCropsType;
    public ForagingCrop(ForagingCropsType foragingCropsType) {
        this.foragingCropsType = foragingCropsType;
    }
    public ForagingCropsType getForagingCropsType() {
        return foragingCropsType;
    }
    public int getMoney(){
        return this.foragingCropsType.basePrice;
    }
    public void setForagingCropsType(ForagingCropsType foragingCropsType) {
        this.foragingCropsType = foragingCropsType;
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }

    @Override
    public String getName() {
        return foragingCropsType.name().toLowerCase().replace("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getPrice() {
        return foragingCropsType.basePrice;
    }

    @Override
    public Sprite getSprite() {
        return null;
    }
}

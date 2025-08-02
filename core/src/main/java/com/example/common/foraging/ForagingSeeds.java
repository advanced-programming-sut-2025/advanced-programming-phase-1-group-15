package com.example.common.foraging;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.map.Tilable;
import com.example.common.time.DateAndTime;
import com.example.common.time.TimeObserver;
import com.example.common.tools.BackPackable;

public class ForagingSeeds implements Tilable, BackPackable, TimeObserver {
    ForagingSeedsType foragingSeedsType;
    boolean fertilized;
    int notWateredDays;

    public ForagingSeeds(ForagingSeedsType foragingSeedsType) {
        this.foragingSeedsType = foragingSeedsType;
    }

    public void water() {

    }
    public void fertilize() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }

    @Override
    public String getName() {
        return foragingSeedsType.name().toLowerCase().replaceAll("_", " ");
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
    public Sprite getSprite() {
        return null;
    }
}

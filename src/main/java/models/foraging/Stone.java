package models.foraging;

import models.map.Tilable;

public class Stone extends ForagingMineral {
    public Stone() {

    }

    @Override
    public String getName() {
        return "stone";
    }

    @Override
    public String getDescription() {
        return "just stone!";
    }

    @Override
    public int getPrice() {
        return 0;
    }
}

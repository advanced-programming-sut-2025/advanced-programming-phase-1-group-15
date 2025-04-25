package models.foraging;

import models.map.Tilable;

public class Stone extends ForagingMineral implements Tilable {
    @Override
    public String getName() {
        return "stone";
    }

    @Override
    public String getDescription() {
        return "just stone!";
    }
}

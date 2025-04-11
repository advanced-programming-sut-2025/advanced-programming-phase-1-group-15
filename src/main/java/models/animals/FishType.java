package models.animals;

import models.time.Season;

public enum FishType {
    SALMON(Season.AUTUMN, 800);

    public final Season season;
    public final int basePrice;

    FishType(Season season, int basePrice) {
        this.season = season;
        this.basePrice = basePrice;
    }
}

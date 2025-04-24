package models.animals;

import models.time.Season;

public enum FishType {
    SALMON(Season.AUTUMN, 75),
    SARDINE(Season.AUTUMN, 40),
    SHAD(Season.AUTUMN, 60),
    BLUE_DISCUS(Season.AUTUMN, 120),
    MIDNIGHT_CARP(Season.WINTER, 150),
    SQUID(Season.WINTER, 80),
    TUNA(Season.WINTER, 100),
    PERCH(Season.WINTER, 55),
    FLOUNDER(Season.SPRING, 100),
    LIONFISH(Season.SPRING, 100),
    HERRING(Season.SPRING, 30),
    GHOST_FISH(Season.SPRING, 45),
    TILAPIA(Season.SUMMER, 75),
    DORADO(Season.SUMMER, 100),
    SUNFISH(Season.SUMMER, 30),
    RAINBOW_TROUT(Season.SUMMER, 65),
    LEGEND(Season.SPRING, 5000),
    GLACIER_FISH(Season.WINTER, 1000),
    ANGLER(Season.AUTUMN, 900),
    CRIMSON_FISH(Season.SUMMER, 1500);
    public final Season season;
    public final int basePrice;

    FishType(Season season, int basePrice) {
        this.season = season;
        this.basePrice = basePrice;
    }
}

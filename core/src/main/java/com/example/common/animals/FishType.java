package com.example.common.animals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.time.Season;

public enum FishType {
    SALMON(Season.AUTUMN, 75, new Sprite(new Texture("Sprites/Fish/salmon.png"))),
    SARDINE(Season.AUTUMN, 40, new Sprite(new Texture("Sprites/Fish/sardine.png"))),
    SHAD(Season.AUTUMN, 60, new Sprite(new Texture("Sprites/Fish/shad.png"))),
    BLUE_DISCUS(Season.AUTUMN, 120, new Sprite(new Texture("Sprites/Fish/blue_discus.png"))),
    MIDNIGHT_CARP(Season.WINTER, 150, new Sprite(new Texture("Sprites/Fish/midnight_carp.png"))),
    SQUID(Season.WINTER, 80, new Sprite(new Texture("Sprites/Fish/squid.png"))),
    TUNA(Season.WINTER, 100, new Sprite(new Texture("Sprites/Fish/tuna.png"))),
    PERCH(Season.WINTER, 55, new Sprite(new Texture("Sprites/Fish/perch.png"))),
    FLOUNDER(Season.SPRING, 100, new Sprite(new Texture("Sprites/Fish/flounder.png"))),
    LIONFISH(Season.SPRING, 100,  new Sprite(new Texture("Sprites/Fish/lionfish.png"))),
    HERRING(Season.SPRING, 30, new Sprite(new Texture("Sprites/Fish/herring.png"))),
    GHOST_FISH(Season.SPRING, 45, new Sprite(new Texture("Sprites/Fish/ghost_fish.png"))),
    TILAPIA(Season.SUMMER, 75, new Sprite(new Texture("Sprites/Fish/tilapia.png"))),
    DORADO(Season.SUMMER, 100, new Sprite(new Texture("Sprites/Fish/dorado.png"))),
    SUNFISH(Season.SUMMER, 30, new Sprite(new Texture("Sprites/Fish/sunfish.png"))),
    RAINBOW_TROUT(Season.SUMMER, 65, new Sprite(new Texture("Sprites/Fish/rainbow_trout.png"))),
    LEGEND(Season.SPRING, 5000, new Sprite(new Texture("Sprites/Fish/legend.png"))),
    GLACIER_FISH(Season.WINTER, 1000, new Sprite(new Texture("Sprites/Fish/glacier_fish.png"))),
    ANGLER(Season.AUTUMN, 900, new Sprite(new Texture("Sprites/Fish/angler.png"))),
    CRIMSON_FISH(Season.SUMMER, 1500, new Sprite(new Texture("Sprites/Fish/crimson_fish.png"))),;
    public final Season season;
    public final int basePrice;
    public final Sprite sprite;

    FishType(Season season, int basePrice, Sprite sprite) {
        this.season = season;
        this.basePrice = basePrice;
        this.sprite = sprite;
    }
    public String getName(){
        return this.name().toLowerCase().replaceAll("_", " ");
    }
}

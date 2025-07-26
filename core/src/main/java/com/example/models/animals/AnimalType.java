package com.example.models.animals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.views.GameAssetManager;

public enum AnimalType {
    CHICKEN(Maintenance.COOP, 800, 1,
        GameAssetManager.chicken_walking_up,
        GameAssetManager.chicken_walking_down,
        GameAssetManager.chicken_walking_right,
        GameAssetManager.chicken_walking_left,
        GameAssetManager.chicken_eating),
    DUCK(Maintenance.COOP, 1200, 2,
        GameAssetManager.duck_walking_up,
        GameAssetManager.duck_walking_down,
        GameAssetManager.duck_walking_right,
        GameAssetManager.duck_walking_left,
        GameAssetManager.duck_eating),
    RABBIT(Maintenance.COOP, 8000, 4,
        GameAssetManager.rabbit_walking_up,
        GameAssetManager.rabbit_walking_down,
        GameAssetManager.rabbit_walking_right,
        GameAssetManager.rabbit_walking_left,
        GameAssetManager.rabbit_eating),
    DINOSAUR(Maintenance.COOP, 14000, 7,
        GameAssetManager.dinosaur_walking_up,
        GameAssetManager.dinosaur_walking_down,
        GameAssetManager.dinosaur_walking_right,
        GameAssetManager.dinosaur_walking_left,
        GameAssetManager.dinosaur_eating),
    COW(Maintenance.BARN, 1500, 1,
        GameAssetManager.cow_walking_up,
        GameAssetManager.cow_walking_down,
        GameAssetManager.cow_walking_right,
        GameAssetManager.cow_walking_left,
        GameAssetManager.cow_eating),
    GOAT(Maintenance.BARN, 4000, 2,
        GameAssetManager.goat_walking_up,
        GameAssetManager.goat_walking_down,
        GameAssetManager.goat_walking_right,
        GameAssetManager.goat_walking_left,
        GameAssetManager.goat_eating),
    SHEEP(Maintenance.BARN, 8000, 3,
        GameAssetManager.sheep_walking_up,
        GameAssetManager.sheep_walking_down,
        GameAssetManager.sheep_walking_right,
        GameAssetManager.sheep_walking_left,
        GameAssetManager.sheep_eating),
    PIG(Maintenance.BARN, 16000, 1,
        GameAssetManager.pig_walking_up,
        GameAssetManager.pig_walking_down,
        GameAssetManager.pig_walking_right,
        GameAssetManager.pig_walking_left,
        GameAssetManager.pig_eating),;

    public final Maintenance maintenance;
    public final int price;
    public final int period;
    public final Animation<TextureRegion> walking_up;
    public final Animation<TextureRegion> walking_down;
    public final Animation<TextureRegion> walking_right;
    public final Animation<TextureRegion> walking_left;
    public final Animation<TextureRegion> eating;

    AnimalType(Maintenance maintenance,
                int price,
                int period,
                Animation<TextureRegion> walking_up,
                Animation<TextureRegion> walking_down,
                Animation<TextureRegion> walking_right,
                Animation<TextureRegion> walking_left,
                Animation<TextureRegion> eating) {
        this.maintenance = maintenance;
        this.price = price;
        this.period = period;
        this.walking_up = walking_up;
        this.walking_down = walking_down;
        this.walking_right = walking_right;
        this.walking_left = walking_left;
        this.eating = eating;
    }
}

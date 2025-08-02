package com.example.common.animals;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.client.views.GameAssetManager;

public enum AnimalProductType{
    EGG(50, GameAssetManager.egg),
    LARGE_EGG(95, GameAssetManager.large_egg),
    DUCK_EGG(95, GameAssetManager.duck_egg),
    DUCK_FEATHER(250, GameAssetManager.duck_feather),
    RABBIT_WOOL(340, GameAssetManager.wool),
    RABBIT_LEG(565, GameAssetManager.rabbit_leg),
    DINOSAUR_EGG(350, GameAssetManager.dinosaur_egg),
    COW_MILK(125, GameAssetManager.cow_milk),
    COW_LARGE_MILK(190, GameAssetManager.cow_large_milk),
    GOAT_MILK(225, GameAssetManager.goat_milk),
    GOAT_LARGE_MILK(345, GameAssetManager.goat_large_milk),
    SHEEP_WOOL(340, GameAssetManager.wool),
    TRUFFLE(625, GameAssetManager.truffle);
    public final int price;
    public final Sprite sprite;

    AnimalProductType(int price, Sprite sprite) {
        this.price = price;
        this.sprite = sprite;
    }
    public String getName(){
        return this.name().toLowerCase().replaceAll("_", " ");
    }
}

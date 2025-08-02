package com.example.common.cooking;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.tools.BackPackable;

public class Food implements BackPackable {
    private final FoodType foodType;
    private final Sprite sprite;
    private boolean available = false;
    public Food(FoodType foodType) {
        this.foodType = foodType;
        this.sprite = new Sprite(foodType.texture);
    }

    public FoodType getFoodType() {
        return foodType;
    }

    @Override
    public String getName() {
        return foodType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }

    public String getRecipe() {
        return foodType.recipe;
    }

    @Override
    public int getPrice() {
        return foodType.price;
    }

    public int getEnergy() {
        return foodType.energy;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

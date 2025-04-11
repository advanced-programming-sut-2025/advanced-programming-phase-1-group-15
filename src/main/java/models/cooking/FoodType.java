package models.cooking;

import models.tools.BackPackable;

import java.util.HashMap;

public enum FoodType {
    FRIED_EGG("1 egg = 1 fried egg",
            new HashMap<>(),
            50,
            false,
            3);

    public String recipe;
    public HashMap<BackPackable, Integer> ingredients;
    public int energy;
    public boolean buff;
    public int price;

    FoodType(String recipe, HashMap<BackPackable, Integer> ingredients, int energy, boolean buff, int price) {
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.energy = energy;
        this.buff = buff;
        this.price = price;
    }
}

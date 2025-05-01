package models.cooking;

import models.tools.BackPackable;

public class Food implements BackPackable {
    private FoodType foodType;
    public Food(FoodType foodType) {
        this.foodType = foodType;
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
}

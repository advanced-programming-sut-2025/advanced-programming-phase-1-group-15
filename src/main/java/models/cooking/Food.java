package models.cooking;

import models.tools.BackPackable;

public class Food implements Fridgable, BackPackable {
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

    @Override
    public int getPrice() {
        return foodType.price;
    }
}

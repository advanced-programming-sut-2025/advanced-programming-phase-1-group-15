package models.cooking;

import models.tools.BackPackable;

public class Food implements Fridgable, BackPackable {
    private FoodType foodType;

    @Override
    public String getName() {
        return foodType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }
}

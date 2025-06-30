package com.example.models.stores;

import com.example.models.cooking.FoodType;
import com.example.models.artisanry.ArtisanItemType;

public enum StarDropSaloonItems {
    BEER(ArtisanItemType.BEER, "Drink in moderation.", 400, Integer.MAX_VALUE),
    SALAD(FoodType.SALAD, "A healthy garden salad.", 220, Integer.MAX_VALUE),
    BREAD(FoodType.BREAD, "A crusty baguette.", 120, Integer.MAX_VALUE),
    SPAGHETTI(FoodType.SPAGHETTI, "An old favorite.", 240, Integer.MAX_VALUE),
    PIZZA(FoodType.PIZZA, "It's popular for all the right reasons.", 600, Integer.MAX_VALUE),
    COFFEE(ArtisanItemType.COFFEE, "It smells delicious. This is sure to give you a boost.", 300, Integer.MAX_VALUE),

    HASHBROWNS_RECIPE(FoodType.HASHBROWNS_OIL_CORN, "A recipe to make Hashbrowns.", 50, 1),
    OMELET_RECIPE(FoodType.OMELET, "A recipe to make Omelet.", 100, 1),
    PANCAKES_RECIPE(FoodType.PANCAKES, "A recipe to make Pancakes.", 100, 1),
    BREAD_RECIPE(FoodType.BREAD, "A recipe to make Bread.", 100, 1),
    TORTILLA_RECIPE(FoodType.TORTILLA, "A recipe to make Tortilla.", 100, 1),
    PIZZA_RECIPE(FoodType.PIZZA, "A recipe to make Pizza.", 150, 1),
    MAKI_ROLL_RECIPE(FoodType.MAKI_ROLL, "A recipe to make Maki Roll.", 300, 1),
    TRIPLE_SHOT_ESPRESSO_RECIPE(FoodType.TRIPLE_SHOT_ESPRESSO, "A recipe to make Triple Shot Espresso.", 5000, 1),
    COOKIE_RECIPE(FoodType.COOKIE, "A recipe to make Cookie.", 300, 1);

    public final Object itemType;
    public final String description;
    public final int price;
    public final int dailyLimit;

    StarDropSaloonItems(Object itemType, String description, int price, int dailyLimit) {
        this.itemType = itemType;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }

    public String getName() {
        return name().toLowerCase().replaceAll("_", " ");
    }
}
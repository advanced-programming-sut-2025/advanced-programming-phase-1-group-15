package com.example.common.stores;

public enum GeneralItemsType {
    HAY("Dried grass used as animal food.", 50, Integer.MAX_VALUE),
    WOOD("A sturdy, yet flexible plant material with a wide variety of uses.", 10, Integer.MAX_VALUE),
    JOJA_COLA("The flagship product of Joja corporation.", 75, Integer.MAX_VALUE),
    ANCIENT_SEED("Could these still grow?", 500, 1),
    GRASS_STARTER("Place this on your farm to start a new patch of grass.", 125, Integer.MAX_VALUE),
    SUGAR("Adds sweetness to pastries and candies. Too much can be unhealthy.", 125, Integer.MAX_VALUE),
    WHEAT_FLOUR("A common cooking ingredient made from crushed wheat seeds.", 125, Integer.MAX_VALUE),
    RICE("A basic grain often served under vegetables.", 250, Integer.MAX_VALUE),
    BOUQUET("A gift that shows your romantic interest. (Unlocked after reaching level 2 friendship with a player)", 1000, 2),
    WEDDING_RING("It's used to ask for another farmer's hand in marriage. (Unlocked after reaching level 3 friendship with a player)", 10_000, 2),
    DEHYDRATOR_RECIPE("A recipe to make Dehydrator.", 10_000, 1),
    GRASS_STARTER_RECIPE("A recipe to make Grass Starter.", 1_000, 1),
    OIL("All-purpose cooking oil.", 200, Integer.MAX_VALUE),
    VINEGAR("An aged fermented liquid used in many cooking recipes.", 200, Integer.MAX_VALUE),
    DELUXE_RETAINING_SOIL("This soil has a 100% chance of staying watered overnight. Mix into tilled soil.", 150, Integer.MAX_VALUE);

    public final String description;
    public final int price;
    public final int dailyLimit;

    GeneralItemsType(String description, int price, int dailyLimit) {
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }

    public String getName() {
        return name().toLowerCase().replaceAll("_", " ");
    }
}

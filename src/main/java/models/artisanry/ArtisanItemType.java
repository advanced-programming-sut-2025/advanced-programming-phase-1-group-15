package models.artisanry;

import models.crafting.CraftItemType;
import models.tools.BackPackable;

import java.util.HashMap;

public enum ArtisanItemType {
    BEER(CraftItemType.CHERRY_BOMB,
            "Drink in moderation.",
            50,
            24,
            new HashMap<>(),
            200);

    public String description;
    public int energy;
    public int productionTimeInHours;
    public HashMap<BackPackable, Integer> ingredients;
    public int sellPrice;

    ArtisanItemType(CraftItemType craftItemType,
                    String description,
                    int energy,
                    int productionTimeInHours,
                    HashMap<BackPackable, Integer> ingridients,
                    int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.productionTimeInHours = productionTimeInHours;
        this.ingredients = ingridients;
        this.sellPrice = sellPrice;
    }
}

package com.example.models.stores;

import com.example.models.foraging.ForagingMineralType;

public enum BlackSmithItems {
    COPPER_ORE(ForagingMineralType.COPPER, 75, Integer.MAX_VALUE),
    IRON_ORE(ForagingMineralType.IRON, 150, Integer.MAX_VALUE),
    COAL(ForagingMineralType.COAL, 150, Integer.MAX_VALUE),
    GOLD_ORE(ForagingMineralType.GOLD, 400, Integer.MAX_VALUE);

    public final ForagingMineralType foragingMineralType;
    public final int price;
    public final int dailyLimit;
    BlackSmithItems(ForagingMineralType foragingMineralType, int price, int dailyLimit) {
        this.foragingMineralType = foragingMineralType;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }

    public String getName() {
        return name().toLowerCase().replaceAll("_", " ");
    }
}

package models.stores;

import models.foraging.ForagingMineralType;

public enum GeneralItemsType {
    HAY("Dried grass used as animal food.", 50, Integer.MAX_VALUE),
    WOOD("A sturdy, yet flexible plant material with a wide variety of uses.", 10, Integer.MAX_VALUE);;

    public final String description;
    public final int price;
    public final int dailyLimit;

    GeneralItemsType(String description, int price, int dailyLimit) {
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }
}

package models.stores;

public enum GeneralItemsType {
    HAY("Dried grass used as animal food.", 50, Integer.MAX_VALUE),
    WOOD("A sturdy, yet flexible plant material with a wide variety of uses.", 10, Integer.MAX_VALUE),
    JOJA_COLA("The flagship product of Joja corporation.", 75, Integer.MAX_VALUE),
    ANCIENT_SEED("Could these still grow?", 500, 1),
    GRASS_STARTER("Place this on your farm to start a new patch of grass.", 125, Integer.MAX_VALUE),
    SUGAR("Adds sweetness to pastries and candies. Too much can be unhealthy.", 125, Integer.MAX_VALUE),
    WHEAT_FLOUR("A common cooking ingredient made from crushed wheat seeds.", 125, Integer.MAX_VALUE),
    RICE("A basic grain often served under vegetables.", 250, Integer.MAX_VALUE),;

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

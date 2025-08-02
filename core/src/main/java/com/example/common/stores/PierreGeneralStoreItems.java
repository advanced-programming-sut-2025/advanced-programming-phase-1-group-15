package com.example.common.stores;

import com.example.common.time.Season;
import com.example.common.foraging.ForagingSeedsType;

public enum PierreGeneralStoreItems {
    // Spring Stock
    PARSNIP_SEEDS(ForagingSeedsType.PARSNIP_SEEDS, "Plant these in the spring. Takes 4 days to mature.", 20, 30, 5, Season.SPRING),
    BEAN_STARTER(null, "Plant these in the spring. Takes 10 days to mature, but keeps producing after that. Grows on a trellis.", 60, 90, 5, Season.SPRING),
    CAULIFLOWER_SEEDS(ForagingSeedsType.CAULIFLOWER_SEEDS, "Plant these in the spring. Takes 12 days to produce a large cauliflower.", 80, 120, 5, Season.SPRING),
    POTATO_SEEDS(ForagingSeedsType.POTATO_SEEDS, "Plant these in the spring. Takes 6 days to mature, and has a chance of yielding multiple potatoes at harvest.", 50, 75, 5, Season.SPRING),
    TULIP_BULB(ForagingSeedsType.TULIP_BULB, "Plant in spring. Takes 6 days to produce a colorful flower. Assorted colors.", 20, 30, 5, Season.SPRING),
    KALE_SEEDS(ForagingSeedsType.KALE_SEEDS, "Plant these in the spring. Takes 6 days to mature. Harvest with the scythe.", 70, 105, 5, Season.SPRING),
    JAZZ_SEEDS(ForagingSeedsType.JAZZ_SEEDS, "Plant in spring. Takes 7 days to produce a blue puffball flower.", 30, 45, 5, Season.SPRING),
    GARLIC_SEEDS(ForagingSeedsType.GARLIC_SEEDS, "Plant these in the spring. Takes 4 days to mature.", 40, 60, 5, Season.SPRING),
    RICE_SHOOT(ForagingSeedsType.RICE_SHOOT, "Plant these in the spring. Takes 8 days to mature. Grows faster if planted near a body of water. Harvest with the scythe.", 40, 60, 5, Season.SPRING),

    // Summer Stock
    MELON_SEEDS(ForagingSeedsType.MELON_SEEDS, "Plant these in the summer. Takes 12 days to mature.", 80, 120, 5, Season.SUMMER),
    TOMATO_SEEDS(ForagingSeedsType.TOMATO_SEEDS, "Plant these in the summer. Takes 11 days to mature, and continues to produce after first harvest.", 50, 75, 5, Season.SUMMER),
    BLUEBERRY_SEEDS(ForagingSeedsType.BLUEBERRY_SEEDS, "Plant these in the summer. Takes 13 days to mature, and continues to produce after first harvest.", 80, 120, 5, Season.SUMMER),
    PEPPER_SEEDS(ForagingSeedsType.PEPPER_SEEDS, "Plant these in the summer. Takes 5 days to mature, and continues to produce after first harvest.", 40, 60, 5, Season.SUMMER),
    WHEAT_SEEDS(ForagingSeedsType.WHEAT_SEEDS, "Plant these in the summer or AUTUMN. Takes 4 days to mature. Harvest with the scythe.", 10, 15, 5, Season.SUMMER),
    RADISH_SEEDS(ForagingSeedsType.RADISH_SEEDS, "Plant these in the summer. Takes 6 days to mature.", 40, 60, 5, Season.SUMMER),
    POPPY_SEEDS(ForagingSeedsType.POPPY_SEEDS, "Plant in summer. Produces a bright red flower in 7 days.", 100, 150, 5, Season.SUMMER),
    SPANGLE_SEEDS(ForagingSeedsType.SPANGLE_SEEDS, "Plant in summer. Takes 8 days to produce a vibrant tropical flower. Assorted colors.", 50, 75, 5, Season.SUMMER),
    HOPS_STARTER(ForagingSeedsType.HOPS_STARTER, "Plant these in the summer. Takes 11 days to grow, but keeps producing after that. Grows on a trellis.", 60, 90, 5, Season.SUMMER),
    CORN_SEEDS(ForagingSeedsType.CORN_SEEDS, "Plant these in the summer or AUTUMN. Takes 14 days to mature, and continues to produce after first harvest.", 150, 225, 5, Season.SUMMER),
    SUNFLOWER_SEEDS(ForagingSeedsType.SUNFLOWER_SEEDS, "Plant in summer or AUTUMN. Takes 8 days to produce a large sunflower. Yields more seeds at harvest.", 200, 300, 5, Season.SUMMER),
    RED_CABBAGE_SEEDS(ForagingSeedsType.RED_CABBAGE_SEEDS, "Plant these in the summer. Takes 9 days to mature.", 100, 150, 5, Season.SUMMER),

    // AUTUMN Stock
    EGGPLANT_SEEDS(ForagingSeedsType.EGGPLANT_SEEDS, "Plant these in the AUTUMN. Takes 5 days to mature, and continues to produce after first harvest.", 20, 30, 5, Season.AUTUMN),
    PUMPKIN_SEEDS(null, "Plant these in the AUTUMN. Takes 13 days to mature.", 100, 150, 5, Season.AUTUMN),
    BOK_CHOY_SEEDS(ForagingSeedsType.BOK_CHOY_SEEDS, "Plant these in the AUTUMN. Takes 4 days to mature.", 50, 75, 5, Season.AUTUMN),
    YAM_SEEDS(ForagingSeedsType.YAM_SEEDS, "Plant these in the AUTUMN. Takes 10 days to mature.", 60, 90, 5, Season.AUTUMN),
    CRANBERRY_SEEDS(ForagingSeedsType.CRANBERRY_SEEDS, "Plant these in the AUTUMN. Takes 7 days to mature, and continues to produce after first harvest.", 240, 360, 5, Season.AUTUMN),
    FAIRY_SEEDS(ForagingSeedsType.FAIRY_SEEDS, "Plant in AUTUMN. Takes 12 days to produce a mysterious flower. Assorted Colors.", 200, 300, 5, Season.AUTUMN),
    AMARANTH_SEEDS(ForagingSeedsType.AMARANTH_SEEDS, "Plant these in the AUTUMN. Takes 7 days to grow. Harvest with the scythe.", 70, 105, 5, Season.AUTUMN),
    GRAPE_STARTER(ForagingSeedsType.GRAPE_STARTER, "Plant these in the AUTUMN. Takes 10 days to grow, but keeps producing after that. Grows on a trellis.", 60, 90, 5, Season.AUTUMN),
    ARTICHOKE_SEEDS(ForagingSeedsType.ARTICHOKE_SEEDS, "Plant these in the AUTUMN. Takes 8 days to mature.", 30, 45, 5, Season.AUTUMN);

    public final Object item;
    public final String description;
    public final int price;
    public final int sellPrice;
    public final int dailyLimit;
    public final Season season;

    PierreGeneralStoreItems(Object item, String description, int price, int sellPrice, int dailyLimit, Season season) {
        this.item = item;
        this.description = description;
        this.price = price;
        this.sellPrice = sellPrice;
        this.dailyLimit = dailyLimit;
        this.season = season;
    }

    public String getName() {
        return name().toLowerCase().replaceAll("_", " ");
    }
}

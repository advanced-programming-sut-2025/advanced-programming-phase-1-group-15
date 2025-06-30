package com.example.models.stores;

import com.example.models.foraging.ForagingSeedsType;
import com.example.models.time.Season;

public enum JojaMartItems {
    // Spring Stock
    PARSNIP_SEEDS(ForagingSeedsType.PARSNIP_SEEDS, "Plant these in the spring. Takes 4 days to mature.", 25, 5, Season.SPRING),
    BEAN_STARTER(null, "Plant these in the spring. Takes 10 days to mature, but keeps producing after that. Grows on a trellis.", 75, 5, Season.SPRING),
    CAULIFLOWER_SEEDS(ForagingSeedsType.CAULIFLOWER_SEEDS, "Plant these in the spring. Takes 12 days to produce a large cauliflower.", 100, 5, Season.SPRING),
    POTATO_SEEDS(ForagingSeedsType.POTATO_SEEDS, "Plant these in the spring. Takes 6 days to mature, and has a chance of yielding multiple potatoes at harvest.", 62, 5, Season.SPRING),
    STRAWBERRY_SEEDS(ForagingSeedsType.STRAWBERRY_SEEDS, "Plant these in spring. Takes 8 days to mature, and keeps producing strawberries after that.", 100, 5, Season.SPRING),
    TULIP_BULB(ForagingSeedsType.TULIP_BULB, "Plant in spring. Takes 6 days to produce a colorful flower. Assorted colors.", 25, 5, Season.SPRING),
    KALE_SEEDS(ForagingSeedsType.KALE_SEEDS, "Plant these in the spring. Takes 6 days to mature. Harvest with the scythe.", 87, 5, Season.SPRING),
    CARROT_SEEDS(ForagingSeedsType.CARROT_SEEDS, "Plant in the spring. Takes 3 days to grow.", 5, 10, Season.SPRING),
    RHUBARB_SEEDS(ForagingSeedsType.RHUBARB_SEEDS, "Plant these in the spring. Takes 13 days to mature.", 100, 5, Season.SPRING),
    JAZZ_SEEDS(ForagingSeedsType.JAZZ_SEEDS, "Plant in spring. Takes 7 days to produce a blue puffball flower.", 37, 5, Season.SPRING),

    // Summer Stock
    TOMATO_SEEDS(ForagingSeedsType.TOMATO_SEEDS, "Plant these in the summer. Takes 11 days to mature, and continues to produce after first harvest.", 62, 5, Season.SUMMER),
    PEPPER_SEEDS(ForagingSeedsType.PEPPER_SEEDS, "Plant these in the summer. Takes 5 days to mature, and continues to produce after first harvest.", 50, 5, Season.SUMMER),
    WHEAT_SEEDS(ForagingSeedsType.WHEAT_SEEDS, "Plant these in the summer or autumn. Takes 4 days to mature. Harvest with the scythe.", 12, 10, Season.SUMMER),
    SUMMER_SQUASH_SEEDS(ForagingSeedsType.SUMMER_SQUASH_SEEDS, "Plant in the summer. Takes 6 days to grow, and continues to produce after first harvest.", 10, 10, Season.SUMMER),
    RADISH_SEEDS(ForagingSeedsType.RADISH_SEEDS, "Plant these in the summer. Takes 6 days to mature.", 50, 5, Season.SUMMER),
    MELON_SEEDS(ForagingSeedsType.MELON_SEEDS, "Plant these in the summer. Takes 12 days to mature.", 100, 5, Season.SUMMER),
    HOPS_STARTER(ForagingSeedsType.HOPS_STARTER, "Plant these in the summer. Takes 11 days to grow, but keeps producing after that. Grows on a trellis.", 75, 5, Season.SUMMER),
    POPPY_SEEDS(ForagingSeedsType.POPPY_SEEDS, "Plant in summer. Produces a bright red flower in 7 days.", 125, 5, Season.SUMMER),
    SPANGLE_SEEDS(ForagingSeedsType.SPANGLE_SEEDS, "Plant in summer. Takes 8 days to produce a vibrant tropical flower. Assorted colors.", 62, 5, Season.SUMMER),
    STARFRUIT_SEEDS(ForagingSeedsType.STARFRUIT_SEEDS, "Plant these in the summer. Takes 13 days to mature.", 400, 5, Season.SUMMER),
    SUNFLOWER_SEEDS(ForagingSeedsType.SUNFLOWER_SEEDS, "Plant in summer or autumn. Takes 8 days to produce a large sunflower. Yields more seeds at harvest.", 125, 5, Season.SUMMER),

    // Autumn Stock
    CORN_SEEDS(ForagingSeedsType.CORN_SEEDS, "Plant these in the summer or autumn. Takes 14 days to mature, and continues to produce after first harvest.", 187, 5, Season.AUTUMN),
    EGGPLANT_SEEDS(ForagingSeedsType.EGGPLANT_SEEDS, "Plant these in the autumn. Takes 5 days to mature, and continues to produce after first harvest.", 25, 5, Season.AUTUMN),
    PUMPKIN_SEEDS(null, "Plant these in the autumn. Takes 13 days to mature.", 125, 5, Season.AUTUMN),
    BROCCOLI_SEEDS(ForagingSeedsType.BROCCOLI_SEEDS, "Plant in the autumn. Takes 8 days to mature, and continues to produce after first harvest.", 15, 5, Season.AUTUMN),
    AMARANTH_SEEDS(ForagingSeedsType.AMARANTH_SEEDS, "Plant these in the autumn. Takes 7 days to grow. Harvest with the scythe.", 87, 5, Season.AUTUMN),
    GRAPE_STARTER(ForagingSeedsType.GRAPE_STARTER, "Plant these in the autumn. Takes 10 days to grow, but keeps producing after that. Grows on a trellis.", 75, 5, Season.AUTUMN),
    BEET_SEEDS(ForagingSeedsType.BEET_SEEDS, "Plant these in the autumn. Takes 6 days to mature.", 20, 5, Season.AUTUMN),
    YAM_SEEDS(ForagingSeedsType.YAM_SEEDS, "Plant these in the autumn. Takes 10 days to mature.", 75, 5, Season.AUTUMN),
    BOK_CHOY_SEEDS(ForagingSeedsType.BOK_CHOY_SEEDS, "Plant these in the autumn. Takes 4 days to mature.", 62, 5, Season.AUTUMN),
    CRANBERRY_SEEDS(ForagingSeedsType.CRANBERRY_SEEDS, "Plant these in the autumn. Takes 7 days to mature, and continues to produce after first harvest.", 300, 5, Season.AUTUMN),
    FAIRY_SEEDS(ForagingSeedsType.FAIRY_SEEDS, "Plant in autumn. Takes 12 days to produce a mysterious flower. Assorted Colors.", 250, 5, Season.AUTUMN),
    RARE_SEED(null, "Sow in autumn. Takes all season to grow.", 1000, 1, Season.AUTUMN),

    // Winter Stock
    POWDER_MELON_SEEDS(null, "This special melon grows in the winter. Takes 7 days to grow.", 20, 10, Season.WINTER)
    ;

    public final ForagingSeedsType foragingSeedsType;
    public final String description;
    public final int price;
    public final int dailyLimit;
    public final Season season;

    JojaMartItems(ForagingSeedsType foragingSeedsType, String description, int price, int dailyLimit, Season season) {
        this.foragingSeedsType = foragingSeedsType;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.season = season;
    }

    public String getName() {
        return name().toLowerCase().replaceAll("_", " ");
    }
}
package models.farming;

public enum CropSeeds {
    JAZZ_SEED,
    CARROT_SEED,
    CAULIFLOWER_SEED,
    COFFEE_SEED,
    GARLIC_SEED,
    BEAN_STARTER,
    KALE_SEED,
    PARSNIP_SEED,
    POTATO_SEED,
    RHUBARB_SEED,
    STRAWBERRY_SEED,
    TULIP_BULB,
    RICE_SHOT,
    BLUEBERRY_SEED,
    CORN_SEED,
    HOPS_STARTER,
    PEPPER_SEED,
    MELON_SEED,
    POPPY_SEED,
    RADISH_SEED,
    RED_CABBAGE_SEED,
    STAR_FRUIT_SEED,
    SPANGLE_SEED,
    SUMMER_SQUASH_SEED,
    SUNFLOWER_SEED,
    TOMATO_SEED,
    WHEAT_SEED,
    AMARANTH_SEED,
    ARTICHOKE_SEED,
    BEET_SEED,
    BOK_CHOY_SEED,
    BROCCOLI_SEED,
    CRANBERRY_SEED,
    EGGPLANT_SEED,
    FAIRY_SEED,
    GRAPE_STARTER,
    PUMPKIN_STARTER,
    YAM_SEED,
    RARE_SEED,
    POWDER_MELON_SEED,
    ANCIENT_SEED;

    public static Crops cropOfThisSeed(CropSeeds seed) {
        switch (seed) {
            case JAZZ_SEED: return Crops.BLUE_JAZZ;
            case CARROT_SEED: return Crops.CARROT;
            case CAULIFLOWER_SEED: return Crops.CAULIFLOWER;
            case COFFEE_SEED: return Crops.COFFEE_BEEN;
            case GARLIC_SEED: return Crops.GARLIC;
            case BEAN_STARTER: return Crops.GREEN_BEEN;
            case KALE_SEED: return Crops.KALE;
            case PARSNIP_SEED: return Crops.PARSNIP;
            case POTATO_SEED: return Crops.POTATO;
            case RHUBARB_SEED: return Crops.RHUBARB;
            case STRAWBERRY_SEED: return Crops.STRAWBERRY;
            case TULIP_BULB: return Crops.TULIP;
            case RICE_SHOT: return Crops.UN_MILLED_RICE;
            case BLUEBERRY_SEED: return Crops.BLUEBERRY;
            case CORN_SEED: return Crops.CORN;
            case HOPS_STARTER: return Crops.HOPS;
            case PEPPER_SEED: return Crops.HOT_PEPPER;
            case MELON_SEED: return Crops.MELON;
            case POPPY_SEED: return Crops.POPPY;
            case RADISH_SEED: return Crops.RADISH;
            case RED_CABBAGE_SEED: return Crops.RED_CABBAGE;
            case STAR_FRUIT_SEED: return Crops.STAR_FRUIT;
            case SPANGLE_SEED: return Crops.SUMMER_SPANGLE;
            case SUMMER_SQUASH_SEED: return Crops.SUMMER_SQUASH;
            case SUNFLOWER_SEED: return Crops.SUNFLOWER;
            case TOMATO_SEED: return Crops.TOMATO;
            case WHEAT_SEED: return Crops.WHEAT;
            case AMARANTH_SEED: return Crops.AMARANTH;
            case ARTICHOKE_SEED: return Crops.ARTICHOKE;
            case BEET_SEED: return Crops.BEET;
            case BOK_CHOY_SEED: return Crops.BOK_CHOY;
            case BROCCOLI_SEED: return Crops.BROCCOLI;
            case CRANBERRY_SEED: return Crops.CRANBERRY;
            case EGGPLANT_SEED: return Crops.EGGPLANT;
            case FAIRY_SEED: return Crops.FAIRY_ROSE;
            case GRAPE_STARTER: return Crops.GRAPE;
            case PUMPKIN_STARTER: return Crops.PUMPKIN;
            case YAM_SEED: return Crops.YAM;
            case RARE_SEED: return Crops.SWEET_GEM_BERRY;
            case POWDER_MELON_SEED: return Crops.POWDER_MELON;
            case ANCIENT_SEED: return Crops.ANCIENT_FRUIT;
            default: return null;
        }
    }
}

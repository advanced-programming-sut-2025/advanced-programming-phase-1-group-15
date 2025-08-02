package com.example.common.farming;

public enum CropSeeds implements Seedable  {
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

    public static CropSeeds getByName(String name) {
        String normalized = name.trim().toLowerCase().replace('_', ' ');
        switch (normalized) {
            case "jazz seed": return CropSeeds.JAZZ_SEED;
            case "carrot seed": return CropSeeds.CARROT_SEED;
            case "cauliflower seed": return CropSeeds.CAULIFLOWER_SEED;
            case "coffee seed": return CropSeeds.COFFEE_SEED;
            case "garlic seed": return CropSeeds.GARLIC_SEED;
            case "bean starter": return CropSeeds.BEAN_STARTER;
            case "kale seed": return CropSeeds.KALE_SEED;
            case "parsnip seed": return CropSeeds.PARSNIP_SEED;
            case "potato seed": return CropSeeds.POTATO_SEED;
            case "rhubarb seed": return CropSeeds.RHUBARB_SEED;
            case "strawberry seed": return CropSeeds.STRAWBERRY_SEED;
            case "tulip bulb": return CropSeeds.TULIP_BULB;
            case "rice shot": return CropSeeds.RICE_SHOT;
            case "blueberry seed": return CropSeeds.BLUEBERRY_SEED;
            case "corn seed": return CropSeeds.CORN_SEED;
            case "hops starter": return CropSeeds.HOPS_STARTER;
            case "pepper seed": return CropSeeds.PEPPER_SEED;
            case "melon seed": return CropSeeds.MELON_SEED;
            case "poppy seed": return CropSeeds.POPPY_SEED;
            case "radish seed": return CropSeeds.RADISH_SEED;
            case "red cabbage seed": return CropSeeds.RED_CABBAGE_SEED;
            case "star fruit seed": return CropSeeds.STAR_FRUIT_SEED;
            case "spangle seed": return CropSeeds.SPANGLE_SEED;
            case "summer squash seed": return CropSeeds.SUMMER_SQUASH_SEED;
            case "sunflower seed": return CropSeeds.SUNFLOWER_SEED;
            case "tomato seed": return CropSeeds.TOMATO_SEED;
            case "wheat seed": return CropSeeds.WHEAT_SEED;
            case "amaranth seed": return CropSeeds.AMARANTH_SEED;
            case "artichoke seed": return CropSeeds.ARTICHOKE_SEED;
            case "beet seed": return CropSeeds.BEET_SEED;
            case "bok choy seed": return CropSeeds.BOK_CHOY_SEED;
            case "broccoli seed": return CropSeeds.BROCCOLI_SEED;
            case "cranberry seed": return CropSeeds.CRANBERRY_SEED;
            case "eggplant seed": return CropSeeds.EGGPLANT_SEED;
            case "fairy seed": return CropSeeds.FAIRY_SEED;
            case "grape starter": return CropSeeds.GRAPE_STARTER;
            case "pumpkin starter": return CropSeeds.PUMPKIN_STARTER;
            case "yam seed": return CropSeeds.YAM_SEED;
            case "rare seed": return CropSeeds.RARE_SEED;
            case "powder melon seed": return CropSeeds.POWDER_MELON_SEED;
            case "ancient seed": return CropSeeds.ANCIENT_SEED;
            default: return null;
        }
    }

}

package com.example.common.farming;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.time.Season;
import com.example.client.views.GameAssetManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Crops{
    BLUE_JAZZ(
        CropSeeds.JAZZ_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.blue_jazz_1, GameAssetManager.blue_jazz_2,
            GameAssetManager.blue_jazz_3, GameAssetManager.blue_jazz_4)),
        new ArrayList<>(Arrays.asList(1,2,2,2)),
        7,
        true,
        0,
        50,
        true,
        45,
        new ArrayList<>(List.of(Season.SPRING)),
        false
    ),
    CARROT(
        CropSeeds.CARROT_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.carrot_1, GameAssetManager.carrot_2,
            GameAssetManager.carrot_3, GameAssetManager.carrot_4)),
        new ArrayList<>(Arrays.asList(1,1,1)),
        3,
        true,
        0,
        35,
        true,
        75,
        new ArrayList<>(List.of(Season.SPRING)),
        false
    ),
    CAULIFLOWER(
        CropSeeds.CAULIFLOWER_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.cauliflower_1, GameAssetManager.cauliflower_2,
            GameAssetManager.cauliflower_3, GameAssetManager.cauliflower_4,
            GameAssetManager.cauliflower_5, GameAssetManager.cauliflower_6)),
        new ArrayList<>(Arrays.asList(1,2,4,4,1)),
        12,
        true,
        0,
        175,
        true,
        75,
        new ArrayList<>(List.of(Season.SPRING)),
        true
    ),
    COFFEE_BEEN(
        CropSeeds.COFFEE_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.coffee_been_1, GameAssetManager.coffee_been_2,
            GameAssetManager.coffee_been_3, GameAssetManager.coffee_been_4,
            GameAssetManager.coffee_been_5, GameAssetManager.coffee_been_6,
            GameAssetManager.coffee_been_7)),
        new ArrayList<>(Arrays.asList(1,2,2,3,2)),
        10,
        false,
        2,
        15,
        false,
        0,
        new ArrayList<>(List.of(Season.SPRING , Season.SUMMER)),
        false
    ),
    GARLIC(
        CropSeeds.GARLIC_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.garlic_1, GameAssetManager.garlic_2,
            GameAssetManager.garlic_3, GameAssetManager.garlic_4,
            GameAssetManager.garlic_5)),
        new ArrayList<>(Arrays.asList(1,1,1,1)),
        4,
        true,
        0,
        60,
        true,
        20,
        new ArrayList<>(List.of(Season.SPRING)),
        false
    ),
    GREEN_BEEN(
        CropSeeds.BEAN_STARTER,
        new ArrayList<>(Arrays.asList(GameAssetManager.green_been_1, GameAssetManager.green_been_2,
            GameAssetManager.green_been_3, GameAssetManager.green_been_4,
            GameAssetManager.green_been_5, GameAssetManager.green_been_6,
            GameAssetManager.green_been_7, GameAssetManager.green_been_8)),
        new ArrayList<>(Arrays.asList(1,1,1,1,1)),
        5,
        false,
        2,
        40,
        true,
        25,
        new ArrayList<>(List.of(Season.SPRING)),
        false
    ),
    PARSNIP(
        CropSeeds.PARSNIP_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.parsnip_1, GameAssetManager.parsnip_2,
            GameAssetManager.parsnip_3, GameAssetManager.parsnip_4,
            GameAssetManager.parsnip_5)),
        new ArrayList<>(Arrays.asList(1,1,1,1)),
        4,
        true,
        0,
        35,
        true,
        25,
        new ArrayList<>(List.of(Season.SPRING)),
        false
    ),
    KALE(
        CropSeeds.KALE_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.kale_1, GameAssetManager.kale_2,
            GameAssetManager.kale_3, GameAssetManager.kale_4,
            GameAssetManager.kale_5)),
        new ArrayList<>(Arrays.asList(1,2,2,1)),
        6,
        true,
        0,
        110,
        true,
        50,
        new ArrayList<>(List.of(Season.SPRING)),
        false
    ),
    POTATO(
        CropSeeds.POTATO_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.potato_1, GameAssetManager.potato_2,
            GameAssetManager.potato_3, GameAssetManager.potato_4,
            GameAssetManager.potato_5, GameAssetManager.potato_6)),
        new ArrayList<>(Arrays.asList(1,1,1,2,1)),
        6,
        true,
        0,
        80,
        true,
        25,
        new ArrayList<>(List.of(Season.SPRING)),
        false
    ),
    RHUBARB(
        CropSeeds.RHUBARB_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.rhubarb_1, GameAssetManager.rhubarb_2,
            GameAssetManager.rhubarb_3, GameAssetManager.rhubarb_4,
            GameAssetManager.rhubarb_5, GameAssetManager.rhubarb_6)),
        new ArrayList<>(Arrays.asList(2,2,2,3,4)),
        13,
        true,
        0,
        220,
        false,
        0,
        new ArrayList<>(List.of(Season.SPRING)),
        false
    ),
    STRAWBERRY(
        CropSeeds.STRAWBERRY_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.strawberry_1, GameAssetManager.strawberry_2,
            GameAssetManager.strawberry_3, GameAssetManager.strawberry_4,
            GameAssetManager.strawberry_5, GameAssetManager.strawberry_6,
            GameAssetManager.strawberry_7)),
        new ArrayList<>(Arrays.asList(1,1,2,2,2)),
        8,
        false,
        4,
        120,
        true,
        50,
        new ArrayList<>(List.of(Season.SPRING)),
        false
    ),
    TULIP(
        CropSeeds.TULIP_BULB,
        new ArrayList<>(Arrays.asList(GameAssetManager.tulip_1, GameAssetManager.tulip_2,
            GameAssetManager.tulip_3, GameAssetManager.tulip_4,
            GameAssetManager.tulip_5, GameAssetManager.tulip_6)),
        new ArrayList<>(Arrays.asList(1,1,2,2)),
        6,
        true,
        0,
        30,
        true,
        45,
        new ArrayList<>(List.of(Season.SPRING)),
        false
    ),
    UN_MILLED_RICE(
        CropSeeds.RICE_SHOT,
        new ArrayList<>(Arrays.asList(GameAssetManager.un_milled_rice_1, GameAssetManager.un_milled_rice_2,
            GameAssetManager.un_milled_rice_3, GameAssetManager.un_milled_rice_4,
            GameAssetManager.un_milled_rice_5)),
        new ArrayList<>(Arrays.asList(1,2,2,3)),
        8,
        true,
        0,
        30,
        true,
        3,
        new ArrayList<>(List.of(Season.SPRING)),
        false
    ),
    BLUEBERRY(
        CropSeeds.BLUEBERRY_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.blueberry_1, GameAssetManager.blueberry_2,
            GameAssetManager.blueberry_3, GameAssetManager.blueberry_4,
            GameAssetManager.blueberry_5, GameAssetManager.blueberry_6,
            GameAssetManager.blueberry_7)),
        new ArrayList<>(Arrays.asList(1,3,3,4,2)),
        13,
        false,
        4,
        50,
        true,
        25,
        new ArrayList<>(List.of(Season.SUMMER)),
        false
    ),
    CORN(
        CropSeeds.CORN_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.corn_1, GameAssetManager.corn_2,
            GameAssetManager.corn_3, GameAssetManager.corn_4,
            GameAssetManager.corn_5, GameAssetManager.corn_6,
            GameAssetManager.corn_7)),
        new ArrayList<>(Arrays.asList(2,3,3,3,3)),
        14,
        false,
        4,
        50,
        true,
        25,
        new ArrayList<>(List.of(Season.SUMMER , Season.AUTUMN)),
        false
    ),
    HOPS(
        CropSeeds.HOPS_STARTER,
        new ArrayList<>(Arrays.asList(GameAssetManager.hops_1, GameAssetManager.hops_2,
            GameAssetManager.hops_3, GameAssetManager.hops_4,
            GameAssetManager.hops_5, GameAssetManager.hops_6,
            GameAssetManager.hops_7, GameAssetManager.hops_8)),
        new ArrayList<>(Arrays.asList(1,1,2,3,4)),
        11,
        false,
        1,
        25,
        true,
        45,
        new ArrayList<>(List.of(Season.SUMMER)),
        false
    ),
    HOT_PEPPER(
        CropSeeds.PEPPER_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.hot_pepper_1, GameAssetManager.hot_pepper_2,
            GameAssetManager.hot_pepper_3, GameAssetManager.hot_pepper_4,
            GameAssetManager.hot_pepper_5, GameAssetManager.hot_pepper_6)),
        new ArrayList<>(Arrays.asList(1,1,1,1,1)),
        5,
        false,
        3,
        40,
        true,
        13,
        new ArrayList<>(List.of(Season.SUMMER)),
        false
    ),
    MELON(
        CropSeeds.MELON_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.melon_1, GameAssetManager.melon_2,
            GameAssetManager.melon_3, GameAssetManager.melon_4,
            GameAssetManager.melon_5, GameAssetManager.melon_6)),
        new ArrayList<>(Arrays.asList(1,2,3,3,3)),
        12,
        true,
        0,
        250,
        true,
        113,
        new ArrayList<>(List.of(Season.SUMMER)),
        true
    ),
    POPPY(
        CropSeeds.POPPY_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.poppy_1, GameAssetManager.poppy_2,
            GameAssetManager.poppy_3, GameAssetManager.poppy_4,
            GameAssetManager.poppy_5, GameAssetManager.poppy_6)),
        new ArrayList<>(Arrays.asList(1,2,2,2)),
        7,
        true,
        0,
        140,
        true,
        45,
        new ArrayList<>(List.of(Season.SUMMER)),
        false
    ),
    RADISH(
        CropSeeds.RADISH_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.radish_1, GameAssetManager.radish_2,
            GameAssetManager.radish_3, GameAssetManager.radish_4,
            GameAssetManager.radish_5)),
        new ArrayList<>(Arrays.asList(2,1,2,1)),
        6,
        true,
        0,
        90,
        true,
        45,
        new ArrayList<>(List.of(Season.SUMMER)),
        false
    ),
    RED_CABBAGE(
        CropSeeds.RED_CABBAGE_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.red_cabbage_1, GameAssetManager.red_cabbage_2,
            GameAssetManager.red_cabbage_3, GameAssetManager.red_cabbage_4,
            GameAssetManager.red_cabbage_5, GameAssetManager.red_cabbage_6)),
        new ArrayList<>(Arrays.asList(2,1,2,2,2)),
        9,
        true,
        0,
        260,
        true,
        75,
        new ArrayList<>(List.of(Season.SUMMER)),
        false
    ),
    STAR_FRUIT(
        CropSeeds.STAR_FRUIT_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.star_fruit_1, GameAssetManager.star_fruit_2,
            GameAssetManager.star_fruit_3, GameAssetManager.star_fruit_4,
            GameAssetManager.star_fruit_5, GameAssetManager.star_fruit_6)),
        new ArrayList<>(Arrays.asList(2,3,2,3,3)),
        13,
        true,
        0,
        750,
        true,
        125,
        new ArrayList<>(List.of(Season.SUMMER)),
        false
    ),
    SUMMER_SPANGLE(
        CropSeeds.SPANGLE_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.summer_spangle_1, GameAssetManager.summer_spangle_2,
            GameAssetManager.summer_spangle_3, GameAssetManager.summer_spangle_4,
            GameAssetManager.summer_spangle_5)),
        new ArrayList<>(Arrays.asList(1,2,3,1)),
        8,
        true,
        0,
        90,
        true,
        45,
        new ArrayList<>(List.of(Season.SUMMER)),
        false
    ),
    SUMMER_SQUASH(
        CropSeeds.SUMMER_SQUASH_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.summer_squash_1, GameAssetManager.summer_squash_2,
            GameAssetManager.summer_squash_3, GameAssetManager.summer_squash_4,
            GameAssetManager.summer_squash_5, GameAssetManager.summer_squash_6,
            GameAssetManager.summer_squash_7)),
        new ArrayList<>(Arrays.asList(1,1,1,2,1)),
        6,
        false,
        3,
        45,
        true,
        63,
        new ArrayList<>(List.of(Season.SUMMER)),
        false
    ),
    SUNFLOWER(
        CropSeeds.SUNFLOWER_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.sunflower_1, GameAssetManager.sunflower_2,
            GameAssetManager.sunflower_3, GameAssetManager.sunflower_4,
            GameAssetManager.sunflower_5)),
        new ArrayList<>(Arrays.asList(1,2,3,2)),
        8,
        true, 0,
        80,
        true,
        45,
        new ArrayList<>(List.of(Season.SUMMER , Season.AUTUMN)),
        false
    ),
    TOMATO(
        CropSeeds.TOMATO_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.tomato_1, GameAssetManager.tomato_2,
            GameAssetManager.tomato_3, GameAssetManager.tomato_4,
            GameAssetManager.tomato_5, GameAssetManager.tomato_6,
            GameAssetManager.tomato_7)),
        new ArrayList<>(Arrays.asList(2,2,2,2,3)),
        11,
        false, 4,
        60,
        true,
        20,
        new ArrayList<>(List.of(Season.SUMMER)),
        false
    ),
    WHEAT(
        CropSeeds.WHEAT_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.wheat_1, GameAssetManager.wheat_2,
            GameAssetManager.wheat_3, GameAssetManager.wheat_4,
            GameAssetManager.wheat_5)),
        new ArrayList<>(Arrays.asList(1,1,1,1)),
        4,
        true, 0,
        25,
        false,
        0,
        new ArrayList<>(List.of(Season.SUMMER , Season.AUTUMN)),
        false
    ),
    AMARANTH(
        CropSeeds.AMARANTH_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.amaranth_1, GameAssetManager.amaranth_2,
            GameAssetManager.amaranth_3, GameAssetManager.amaranth_4,
            GameAssetManager.amaranth_5)),
        new ArrayList<>(Arrays.asList(1,2,2,2)),
        7,
        true, 0,
        150,
        true,
        50,
        new ArrayList<>(List.of(Season.AUTUMN)),
        false
    ),
    ARTICHOKE(
        CropSeeds.ARTICHOKE_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.artichoke_1, GameAssetManager.artichoke_2,
            GameAssetManager.artichoke_3, GameAssetManager.artichoke_4,
            GameAssetManager.artichoke_5, GameAssetManager.artichoke_6)),
        new ArrayList<>(Arrays.asList(2,2,1,2,1)),
        8,
        true, 0,
        160,
        true,
        30,
        new ArrayList<>(List.of(Season.AUTUMN)),
        false
    ),
    BEET(
        CropSeeds.BEET_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.beet_1, GameAssetManager.beet_2,
            GameAssetManager.beet_3, GameAssetManager.beet_4,
            GameAssetManager.beet_5)),
        new ArrayList<>(Arrays.asList(1,1,2,2)),
        6,
        true, 0,
        100,
        true,
        30,
        new ArrayList<>(List.of(Season.AUTUMN)),
        false
    ),
    BOK_CHOY(
        CropSeeds.BOK_CHOY_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.bok_choy_1, GameAssetManager.bok_choy_2,
            GameAssetManager.bok_choy_3, GameAssetManager.bok_choy_4,
            GameAssetManager.bok_choy_5)),
        new ArrayList<>(Arrays.asList(1,1,1,1)),
        4,
        true, 0,
        80,
        true,
        25,
        new ArrayList<>(List.of(Season.AUTUMN)),
        false
    ),
    BROCCOLI(
        CropSeeds.BROCCOLI_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.broccoli_1, GameAssetManager.broccoli_2,
            GameAssetManager.broccoli_3, GameAssetManager.broccoli_4,
            GameAssetManager.broccoli_5)),
        new ArrayList<>(Arrays.asList(2,2,2,2)),
        8,
        false, 4,
        70,
        true,
        63,
        new ArrayList<>(List.of(Season.AUTUMN)),
        false
    ),
    CRANBERRY(
        CropSeeds.CRANBERRY_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.cranberry_1, GameAssetManager.cranberry_2,
            GameAssetManager.cranberry_3, GameAssetManager.cranberry_4,
            GameAssetManager.cranberry_5, GameAssetManager.cranberry_6,
            GameAssetManager.cranberry_7)),
        new ArrayList<>(Arrays.asList(1,2,1,1,2)),
        7,
        false, 5,
        75,
        true,
        38,
        new ArrayList<>(List.of(Season.AUTUMN)),
        false
    ),
    EGGPLANT(
        CropSeeds.EGGPLANT_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.eggplant_1, GameAssetManager.eggplant_2,
            GameAssetManager.eggplant_3, GameAssetManager.eggplant_4,
            GameAssetManager.eggplant_5, GameAssetManager.eggplant_6,
            GameAssetManager.eggplant_7)),
        new ArrayList<>(Arrays.asList(1,1,1,1)),
        5,
        false, 3,
        60,
        true,
        20,
        new ArrayList<>(List.of(Season.AUTUMN)),
        false
    ),
    FAIRY_ROSE(
        CropSeeds.FAIRY_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.fairy_rose_1, GameAssetManager.fairy_rose_2,
            GameAssetManager.fairy_rose_3, GameAssetManager.fairy_rose_4,
            GameAssetManager.fairy_rose_5)),
        new ArrayList<>(Arrays.asList(1,4,4,3)),
        12,
        true, 0,
        290,
        true,
        45,
        new ArrayList<>(List.of(Season.AUTUMN)),
        false
    ),
    GRAPE(
        CropSeeds.GRAPE_STARTER,
        new ArrayList<>(Arrays.asList(GameAssetManager.grape_1, GameAssetManager.grape_2,
            GameAssetManager.grape_3, GameAssetManager.grape_4,
            GameAssetManager.grape_5, GameAssetManager.grape_6,
            GameAssetManager.grape_7)),
        new ArrayList<>(Arrays.asList(1,1,2,3,3)),
        10,
        false, 3,
        80,
        true,
        38,
        new ArrayList<>(List.of(Season.AUTUMN)),
        false
    ),
    PUMPKIN(
        CropSeeds.PUMPKIN_STARTER,
        new ArrayList<>(Arrays.asList(GameAssetManager.pumpkin_1, GameAssetManager.pumpkin_2,
            GameAssetManager.pumpkin_3, GameAssetManager.pumpkin_4,
            GameAssetManager.pumpkin_5, GameAssetManager.pumpkin_6)),
        new ArrayList<>(Arrays.asList(1,2,3,4,3)),
        13,
        true, 0,
        320,
        false,
        0,
        new ArrayList<>(List.of(Season.AUTUMN)),
        true
    ),
    YAM(
        CropSeeds.YAM_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.yam_1, GameAssetManager.yam_2,
            GameAssetManager.yam_3, GameAssetManager.yam_4,
            GameAssetManager.yam_5)),
        new ArrayList<>(Arrays.asList(1,3,3,3)),
        10,
        true, 0,
        160,
        true,
        45,
        new ArrayList<>(List.of(Season.AUTUMN)),
        false
    ),
    SWEET_GEM_BERRY(
        CropSeeds.RARE_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.sweet_gem_berry_1, GameAssetManager.sweet_gem_berry_2,
            GameAssetManager.sweet_gem_berry_3, GameAssetManager.sweet_gem_berry_4,
            GameAssetManager.sweet_gem_berry_5, GameAssetManager.sweet_gem_berry_6)),
        new ArrayList<>(Arrays.asList(2,4,4,6,6)), 24,
        true, 0,
        3000,
        false,
        0,
        new ArrayList<>(List.of(Season.AUTUMN)),
        false
    ),
    POWDER_MELON(
        CropSeeds.POWDER_MELON_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.powder_melon_1, GameAssetManager.powder_melon_2,
            GameAssetManager.powder_melon_3, GameAssetManager.powder_melon_4,
            GameAssetManager.powder_melon_5, GameAssetManager.powder_melon_6)),
        new ArrayList<>(Arrays.asList(1,2,1,2,1)), 7,
        true, 0,
        60,
        true,
        63,
        new ArrayList<>(List.of(Season.WINTER)),
        true
    ),
    ANCIENT_FRUIT(
        CropSeeds.ANCIENT_SEED,
        new ArrayList<>(Arrays.asList(GameAssetManager.ancient_fruit_1, GameAssetManager.ancient_fruit_2,
            GameAssetManager.ancient_fruit_3, GameAssetManager.ancient_fruit_4,
            GameAssetManager.ancient_fruit_5, GameAssetManager.ancient_fruit_6,
            GameAssetManager.ancient_fruit_7)),
        new ArrayList<>(Arrays.asList(2,7,7,7,5)), 28,
        false, 7,
        550,
        false,
        0,
        new ArrayList<>(List.of(Season.AUTUMN , Season.SPRING , Season.SUMMER)),
        false
    );

    public ArrayList<Integer> getStages() {
        return stages;
    }

    private final CropSeeds source;
    private final ArrayList<Integer> stages;
    private final int totalHarvestTime;
    private final boolean OneTime;
    private final int RegrowthTime;
    private final int basePrice;
    private final ArrayList<Season> season;
    private final boolean edible;
    private final boolean CanBecomeGiant;
    private final int energy;
    private final ArrayList<Sprite> sprites;

    public boolean canGrowInThisSeason(Season currentSeason){
        for(Season s: season){
            if(currentSeason == s){
                return true;
            }
        }
        return false;
    }

    public boolean canBecomeGiant() {
        return CanBecomeGiant;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public CropSeeds getSource() {
        return source;
    }

    public boolean isOneTime() {
        return OneTime;
    }

    public int getRegrowthTime() {
        return RegrowthTime;
    }

    public boolean isEdible() {
        return edible;
    }

    public ArrayList<Season> getSeason() {
        return season;
    }

    public boolean isCanBecomeGiant() {
        return CanBecomeGiant;
    }

    public int getEnergy() {
        return energy;
    }

    Crops(
            CropSeeds seedType,
            ArrayList<Sprite> sprites,
            ArrayList<Integer> stages,
    int totalHarvestTime,
    boolean OneTime,
    int RegrowthTime,
    int basePrice,
    boolean edible,
    int energy,
    ArrayList<Season> season,
    boolean CanBecomeGiant
    ) {
        this.source = seedType;
        this.sprites = sprites;
        this.stages = stages;
        this.OneTime = OneTime;
        this.totalHarvestTime = totalHarvestTime;
        this.RegrowthTime = RegrowthTime;
        this.basePrice = basePrice;
        this.season = season;
        this.edible = edible;
        this.CanBecomeGiant = CanBecomeGiant;
        this.energy = energy;
    }

    public String getName() {
        return this.name().toLowerCase().replaceAll("_", " ");
    }
    public static Crops getByName(String name) {
        String lowerName = name.trim().toLowerCase();
        switch (lowerName) {
            case "blue jazz": return BLUE_JAZZ;
            case "carrot": return CARROT;
            case "cauliflower": return CAULIFLOWER;
            case "coffee been": return COFFEE_BEEN;
            case "garlic": return GARLIC;
            case "green been": return GREEN_BEEN;
            case "parsnip": return PARSNIP;
            case "kale": return KALE;
            case "potato": return POTATO;
            case "rhubarb": return RHUBARB;
            case "strawberry": return STRAWBERRY;
            case "tulip": return TULIP;
            case "un milled rice": return UN_MILLED_RICE;
            case "blueberry": return BLUEBERRY;
            case "corn": return CORN;
            case "hops": return HOPS;
            case "hot pepper": return HOT_PEPPER;
            case "melon": return MELON;
            case "poppy": return POPPY;
            case "radish": return RADISH;
            case "red cabbage": return RED_CABBAGE;
            case "star fruit": return STAR_FRUIT;
            case "summer spangle": return SUMMER_SPANGLE;
            case "summer squash": return SUMMER_SQUASH;
            case "sunflower": return SUNFLOWER;
            case "tomato": return TOMATO;
            case "wheat": return WHEAT;
            case "amaranth": return AMARANTH;
            case "artichoke": return ARTICHOKE;
            case "beet": return BEET;
            case "bok choy": return BOK_CHOY;
            case "broccoli": return BROCCOLI;
            case "cranberry": return CRANBERRY;
            case "eggplant": return EGGPLANT;
            case "fairy rose": return FAIRY_ROSE;
            case "grape": return GRAPE;
            case "pumpkin": return PUMPKIN;
            case "yam": return YAM;
            case "sweet gem berry": return SWEET_GEM_BERRY;
            case "powder melon": return POWDER_MELON;
            case "ancient fruit": return ANCIENT_FRUIT;
            default: return null;
        }
    }
    @Override
    public String toString() {
        return  getName()+": \n"+
                "source= " + source + '\n' +
                ", stages= " + stages + '\n' +
                ", totalHarvestTime= " + totalHarvestTime + '\n' +
                ", OneTime= " + OneTime + '\n' +
                ", RegrowthTime= " + RegrowthTime + '\n' +
                ", basePrice= " + basePrice + '\n' +
                ", season= " + season + '\n' +
                ", edible= " + edible + '\n' +
                ", CanBecomeGiant= " + CanBecomeGiant + '\n' +
                ", energy= " + energy + '\n' +
                "-------------------------------------";
    }
    public int getBasePrice() {
        return basePrice;
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }
}

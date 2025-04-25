package models.farming;

import models.time.Season;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Crops{
    BLUE_JAZZ(
            CropSeed.JAZZ_SEED,
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
            CropSeed.CARROT_SEED,
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
            CropSeed.CAULIFLOWER_SEED,
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
            CropSeed.CAULIFLOWER_SEED,
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
            CropSeed.GARLIC_SEED,
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
            CropSeed.BEAN_STARTER,
            new ArrayList<>(Arrays.asList(1,1,1,3,4)),
            10,
            false,
            3,
            40,
            true,
            25,
            new ArrayList<>(List.of(Season.SPRING)),
            false
    ),
    PARSNIP(
            CropSeed.PARSNIP_SEED,
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
            CropSeed.KALE_SEED,
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
            CropSeed.POTATO_SEED,
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
            CropSeed.RHUBARB_SEED,
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
            CropSeed.STRAWBERRY_SEED,
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
            CropSeed.TULIP_BULB,
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
            CropSeed.RICE_SHOT,
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
            CropSeed.BLUEBERRY_SEED,
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
            CropSeed.CORN_SEED,
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
            CropSeed.HOPS_STARTER,
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
            CropSeed.PEPPER_SEED,
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
            CropSeed.MELON_SEED,
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
            CropSeed.POPPY_SEED,
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
            CropSeed.RADISH_SEED,
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
            CropSeed.RED_CABBAGE_SEED,
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
            CropSeed.STAR_FRUIT_SEED,
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
            CropSeed.SPANGLE_SEED,
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
            CropSeed.SUMMER_SQUASH_SEED,
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
            CropSeed.SUNFLOWER_SEED,
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
            CropSeed.TOMATO_SEED,
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
            CropSeed.WHEAT_SEED,
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
            CropSeed.AMARANTH_SEED,
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
            CropSeed.ARTICHOKE_SEED,
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
            CropSeed.BEET_SEED,
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
            CropSeed.BOK_CHOY_SEED,
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
            CropSeed.BROCCOLI_SEED,
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
            CropSeed.CRANBERRY_SEED,
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
            CropSeed.EGGPLANT_SEED,
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
            CropSeed.FAIRY_SEED,
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
            CropSeed.GRAPE_STARTER,
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
            CropSeed.PUMPKIN_SEED,
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
            CropSeed.YAM_SEED,
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
            CropSeed.RARE_SEED,
            new ArrayList<>(Arrays.asList(2,4,4,6,6)), 24,
            true, 0,
            3000,
            false,
            0,
            new ArrayList<>(List.of(Season.AUTUMN)),
            false
    ),
    POWDER_MELON(
            CropSeed.POWDER_MELON_SEED,
            new ArrayList<>(Arrays.asList(1,2,1,2,1)), 7,
            true, 0,
            60,
            true,
            63,
            new ArrayList<>(List.of(Season.WINTER)),
            true
    ),
    ANCIENT_FRUIT(
            CropSeed.ANCIENT_SEED,
            new ArrayList<>(Arrays.asList(2,7,7,7,5)), 28,
            false, 7,
            550,
            false,
            0,
            new ArrayList<>(List.of(Season.AUTUMN , Season.SPRING , Season.SUMMER)),
            false
    );
    private final CropSeed seedType;
    private final int totalHarvestTime;
    private final boolean OneTime;
    private final int RegrowthTime;
    private final int basePrice;
    private final ArrayList<Season> season;
    private final boolean edible;
    private final boolean CanBecomeGiant;
    private final int energy;
    Crops(
            CropSeed seedType,
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
        this.seedType = seedType;
        this.OneTime = OneTime;
        this.totalHarvestTime = totalHarvestTime;
        this.RegrowthTime = RegrowthTime;
        this.basePrice = basePrice;
        this.season = season;
        this.edible = edible;
        this.CanBecomeGiant = CanBecomeGiant;
        this.energy = energy;
    }
}

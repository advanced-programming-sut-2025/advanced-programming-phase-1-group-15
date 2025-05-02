package models.farming;

import models.time.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Crops{
    BLUE_JAZZ(
            CropSeeds.JAZZ_SEED,
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
            CropSeeds.PARSNIP_SEED,
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

    public String getName(){
        return name();
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
}

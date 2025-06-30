package com.example.models.farming;

import com.example.models.time.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TreeType {
    APRICOT_TREE(
            SeedType.APRICOT_SAPLING,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.APRICOT,
            1,
            59,
            true,
            38,
            new ArrayList<>(List.of(Season.SPRING))

    ),
    CHERRY_TREE(
            SeedType.CHERRY_SAPLING,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
                    28,
            FruitType.CHERRY,
            1,
                    80,
                    true,
                    38,
            new ArrayList<>(List.of(Season.SPRING))
            ),
    BANANA_TREE(
            SeedType.BANANA_SAPLING,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.BANANA,
            1,
            150,
            true,
            75,
            new ArrayList<>(List.of(Season.SUMMER))
            ),
    MANGO_TREE(
            SeedType.MANGO_SAPLING,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.MANGO,
            1,
            130,
            true,
            100,
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    ORANGE_TREE(
            SeedType.ORANGE_SAPLING,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.ORANGE,
            1,
            100,
            true,
            38,
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    PEACH_TREE(
            SeedType.PEACH_SAPLING,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.MANGO,
            1,
            140,
            true,
            38,
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    APPLE_TREE(
            SeedType.APPLE_SAPLING,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.MANGO,
            1,
            100,
            true,
            38,
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    POMEGRANATE_TREE(
            SeedType.POMEGRANATE_SAPLING,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.POMEGRANATE,
            1,
            140,
            true,
            38,
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    OAK_TREE(
            SeedType.ACORNS,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.OAK_RESIN,
            7,
            150,
            false,
            0,
            new ArrayList<>(Arrays.asList(Season.SPRING , Season.SUMMER ,Season.AUTUMN , Season.WINTER))
    ),
    MAPLE_TREE(
            SeedType.MAPLE_SEEDS,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.MAPLE_SYRUP,
            9,
            200,
            false,
            0,
            new ArrayList<>(Arrays.asList(Season.SPRING , Season.SUMMER ,Season.AUTUMN , Season.WINTER))
    ),
    PINE_TREE(
            SeedType.PINE_CONES,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.PINE_TAR,
            5,
            100,
            false,
            0,
            new ArrayList<>(Arrays.asList(Season.SPRING , Season.SUMMER ,Season.AUTUMN , Season.WINTER))
    ),
    MAHOGANY_TREE(
            SeedType.MAHOGANY_SEEDS,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.SAP,
            1,
            2,
            true,
            -2,
            new ArrayList<>(Arrays.asList(Season.SPRING , Season.SUMMER ,Season.AUTUMN , Season.WINTER))
    ),
    MUSHROOM_TREE(
            SeedType.MUSHROOM_TREE_SEEDS,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.COMMON_MUSHROOM,
            1,
            40,
            true,
            38,
            new ArrayList<>(Arrays.asList(Season.SPRING , Season.SUMMER ,Season.AUTUMN , Season.WINTER))
    ),
    MYSTIC_TREE(
            SeedType.MYSTIC_TREE_SEEDS,
            new ArrayList<>(Arrays.asList(7,7,7,7)),
            28,
            FruitType.MYSTIC_SYRUP,
            7,
                    1000,
                    true,
                    500,
                    new ArrayList<>(Arrays.asList(Season.SPRING , Season.SUMMER ,Season.AUTUMN , Season.WINTER))
            );

    public final SeedType seedType;
    public final ArrayList<Integer> stages;
    public final int totalHarvestTime;
    public final FruitType fruitType;
    public final int harvestCycle;
    public final int basePrice;
    public final boolean edible;
    public final int energy;
    public final ArrayList<Season> season;

    TreeType(
            SeedType seedType,
            ArrayList<Integer> stages,
            int totalHarvestTime,
            FruitType fruitType,
            int harvestCycle,
            int basePrice,
            boolean edible,
            int energy,
            ArrayList<Season> season
    ) {
        this.seedType = seedType;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.fruitType = fruitType;
        this.harvestCycle = harvestCycle;
        this.basePrice = basePrice;
        this.edible = edible;
        this.energy = energy;
        this.season = season;
    }

    public SeedType getSeedType() {
        return seedType;
    }

    public ArrayList<Integer> getStages() {
        return stages;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public FruitType getFruitType() {
        return fruitType;
    }

    public int getHarvestCycle() {
        return harvestCycle;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public boolean isEdible() {
        return edible;
    }

    public int getEnergy() {
        return energy;
    }

    public ArrayList<Season> getSeason() {
        return season;
    }

    @Override
    public String toString() {
        return "Tree Type: " + this.name().replace("_", " ") + "\n" +
                "Seed Type: " + seedType + "\n" +
                "Growth Stages Duration (days): " + stages + "\n" +
                "Total Harvest Time: " + totalHarvestTime + " days\n" +
                "Fruit Produced: " + fruitType + "\n" +
                "Harvest Cycle: Every " + harvestCycle + " days\n" +
                "Base Price: " + basePrice + " coins\n" +
                "Edible: " + (edible ? "Yes" : "No") + "\n" +
                "Energy Restored: " + energy + "\n" +
                "Growing Season: " + (season.size() > 1 ? "Special" : season) + "\n" +
                "-----------------------------\n";
    }

    public static TreeType getTreeTypeByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        String normalized = name.trim().replace(" ", "_").toUpperCase();
        for (TreeType type : TreeType.values()) {
            if (type.name().equalsIgnoreCase(normalized)) {
                return type;
                }
            }
        return null;
    }

}


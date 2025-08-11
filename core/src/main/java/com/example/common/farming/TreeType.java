package com.example.common.farming;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.time.Season;

import com.example.client.views.GameAssetManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TreeType {
    APRICOT_TREE(
        SeedType.APRICOT_SAPLING,
        new ArrayList<>(Arrays.asList(GameAssetManager.apricot_tree_1, GameAssetManager.apricot_tree_2,
            GameAssetManager.apricot_tree_3, GameAssetManager.apricot_tree_4, GameAssetManager.apricot_tree_5)),
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
        new ArrayList<>(Arrays.asList(GameAssetManager.cherry_tree_1, GameAssetManager.cherry_tree_2,
            GameAssetManager.cherry_tree_3, GameAssetManager.cherry_tree_4, GameAssetManager.cherry_tree_5)),
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
        new ArrayList<>(Arrays.asList(GameAssetManager.banana_tree_1, GameAssetManager.banana_tree_2,
            GameAssetManager.banana_tree_3, GameAssetManager.banana_tree_4, GameAssetManager.banana_tree_5)),
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
        new ArrayList<>(Arrays.asList(GameAssetManager.mango_tree_1, GameAssetManager.mango_tree_2,
            GameAssetManager.mango_tree_3, GameAssetManager.mango_tree_4, GameAssetManager.mango_tree_5)),
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
        new ArrayList<>(Arrays.asList(GameAssetManager.orange_tree_1, GameAssetManager.orange_tree_2,
            GameAssetManager.orange_tree_3, GameAssetManager.orange_tree_4, GameAssetManager.orange_tree_5)),
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
        new ArrayList<>(Arrays.asList(GameAssetManager.peach_tree_1, GameAssetManager.peach_tree_2,
            GameAssetManager.peach_tree_3, GameAssetManager.peach_tree_4, GameAssetManager.peach_tree_5)),
        new ArrayList<>(Arrays.asList(7,7,7,7)),
        28,
        FruitType.PEACH, // Corrected from MANGO
        1,
        140,
        true,
        38,
        new ArrayList<>(List.of(Season.SUMMER))
    ),
    APPLE_TREE(
        SeedType.APPLE_SAPLING,
        new ArrayList<>(Arrays.asList(GameAssetManager.apple_tree_1, GameAssetManager.apple_tree_2,
            GameAssetManager.apple_tree_3, GameAssetManager.apple_tree_4, GameAssetManager.apple_tree_5)),
        new ArrayList<>(Arrays.asList(7,7,7,7)),
        28,
        FruitType.APPLE, // Corrected from MANGO
        1,
        100,
        true,
        38,
        new ArrayList<>(List.of(Season.AUTUMN))
    ),
    POMEGRANATE_TREE(
        SeedType.POMEGRANATE_SAPLING,
        new ArrayList<>(Arrays.asList(GameAssetManager.pomegranate_tree_1, GameAssetManager.pomegranate_tree_2,
            GameAssetManager.pomegranate_tree_3, GameAssetManager.pomegranate_tree_4, GameAssetManager.pomegranate_tree_5)),
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
        new ArrayList<>(Arrays.asList(GameAssetManager.oak_tree_1, GameAssetManager.oak_tree_2,
            GameAssetManager.oak_tree_3, GameAssetManager.oak_tree_4, GameAssetManager.oak_tree_5)),
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
        new ArrayList<>(Arrays.asList(GameAssetManager.maple_tree_1, GameAssetManager.maple_tree_2,
            GameAssetManager.maple_tree_3, GameAssetManager.maple_tree_4, GameAssetManager.maple_tree_5)),
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
        new ArrayList<>(Arrays.asList(GameAssetManager.pine_tree_1, GameAssetManager.pine_tree_2,
            GameAssetManager.pine_tree_3, GameAssetManager.pine_tree_4, GameAssetManager.pine_tree_5)),
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
        new ArrayList<>(Arrays.asList(GameAssetManager.mahogany_tree_1, GameAssetManager.mahogany_tree_2,
            GameAssetManager.mahogany_tree_3, GameAssetManager.mahogany_tree_4, GameAssetManager.mahogany_tree_5)),
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
        new ArrayList<>(Arrays.asList(GameAssetManager.mushroom_tree_1, GameAssetManager.mushroom_tree_2,
            GameAssetManager.mushroom_tree_3, GameAssetManager.mushroom_tree_4, GameAssetManager.mushroom_tree_5)),
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
        new ArrayList<>(Arrays.asList(GameAssetManager.mystic_tree_1, GameAssetManager.mystic_tree_2,
            GameAssetManager.mystic_tree_3, GameAssetManager.mystic_tree_4, GameAssetManager.mystic_tree_5)),
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
    private final ArrayList<Sprite> sprites;

    TreeType(
            SeedType seedType,
            ArrayList<Sprite> sprites,
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
        this.sprites = sprites;
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

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }
    public String getName() {
        return this.name().toLowerCase().replaceAll("_", " ");
    }
}


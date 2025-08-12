package com.example.common.farming;


public enum SeedType  implements Seedable  {

    BANANA_SAPLING("", 0),
    MANGO_SAPLING("", 0),
    ACORNS("", 0),
    MAPLE_SEEDS("", 0),
    PINE_CONES("", 0),
    MAHOGANY_SEEDS("", 0),
    MUSHROOM_TREE_SEEDS("", 0),
    MYSTIC_TREE_SEEDS("", 0),
    APPLE_SAPLING("Takes 28 days to produce a mature Apple tree.", 4000),
    APRICOT_SAPLING("Takes 28 days to produce a mature Apricot tree.", 2000),
    CHERRY_SAPLING("Takes 28 days to produce a mature Cherry tree.", 3400),
    ORANGE_SAPLING("Takes 28 days to produce a mature Orange tree.", 4000),
    PEACH_SAPLING("Takes 28 days to produce a mature Peach tree.", 6000),
    POMEGRANATE_SAPLING("Takes 28 days to produce a mature Pomegranate tree.", 6000);

    public final String description;
    public final int price;

    SeedType(String description, int price) {
        this.description = description;
        this.price = price;
    }

    public static TreeType getTreeOfSeedType(SeedType seedType) {
        switch (seedType) {
            case APRICOT_SAPLING: return TreeType.APRICOT_TREE;
            case CHERRY_SAPLING: return TreeType.CHERRY_TREE;
            case BANANA_SAPLING: return TreeType.BANANA_TREE;
            case MANGO_SAPLING: return TreeType.MANGO_TREE;
            case ORANGE_SAPLING: return TreeType.ORANGE_TREE;
            case PEACH_SAPLING: return TreeType.PEACH_TREE;
            case APPLE_SAPLING: return TreeType.APPLE_TREE;
            case POMEGRANATE_SAPLING: return TreeType.POMEGRANATE_TREE;
            case ACORNS: return TreeType.OAK_TREE;
            case MAPLE_SEEDS: return TreeType.MAPLE_TREE;
            case PINE_CONES: return TreeType.PINE_TREE;
            case MAHOGANY_SEEDS: return TreeType.MAHOGANY_TREE;
            case MUSHROOM_TREE_SEEDS: return TreeType.MUSHROOM_TREE;
            case MYSTIC_TREE_SEEDS: return TreeType.MYSTIC_TREE;
            default: return null;
        }
    }

    public static SeedType getByName(String name) {
        String simplifiedName = name.trim().toLowerCase();
        switch (simplifiedName) {
            case "apricot_sapling": return SeedType.APRICOT_SAPLING;
            case "cherry_sapling": return SeedType.CHERRY_SAPLING;
            case "banana_sapling": return SeedType.BANANA_SAPLING;
            case "mango_sapling": return SeedType.MANGO_SAPLING;
            case "orange_sapling": return SeedType.ORANGE_SAPLING;
            case "peach_sapling": return SeedType.PEACH_SAPLING;
            case "apple_sapling": return SeedType.APPLE_SAPLING;
            case "pomegranate_sapling": return SeedType.POMEGRANATE_SAPLING;
            case "acorns": return SeedType.ACORNS;
            case "maple_seeds": return SeedType.MAPLE_SEEDS;
            case "pine_cones": return SeedType.PINE_CONES;
            case "mahogany_seeds": return SeedType.MAHOGANY_SEEDS;
            case "mushroom_tree_seeds": return SeedType.MUSHROOM_TREE_SEEDS;
            case "mystic_tree seeds": return SeedType.MYSTIC_TREE_SEEDS;
            default: return null;
        }
    }


    public String getName() {
        return name().toLowerCase().replaceAll("_", " ");
    }
}

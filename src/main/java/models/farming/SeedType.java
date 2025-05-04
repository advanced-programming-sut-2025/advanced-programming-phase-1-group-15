package models.farming;


public enum SeedType  implements Seedable  {
    APRICOT_SAPLING,
    CHERRY_SAPLING,
    BANANA_SAPLING,
    MANGO_SAPLING,
    ORANGE_SAPLING,
    PEACH_SAPLING,
    APPLE_SAPLING,
    POMEGRANATE_SAPLING,
    ACORNS,
    MAPLE_SEEDS,
    PINE_CONES,
    MAHOGANY_SEEDS,
    MUSHROOM_TREE_SEEDS,
    MYSTIC_TREE_SEEDS;

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
            case "apricot sapling": return SeedType.APRICOT_SAPLING;
            case "cherry sapling": return SeedType.CHERRY_SAPLING;
            case "banana sapling": return SeedType.BANANA_SAPLING;
            case "mango sapling": return SeedType.MANGO_SAPLING;
            case "orange sapling": return SeedType.ORANGE_SAPLING;
            case "peach sapling": return SeedType.PEACH_SAPLING;
            case "apple sapling": return SeedType.APPLE_SAPLING;
            case "pomegranate sapling": return SeedType.POMEGRANATE_SAPLING;
            case "acorns": return SeedType.ACORNS;
            case "maple seeds": return SeedType.MAPLE_SEEDS;
            case "pine cones": return SeedType.PINE_CONES;
            case "mahogany seeds": return SeedType.MAHOGANY_SEEDS;
            case "mushroom tree seeds": return SeedType.MUSHROOM_TREE_SEEDS;
            case "mystic tree seeds": return SeedType.MYSTIC_TREE_SEEDS;
            default: return null;
        }
    }
}

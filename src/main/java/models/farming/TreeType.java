package models.farming;

import models.time.Season;

public enum TreeType {
    APRICOT_TREE(
            SeedType.APRICOT_SAPLING,
            "7-7-7-7",
            28,
            FruitType.APRICOT,
            1,
            59,
            true,
            38,
            Season.SPRING
    );

    public final SeedType seedType;
    public final String stages;
    public final int totalHarvestTime;
    public final FruitType fruitType;
    public final int harvestCycle;
    public final int basePrice;
    public final boolean edible;
    public final int energy;
    public final Season season;

    TreeType(
            SeedType seedType,
            String stages,
            int totalHarvestTime,
            FruitType fruitType,
            int harvestCycle,
            int basePrice,
            boolean edible,
            int energy,
            Season season
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

    public void displayInfo() {
        System.out.println("Tree Type: " + this.name().replace("_", " "));
        System.out.println("Seed Type: " + seedType);
        System.out.println("Growth Stages Duration (days): " + stages);
        System.out.println("Total Harvest Time: " + totalHarvestTime + " days");
        System.out.println("Fruit Produced: " + fruitType);
        System.out.println("Harvest Cycle: Every " + harvestCycle + " days");
        System.out.println("Base Price: " + basePrice + " coins");
        System.out.println("Edible: " + (edible ? "Yes" : "No"));
        System.out.println("Energy Restored: " + energy);
        System.out.println("Growing Season: " + season);
        System.out.println("-----------------------------");
    }
}


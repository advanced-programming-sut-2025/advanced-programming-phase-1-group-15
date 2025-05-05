package models.animals;

import models.tools.BackPackable;

public class Fish implements BackPackable {
    private final FishType fishType;
    public Fish(FishType fishType) {
        this.fishType = fishType;
    }
    @Override
    public String getName() {
        return fishType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getPrice() {
        return fishType.basePrice;
    }
}

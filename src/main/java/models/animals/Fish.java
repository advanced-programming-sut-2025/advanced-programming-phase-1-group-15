package models.animals;

import models.map.Tilable;
import models.tools.BackPackable;

public class Fish implements BackPackable, Tilable {
    private final FishType fishType;
    private ProductQuality quality;
    private final int price;

    public Fish(FishType fishType) {
        this.fishType = fishType;
        this.quality = ProductQuality.NORMAL;
        this.price = fishType.basePrice;
    }
    public Fish(FishType fishType, ProductQuality quality) {
        this.fishType = fishType;
        this.quality = quality;
        this.price = fishType.basePrice;
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
        return switch (quality) {
            case NORMAL -> price;
            case SILVER -> (int) (1.25 * price);
            case GOLD -> (int) (1.5 * price);
            case IRIDIUM -> 2 * price;
        };
    }
}

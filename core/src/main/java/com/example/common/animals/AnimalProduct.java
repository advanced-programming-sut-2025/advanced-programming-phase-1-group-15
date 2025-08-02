package com.example.common.animals;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.tools.BackPackable;

public class AnimalProduct  implements BackPackable {
    private final AnimalProductType animalProductType;
    private ProductQuality productQuality;
    private final int price;

    public AnimalProduct(AnimalProductType animalProductType) {
        this.animalProductType = animalProductType;
        this.price = animalProductType.price;
    }
    public AnimalProduct(AnimalProductType animalProductType, ProductQuality productQuality) {
        this.animalProductType = animalProductType;
        this.productQuality = productQuality;
        this.price = animalProductType.price;
    }

    public AnimalProductType getAnimalProductType() {
        return animalProductType;
    }
    public ProductQuality getProductQuality() {
        return productQuality;
    }

    @Override
    public String getName() {
        return animalProductType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getPrice() {
        return switch (productQuality) {
            case NORMAL -> price;
            case SILVER -> (int) (1.25 * price);
            case GOLD -> (int) (1.5 * price);
            case IRIDIUM -> 2 * price;
        };
    }

    @Override
    public Sprite getSprite() {
        return animalProductType.sprite;
    }
}

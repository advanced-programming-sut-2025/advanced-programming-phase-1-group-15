package models.animals;

import models.tools.BackPackable;

public class AnimalProduct  implements BackPackable{
    private AnimalProductType animalProductType;
    private int count;
    private ProductQuality productQuality;
    private int price;

    public AnimalProduct(AnimalProductType animalProductType) {
        this.animalProductType = animalProductType;
        this.price = animalProductType.price;
    }
    public AnimalProduct(AnimalProductType animalProductType, int count, ProductQuality productQuality) {
        this.animalProductType = animalProductType;
        this.count = count;
        this.productQuality = productQuality;
        this.price = animalProductType.price;
    }
    public AnimalProductType getProductType() {
        return animalProductType;
    }

    public int getCount() {
        return count;
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
}

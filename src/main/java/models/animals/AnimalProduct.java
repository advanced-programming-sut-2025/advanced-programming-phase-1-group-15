package models.animals;

import models.tools.BackPackable;

public class AnimalProduct  implements BackPackable{
    private AnimalProductType animalProductType;
    private int count;

    public AnimalProduct(AnimalProductType animalProductType) {
        this.animalProductType = animalProductType;
    }
    public AnimalProductType getProductType() {
        return animalProductType;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String getName() {
        return animalProductType.name().toLowerCase().replace("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getPrice() {
        return animalProductType.getPrice();
    }
}

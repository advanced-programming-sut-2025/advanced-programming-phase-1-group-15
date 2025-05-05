package models.tools;

import models.Player;
import models.animals.Animal;
import models.animals.AnimalProduct;
import models.animals.AnimalType;
import models.map.Tile;

public class MilkPail extends Tool {
    public MilkPail() {
        this.toolType = ToolType.MILK_PAIL;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "gather milk from your animals.";
    }

    public boolean successfulAttempt(Tile tile) {
        if(tile.isEmpty() || !(tile.getObjectInTile() instanceof Animal animal)) {
            return false;
        }
        return animal.getAnimalType().equals(AnimalType.COW) || animal.getAnimalType().equals(AnimalType.GOAT);
    }

    @Override
    public String use(Tile tile, Player user) {
        int energyConsume = 4;
        if(energyConsume > user.getEnergy()) {
            return "you do not have enough energy to use this tool.";
        }

        user.subtractEnergy(energyConsume);
        if(successfulAttempt(tile)) {
            Animal animal = (Animal) tile.getObjectInTile();
            if(animal.getCurrentProduct() == null) {
                return "no milk to collect!";
            }
            else {
                AnimalProduct product = animal.getCurrentProduct();
                user.getInventory().addToBackPack(product, 1);
                animal.setCurrentProduct(null);

                return 1 + " " + product.getName() + " added to your inventory.\n" + energyConsume + " energy has been consumed.";
            }
        }
        else {
            return "unsuccessful attempt! " + energyConsume + " energy has been consumed.";
        }
    }
}

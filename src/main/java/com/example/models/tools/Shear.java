package com.example.models.tools;

import com.example.models.Player;
import com.example.models.animals.Animal;
import com.example.models.animals.AnimalProduct;
import com.example.models.animals.AnimalType;
import com.example.models.map.Tile;

public class Shear extends Tool implements BackPackable {
    public Shear() {
        this.toolType = ToolType.SHEAR;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "use this to collect wool from sheep.";
    }

    public boolean successfulAttempt(Tile tile) {
        if(tile.isEmpty() || !(tile.getObjectInTile() instanceof Animal animal)) {
            return false;
        }
        else return animal.getAnimalType().equals(AnimalType.SHEEP);
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
                return "no wool to collect!";
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

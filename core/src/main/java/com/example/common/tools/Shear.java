package com.example.common.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.animals.Animal;
import com.example.common.animals.AnimalProduct;
import com.example.common.animals.AnimalType;
import com.example.common.map.Tile;
import com.example.client.views.GameAssetManager;

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
    public String upgrade(Player user) {
        return "this tool is not upgradable.";
    }

    @Override
    public Result use(Tile tile, Player user) {
        int energyConsume = 4;
        if(energyConsume > user.getEnergy()) {
            return new Result(false, "you do not have enough energy to use this tool.");
        }

        user.subtractEnergy(energyConsume);
        if(successfulAttempt(tile)) {
            Animal animal = (Animal) tile.getObjectInTile();
            if(animal.getCurrentProduct() == null) {
                return new Result(false, "no wool to collect!");
            }
            else {
                AnimalProduct product = animal.getCurrentProduct();
                user.getInventory().addToBackPack(product, 1);
                animal.setCurrentProduct(null);

                return new Result(true, 1 + " " + product.getName() + " added to your inventory.\n" + energyConsume + " energy has been consumed.");
            }
        }
        else {
            return new Result(false, "unsuccessful attempt! " + energyConsume + " energy has been consumed.");
        }
    }

    @Override
    public Sprite getSprite() {
        return GameAssetManager.shear;
    }
}

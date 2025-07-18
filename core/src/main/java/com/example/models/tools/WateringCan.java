package com.example.models.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.Player;
import com.example.models.map.AreaType;
import com.example.models.map.Tile;
import com.example.views.GameAssetManager;

public class WateringCan extends Tool {
    private int waterAmount;
    private int capacity;

    public WateringCan() {
        this.toolType = ToolType.WATERING_CAN;
        this.toolLevel = ToolLevel.NORMAL;
        this.waterAmount = 0;
        this.capacity = 40;
        this.description = "don't forget to water your plants everyday.";
    }

    public boolean successfulAttempt(Tile tile) {
        return (waterAmount < capacity && tile.getAreaType().equals(AreaType.LAKE)) ||
                (waterAmount > 0 && (tile.getAreaType().equals(AreaType.FARM) || tile.getAreaType().equals(AreaType.GREENHOUSE)));
    }

    @Override
    public int calculateEnergyConsume(Tile tile, Player user) {
        if(successfulAttempt(tile)) {
            return switch (toolLevel) {
                case NORMAL -> user.getFarmingLevel() == 4 ? 4 : 5;
                case COPPER -> user.getFarmingLevel() == 4 ? 3 : 4;
                case IRON -> user.getFarmingLevel() == 4 ? 2 : 3;
                case GOLD -> user.getFarmingLevel() == 4 ? 1 : 2;
                case IRIDIUM -> user.getFarmingLevel() == 4 ? 0 : 1;
                default -> 0;
            };
        }
        else {
            return switch (toolLevel) {
                case NORMAL -> user.getFarmingLevel() == 4 ? 3 : 4;
                case COPPER -> user.getFarmingLevel() == 4 ? 2 : 3;
                case IRON -> user.getFarmingLevel() == 4 ? 1 : 2;
                case GOLD -> user.getFarmingLevel() == 4 ? 0 : 1;
                case IRIDIUM -> 0;
                default -> 0;
            };
        }
    }

    @Override
    public String upgrade(Player user) {
        switch (toolLevel) {
            case NORMAL -> {
                if(user.getInventory().getItemCount("copper") < 5) {
                    return "not enough cooper!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("copper"), 5);
                toolLevel = ToolLevel.COPPER;
                capacity = 70;
                return "your axe upgraded to COPPER!";
            }
            case COPPER -> {
                if(user.getInventory().getItemCount("iron") < 5) {
                    return "not enough iron!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iron"), 5);
                toolLevel = ToolLevel.IRON;
                return "your axe upgraded to IRON!";
            }
            case IRON -> {
                if(user.getInventory().getItemCount("gold") < 5) {
                    return "not enough gold!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("gold"), 5);
                toolLevel = ToolLevel.GOLD;
                capacity = 85;
                return "your axe upgraded to GOLD!";
            }
            case GOLD -> {
                if(user.getInventory().getItemCount("iridium") < 5) {
                    return "not enough iridium!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iridium"), 5);
                toolLevel = ToolLevel.IRIDIUM;
                capacity = 100;
                return "your axe upgraded to IRIDIUM!";
            }
        }

        return "you can't upgrade this tool.";
    }

    @Override
    public String use(Tile tile, Player user) {
        int energyConsume = calculateEnergyConsume(tile, user);
        if(energyConsume > user.getEnergy()) {
            return "you do not have enough energy to use this tool.";
        }

        if(waterAmount == capacity && tile.getAreaType().equals(AreaType.LAKE)) {
            return "can already filled!";
        }
        else if(waterAmount == 0 && (tile.getAreaType().equals(AreaType.FARM) || tile.getAreaType().equals(AreaType.GREENHOUSE))) {
            return "fill the can first!";
        }
        else if(waterAmount < capacity && tile.getAreaType().equals(AreaType.LAKE)) {
            user.subtractEnergy(energyConsume);
            waterAmount = capacity;
            return "can filled. " + energyConsume + " energy has been consumed.";
        }
        else if(waterAmount > 0 && (tile.getAreaType().equals(AreaType.FARM) || tile.getAreaType().equals(AreaType.GREENHOUSE))) {
            user.subtractEnergy(energyConsume);
            waterAmount--;
            tile.water();
            return "tile " + tile.getPosition() + " watered.\n" + energyConsume + " energy has been consumed.";
        }
        else {
            user.subtractEnergy(energyConsume);
            return "unsuccessful attempt! " + energyConsume + " energy has been consumed.";
        }
    }

    @Override
    public Sprite getSprite() {
        switch (toolLevel) {
            case COPPER -> {
                return GameAssetManager.copper_watering_can;
            }
            case IRON -> {
                return GameAssetManager.iron_watering_can;
            }
            case GOLD -> {
                return GameAssetManager.gold_watering_can;
            }
            case IRIDIUM -> {
                return GameAssetManager.iridium_watering_can;
            }
            default -> {
                return GameAssetManager.watering_can;
            }
        }
    }
}

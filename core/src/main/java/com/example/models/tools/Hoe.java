package com.example.models.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.Player;
import com.example.models.Result;
import com.example.models.map.AreaType;
import com.example.models.map.Tile;
import com.example.views.GameAssetManager;

public class Hoe extends Tool {
    public Hoe() {
        this.toolType = ToolType.HOE;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "prepare your land for planting crops.";
    }

    @Override
    public int calculateEnergyConsume(Tile tile, Player user) {
        return switch (toolLevel) {
            case NORMAL -> 5;
            case COPPER -> 4;
            case IRON -> 3;
            case GOLD -> 2;
            case IRIDIUM -> 1;
            default -> 0;
        };
    }

    @Override
    public boolean isUpgradable(Player user) {
        switch (toolLevel) {
            case NORMAL -> {
                return user.getInventory().getItemCount("copper") >= 5;
            }
            case COPPER -> {
                return user.getInventory().getItemCount("iron") >= 5;
            }
            case IRON -> {
                return user.getInventory().getItemCount("gold") >= 5;
            }
            case GOLD -> {
                return user.getInventory().getItemCount("iridium") >= 5;
            }
        }

        return false;
    }

    @Override
    public String upgrade(Player user) {
        switch (toolLevel) {
            case NORMAL -> {
                if(user.getInventory().getItemCount("copper") < 5) {
                    return "you need to have at least 5 copper.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("copper"), 5);
                toolLevel = ToolLevel.COPPER;
                return "your hoe upgraded to COPPER!";
            }
            case COPPER -> {
                if(user.getInventory().getItemCount("iron") < 5) {
                    return "you need to have at least 5 iron.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iron"), 5);
                toolLevel = ToolLevel.IRON;
                return "your hoe upgraded to IRON!";
            }
            case IRON -> {
                if(user.getInventory().getItemCount("gold") < 5) {
                    return "you need to have at least 5 gold.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("gold"), 5);
                toolLevel = ToolLevel.GOLD;
                return "your hoe upgraded to GOLD!";
            }
            case GOLD -> {
                if(user.getInventory().getItemCount("iridium") < 5) {
                    return "you need to have at least 5 iridium.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iridium"), 5);
                toolLevel = ToolLevel.IRIDIUM;
                return "your hoe upgraded to IRIDIUM!";
            }
        }

        return "this tool is at maximum level!";
    }

    @Override
    public Result use(Tile tile, Player user) {
        int energyConsume = calculateEnergyConsume(tile, user);
        if(energyConsume > user.getEnergy()) {
            return new Result(false, "you do not have enough energy to use this tool.");
        }
        else if(!tile.getAreaType().equals(AreaType.FARM) && !tile.getAreaType().equals(AreaType.GREENHOUSE)) {
            user.subtractEnergy(energyConsume);
            return new Result(false, "you can only use hoe on your farm or greenhouse tiles.");
        }
        else if(tile.isPlowed()) {
            return new Result(false, "this tile is already plowed!");
        }
        else if(!tile.isEmpty()) {
            return new Result(false, "this tile isn't empty!");
        }

        tile.plow();
        user.subtractEnergy(energyConsume);
        return new Result(true, "tile plowed!\n" + energyConsume + " energy has been consumed.");
    }

    @Override
    public Sprite getSprite() {
        switch (toolLevel) {
            case COPPER -> {
                return GameAssetManager.copper_hoe;
            }
            case IRON -> {
                return GameAssetManager.iron_hoe;
            }
            case GOLD -> {
                return GameAssetManager.gold_hoe;
            }
            case IRIDIUM -> {
                return GameAssetManager.iridium_hoe;
            }
            default -> {
                return GameAssetManager.hoe;
            }
        }
    }
}

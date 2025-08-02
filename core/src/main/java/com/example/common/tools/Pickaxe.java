package com.example.common.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.foraging.ForagingMineral;
import com.example.common.map.Tile;
import com.example.client.views.GameAssetManager;

public class Pickaxe extends Tool {
    public Pickaxe() {
        this.toolType = ToolType.PICKAXE;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "can break small rocks anywhere.";
    }

    public boolean successfulAttempt(Tile tile) {
        if(tile.isPlowed()) {
            return true;
        }
        else return tile.getObjectInTile() instanceof ForagingMineral;
    }

    @Override
    public int calculateEnergyConsume(Tile tile, Player user) {
        if(successfulAttempt(tile)) {
            return switch (toolLevel) {
                case NORMAL -> user.getMiningLevel() == 4 ? 4 : 5;
                case COPPER -> user.getMiningLevel() == 4 ? 3 : 4;
                case IRON -> user.getMiningLevel() == 4 ? 2 : 3;
                case GOLD -> user.getMiningLevel() == 4 ? 1 : 2;
                case IRIDIUM -> user.getMiningLevel() == 4 ? 0 : 1;
                default -> 0;
            };
        }
        else {
            return switch (toolLevel) {
                case NORMAL -> user.getMiningLevel() == 4 ? 3 : 4;
                case COPPER -> user.getMiningLevel() == 4 ? 2 : 3;
                case IRON -> user.getMiningLevel() == 4 ? 1 : 2;
                case GOLD -> user.getMiningLevel() == 4 ? 0 : 1;
                case IRIDIUM -> 0;
                default -> 0;
            };
        }
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
                return "your pickaxe upgraded to COPPER!";
            }
            case COPPER -> {
                if(user.getInventory().getItemCount("iron") < 5) {
                    return "you need to have at least 5 iron.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iron"), 5);
                toolLevel = ToolLevel.IRON;
                return "your pickaxe upgraded to IRON!";
            }
            case IRON -> {
                if(user.getInventory().getItemCount("gold") < 5) {
                    return "you need to have at least 5 gold.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("gold"), 5);
                toolLevel = ToolLevel.GOLD;
                return "your pickaxe upgraded to GOLD!";
            }
            case GOLD -> {
                if(user.getInventory().getItemCount("iridium") < 5) {
                    return "you need to have at least 5 iridium.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iridium"), 5);
                toolLevel = ToolLevel.IRIDIUM;
                return "your pickaxe upgraded to IRIDIUM!";
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

        user.subtractEnergy(energyConsume);
        if(successfulAttempt(tile)) {
            if(tile.isPlowed()) {
                tile.unplow();
                return new Result(true, "tile unplowed.\n" + energyConsume + " energy has been consumed.");
            }
            else if(tile.getObjectInTile() instanceof ForagingMineral fm) {
                int count = user.getMiningLevel() >= 2 ? 2 : 1;

                user.addToBackPack(fm, count);
                user.upgradeMiningAbility(10);
                tile.empty();

                return new Result(true, count + " " + fm.getName() + " added to your inventory.\n" + energyConsume + " energy has been consumed.");
            }
        }
        else {
            return new Result(false, "unsuccessful attempt! " + energyConsume + " energy has been consumed.");
        }

        return new Result(false, "unsuccessful attempt!");
    }

    @Override
    public Sprite getSprite() {
        switch (toolLevel) {
            case COPPER -> {
                return GameAssetManager.copper_pickaxe;
            }
            case IRON -> {
                return GameAssetManager.iron_pickaxe;
            }
            case GOLD -> {
                return GameAssetManager.gold_pickaxe;
            }
            case IRIDIUM -> {
                return GameAssetManager.iridium_pickaxe;
            }
            default -> {
                return GameAssetManager.pickaxe;
            }
        }
    }
}

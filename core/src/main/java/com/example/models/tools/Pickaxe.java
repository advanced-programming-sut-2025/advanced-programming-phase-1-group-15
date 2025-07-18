package com.example.models.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.Player;
import com.example.models.foraging.ForagingMineral;
import com.example.models.foraging.Stone;
import com.example.models.map.Tile;
import com.example.views.GameAssetManager;

public class Pickaxe extends Tool {
    public Pickaxe() {
        this.toolType = ToolType.PICKAXE;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "can break small rocks anywhere.";
    }

    public boolean successfulAttempt(Tile tile) {
        if(tile.isEmpty()) {
            return tile.isPlowed();
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
    public String upgrade(Player user) {
        switch (toolLevel) {
            case NORMAL -> {
                if(user.getInventory().getItemCount("copper") < 5) {
                    return "not enough cooper!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("copper"), 5);
                toolLevel = ToolLevel.COPPER;
                return "your pickaxe upgraded to COPPER!";
            }
            case COPPER -> {
                if(user.getInventory().getItemCount("iron") < 5) {
                    return "not enough iron!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iron"), 5);
                toolLevel = ToolLevel.IRON;
                return "your pickaxe upgraded to IRON!";
            }
            case IRON -> {
                if(user.getInventory().getItemCount("gold") < 5) {
                    return "not enough gold!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("gold"), 5);
                toolLevel = ToolLevel.GOLD;
                return "your pickaxe upgraded to GOLD!";
            }
            case GOLD -> {
                if(user.getInventory().getItemCount("iridium") < 5) {
                    return "not enough iridium!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iridium"), 5);
                toolLevel = ToolLevel.IRIDIUM;
                return "your pickaxe upgraded to IRIDIUM!";
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

        user.subtractEnergy(energyConsume);
        if(successfulAttempt(tile)) {
            if(tile.isPlowed()) {
                tile.unplow();
                return "tile " + tile.getPosition() + " unplowed.\n" + energyConsume + " energy has been consumed.";
            }
            else if(tile.getObjectInTile() instanceof ForagingMineral fm) {
                int count = user.getMiningLevel() >= 2 ? 2 : 1;
                if(fm instanceof Stone) {
                    count = user.getMiningLevel() >= 2 ? 100 : 50;
                }

                user.addToBackPack(fm, count);
                user.upgradeMiningAbility(10);
                tile.empty();

                return count + " " + fm.getName() + " added to your inventory.\n" + energyConsume + " energy has been consumed.";
            }
        }
        else {
            return "unsuccessful attempt! " + energyConsume + " energy has been consumed.";
        }

        return "unsuccessful attempt!";
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

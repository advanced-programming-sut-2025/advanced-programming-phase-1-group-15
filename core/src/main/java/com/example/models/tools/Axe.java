package com.example.models.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.Player;
import com.example.models.Result;
import com.example.models.farming.Seed;
import com.example.models.farming.Tree;
import com.example.models.map.Tile;
import com.example.models.stores.GeneralItem;
import com.example.models.stores.GeneralItemsType;
import com.example.views.GameAssetManager;

public class Axe extends Tool {
    public Axe() {
        this.toolType = ToolType.AXE;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "chop, split, and shape woods!";
    }

    public boolean successfulAttempt(Tile tile) {
        if(tile.isEmpty()) {
            return false;
        }
        else if(tile.getObjectInTile() instanceof Tree) {
            return true;
        }
        else if(tile.getObjectInTile() instanceof GeneralItem generalItem) {
            return generalItem.getName().equals("wood");
        }
        else {
            return false;
        }
    }

    @Override
    public int calculateEnergyConsume(Tile tile, Player user) {
        if(successfulAttempt(tile)) {
            return switch (toolLevel) {
                case NORMAL -> user.getForagingLevel() == 4 ? 4 : 5;
                case COPPER -> user.getForagingLevel() == 4 ? 3 : 4;
                case IRON -> user.getForagingLevel() == 4 ? 2 : 3;
                case GOLD -> user.getForagingLevel() == 4 ? 1 : 2;
                case IRIDIUM -> user.getForagingLevel() == 4 ? 0 : 1;
                default -> 0;
            };
        }
        else {
            return switch (toolLevel) {
                case NORMAL -> user.getForagingLevel() == 4 ? 3 : 4;
                case COPPER -> user.getForagingLevel() == 4 ? 2 : 3;
                case IRON -> user.getForagingLevel() == 4 ? 1 : 2;
                case GOLD -> user.getForagingLevel() == 4 ? 0 : 1;
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
                return "your axe upgraded to COPPER!";
            }
            case COPPER -> {
                if(user.getInventory().getItemCount("iron") < 5) {
                    return "you need to have at least 5 iron.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iron"), 5);
                toolLevel = ToolLevel.IRON;
                return "your axe upgraded to IRON!";
            }
            case IRON -> {
                if(user.getInventory().getItemCount("gold") < 5) {
                    return "you need to have at least 5 gold.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("gold"), 5);
                toolLevel = ToolLevel.GOLD;
                return "your axe upgraded to GOLD!";
            }
            case GOLD -> {
                if(user.getInventory().getItemCount("iridium") < 5) {
                    return "you need to have at least 5 iridium.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iridium"), 5);
                toolLevel = ToolLevel.IRIDIUM;
                return "your axe upgraded to IRIDIUM!";
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
            if(tile.getObjectInTile() instanceof Tree tr) {
                Seed s = new Seed(tr.getSeedType());
                int count = user.getForagingLevel() >= 2 ? 2 : 1;


                user.addToBackPack(s, count);
                user.addToBackPack(new GeneralItem(GeneralItemsType.WOOD), 50);
                user.upgradeForagingAbility(10);
                tile.empty();

                return new Result(true, count + " " + s.getName() + " + 50 wood added to your inventory.\n" + energyConsume + " energy has been consumed.");
            }
            else {
                user.addToBackPack(new GeneralItem(GeneralItemsType.WOOD), 1);
                tile.empty();
                return new Result(true, "1 wood added to your inventory.\n" + energyConsume + " energy has been consumed.");
            }
        }
        else {
            return new Result(false, "unsuccessful attempt! " + energyConsume + " energy has been consumed.");
        }
    }

    @Override
    public Sprite getSprite() {
        switch (toolLevel) {
            case COPPER -> {
                return GameAssetManager.copper_axe;
            }
            case IRON -> {
                return GameAssetManager.iron_axe;
            }
            case GOLD -> {
                return GameAssetManager.gold_axe;
            }
            case IRIDIUM -> {
                return GameAssetManager.iridium_axe;
            }
            default -> {
                return GameAssetManager.axe;
            }
        }
    }
}

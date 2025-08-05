package com.example.common.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.Player;
import com.example.client.views.GameAssetManager;

import java.util.ArrayList;
import java.util.HashMap;

public class TrashCan extends Tool {
    double returnPercentage;
    HashMap<BackPackable , Integer> trashes = new HashMap<>();
    public TrashCan() {
        this.toolType = ToolType.TRASH_CAN;
        this.toolLevel = ToolLevel.NORMAL;
        returnPercentage = 0;
        this.description = "used to delete items from the inventory menu.";
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
                returnPercentage = 0.15;
                return "your trash can upgraded to COPPER!";
            }
            case COPPER -> {
                if(user.getInventory().getItemCount("iron") < 5) {
                    return "you need to have at least 5 iron.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iron"), 5);
                toolLevel = ToolLevel.IRON;
                returnPercentage = 0.3;
                return "your trash can upgraded to IRON!";
            }
            case IRON -> {
                if(user.getInventory().getItemCount("gold") < 5) {
                    return "you need to have at least 5 gold.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("gold"), 5);
                toolLevel = ToolLevel.GOLD;
                returnPercentage = 0.45;
                return "your trash can upgraded to GOLD!";
            }
            case GOLD -> {
                if(user.getInventory().getItemCount("iridium") < 5) {
                    return "you need to have at least 5 iridium.";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iridium"), 5);
                toolLevel = ToolLevel.IRIDIUM;
                returnPercentage = 0.6;
                return "your trash can upgraded to IRIDIUM!";
            }
        }
        return "this tool is at maximum level!";
    }

    public void use(BackPackable item, int amount, Player player) {
        int existing = trashes.getOrDefault(item, 0);
        trashes.put(item, existing + amount);
        player.getInventory().removeCountFromBackPack(item, amount);
    }

    @Override
    public Sprite getSprite() {
        switch (toolLevel) {
            case COPPER -> {
                return GameAssetManager.copper_trash_can;
            }
            case IRON -> {
                return GameAssetManager.iron_trash_can;
            }
            case GOLD -> {
                return GameAssetManager.gold_trash_can;
            }
            case IRIDIUM -> {
                return GameAssetManager.iridium_trash_can;
            }
            default -> {
                return GameAssetManager.trash_can;
            }
        }
    }
    public BackPackable getItemByName(String name) {
        for (BackPackable item : trashes.keySet()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public boolean removeCountFromTrashCan(BackPackable item, int amount) {
        if(trashes.get(item) == null) {
            return false;
        }
        if(amount == trashes.get(item)) {
            removeFromBackPack(item);
            return true;
        }
        if(amount > trashes.get(item)) {
            return false;
        }
        trashes.put(item, trashes.get(item) - amount);
        return true;
    }
    public void removeFromBackPack(BackPackable item) {
        trashes.remove(item);
    }

    public HashMap<BackPackable, Integer> getTrashes() {
        return trashes;
    }
}

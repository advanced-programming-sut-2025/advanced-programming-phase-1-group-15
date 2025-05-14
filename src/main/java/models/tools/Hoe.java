package models.tools;

import models.Player;
import models.map.AreaType;
import models.map.Tile;

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
    public String upgrade(Player user) {
        switch (toolLevel) {
            case NORMAL -> {
                if(user.getInventory().getItemCount("copper") < 5) {
                    return "not enough cooper!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("copper"), 5);
                toolLevel = ToolLevel.COPPER;
                return "your hoe upgraded to COPPER!";
            }
            case COPPER -> {
                if(user.getInventory().getItemCount("iron") < 5) {
                    return "not enough iron!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iron"), 5);
                toolLevel = ToolLevel.IRON;
                return "your hoe upgraded to IRON!";
            }
            case IRON -> {
                if(user.getInventory().getItemCount("gold") < 5) {
                    return "not enough gold!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("gold"), 5);
                toolLevel = ToolLevel.GOLD;
                return "your hoe upgraded to GOLD!";
            }
            case GOLD -> {
                if(user.getInventory().getItemCount("iridium") < 5) {
                    return "not enough iridium!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iridium"), 5);
                toolLevel = ToolLevel.IRIDIUM;
                return "your hoe upgraded to IRIDIUM!";
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
        else if(!tile.getAreaType().equals(AreaType.FARM) && !tile.getAreaType().equals(AreaType.GREENHOUSE)) {
            user.subtractEnergy(energyConsume);
            return "you can only use hoe on your farm or greenhouse tiles.";
        }

        tile.plow();
        user.subtractEnergy(energyConsume);
        return "tile " + tile.getPosition() + " plowed!\n" + energyConsume + " energy has been consumed.";
    }
}

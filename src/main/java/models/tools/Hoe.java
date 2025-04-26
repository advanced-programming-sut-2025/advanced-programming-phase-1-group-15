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
            case COOPER -> 4;
            case IRON -> 3;
            case GOLD -> 2;
            case IRIDIUM -> 1;
            default -> 0;
        };
    }

    @Override
    public void upgrade() {
        switch (toolLevel) {
            case NORMAL -> toolLevel = ToolLevel.COOPER;
            case COOPER -> toolLevel = ToolLevel.IRON;
            case IRON -> toolLevel = ToolLevel.GOLD;
            case GOLD -> toolLevel = ToolLevel.IRIDIUM;
        }
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

package models.tools;

import models.Player;
import models.map.AreaType;
import models.map.Tile;

public class Hoe extends Tool {
    public Hoe() {
        this.toolType = ToolType.HOE;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "prepare your land for planting crops";
    }

    @Override
    public int calculateEnergyConsume(Tile tile, Player user) {
        return switch (toolLevel) {
            case NORMAL -> 5;
            case COOPER -> 4;
            case IRON -> 3;
            case GOLD -> 2;
            case IRIDIUM -> 1;
        };
    }

    @Override
    public void upgrade() {

    }

    @Override
    public String use(Tile tile, Player user) {
        if(calculateEnergyConsume(tile, user) > user.getEnergy()) {
            return "you do not have enough energy to use this tool.";
        }
        else if(!tile.getAreaType().equals(AreaType.FARM) && !tile.getAreaType().equals(AreaType.GREENHOUSE)) {
            user.subtractEnergy(calculateEnergyConsume(tile, user));
            return "you can only use hoe on your farm or greenhouse tiles.";
        }

        tile.plow();
        int energyConsume = calculateEnergyConsume(tile, user);
        user.subtractEnergy(energyConsume);
        return "tile " + tile + " plowed!\n" + energyConsume + " energy has been consumed.";
    }
}

package models.tools;

import models.map.Tile;

public class Hoe extends Tool {
    public Hoe() {
        this.toolType = ToolType.HOE;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "Prepare your land for planting crops.";
    }

    @Override
    public int calculateEnergyConsume(Tile tile) {
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
    public BackPackable use(Tile tile) {
        tile.plow();
        return null;
    }
}

package models.tools;

import models.foraging.ForagingMineral;
import models.map.Tile;

public class Pickaxe extends Tool {
    public Pickaxe() {
        this.toolType = ToolType.PICKAXE;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "Can break small rocks anywhere.";
    }

    public boolean successfulAttempt(Tile tile) {
        if(tile.isEmpty()) {
            return true;
        }
        else return tile.getObjectInTile() instanceof ForagingMineral;
    }

    @Override
    public int calculateEnergyConsume(Tile tile) {
        if(successfulAttempt(tile)) {
            return switch (toolLevel) {
                case NORMAL -> 5;
                case COOPER -> 4;
                case IRON -> 3;
                case GOLD -> 2;
                case IRIDIUM -> 1;
            };
        }
        else {
            return switch (toolLevel) {
                case NORMAL -> 4;
                case COOPER -> 3;
                case IRON -> 2;
                case GOLD -> 1;
                case IRIDIUM -> 0;
            };
        }
    }

    @Override
    public void upgrade() {

    }

    @Override
    public BackPackable use(Tile tile) {
        if(tile.isEmpty()) {
            tile.unplow();
            return null;
        }
        else {
            if(successfulAttempt(tile)) {
                ForagingMineral fm = (ForagingMineral) tile.getObjectInTile();
                tile.empty();
                return fm;
            }
            else {
                tile.empty();
                return null;
            }
        }
    }
}

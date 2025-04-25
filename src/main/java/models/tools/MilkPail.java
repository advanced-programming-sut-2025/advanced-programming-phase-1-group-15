package models.tools;

import models.map.Tile;

public class MilkPail extends Tool {
    public MilkPail() {
        this.toolType = ToolType.MILK_PAIL;
        this.toolLevel = ToolLevel.COOPER;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public BackPackable use(Tile tile) {
        return null;
    }
}

package models.tools;

import models.map.Tile;

public class Scythe extends Tool {
    public Scythe() {
        this.toolType = ToolType.SCYTHE;
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

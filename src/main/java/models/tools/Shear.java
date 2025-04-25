package models.tools;

import models.map.Tile;

public class Shear extends Tool implements BackPackable {
    public Shear() {
        this.toolType = ToolType.SHEAR;
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

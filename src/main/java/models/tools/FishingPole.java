package models.tools;

import models.map.Tile;

public class FishingPole extends Tool {
    public FishingPole() {
        this.toolType = ToolType.FISHING_POLE;
        this.toolLevel = ToolLevel.COOPER;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(Tile tile) {

    }
}

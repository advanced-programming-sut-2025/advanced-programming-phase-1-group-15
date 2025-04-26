package models.tools;

import models.Player;
import models.map.Tile;

public class Axe extends Tool {
    public Axe() {
        this.toolType = ToolType.AXE;
        this.toolLevel = ToolLevel.COOPER;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public String use(Tile tile, Player user) {
        return null;
    }
}

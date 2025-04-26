package models.tools;

import models.Player;
import models.map.Tile;

public class TrashCan extends Tool {
    public TrashCan() {
        this.toolType = ToolType.TRASH_CAN;
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

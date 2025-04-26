package models.tools;

import models.Player;
import models.map.Tile;

public class WateringCan extends Tool {
    public WateringCan() {
        this.toolType = ToolType.WATERING_CAN;
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

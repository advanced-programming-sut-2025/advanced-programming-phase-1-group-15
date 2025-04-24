package models.tools;

import models.map.Position;

public class WateringCan extends Tool {
    public WateringCan() {
        this.toolType = ToolType.WATERING_CAN;
        this.toolLevel = ToolLevel.COOPER;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(Position position) {

    }
}

package models.tools;

import models.map.Position;

public class MilkPail extends Tool {
    public MilkPail() {
        this.name = "Shear";
        this.toolLevel = ToolLevel.COOPER;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(Position position) {

    }
}

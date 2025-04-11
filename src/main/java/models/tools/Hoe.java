package models.tools;

import models.map.Position;

public class Hoe extends Tool {
    public Hoe() {
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

package models.tools;

import models.map.Position;

public class Scythe extends Tool {
    public Scythe() {
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

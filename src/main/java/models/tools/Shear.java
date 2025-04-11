package models.tools;

import models.map.Position;

public class Shear extends Tool implements BackPackable {
    public Shear() {
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

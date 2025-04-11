package models.tools;

import models.map.Position;

public class TrashCan extends Tool {
    public TrashCan() {
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

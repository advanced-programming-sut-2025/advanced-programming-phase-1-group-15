package models.tools;

import models.map.Position;

public class Axe extends Tool {
    public Axe() {
        this.toolType = ToolType.AXE;
        this.toolLevel = ToolLevel.COOPER;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(Position position) {

    }
}

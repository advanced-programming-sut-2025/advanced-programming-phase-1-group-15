package models.tools;

import models.map.Position;

public class Pickaxe extends Tool {
    public Pickaxe() {
        this.toolType = ToolType.PICKAXE;
        this.toolLevel = ToolLevel.COOPER;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(Position position) {

    }
}

package models.tools;

import models.map.Position;

public class Tool implements BackPackable {
    protected ToolType toolType;

    protected int price;

    protected boolean upgradable;
    protected ToolLevel toolLevel;

    protected int energyConsume;

    public void upgrade() {

    }

    public void use(Position position) {

    }

    @Override
    public String getName() {
        return toolType.name().toLowerCase().replaceAll("_", " ");
    }
}

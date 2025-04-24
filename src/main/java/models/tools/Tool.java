package models.tools;

import models.map.Tile;

public class Tool implements BackPackable {
    protected ToolType toolType;

    protected int price;

    protected boolean upgradable;
    protected ToolLevel toolLevel;

    public int calculateEnergyConsume() {
        return 0;
    }

    public void use(Tile tile) {

    }

    public void upgrade() {

    }

    @Override
    public String getName() {
        return toolType.name().toLowerCase().replaceAll("_", " ");
    }
}

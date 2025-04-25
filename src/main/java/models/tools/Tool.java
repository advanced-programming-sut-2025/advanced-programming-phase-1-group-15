package models.tools;

import models.map.Tile;

public class Tool implements BackPackable {
    protected ToolType toolType;
    protected String description;

    protected int price;

    protected boolean upgradable;
    protected ToolLevel toolLevel;

    public int calculateEnergyConsume(Tile tile) {
        return 0;
    }

    public BackPackable use(Tile tile) {
        return null;
    }

    public void upgrade() {

    }

    @Override
    public String getName() {
        return toolType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return description;
    }
}

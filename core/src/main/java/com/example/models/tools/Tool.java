package com.example.models.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.Player;
import com.example.models.map.Tile;

public class Tool implements BackPackable {
    protected ToolType toolType;
    protected String description;

    protected int price;

    protected boolean upgradable;
    protected ToolLevel toolLevel;

    public static Tool toolFactory(ToolType toolType) {
        return switch (toolType) {
            case AXE -> new Axe();
            case HOE -> new Hoe();
            case PICKAXE -> new Pickaxe();
            case SHEAR -> new Shear();
            case SCYTHE -> new Scythe();
            case BACKPACK -> new BackPack();
            case MILK_PAIL -> new MilkPail();
            case TRASH_CAN -> new TrashCan();
            case FISHING_POLE -> new FishingPole();
            case WATERING_CAN -> new WateringCan();
        };
    }

    public int calculateEnergyConsume(Tile tile, Player user) {
        return 0;
    }

    public String use(Tile tile, Player user) {
        return null;
    }

    public String upgrade(Player user) {
        return "";
    }

    @Override
    public String getName() {
        return toolType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public Sprite getSprite() {
        return null;
    }
}

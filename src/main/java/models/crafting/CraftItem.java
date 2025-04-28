package models.crafting;

import models.map.Tilable;
import models.tools.BackPackable;

public class CraftItem implements Tilable, BackPackable {
    private final CraftItemType craftItemType;
    public CraftItem(CraftItemType craftItemType) {
        this.craftItemType = craftItemType;
    }

    public CraftItemType getCraftItemType() {
        return craftItemType;
    }

    @Override
    public String getName() {
        return craftItemType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getPrice() {
        return craftItemType.price;
    }
}

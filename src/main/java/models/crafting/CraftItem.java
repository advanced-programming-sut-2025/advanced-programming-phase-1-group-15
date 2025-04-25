package models.crafting;

import models.map.Tilable;
import models.tools.BackPackable;

public class CraftItem implements Tilable, BackPackable {
    private CraftItemType craftItemType;

    @Override
    public String getName() {
        return craftItemType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }
}

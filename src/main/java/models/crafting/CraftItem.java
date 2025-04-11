package models.crafting;

import models.map.Tilable;
import models.tools.BackPackable;

public class CraftItem implements Tilable, BackPackable {
    private CraftItemType craftItemType;

    public String getRecipe() {
        return craftItemType.recepie;
    }
}

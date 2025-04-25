package models.artisanry;

import models.tools.BackPackable;

public class ArtisanItem implements BackPackable {
    private ArtisanItemType artisanItemType;

    @Override
    public String getName() {
        return artisanItemType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return artisanItemType.description;
    }
}

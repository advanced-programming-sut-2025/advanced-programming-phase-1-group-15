package models.stores;

import models.tools.BackPackable;

public class GeneralItem implements BackPackable {
    GeneralItemsType generalItemsType;
    public GeneralItem(GeneralItemsType generalItemsType) {
        this.generalItemsType = generalItemsType;
    }

    @Override
    public String getName() {
        return generalItemsType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return generalItemsType.description;
    }

    @Override
    public int getPrice() {
        return generalItemsType.price;
    }
}

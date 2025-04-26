package models.foraging;

import models.map.Tilable;
import models.tools.BackPackable;

public class ForagingMineral implements Tilable, BackPackable {
    ForagingMineralType foragingMineralType;
    public ForagingMineral(ForagingMineralType foragingMineralType) {
        this.foragingMineralType = foragingMineralType;
    }
    @Override
    public String getName() {
        return foragingMineralType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return foragingMineralType.description;
    }

    @Override
    public int getPrice() {
        return foragingMineralType.sellPrice;
    }
}

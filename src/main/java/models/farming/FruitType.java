package models.farming;

import models.map.Tilable;
import models.tools.BackPackable;

public enum FruitType implements BackPackable {
    APRICOT,
    CHERRY,
    BANANA,
    MANGO,
    ORANGE,
    PEACH,
    APPLE,
    POMEGRANATE,
    OAK_RESIN,
    MAPLE_SYRUP,
    PINE_TAR,
    SAP,
    COMMON_MUSHROOM,
    MYSTIC_SYRUP;

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getPrice() {
        return 0;
    }
}

package models.foraging;

import models.time.Season;

public enum ForagingMineralType {
    QUARTZ(
            "A clear crystal commonly found in caves and mines.",
            25
    );

    public final String description;
    public final int sellPrice;

    ForagingMineralType(String description, int sellPrice) {
        this.description = description;
        this.sellPrice = sellPrice;
    }
}

package models.farming;

import models.time.Season;

public enum ForagingCropsType {
    DAFFODIL(
            Season.SPRING,
            30,
            0
    );

    public final Season season;
    public final int basePrice;
    public final int energy;

    ForagingCropsType(
            Season season,
            int basePrice,
            int energy
    ) {
        this.season = season;
        this.basePrice = basePrice;
        this.energy = energy;
    }
}

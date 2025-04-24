package models.farming;

import models.time.Season;
import models.tools.BackPackable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MixedSeedCrop {
    CAULIFLOWER(new ArrayList<>(List.of(Season.SPRING))),
    PARSNIP(new ArrayList<>(List.of(Season.SPRING))),
    POTATO(new ArrayList<>(List.of(Season.SPRING))),
    BLUE_JAZZ(new ArrayList<>(List.of(Season.SPRING))),
    TULIP(new ArrayList<>(List.of(Season.SPRING))),
    CORN(new ArrayList<>(Arrays.asList(Season.SUMMER , Season.AUTUMN))),
    HOT_PEPPER(new ArrayList<>(List.of(Season.SUMMER))),
    RADISH(new ArrayList<>(List.of(Season.SUMMER))),
    WHEAT(new ArrayList<>(List.of(Season.SUMMER))),
    POPPY(new ArrayList<>(List.of(Season.SUMMER))),
    SUNFLOWER(new ArrayList<>(Arrays.asList(Season.SUMMER , Season.AUTUMN))),
    SUMMER_SPANGLE(new ArrayList<>(List.of(Season.SUMMER))),
    ARTICHOKE(new ArrayList<>(List.of(Season.AUTUMN))),
    EGGPLANT(new ArrayList<>(List.of(Season.AUTUMN))),
    PUMPKIN(new ArrayList<>(List.of(Season.AUTUMN))),
    FAIRY_ROSE(new ArrayList<>(List.of(Season.AUTUMN))),
    POWDER_MELON(new ArrayList<>(List.of(Season.WINTER)));
    public final ArrayList<Season> season;
    MixedSeedCrop(ArrayList<Season> season) {
        this.season = season;
    }
}

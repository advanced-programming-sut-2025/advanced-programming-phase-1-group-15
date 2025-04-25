package models.farming;

import models.time.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ForagingCropsType {
    DAFFODIL(
            new ArrayList<>(Arrays.asList(Season.SUMMER , Season.AUTUMN, Season.SPRING , Season.WINTER)),
            30,
            0
    ),
    COMMON_MUSHROOM(
            new ArrayList<>(List.of(Season.SPRING)),
            40,
            38
    ),
    DANDELION(
            new ArrayList<>(List.of(Season.SPRING)),
            40,
            25
    ),
    LEEK(
            new ArrayList<>(List.of(Season.SPRING)),
            60,
            40
    ),
    MOREL(
            new ArrayList<>(List.of(Season.SPRING)),
            150,
            20
    ),
    SALMONBERRY(
            new ArrayList<>(List.of(Season.SPRING)),
            5,
            25
    ),
    SPRING_UNION(
            new ArrayList<>(List.of(Season.SPRING)),
            8,
            13
    ),
    WILD_HORSERADISH(
            new ArrayList<>(List.of(Season.SPRING)),
            50,
            13
    ),
    FIDDLE_HEAD_FERN(
            new ArrayList<>(List.of(Season.SUMMER)),
            90,
            25
    ),
    GRAPE(
            new ArrayList<>(List.of(Season.SUMMER)),
            80,
            38
    ),
    RED_MUSHROOM(
            new ArrayList<>(List.of(Season.SUMMER)),
            75,
            -50
    ),
    SPICE_BERRY(
            new ArrayList<>(List.of(Season.SUMMER)),
            80,
            25
    ),
    SWEET_PEA(
            new ArrayList<>(List.of(Season.SUMMER)),
            50,
            0
    ),
    BLACKBERRY(
            new ArrayList<>(List.of(Season.AUTUMN)),
            25,
            25
    ),
    CHANTERELLE(
            new ArrayList<>(List.of(Season.AUTUMN)),
            160,
            75
            ),
    HAZElNUT(
            new ArrayList<>(List.of(Season.AUTUMN)),
            40,
            38
    ),
    PURPLE_MUSHROOM(
            new ArrayList<>(List.of(Season.AUTUMN)),
            90,
            30
    ),
    WILD_PLUM(
            new ArrayList<>(List.of(Season.AUTUMN)),
            80,
            25
    ),
    CROCUS(
            new ArrayList<>(List.of(Season.WINTER)),
            60,
            0
    ),
    CRYSTAL_FRUIT(
            new ArrayList<>(List.of(Season.WINTER)),
            150,
            63
    ),
    HOLLEY(
            new ArrayList<>(List.of(Season.WINTER)),
            80,
            -37
    ),
    SNOW_YAM(
            new ArrayList<>(List.of(Season.WINTER)),
            100,
            30
    ),
    WINTER_ROOT(
            new ArrayList<>(List.of(Season.WINTER)),
            70,
            25
    );

    public final ArrayList<Season> season;
    public final int basePrice;
    public final int energy;

    ForagingCropsType(
            ArrayList<Season> season,
            int basePrice,
            int energy
    ) {
        this.season = season;
        this.basePrice = basePrice;
        this.energy = energy;
    }


}

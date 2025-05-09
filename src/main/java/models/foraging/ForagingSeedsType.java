package models.foraging;

import models.time.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum ForagingSeedsType {
    JAZZ_SEEDS(
            new ArrayList<>(List.of(Season.SPRING))
    ),
    CARROT_SEEDS(
            new ArrayList<>(List.of(Season.SPRING))
    ),
    CAULIFLOWER_SEEDS(
            new ArrayList<>(List.of(Season.SPRING))
    ),
    COFFEE_BEAN(
            new ArrayList<>(List.of(Season.SPRING))
    ),
    GARLIC_SEEDS(
            new ArrayList<>(List.of(Season.SPRING))
    ),
    BEEN_STARTER(
            new ArrayList<>(List.of(Season.SPRING))
    ),
    KALE_SEEDS(
            new ArrayList<>(List.of(Season.SPRING))
    ),
    PARSNIP_SEEDS(
            new ArrayList<>(List.of(Season.SPRING))
    ),
    POTATO_SEEDS(
            new ArrayList<>(List.of(Season.SPRING))
    ),
    RHUBARB_SEEDS(
            new ArrayList<>(List.of(Season.SPRING))
    ),
    STRAWBERRY_SEEDS(
            new ArrayList<>(List.of(Season.SPRING))
    ),
    TULIP_BULB(
            new ArrayList<>(List.of(Season.SPRING))
    ),RICE_SHOOT(
            new ArrayList<>(List.of(Season.SPRING))
    ),BLUEBERRY_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    CORN_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    HOPS_STARTER(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    PEPPER_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    MELON_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    POPPY_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    RADISH_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    RED_CABBAGE_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    STARFRUIT_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    SPANGLE_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    SUMMER_SQUASH_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    SUNFLOWER_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    TOMATO_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    WHEAT_SEEDS(
            new ArrayList<>(List.of(Season.SUMMER))
    ),
    AMARANTH_SEEDS(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    ARTICHOKE_SEEDS(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    BEET_SEEDS(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    BOK_CHOY_SEEDS(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    BROCCOLI_SEEDS(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    CRANBERRY_SEEDS(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    EGGPLANT_SEEDS(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    FAIRY_SEEDS(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    GRAPE_STARTER(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    PUMBPKIN_SEEDS(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    YAM_SEEDS(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    RARE_SEEDS(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    POWDER_MELON_SEEDS(
            new ArrayList<>(List.of(Season.AUTUMN))
    ),
    ANCIENT_SEEDS(
            new ArrayList<>(Arrays.asList(Season.AUTUMN , Season.SUMMER , Season.WINTER , Season.AUTUMN))
    ),
    MIXED_SEEDS(
            new ArrayList<>(Arrays.asList(Season.AUTUMN , Season.SUMMER , Season.WINTER , Season.AUTUMN))
    );
    private final ArrayList<Season> seasons;
    ForagingSeedsType(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public static ForagingSeedsType getSeasonForagingSeed(Season season) {
        List<ForagingSeedsType> possibleSeeds = new ArrayList<>();
        for (ForagingSeedsType seed : ForagingSeedsType.values()) {
            if (seed.seasons.contains(season)) {
                possibleSeeds.add(seed);
            }
        }
        int randomIndex = new Random().nextInt(possibleSeeds.size());
        return possibleSeeds.get(randomIndex);
    }
}

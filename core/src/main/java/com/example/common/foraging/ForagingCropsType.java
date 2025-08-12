package com.example.common.foraging;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.client.views.GameAssetManager;
import com.example.common.RandomGenerator;
import com.example.common.time.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ForagingCropsType {
    DAFFODIL(
            GameAssetManager.daffodil,
            new ArrayList<>(Arrays.asList(Season.SUMMER, Season.AUTUMN, Season.SPRING, Season.WINTER)),
            30,
            0
    ),
    COMMON_MUSHROOM(
            GameAssetManager.common_mushroom,
            new ArrayList<>(List.of(Season.SPRING)),
            40,
            38
    ),
    DANDELION(
            GameAssetManager.dandelion,
            new ArrayList<>(List.of(Season.SPRING)),
            40,
            25
    ),
    LEEK(
            GameAssetManager.leek,
            new ArrayList<>(List.of(Season.SPRING)),
            60,
            40
    ),
    MOREL(
            GameAssetManager.morel,
            new ArrayList<>(List.of(Season.SPRING)),
            150,
            20
    ),
    SALMONBERRY(
            GameAssetManager.salmonberry,
            new ArrayList<>(List.of(Season.SPRING)),
            5,
            25
    ),
    SPRING_UNION(
            GameAssetManager.spring_union,
            new ArrayList<>(List.of(Season.SPRING)),
            8,
            13
    ),
    WILD_HORSERADISH(
            GameAssetManager.wild_horseradish,
            new ArrayList<>(List.of(Season.SPRING)),
            50,
            13
    ),
    FIDDLE_HEAD_FERN(
            GameAssetManager.fiddle_head_fern,
            new ArrayList<>(List.of(Season.SUMMER)),
            90,
            25
    ),
    GRAPE(
            GameAssetManager.grape,
            new ArrayList<>(List.of(Season.SUMMER)),
            80,
            38
    ),
    RED_MUSHROOM(
            GameAssetManager.red_mushroom,
            new ArrayList<>(List.of(Season.SUMMER)),
            75,
            -50
    ),
    SPICE_BERRY(
            GameAssetManager.spice_berry,
            new ArrayList<>(List.of(Season.SUMMER)),
            80,
            25
    ),
    SWEET_PEA(
            GameAssetManager.sweet_pea,
            new ArrayList<>(List.of(Season.SUMMER)),
            50,
            0
    ),
    BLACKBERRY(
            GameAssetManager.blackberry,
            new ArrayList<>(List.of(Season.AUTUMN)),
            25,
            25
    ),
    CHANTERELLE(
            GameAssetManager.chanterelle,
            new ArrayList<>(List.of(Season.AUTUMN)),
            160,
            75
    ),
    HAZElNUT(
            GameAssetManager.hazelnut,
            new ArrayList<>(List.of(Season.AUTUMN)),
            40,
            38
    ),
    PURPLE_MUSHROOM(
            GameAssetManager.purple_mushroom,
            new ArrayList<>(List.of(Season.AUTUMN)),
            90,
            30
    ),
    WILD_PLUM(
            GameAssetManager.wild_plum,
            new ArrayList<>(List.of(Season.AUTUMN)),
            80,
            25
    ),
    CROCUS(
            GameAssetManager.crocus,
            new ArrayList<>(List.of(Season.WINTER)),
            60,
            0
    ),
    CRYSTAL_FRUIT(
            GameAssetManager.crystal_fruit,
            new ArrayList<>(List.of(Season.WINTER)),
            150,
            63
    ),
    HOLLEY(
            GameAssetManager.holley,
            new ArrayList<>(List.of(Season.WINTER)),
            80,
            -37
    ),
    SNOW_YAM(
            GameAssetManager.snow_yam,
            new ArrayList<>(List.of(Season.WINTER)),
            100,
            30
    ),
    WINTER_ROOT(
            GameAssetManager.winter_root,
            new ArrayList<>(List.of(Season.WINTER)),
            70,
            25
    );
    public final Sprite sprite;
    public final ArrayList<Season> season;
    public final int basePrice;
    public final int energy;

    ForagingCropsType(
        Sprite sprite,
            ArrayList<Season> season,
            int basePrice,
            int energy
    ) {
        this.sprite = sprite;
        this.season = season;
        this.basePrice = basePrice;
        this.energy = energy;
    }


    public static ForagingCropsType getSeasonForagingCrop(Season season) {
        List<ForagingCropsType> possibleCrops = new ArrayList<>();
        for (ForagingCropsType crop : ForagingCropsType.values()) {
            if (crop.season.contains(season)) {
                possibleCrops.add(crop);
            }
        }
        int randomIndex = RandomGenerator.getInstance().randomInt(0,possibleCrops.size()-1);
        return possibleCrops.get(randomIndex);
    }
    public String getName(){
        return this.name().toLowerCase().replaceAll("_", " ");
    }


    public static ForagingCropsType getByName(String input) {
        String normalized = input.trim().toLowerCase().replace(' ', '_');
        for (ForagingCropsType c : values()) {
            if (c.name().toLowerCase().equals(normalized)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getName() +
                " — seasons: " + season +"\n"+
                ", basePrice: " + basePrice +"\n"+
                ", energy: " + energy+"\n";
    }

    public Sprite getSprite() {
        return sprite;
    }

    public ArrayList<Season> getSeason() {
        return season;
    }

    public int getEnergy() {
        return energy;
    }

    public int getBasePrice() {
        return basePrice;
    }
}

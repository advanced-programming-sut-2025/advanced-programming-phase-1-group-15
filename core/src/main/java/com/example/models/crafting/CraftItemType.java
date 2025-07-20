package com.example.models.crafting;

import com.example.models.farming.*;
import com.example.models.foraging.ForagingMineral;
import com.example.models.foraging.ForagingMineralType;
import com.example.models.tools.BackPackable;

import java.util.HashMap;

public enum CraftItemType {
    CHERRY_BOMB("4 copper ore + 1 coal",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.COPPER),4);
            }},
            Ability.miningAbility,
            1,
            50),
    BOMB("4 iron ore + 1 coal",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.IRON),4);
                put(new ForagingMineral(ForagingMineralType.COAL),1);
            }},
            Ability.miningAbility,
            2,
            50),
    MEGA_BOMB("4 gold ore + 1 coal",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.GOLD),4);
                put(new ForagingMineral(ForagingMineralType.COAL),1);
            }},
            Ability.miningAbility,
            3,
            50),
    SPRINKLER("1 copper bar + 1 iron bar",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.COPPER),1);
                put(new ForagingMineral(ForagingMineralType.IRON),1);
            }},
            Ability.farmingAbility,
            1,
            0),
   QUALITY_SPRINKLER("1 iron bar + 1 gold bar",
           new HashMap<>(){{
               put(new ForagingMineral(ForagingMineralType.IRON),1);
               put(new ForagingMineral(ForagingMineralType.GOLD),1);
           }},
            Ability.farmingAbility,
            2,
            0),
    IRIDIUM_SPRINKLER("1 gold bar + 1 iridium bar",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.GOLD),1);
                put(new ForagingMineral(ForagingMineralType.IRIDIUM),1);
            }},
            Ability.farmingAbility,
            3,
            0),
    CHARCOAL_KLIN("20 wood + 2 copper bar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.COPPER),2);
            }},
            Ability.foragingAbility,
            1,
            0),
    FURNACE("20 copper ore + 25 stone",
            new HashMap<>(),
            null,
            0,
            0),
    SCARECROW("50 wood + 1 coal + 20 fiber",
            new HashMap<>(),
            null,
            0,
            0),
    DELUXE_SCARECROW("50 wood + 1 coal + 20 fiber + 1 iridium ore",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.COAL),1);
                put(new ForagingMineral(ForagingMineralType.IRIDIUM),1);
                //put(ForagingMineralType.FIBER,1);
            }},
            Ability.farmingAbility,
            2,
            0),
    BEE_HOUSE("40 wood + 8 coal + 1 iron bar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.COAL),8);
                put(new ForagingMineral(ForagingMineralType.IRON),1);
            }},
            Ability.farmingAbility,
            1,
            0),
    CHEESE_PRESS("45 wood + 45 stone + 1 copper bar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,45);
                //put(ForagingMineralType.Stone,45);
                put(new ForagingMineral(ForagingMineralType.COPPER),1);
            }},
            Ability.farmingAbility,
            2,
            0),
    KEG("30 wood + 1 copper bar + 1 iron bar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.COPPER),1);
                put(new ForagingMineral(ForagingMineralType.IRON),1);
            }},
            Ability.farmingAbility,
            3,
            0),
    LOOM("60 wood + 30 fiber",
            new HashMap<>(),
            Ability.farmingAbility,
            3,
            0),
    MAYONNAISE_MACHINE("15 wood + 15 stone + 1 copper bar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                //put(ForagingMineralType.Stone,20);
                put(new ForagingMineral(ForagingMineralType.COPPER),1);
            }},
            null,
            0,
            0),
    OIL_MAKER("100 wood + 1 gold bar + 1 iron bar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.GOLD),1);
                put(new ForagingMineral(ForagingMineralType.IRON),1);
            }},
            Ability.farmingAbility,
            3,
            0),
    PRESERVES_JAR("50 wood + 40 stone + 8 coal",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                //put(ForagingMineralType.Stone,20);
                put(new ForagingMineral(ForagingMineralType.COAL),8);
            }},
            Ability.farmingAbility,
            2,
            0),
    DEHYDRATOR("30 wood + 20 stone + 30 fiber",
            new HashMap<>(),
            null,
            0,
            0),
    FISH_SMOKER("50 wood + 3 iron bar + 10 coal",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,50);
                put(new ForagingMineral(ForagingMineralType.IRON),3);
                put(new ForagingMineral(ForagingMineralType.COAL),10);
            }},
            null,
            0,
            0),
    GRASS_STARTER("1 wood + 1 fiber",
            new HashMap<>(),
            null,
            0,
            0
    ),
    MYSTIC_TREE_SEED("5 acorn + 5 maple seed + 5 pine cone + 5 mahogany seed",
            new HashMap<>(){{
                put(new Seed(SeedType.ACORNS),5);
                put(new Seed(SeedType.MAHOGANY_SEEDS),5);
                put(new Seed(SeedType.MAPLE_SEEDS),5);
                put(new Seed(SeedType.PINE_CONES),5);
            }},
            Ability.foragingAbility,
            4,
            100);
    public final String recipe;
    public final HashMap<BackPackable, Integer> ingredients;
    public final Ability ability;
    public final int levelRequired;
    public final int price;

    CraftItemType(String recipe, HashMap<BackPackable, Integer> ingredients, Ability ability, int levelRequired, int price) {
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.ability = ability;
        this.levelRequired = levelRequired;
        this.price = price;
    }
    public String getName(){
        return this.name().toLowerCase().replaceAll("_", " ");
    }
    public String getRecipe(){
        return this.recipe;
    }
}

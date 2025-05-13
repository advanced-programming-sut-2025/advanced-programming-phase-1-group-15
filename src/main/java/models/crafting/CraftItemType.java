package models.crafting;

import models.farming.*;
import models.foraging.ForagingMineral;
import models.foraging.ForagingMineralType;
import models.tools.BackPackable;

import java.util.HashMap;

public enum CraftItemType {
    CHERRY_BOMB("4 copper ore + 1 coal = 1 cherry bomb",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.COPPER),4);
            }},
            Ability.miningAbility,
            1,
            50),
    BOMB("4 iron ore + 1 coal = 1 bomb",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.IRON),4);
                put(new ForagingMineral(ForagingMineralType.COAL),1);
            }},
            Ability.miningAbility,
            2,
            50),
    MEGA_BOMB("4 gold ore + 1 coal = 1 mega bomb",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.GOLD),4);
                put(new ForagingMineral(ForagingMineralType.COAL),1);
            }},
            Ability.miningAbility,
            3,
            50),
    SPRINKLER("1 copper bar + 1 iron bar = 1 sprinkler",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.COPPER),1);
                put(new ForagingMineral(ForagingMineralType.IRON),1);
            }},
            Ability.farmingAbility,
            1,
            0),
   QUALITY_SPRINKLER("1 iron bar + 1 gold bar = 1 quality sprinkler",
           new HashMap<>(){{
               put(new ForagingMineral(ForagingMineralType.IRON),1);
               put(new ForagingMineral(ForagingMineralType.GOLD),1);
           }},
            Ability.farmingAbility,
            2,
            0),
    IRIDIUM_SPRINKLER("1 gold bar + 1 iridium bar = 1 iridium sprinkler",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.GOLD),1);
                put(new ForagingMineral(ForagingMineralType.IRIDIUM),1);
            }},
            Ability.farmingAbility,
            3,
            0),
    CHARCOAL_KLIN("20 wood + 2 copper bar = 1 charcoal klin",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.COPPER),2);
            }},
            Ability.foragingAbility,
            1,
            0),
    FURNACE("20 copper ore + 25 stone = 1 furnace",
            new HashMap<>(),
            null,
            0,
            0),
    SCARECROW("50 wood + 1 coal + 20 fiber = 1 scarecrow",
            new HashMap<>(),
            null,
            0,
            0),
    DELUXE_SCARECROW("50 wood + 1 coal + 20 fiber + 1 iridium ore = 1 deluxe scarecrow",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.COAL),1);
                put(new ForagingMineral(ForagingMineralType.IRIDIUM),1);
                //put(ForagingMineralType.FIBER,1);
            }},
            Ability.farmingAbility,
            2,
            0),
    BEE_HOUSE("40 wood + 8 coal + 1 iron bar = 1 bee house",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.COAL),8);
                put(new ForagingMineral(ForagingMineralType.IRON),1);
            }},
            Ability.farmingAbility,
            1,
            0),
    CHEESE_PRESS("45 wood + 45 stone + 1 copper bar = 1 cheese press",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,45);
                //put(ForagingMineralType.Stone,45);
                put(new ForagingMineral(ForagingMineralType.COPPER),1);
            }},
            Ability.farmingAbility,
            2,
            0),
    KEG("30 wood + 1 copper bar + 1 iron bar = 1 keg",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.COPPER),1);
                put(new ForagingMineral(ForagingMineralType.IRON),1);
            }},
            Ability.farmingAbility,
            3,
            0),
    LOOM("60 wood + 30 fiber = 1 loom",
            new HashMap<>(),
            Ability.farmingAbility,
            3,
            0),
    MAYONNAISE_MACHINE("15 wood + 15 stone + 1 copper bar = 1 mayonnaise machine",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                //put(ForagingMineralType.Stone,20);
                put(new ForagingMineral(ForagingMineralType.COPPER),1);
            }},
            null,
            0,
            0),
    OIL_MAKER("100 wood + 1 gold bar + 1 iron bar = 1 oil maker",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.GOLD),1);
                put(new ForagingMineral(ForagingMineralType.IRON),1);
            }},
            Ability.farmingAbility,
            3,
            0),
    PRESERVES_JAR("50 wood + 40 stone + 8 coal = 1 preserves jar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                //put(ForagingMineralType.Stone,20);
                put(new ForagingMineral(ForagingMineralType.COAL),8);
            }},
            Ability.farmingAbility,
            2,
            0),
    DEHYDRATOR("30 wood + 20 stone + 30 fiber = 1 dehydrator",
            new HashMap<>(),
            null,
            0,
            0),
    FISH_SMOKER("50 wood + 3 iron bar + 10 coal = 1 fish smoker",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,50);
                put(new ForagingMineral(ForagingMineralType.IRON),3);
                put(new ForagingMineral(ForagingMineralType.COAL),10);
            }},
            null,
            0,
            0),
    GRASS_STARTER("1 wood + 1 fiber = 1 grass starter",
            new HashMap<>(),
            null,
            0,
            0
    ),
    MYSTIC_TREE_SEED("5 acorn + 5 maple seed + 5 pine cone + 5 mahogany seed = 1 mystic tree seed",
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
}

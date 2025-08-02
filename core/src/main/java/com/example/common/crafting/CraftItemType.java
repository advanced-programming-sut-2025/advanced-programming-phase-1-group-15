package com.example.common.crafting;

import com.badlogic.gdx.graphics.Texture;
import com.example.common.farming.Seed;
import com.example.common.farming.SeedType;
import com.example.common.foraging.ForagingMineral;
import com.example.common.foraging.ForagingMineralType;
import com.example.common.tools.BackPackable;

import java.util.HashMap;

public enum CraftItemType {
    CHERRY_BOMB("4 copper ore + 1 coal",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.COPPER),4);
            }},
            Ability.miningAbility,
            1,
            50 , new Texture("Sprites/CraftItems/Cherry_Bomb.png")),
    BOMB("4 iron ore + 1 coal",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.IRON),4);
                put(new ForagingMineral(ForagingMineralType.COAL),1);
            }},
            Ability.miningAbility,
            2,
            50 , new Texture("Sprites/CraftItems/Bomb.png")),
    MEGA_BOMB("4 gold ore + 1 coal",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.GOLD),4);
                put(new ForagingMineral(ForagingMineralType.COAL),1);
            }},
            Ability.miningAbility,
            3,
            50 , new Texture("Sprites/CraftItems/Mega_Bomb.png")),
    SPRINKLER("1 copper bar + 1 iron bar",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.COPPER),1);
                put(new ForagingMineral(ForagingMineralType.IRON),1);
            }},
            Ability.farmingAbility,
            1,
            0 , new Texture("Sprites/CraftItems/Sprinkler.png")),
   QUALITY_SPRINKLER("1 iron bar + 1 gold bar",
           new HashMap<>(){{
               put(new ForagingMineral(ForagingMineralType.IRON),1);
               put(new ForagingMineral(ForagingMineralType.GOLD),1);
           }},
            Ability.farmingAbility,
            2,
            0 , new Texture("Sprites/CraftItems/Quality_Sprinkler.png")),
    IRIDIUM_SPRINKLER("1 gold bar + 1 iridium bar",
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.GOLD),1);
                put(new ForagingMineral(ForagingMineralType.IRIDIUM),1);
            }},
            Ability.farmingAbility,
            3,
            0 , new Texture("Sprites/CraftItems/Iridium_Sprinkler.png")),
    CHARCOAL_KLIN("20 wood + 2 copper bar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.COPPER),2);
            }},
            Ability.foragingAbility,
            1,
            0 , new Texture("Sprites/CraftItems/Charcoal_Kiln.png")),
    FURNACE("20 copper ore + 25 stone",
            new HashMap<>(),
            null,
            0,
            0 , new Texture("Sprites/CraftItems/Furnace.png")),
    SCARECROW("50 wood + 1 coal + 20 fiber",
            new HashMap<>(),
            null,
            0,
            0 , new Texture("Sprites/CraftItems/Scarecrow.png")),
    DELUXE_SCARECROW("50 wood + 1 coal + 20 fiber + 1 iridium ore",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.COAL),1);
                put(new ForagingMineral(ForagingMineralType.IRIDIUM),1);
                //put(ForagingMineralType.FIBER,1);
            }},
            Ability.farmingAbility,
            2,
            0 , new Texture("Sprites/CraftItems/Deluxe_Scarecrow.png")),
    BEE_HOUSE("40 wood + 8 coal + 1 iron bar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.COAL),8);
                put(new ForagingMineral(ForagingMineralType.IRON),1);
            }},
            Ability.farmingAbility,
            1,
            0 , new Texture("Sprites/CraftItems/Bee_House.png")),
    CHEESE_PRESS("45 wood + 45 stone + 1 copper bar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,45);
                //put(ForagingMineralType.Stone,45);
                put(new ForagingMineral(ForagingMineralType.COPPER),1);
            }},
            Ability.farmingAbility,
            2,
            0 , new Texture("Sprites/CraftItems/Cheese_Press.png")),
    KEG("30 wood + 1 copper bar + 1 iron bar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.COPPER),1);
                put(new ForagingMineral(ForagingMineralType.IRON),1);
            }},
            Ability.farmingAbility,
            3,
            0 , new Texture("Sprites/CraftItems/Keg.png")),
    LOOM("60 wood + 30 fiber",
            new HashMap<>(),
            Ability.farmingAbility,
            3,
            0 , new Texture("Sprites/CraftItems/Loom.png")),
    MAYONNAISE_MACHINE("15 wood + 15 stone + 1 copper bar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                //put(ForagingMineralType.Stone,20);
                put(new ForagingMineral(ForagingMineralType.COPPER),1);
            }},
            null,
            0,
            0 , new Texture("Sprites/CraftItems/Mayonnaise_Machine.png")),
    OIL_MAKER("100 wood + 1 gold bar + 1 iron bar",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                put(new ForagingMineral(ForagingMineralType.GOLD),1);
                put(new ForagingMineral(ForagingMineralType.IRON),1);
            }},
            Ability.farmingAbility,
            3,
            0 , new Texture("Sprites/CraftItems/Oil_Maker.png")),
    PRESERVES_JAR("50 wood + 40 stone + 8 coal",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,20);
                //put(ForagingMineralType.Stone,20);
                put(new ForagingMineral(ForagingMineralType.COAL),8);
            }},
            Ability.farmingAbility,
            2,
            0 , new Texture("Sprites/CraftItems/Preserves_Jar.png")),
    DEHYDRATOR("30 wood + 20 stone + 30 fiber",
            new HashMap<>(),
            null,
            0,
            0 , new Texture("Sprites/CraftItems/Dehydrator.png")),
    FISH_SMOKER("50 wood + 3 iron bar + 10 coal",
            new HashMap<>(){{
                //put(ForagingMineralType.Wood,50);
                put(new ForagingMineral(ForagingMineralType.IRON),3);
                put(new ForagingMineral(ForagingMineralType.COAL),10);
            }},
            null,
            0,
            0 , new Texture("Sprites/CraftItems/Fish_Smoker.png")),
    GRASS_STARTER("1 wood + 1 fiber",
            new HashMap<>(),
            null,
            0,
            0 , new Texture("Sprites/CraftItems/Grass_Starter.png")
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
            100 , new Texture("Sprites/CraftItems/Mystic_Tree_Seed.png"));
    public final String recipe;
    public final HashMap<BackPackable, Integer> ingredients;
    public final Ability ability;
    public final int levelRequired;
    public final int price;
    public final Texture texture;
    CraftItemType(String recipe, HashMap<BackPackable, Integer> ingredients, Ability ability, int levelRequired, int price , Texture texture) {
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.ability = ability;
        this.levelRequired = levelRequired;
        this.price = price;
        this.texture = texture;
    }
    public String getName(){
        return this.name().toLowerCase().replaceAll("_", " ");
    }
    public String getRecipe(){
        return this.recipe;
    }
    public Texture getTexture(){
        return this.texture;
    }
    public HashMap<BackPackable, Integer> getIngredients(){
        return this.ingredients;
    }
}

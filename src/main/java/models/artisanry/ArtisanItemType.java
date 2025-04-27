package models.artisanry;
import models.animals.AnimalProduct;
import models.animals.AnimalProductType;
import models.crafting.CraftItemType;
import models.farming.*;
import models.foraging.*;
import models.tools.BackPackable;
import java.util.HashMap;

public enum ArtisanItemType {
    BEER(CraftItemType.KEG,
            "Drink in moderation.",
            50,
            24,
            new HashMap<>(){{
                put(new Crop(Crops.WHEAT),1);
            }},
            200),
    VINEGAR(CraftItemType.KEG,
            "An aged fermented liquid used in many cooking recipes.",
            13,
            10,
            new HashMap<>(){{
                put(new Crop(Crops.UN_MILLED_RICE),1);
            }},
            100),
    COFFEE(CraftItemType.KEG,
            "It smells delicious. This is sure to give you a boost.",
            75,
            2,
            new HashMap<>(){{
                put(new Crop(Crops.COFFEE_BEEN),5);
            }},
            150),
    JUICE(CraftItemType.KEG,
            "A sweet, nutritious beverage.",
            0,
            96,
            new HashMap<>(),
            0),
    MEAD(CraftItemType.KEG,
            "A fermented beverage made from honey.Drink in moderation.",
            100,
            10,
            new HashMap<>(){{
                put(new ArtisanItem(ArtisanItemType.HONEY),1);
            }},
            300),
    PALE_ALE(CraftItemType.KEG,
            "Drink in moderation.",
            50,
            72,
            new HashMap<>(){{
                put(new Crop(Crops.HOPS),1);
            }},
            300),
    WINE(CraftItemType.KEG,
            "Drink in moderation.",
            0,
            168,
            new HashMap<>(),
            0),
    HONEY(CraftItemType.BEE_HOUSE,
            "It's a sweet syrup produced by bees.",
            75,
            96,
            null,
            350),
    CHEESE_MILK(CraftItemType.CHEESE_PRESS,
            "It's your basic cheese.",
            100,
            72,
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.COW_MILK),1);
            }},
            230),
    CHEESE_LARGE_MILK(CraftItemType.CHEESE_PRESS,
            "It's your basic cheese.",
            100,
            72,
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.COW_LARGE_MILK),1);
            }},
            345),
    GOAT_CHEESE_MILK(CraftItemType.CHEESE_PRESS,
            "Soft cheese made from goat's milk.",
            100,
            72,
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.GOAT_MILK),1);
            }},
            400),
    GOAT_CHEESE_LARGE_MILK(CraftItemType.CHEESE_PRESS,
            "Soft cheese made from goat's milk.",
            100,
            72,
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.COW_LARGE_MILK),1);
            }},
            600),
    DRIED_COMMON_MUSHROOM(CraftItemType.DEHYDRATOR,
            "A package of gourmet mushrooms.",
            50,
            9,
            new HashMap<>(){{
                put(new ForagingCrop(ForagingCropsType.COMMON_MUSHROOM),5);
            }},
            7*ForagingCropsType.COMMON_MUSHROOM.basePrice/10+25),
    DRIED_RED_MUSHROOM(CraftItemType.DEHYDRATOR,
            "A package of gourmet mushrooms.",
            50,
            9,
            new HashMap<>(){{
                put(new ForagingCrop(ForagingCropsType.RED_MUSHROOM),5);
            }},
            7*ForagingCropsType.RED_MUSHROOM.basePrice/10+25),
    DRIED_PURPLE_MUSHROOM(CraftItemType.DEHYDRATOR,
            "A package of gourmet mushrooms.",
            50,
            9,
            new HashMap<>(){{
                put(new ForagingCrop(ForagingCropsType.PURPLE_MUSHROOM),5);
            }},
            7*ForagingCropsType.RED_MUSHROOM.basePrice/10+25),
    DRIED_FRUIT(CraftItemType.DEHYDRATOR,
            "Chewy pieces of dried fruit.",
            75,
            0,
            new HashMap<>(),
            0),
    RAISINS(CraftItemType.DEHYDRATOR,
            "It's said to be the Junimos' favorite food.",
            125,
            0,
            new HashMap<>(){{
                put(new Crop(Crops.GRAPE),5);
            }},
            600),
    COAL(CraftItemType.CHARCOAL_KLIN,
            "Turns 10 pieces of wood into one piece of coal.",
            0,
            1,
            new HashMap<>(),
            50),
    CLOTH_SHEEP(CraftItemType.LOOM,
            "A bolt of fine wool cloth.",
            0,
            4,
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.SHEEP_WOOL),1);
            }},
            470),
    CLOTH_RABBIT(CraftItemType.LOOM,
            "A bolt of fine wool cloth.",
            0,
            4,
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.RABBIT_WOOL),1);
            }},
            470),
    MAYONNAISE_EGG(CraftItemType.MAYONNAISE_MACHINE,
            "It looks spreadable.",
            50,
            3,
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.EGG),1);
            }},
            190),
    MAYONNAISE_LARGE_EGG(CraftItemType.MAYONNAISE_MACHINE,
            "It looks spreadable.",
            50,
            3,
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.LARGE_EGG),1);
            }},
            237),
    DUCK_MAYONNAISE(CraftItemType.MAYONNAISE_MACHINE,
            "It's a rich, yellow mayonnaise.",
            75,
            3,
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.DUCK_EGG),1);
            }},
            37),
    DINOSAUR_MAYONNAISE(CraftItemType.MAYONNAISE_MACHINE,
            "It's thick and creamy, with a vivid green hue.It smells like grass and leather.",
            125,
            3,
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.DINOSAUR_EGG),1);
            }},
            800),
    TRUFFLE_OIL(CraftItemType.OIL_MAKER,
            "A gourmet cooking ingredient.",
            38,
            6,
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.TRUFFLE),1);
            }},
            1065),
    OIL_CORN(CraftItemType.OIL_MAKER,
            "All purpose cooking oil.",
            13,
            6,
            new HashMap<>(){{
                put(new Crop(Crops.CORN),1);
            }},
            100),
    OIL_SUNFLOWER(CraftItemType.OIL_MAKER,
            "All purpose cooking oil.",
            13,
            1,
            new HashMap<>(){{
                put(new Crop(Crops.SUNFLOWER),1);
            }},
            100),
    OIL_SUNFLOWER_SEED(CraftItemType.OIL_MAKER,
            "All purpose cooking oil.",
            13,
            24,
            new HashMap<>(){{
                put(new ForagingSeeds(ForagingSeedsType.SUNFLOWER_SEEDS),1);
            }},
            100),
    PICKLES(CraftItemType.PRESERVES_JAR,
            "A jar of your home-made pickles.",
            0,
            6,
            new HashMap<>(),
            0),
    JELLY(CraftItemType.PRESERVES_JAR,
            "Gooey.",
            0,
            72,
            new HashMap<>(),
            0),
    SMOKED_FISH(CraftItemType.FISH_SMOKER,
            "A whole fish, smoked to perfection.",
            0,
            1,
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.COAL),1);
            }},
            0),
    METAL_BAR(CraftItemType.FURNACE,
            "Turns ore and coal into metal bars.",
            0,
            4,
            new HashMap<>(){{
                put(new ForagingMineral(ForagingMineralType.COAL),1);
            }},
            0);
    public final String description;
    public final int energy;
    public final int productionTimeInHours;
    public final HashMap<BackPackable, Integer> ingredients;
    public final int sellPrice;

    ArtisanItemType(CraftItemType craftItemType,
                    String description,
                    int energy,
                    int productionTimeInHours,
                    HashMap<BackPackable, Integer> ingridients,
                    int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.productionTimeInHours = productionTimeInHours;
        this.ingredients = ingridients;
        this.sellPrice = sellPrice;
    }
}

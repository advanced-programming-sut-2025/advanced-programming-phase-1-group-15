package models.cooking;

import models.animals.*;
import models.artisanry.ArtisanItem;
import models.artisanry.ArtisanItemType;
import models.farming.*;
import models.foraging.ForagingCrop;
import models.foraging.ForagingCropsType;
import models.tools.BackPackable;
import java.util.HashMap;

public enum FoodType {
    FRIED_EGG("1 egg = 1 fried egg",
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.EGG),1);
            }},
            50,
            false,
            3),
    BACKED_FISH("1 sardine + 1 salmon + 1 wheat = 1 backed fish",
            new HashMap<>(){{
                put(new Fish(FishType.SALMON),1);
                put(new Fish(FishType.SARDINE),1);
                put(new Crop(Crops.WHEAT),1);
            }},
            75,
            false,
            100),
    SALAD("1 leek + 1 dandelion = 1 salad",
            new HashMap<>(){{
                put(new ForagingCrop(ForagingCropsType.LEEK),1);
                put(new ForagingCrop(ForagingCropsType.DANDELION),1);
            }},
            113,
            false,
            110),
    OMELET("1 egg + 1 milk = 1 olmelet",
            new HashMap<>(){{
                put(new AnimalProduct(AnimalProductType.EGG),1);
                put(new AnimalProduct(AnimalProductType.COW_MILK),1);
            }},
            100,
            false,
            125),
    PUMPKIN_PIE("1 pumpkin + 1 wheat flour + 1 milk + 1 sugar = 1 pumpkin pie",
            new HashMap<>(){{
                put(new Crop(Crops.PUMPKIN),1);
                put(new Crop(Crops.WHEAT),1);
                put(new AnimalProduct(AnimalProductType.COW_MILK),1);

            }},
            225,
            false,
            385),
    SPAGHETTI("1 wheat flour + 1 tomato = 1 spaghetti",
            new HashMap<>(){{
                put(new Crop(Crops.WHEAT),1);
                put(new Crop(Crops.TOMATO),1);
            }},
            75,
            false,
            120),
    PIZZA_MILK("1 wheat flour + 1 tomato + 1 cheese = 1 pizza",
            new HashMap<>(){{
                put(new Crop(Crops.WHEAT),1);
                put(new Crop(Crops.TOMATO),1);
                put(new ArtisanItem(ArtisanItemType.CHEESE_MILK),1);
            }},
            150,
            false,
            300),
    PIZZA_LARGE_MILK("1 wheat flour + 1 tomato + 1 cheese = 1 pizza",
            new HashMap<>(){{
                put(new Crop(Crops.WHEAT),1);
                put(new Crop(Crops.TOMATO),1);
                put(new ArtisanItem(ArtisanItemType.CHEESE_LARGE_MILK),1);
            }},
            150,
            false,
            300),
    TORTILLA("1 corn = 1 tortilla",
            new HashMap<>(){{
                put(new Crop(Crops.CORN),1);
            }},
            50,
            false,
            50),
    MAKI_ROLL("1 any fish + 1 rice + 1 fiber = 1 maki roll",
            new HashMap<>(){{
                put(new Crop(Crops.UN_MILLED_RICE),1);
                //put(new Crop(Crops.FIBER),1);
                //put(new Fish(FishType.),1);
            }},
            100,
            false,
            220),
    TRIPLE_SHOT_ESPRESSO("3 coffee = 1 triple shot espresso",
            new HashMap<>(){{
                put(new Crop(Crops.COFFEE_BEEN),3);
            }},
            200,
            true,
            450),
    COOKIE("1 wheat flour + 1 sugar + 1 egg = 1 cookie",
            new HashMap<>(){{
                put(new Crop(Crops.WHEAT),1);
                //put(new Crop(Crops.Sugar),1);
                put(new AnimalProduct(AnimalProductType.EGG),1);
            }},
            90,
            false,
            140),
    HASH_BROWNS_OIL_CORN("1 potato + 1 oil = 1 hash browns",
            new HashMap<>(){{
                put(new Crop(Crops.POTATO),1);
                put(new ArtisanItem(ArtisanItemType.OIL_CORN),1);
            }},
            90,
            true,
            120),
    HASH_BROWNS_OIL_SUNFLOWER("1 potato + 1 oil = 1 hash browns",
            new HashMap<>(){{
                put(new Crop(Crops.POTATO),1);
                put(new ArtisanItem(ArtisanItemType.OIL_SUNFLOWER),1);
            }},
            90,
            true,
            120),HASH_BROWNS_OIL_SUNFLOWER_SEED("1 potato + 1 oil = 1 hash browns",
            new HashMap<>(){{
                put(new Crop(Crops.POTATO),1);
                put(new ArtisanItem(ArtisanItemType.OIL_SUNFLOWER_SEED),1);
            }},
            90,
            true,
            120),
    HASH_BROWNS_TRUFFLE_OIL("1 potato + 1 oil = 1 hash browns",
            new HashMap<>(){{
                put(new Crop(Crops.POTATO),1);
                put(new ArtisanItem(ArtisanItemType.TRUFFLE_OIL),1);
            }},
            90,
            true,
            120),
    PANCAKES("1 wheat flour + 1 egg = 1 pancakes",
            new HashMap<>(){{
                put(new Crop(Crops.WHEAT),1);
                put(new AnimalProduct(AnimalProductType.EGG),1);
            }},
            90,
            true,
            80),
    FRUIT_SALAD("1 blueberry + 1 melon + 1 apricot = 1 fruit salad",
            new HashMap<>(){{
                put(new Crop(Crops.BLUEBERRY),1);
                put(new Crop(Crops.MELON),1);
                //put(new Tree(TreeType.APRICOT_TREE).getTreeType().fruitType, 1);
            }},
            263,
            false,
            450),
    RED_PLATE("1 red cabbage + 1 radish = 1 red plate",
            new HashMap<>(){{
                put(new Crop(Crops.RED_CABBAGE),1);
                put(new Crop(Crops.RADISH),1);
            }},
            240,
            true,
            400),
    BREAD("1 wheat flour = 1 bread",
            new HashMap<>(){{
                put(new Crop(Crops.WHEAT),1);
            }},
            50,
            false,
            60),
    SALMON_DINNER("1 salmon + 1 amaranth + 1 kale = 1 salmon dinner",
            new HashMap<>(){{
                put(new Fish(FishType.SALMON),1);
                put(new Crop(Crops.AMARANTH),1);
                put(new Crop(Crops.KALE),1);
            }},
            125,
            false,
            300),
    VEGETABLE_MEDLEY("1 tomato + 1 beet = 1 vegetable medley",
            new HashMap<>(){{
                put(new Crop(Crops.TOMATO),1);
                put(new Crop(Crops.BEET),1);
            }},
            165,
            false,
            120),
    FARMERS_LAUNCH("1 omelet + 1 parsnip = 1 farmer launch",
            new HashMap<>(){{
                put(new Crop(Crops.PARSNIP),1);
                put(new Food(FoodType.OMELET),1);
            }},
            200,
            true,
            150),
    SURVIVAL_BURGERS("1 bread + 1 carrot + 1 eggplant = 1 survival burger",
            new HashMap<>(){{
                put(new Food(FoodType.BREAD),1);
                put(new Crop(Crops.CARROT),1);
                put(new Crop(Crops.EGGPLANT),1);
            }},
            125,
            true,
            180),
    DISH_O_THE_SEA_CORN("2 sardines + 1 hash browns = 1 dish O' the sea",
            new HashMap<>(){{
                put(new Food(FoodType.HASH_BROWNS_OIL_CORN),1);
            }},
            150,
            true,
            220),
    DISH_O_THE_SEA_SUNFLOWER("2 sardines + 1 hash browns = 1 dish O' the sea",
            new HashMap<>(){{
                put(new Food(FoodType.HASH_BROWNS_OIL_SUNFLOWER),1);
            }},
            150,
            true,
            220),
    DISH_O_THE_SEA_SUNFLOWER_SEED("2 sardines + 1 hash browns = 1 dish O' the sea",
            new HashMap<>(){{
                put(new Food(FoodType.HASH_BROWNS_OIL_SUNFLOWER_SEED),1);
            }},
            150,
            true,
            220),
    DISH_O_THE_SEA_TRUFFLE_OIL("2 sardines + 1 hash browns = 1 dish O' the sea",
            new HashMap<>(){{
                put(new Food(FoodType.HASH_BROWNS_TRUFFLE_OIL),1);
            }},
            150,
            true,
            220),
    SEA_FORM_PUDDING("1 flounder + 1 midnight carp = 1 sea form pudding",
            new HashMap<>(){{
                put(new Fish(FishType.FLOUNDER),1);
                put(new Fish(FishType.MIDNIGHT_CARP),1);
            }},
            175,
            true,
            300),
    MINERS_TREAT_MILK("2 carrot + 1 suger + 1 milk = 1 miner treat",
            new HashMap<>(){{
                put(new Crop(Crops.CARROT),1);
                put(new AnimalProduct(AnimalProductType.COW_MILK),1);
            }},
            125,
            true,
            200),
    MINERS_TREAT_LARGE_MILK("2 carrot + 1 suger + 1 milk = 1 miner treat",
            new HashMap<>(){{
                put(new Crop(Crops.CARROT),1);
                put(new AnimalProduct(AnimalProductType.COW_LARGE_MILK),1);
            }},
            125,
            true,
            200);

    public  final String recipe;
    public final HashMap<BackPackable, Integer> ingredients;
    public final int energy;
    public final boolean buff;
    public final int price;

    FoodType(String recipe, HashMap<BackPackable, Integer> ingredients, int energy, boolean buff, int price) {
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.energy = energy;
        this.buff = buff;
        this.price = price;
    }
}

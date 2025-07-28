package com.example.models.artisanry;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.example.models.animals.AnimalProduct;
import com.example.models.animals.AnimalProductType;
import com.example.models.crafting.CraftItemType;
import com.example.models.farming.*;
import com.example.models.foraging.*;
import com.example.models.tools.BackPackable;

import static com.example.models.foraging.ForagingCropsType.*;

public enum ArtisanItemType {
    HONEY(CraftItemType.BEE_HOUSE,
            "It's a sweet syrup produced by bees.",
            75,
            96,
            null,
            1,
            350 , new Texture("Sprites/ArtisanItem/Honey.png")),
    BEER(CraftItemType.KEG,
            "Drink in moderation.",
            50,
            24,
            new Crop(Crops.WHEAT),1,
            200 , new Texture("Sprites/ArtisanItem/Beer.png")),
    VINEGAR(CraftItemType.KEG,
            "An aged fermented liquid used in many cooking recipes.",
            13,
            10,
            new Crop(Crops.UN_MILLED_RICE),1,
            100, new Texture("Sprites/ArtisanItem/Vinegar.png")),
    COFFEE(CraftItemType.KEG,
            "It smells delicious. This is sure to give you a boost.",
            75,
            2,
            new Crop(Crops.COFFEE_BEEN),5,
            150, new Texture("Sprites/ArtisanItem/Coffee.png")),
    JUICE(CraftItemType.KEG,
            "A sweet, nutritious beverage.",
            0,
            96,
            null,
            1,
            0, new Texture("Sprites/ArtisanItem/Juice.png")),
    MEAD(CraftItemType.KEG,
            "A fermented beverage made from honey.Drink in moderation.",
            100,
            10,
            new ArtisanItem(ArtisanItemType.HONEY),1,
            300, new Texture("Sprites/ArtisanItem/Mead.png")),
    PALE_ALE(CraftItemType.KEG,
            "Drink in moderation.",
            50,
            72,
            new Crop(Crops.HOPS),1,
            300, new Texture("Sprites/ArtisanItem/Pale_Ale.png")),
    WINE(CraftItemType.KEG,
            "Drink in moderation.",
            0,
            168,
            new Crop(Crops.WHEAT),
            1,
            0, new Texture("Sprites/ArtisanItem/Wine.png")),
    CHEESE_MILK(CraftItemType.CHEESE_PRESS,
            "It's your basic cheese.",
            100,
            72,
            new AnimalProduct(AnimalProductType.COW_MILK),1,
            230, new Texture("Sprites/ArtisanItem/Cheese.png")),
    CHEESE_LARGE_MILK(CraftItemType.CHEESE_PRESS,
            "It's your basic cheese.",
            100,
            72,
            new AnimalProduct(AnimalProductType.COW_LARGE_MILK),1,
            345, new Texture("Sprites/ArtisanItem/Cheese.png")),
    GOAT_CHEESE_MILK(CraftItemType.CHEESE_PRESS,
            "Soft cheese made from goat's milk.",
            100,
            72,
            new AnimalProduct(AnimalProductType.GOAT_MILK),1,
            400, new Texture("Sprites/ArtisanItem/Cheese.png")),
    GOAT_CHEESE_LARGE_MILK(CraftItemType.CHEESE_PRESS,
            "Soft cheese made from goat's milk.",
            100,
            72,
            new AnimalProduct(AnimalProductType.COW_LARGE_MILK),1,
            600, new Texture("Sprites/ArtisanItem/Cheese.png")),
    DRIED_COMMON_MUSHROOM(CraftItemType.DEHYDRATOR,
            "A package of gourmet mushrooms.",
            50,
            9,
            new ForagingCrop(COMMON_MUSHROOM),5,
            7*COMMON_MUSHROOM.basePrice/10+25, new Texture("Sprites/ArtisanItem/Dried_Common_Mushrooms.png")),
    DRIED_RED_MUSHROOM(CraftItemType.DEHYDRATOR,
            "A package of gourmet mushrooms.",
            50,
            9,
            new ForagingCrop(RED_MUSHROOM),5,
            7*RED_MUSHROOM.basePrice/10+25, new Texture("Sprites/ArtisanItem/Dried_Mushrooms.png")),
    DRIED_PURPLE_MUSHROOM(CraftItemType.DEHYDRATOR,
            "A package of gourmet mushrooms.",
            50,
            9,
            new ForagingCrop(PURPLE_MUSHROOM),5,
            7* PURPLE_MUSHROOM.basePrice/10+25, new Texture("Sprites/ArtisanItem/Dried_Purple_Mushrooms.png")),
    DRIED_FRUIT(CraftItemType.DEHYDRATOR,
            "Chewy pieces of dried fruit.",
            75,
            0,
            null,5,
            0, new Texture("Sprites/ArtisanItem/Blue_Dried_Fruit.png")),
    RAISINS(CraftItemType.DEHYDRATOR,
            "It's said to be the Junimos' favorite food.",
            125,
            0,
            new Crop(Crops.GRAPE),5,
            600, new Texture("Sprites/ArtisanItem/Raisins.png")),
    COAL(CraftItemType.CHARCOAL_KLIN,
            "Turns 10 pieces of wood into one piece of coal.",
            0,
            1,
            null,
            1,
            50, new Texture("Sprites/ArtisanItem/Coal.png")),
    CLOTH_SHEEP(CraftItemType.LOOM,
            "A bolt of fine wool cloth.",
            0,
            4,
            new AnimalProduct(AnimalProductType.SHEEP_WOOL),1,
            470, new Texture("assets/Sprites/ArtisanItem/Cloth.png")),
    CLOTH_RABBIT(CraftItemType.LOOM,
            "A bolt of fine wool cloth.",
            0,
            4,
            new AnimalProduct(AnimalProductType.RABBIT_WOOL),1,
            470, new Texture("assets/Sprites/ArtisanItem/Cloth.png")),
    MAYONNAISE_EGG(CraftItemType.MAYONNAISE_MACHINE,
            "It looks spreadable.",
            50,
            3,
            new AnimalProduct(AnimalProductType.EGG),1,
            190, new Texture("Sprites/ArtisanItem/Mayonnaise.png")),
    MAYONNAISE_LARGE_EGG(CraftItemType.MAYONNAISE_MACHINE,
            "It looks spreadable.",
            50,
            3,
            new AnimalProduct(AnimalProductType.LARGE_EGG),1,
            237, new Texture("Sprites/ArtisanItem/Mayonnaise.png")),
    DUCK_MAYONNAISE(CraftItemType.MAYONNAISE_MACHINE,
            "It's a rich, yellow mayonnaise.",
            75,
            3,
            new AnimalProduct(AnimalProductType.DUCK_EGG),1,
            37, new Texture("Sprites/ArtisanItem/Mayonnaise.png")),
    DINOSAUR_MAYONNAISE(CraftItemType.MAYONNAISE_MACHINE,
            "It's thick and creamy, with a vivid green hue.It smells like grass and leather.",
            125,
            3,
            new AnimalProduct(AnimalProductType.DINOSAUR_EGG),1,
            800, new Texture("Sprites/ArtisanItem/Mayonnaise.png")),
    TRUFFLE_OIL(CraftItemType.OIL_MAKER,
            "A gourmet cooking ingredient.",
            38,
            6,
            new AnimalProduct(AnimalProductType.TRUFFLE),1,
            1065, new Texture("Sprites/ArtisanItem/Truffle_Oil.png")),
    OIL_CORN(CraftItemType.OIL_MAKER,
            "All purpose cooking oil.",
            13,
            6,
            new Crop(Crops.CORN),1,
            100, new Texture("Sprites/ArtisanItem/Oil.png")),
    OIL_SUNFLOWER(CraftItemType.OIL_MAKER,
            "All purpose cooking oil.",
            13,
            1,
            new Crop(Crops.SUNFLOWER),1,
            100, new Texture("Sprites/ArtisanItem/Oil.png")),
    OIL_SUNFLOWER_SEED(CraftItemType.OIL_MAKER,
            "All purpose cooking oil.",
            13,
            24,
            new ForagingSeeds(ForagingSeedsType.SUNFLOWER_SEEDS),1,
            100, new Texture("Sprites/ArtisanItem/Oil.png")),
    PICKLES(CraftItemType.PRESERVES_JAR,
            "A jar of your home-made pickles.",
            0,
            6,
            new Crop(Crops.CORN),
            1,
            0, new Texture("Sprites/ArtisanItem/Orange_Pickles.png")),
    JELLY(CraftItemType.PRESERVES_JAR,
            "Gooey.",
            0,
            72,
            new Crop(Crops.CORN),
            1,
            0, new Texture("Sprites/ArtisanItem/Purple_Jelly.png")),
    SMOKED_FISH(CraftItemType.FISH_SMOKER,
            "A whole fish, smoked to perfection.",
            0,
            1,
            new ForagingMineral(ForagingMineralType.COAL),1,
            0, new Texture("Sprites/ArtisanItem/Smoked_Fish.png")),
    METAL_BAR(CraftItemType.FURNACE,
            "Turns ore and coal into metal bars.",
            0,
            4,
            new ForagingMineral(ForagingMineralType.COAL),1,
            0, new Texture("Sprites/ArtisanItem/Iron_Bar.png"));
    public CraftItemType craftItemType;
    public final String description;
    public int energy;
    public final int productionTimeInHours;
    public final BackPackable ingredients;
    public final int number;
    public int sellPrice;
    public Texture texture;

    ArtisanItemType(CraftItemType craftItemType,
                    String description,
                    int energy,
                    int productionTimeInHours,
                    BackPackable ingridients,
                    int number,
                    int sellPrice , Texture texture) {
        this.craftItemType = craftItemType;
        this.description = description;
        this.energy = energy;
        this.productionTimeInHours = productionTimeInHours;
        this.ingredients = ingridients;
        this.number = number;
        this.sellPrice = sellPrice;
        this.texture = texture;
    }
    public void setMoney(int money) {
        this.sellPrice = money;
    }
    public void  setEnergy(int energy) {
        this.energy = energy;
    }
    public BackPackable getIngredients() {
        return ingredients;
    }
    public int getNumber() {
        return number;
    }
    public CraftItemType getCraftItemType() {
        return craftItemType;
    }
    public Texture getTexture() {
        return texture;
    }
}

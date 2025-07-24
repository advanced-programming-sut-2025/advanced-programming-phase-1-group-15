package com.example.models.foraging;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public enum ForagingMineralType{
    QUARTZ(
        "A clear crystal commonly found in caves and mines.",
        25,
        new Sprite(new Texture("Sprites/Mineral/Quartz.png"))
    ),
    EARTH_CRYSTAL(
        "A resinous substance found near the surface.",
        50,
        new Sprite(new Texture("Sprites/Mineral/Earth_Crystal.png"))
    ),
    FROZEN_TEAR(
        "A crystal fabled to be the frozen tears of a yeti.",
        75,
        new Sprite(new Texture("Sprites/Mineral/Frozen_Tear.png"))
    ),
    FIRE_QUARTZ(
        "A glowing red crystal commonly found near hot lava.",
        100,
        new Sprite(new Texture("Sprites/Mineral/Fire_Quartz.png"))
    ),
    EMERALD(
        "A precious stone with a brilliant green color.",
        250,
        new Sprite(new Texture("Sprites/Mineral/Emerald.png"))
    ),
    AQUAMARINE(
        "A shimmery blue-green gem.",
        180,
        new Sprite(new Texture("Sprites/Mineral/Aquamarine.png"))
    ),
    RUBY(
        "A precious stone that is sought after for its rich color and beautiful luster.",
        250,
        new Sprite(new Texture("Sprites/Mineral/Ruby.png"))
    ),
    AMETHYST(
        "A purple variant of quartz.",
        100,
        new Sprite(new Texture("Sprites/Mineral/Amethyst.png"))
    ),
    TOPAZ(
        "Fairly common but still prized for its beauty.",
        80,
        new Sprite(new Texture("Sprites/Mineral/Topaz.png"))
    ),
    JADE(
        "A pale green ornamental stone.",
        200,
        new Sprite(new Texture("Sprites/Mineral/Jade.png"))
    ),
    DIAMOND(
        "A rare and valuable gem.",
        750,
        new Sprite(new Texture("Sprites/Mineral/Diamond.png"))
    ),
    PRISMATIC_SHARD(
        "A very rare and powerful substance with unknown origins.",
        2000,
        new Sprite(new Texture("Sprites/Mineral/Prismatic_Shard.png"))
    ),
    COPPER(
        "A common ore that can be smelted into bars.",
        5,
        new Sprite(new Texture("Sprites/Mineral/Copper.png"))
    ),
    IRON(
        "A fairly common ore that can be smelted into bars.",
        10,
        new Sprite(new Texture("Sprites/Mineral/Iron.png"))
    ),
    GOLD(
        "A precious ore that can be smelted into bars.",
        25,
        new Sprite(new Texture("Sprites/Mineral/Gold.png"))
    ),
    IRIDIUM(
        "An exotic ore with many curious properties. Can be smelted into bars.",
        100,
        new Sprite(new Texture("Sprites/Mineral/Iridium.png"))
    ),
    COAL(
        "A combustible rock that is useful for crafting and smelting.",
        15,
        new Sprite(new Texture("Sprites/Mineral/Coal.png"))
    );
    public final String description;
    public final int sellPrice;
    public final Sprite sprite;

    ForagingMineralType(String description, int sellPrice, Sprite sprite) {
        this.description = description;
        this.sellPrice = sellPrice;
        this.sprite = sprite;
    }
    public String getName(){
        return this.name().toLowerCase().replaceAll("_", " ");
    }
}

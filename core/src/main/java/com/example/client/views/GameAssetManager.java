package com.example.client.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAssetManager {
    public static BitmapFont font;

    // avatars
    public static TextureRegion boy_default_avatar, sam_avatar, sebastian_avatar;
    public static TextureRegion girl_default_avatar, abigail_avatar, marnie_avatar;

    // Weather & Time
    public static TextureRegion clock;
    public static TextureRegion spring, summer, autumn, winter;
    public static TextureRegion rainy, stormy, snowy, sunny;

    // Sprites and Items
    public static Sprite grass0, grass1, grass2, plowed_tile;
    public static Sprite land0, land1, land2, land3;
    public static Sprite quarry0, quarry1;
    public static Sprite stone, wood;

    // Area Textures
    public static TextureRegion house, lake, broken_greenhouse, greenhouse;
    public static TextureRegion barn, coop;
    public static TextureRegion carpenter_shop, marnie_ranch;
    public static TextureRegion BlackSmith , FishShop , JojaMart , MarnieRanch , PierreGeneralStore , StarDropSaloon;
    // Player Sprites
    public static TextureRegion boy_face, boy_fainted;
    public static Animation<TextureRegion> boy_walking_up, boy_walking_down, boy_walking_right, boy_walking_left;
    public static TextureRegion girl_face, girl_fainted;
    public static Animation<TextureRegion> girl_walking_up, girl_walking_down, girl_walking_right, girl_walking_left;

    // Tools
    public static Sprite axe, copper_axe, iron_axe, gold_axe, iridium_axe;
    public static Sprite hoe, copper_hoe, iron_hoe, gold_hoe, iridium_hoe;
    public static Sprite pickaxe, copper_pickaxe, iron_pickaxe, gold_pickaxe, iridium_pickaxe;
    public static Sprite watering_can, copper_watering_can, iron_watering_can, gold_watering_can, iridium_watering_can;
    public static Sprite trash_can, copper_trash_can, iron_trash_can, gold_trash_can, iridium_trash_can;
    public static Sprite scythe, gold_scythe, iridium_scythe;
    public static Sprite training_rod, bamboo_rod, fiberglass_rod, iridium_rod;
    public static Sprite milk_pail, shear, shipping_bin;

    // Animals
    public static Animation<TextureRegion> chicken_walking_up, chicken_walking_down, chicken_walking_right, chicken_walking_left, chicken_eating;
    public static Animation<TextureRegion> duck_walking_up, duck_walking_down, duck_walking_right, duck_walking_left, duck_eating;
    public static Animation<TextureRegion> dinosaur_walking_up, dinosaur_walking_down, dinosaur_walking_right, dinosaur_walking_left, dinosaur_eating;
    public static Animation<TextureRegion> rabbit_walking_up, rabbit_walking_down, rabbit_walking_right, rabbit_walking_left, rabbit_eating;

    public static Animation<TextureRegion> cow_walking_up, cow_walking_down, cow_walking_right, cow_walking_left, cow_eating;
    public static Animation<TextureRegion>  goat_walking_up, goat_walking_down, goat_walking_right, goat_walking_left, goat_eating;
    public static Animation<TextureRegion>  pig_walking_up, pig_walking_down, pig_walking_right, pig_walking_left, pig_eating;
    public static Animation<TextureRegion>  sheep_walking_up, sheep_walking_down, sheep_walking_right, sheep_walking_left, sheep_eating;
    public static Sprite heart;

    // Animal Products
    public static Sprite egg, large_egg, duck_egg, duck_feather, wool, rabbit_leg, dinosaur_egg, cow_milk, cow_large_milk,
        goat_milk, goat_large_milk, truffle;

    //NPCs
    public static Sprite abigail,harvey,leah,robin,sebastian;

    //Crops
    public static Sprite blue_jazz,blue_jazz_1,blue_jazz_2,blue_jazz_3,blue_jazz_4,blue_jazz_5;
    public static Sprite carrot,carrot_1,carrot_2,carrot_3,carrot_4;
    public static Sprite cauliflower,cauliflower_1,cauliflower_2,cauliflower_3,cauliflower_4,cauliflower_5,cauliflower_6;
    public static Sprite coffee_been,coffee_been_1,coffee_been_2,coffee_been_3,coffee_been_4,coffee_been_5,coffee_been_6,coffee_been_7;
    public static Sprite garlic,garlic_1,garlic_2,garlic_3,garlic_4,garlic_5;
    public static Sprite green_been,green_been_1,green_been_2,green_been_3,green_been_4,green_been_5,green_been_6,green_been_7,green_been_8;
    public static Sprite parsnip,parsnip_1,parsnip_2,parsnip_3,parsnip_4,parsnip_5;
    public static Sprite kale, kale_1, kale_2, kale_3, kale_4, kale_5;
    public static Sprite potato, potato_1, potato_2, potato_3, potato_4, potato_5, potato_6;
    public static Sprite rhubarb, rhubarb_1, rhubarb_2, rhubarb_3, rhubarb_4, rhubarb_5, rhubarb_6;
    public static Sprite strawberry, strawberry_1, strawberry_2, strawberry_3, strawberry_4, strawberry_5, strawberry_6,strawberry_7;
    public static Sprite tulip, tulip_1, tulip_2, tulip_3, tulip_4, tulip_5, tulip_6;
    public static Sprite un_milled_rice, un_milled_rice_1, un_milled_rice_2, un_milled_rice_3, un_milled_rice_4, un_milled_rice_5;
    public static Sprite blueberry, blueberry_1, blueberry_2, blueberry_3, blueberry_4, blueberry_5, blueberry_6, blueberry_7;
    public static Sprite corn, corn_1, corn_2, corn_3, corn_4, corn_5, corn_6, corn_7;
    public static Sprite hops, hops_1, hops_2, hops_3, hops_4, hops_5, hops_6, hops_7, hops_8;
    public static Sprite hot_pepper, hot_pepper_1, hot_pepper_2, hot_pepper_3, hot_pepper_4, hot_pepper_5, hot_pepper_6;
    public static Sprite melon, melon_1, melon_2, melon_3, melon_4, melon_5, melon_6;
    public static Sprite poppy, poppy_1, poppy_2, poppy_3, poppy_4, poppy_5, poppy_6;
    public static Sprite radish, radish_1, radish_2, radish_3, radish_4, radish_5;
    public static Sprite red_cabbage, red_cabbage_1, red_cabbage_2, red_cabbage_3, red_cabbage_4, red_cabbage_5, red_cabbage_6;
    public static Sprite star_fruit, star_fruit_1, star_fruit_2, star_fruit_3, star_fruit_4, star_fruit_5, star_fruit_6;
    public static Sprite summer_spangle, summer_spangle_1, summer_spangle_2, summer_spangle_3, summer_spangle_4, summer_spangle_5;
    public static Sprite summer_squash, summer_squash_1, summer_squash_2, summer_squash_3, summer_squash_4, summer_squash_5, summer_squash_6, summer_squash_7;
    public static Sprite sunflower, sunflower_1, sunflower_2, sunflower_3, sunflower_4, sunflower_5;
    public static Sprite tomato, tomato_1, tomato_2, tomato_3, tomato_4, tomato_5, tomato_6, tomato_7;
    public static Sprite wheat, wheat_1, wheat_2, wheat_3, wheat_4, wheat_5;
    public static Sprite amaranth, amaranth_1, amaranth_2, amaranth_3, amaranth_4, amaranth_5;
    public static Sprite artichoke, artichoke_1, artichoke_2, artichoke_3, artichoke_4, artichoke_5, artichoke_6;
    public static Sprite beet, beet_1, beet_2, beet_3, beet_4, beet_5;
    public static Sprite bok_choy, bok_choy_1, bok_choy_2, bok_choy_3, bok_choy_4, bok_choy_5;
    public static Sprite broccoli, broccoli_1, broccoli_2, broccoli_3, broccoli_4, broccoli_5;
    public static Sprite cranberry, cranberry_1, cranberry_2, cranberry_3, cranberry_4, cranberry_5, cranberry_6, cranberry_7;
    public static Sprite eggplant, eggplant_1, eggplant_2, eggplant_3, eggplant_4, eggplant_5, eggplant_6, eggplant_7;
    public static Sprite fairy_rose, fairy_rose_1, fairy_rose_2, fairy_rose_3, fairy_rose_4, fairy_rose_5;
    public static Sprite grape, grape_1, grape_2, grape_3, grape_4, grape_5, grape_6, grape_7;
    public static Sprite pumpkin, pumpkin_1, pumpkin_2, pumpkin_3, pumpkin_4, pumpkin_5, pumpkin_6;
    public static Sprite yam, yam_1, yam_2, yam_3, yam_4, yam_5;
    public static Sprite sweet_gem_berry, sweet_gem_berry_1, sweet_gem_berry_2, sweet_gem_berry_3, sweet_gem_berry_4, sweet_gem_berry_5, sweet_gem_berry_6;
    public static Sprite powder_melon, powder_melon_1, powder_melon_2, powder_melon_3, powder_melon_4, powder_melon_5, powder_melon_6;
    public static Sprite ancient_fruit, ancient_fruit_1, ancient_fruit_2, ancient_fruit_3, ancient_fruit_4, ancient_fruit_5, ancient_fruit_6, ancient_fruit_7;

    public static Sprite apricot_tree_1,apricot_tree_2,apricot_tree_3,apricot_tree_4,apricot_tree_5;
    public static Sprite cherry_tree_1,cherry_tree_2,cherry_tree_3,cherry_tree_4,cherry_tree_5;
    public static Sprite banana_tree_1,banana_tree_2,banana_tree_3,banana_tree_4,banana_tree_5;
    public static Sprite mango_tree_1,mango_tree_2,mango_tree_3,mango_tree_4,mango_tree_5;
    public static Sprite orange_tree_1,orange_tree_2,orange_tree_3,orange_tree_4,orange_tree_5;
    public static Sprite peach_tree_1,peach_tree_2,peach_tree_3,peach_tree_4,peach_tree_5;
    public static Sprite apple_tree_1,apple_tree_2,apple_tree_3,apple_tree_4,apple_tree_5;
    public static Sprite pomegranate_tree_1,pomegranate_tree_2,pomegranate_tree_3,pomegranate_tree_4,pomegranate_tree_5;
    public static Sprite oak_tree_1,oak_tree_2,oak_tree_3,oak_tree_4,oak_tree_5;
    public static Sprite maple_tree_1,maple_tree_2,maple_tree_3,maple_tree_4,maple_tree_5;
    public static Sprite pine_tree_1,pine_tree_2,pine_tree_3,pine_tree_4,pine_tree_5;
    public static Sprite mahogany_tree_1,mahogany_tree_2,mahogany_tree_3,mahogany_tree_4,mahogany_tree_5;
    public static Sprite mushroom_tree_1,mushroom_tree_2,mushroom_tree_3,mushroom_tree_4,mushroom_tree_5;
    public static Sprite mystic_tree_1,mystic_tree_2,mystic_tree_3,mystic_tree_4,mystic_tree_5;
    public static Sprite angry,cry,laugh,love,party,smile,thumbs_up;

    // foraging crops
    public static Sprite daffodil,common_mushroom,dandelion,leek,morel,salmonberry,spring_union,wild_horseradish,
        fiddle_head_fern,foraging_grape,red_mushroom,spice_berry,sweet_pea,blackberry,
    chanterelle,hazelnut,purple_mushroom,wild_plum,crocus,crystal_fruit,holley,snow_yam,winter_root;

    // foraging trees


    public static void load() {
        font = new BitmapFont(Gdx.files.internal("UI/font.fnt"));
        boy_default_avatar = new TextureRegion(new Texture(Gdx.files.internal("Sprites/Characters/boy_default_avatar.png")));
        sam_avatar = new TextureRegion(new Texture(Gdx.files.internal("Sprites/Characters/sam_avatar.png")));
        sebastian_avatar = new TextureRegion(new Texture(Gdx.files.internal("Sprites/Characters/sebastian_avatar.png")));
        girl_default_avatar = new TextureRegion(new Texture(Gdx.files.internal("Sprites/Characters/girl_default_avatar.png")));
        abigail_avatar = new TextureRegion(new Texture(Gdx.files.internal("Sprites/Characters/abigail_avatar.png")));
        marnie_avatar = new TextureRegion(new Texture(Gdx.files.internal("Sprites/Characters/marnie_avatar.png")));

        spring = loadRegion("Sprites/time/spring.png");
        summer = loadRegion("Sprites/time/summer.png");
        autumn = loadRegion("Sprites/time/autumn.png");
        winter = loadRegion("Sprites/time/winter.png");

        clock = loadRegion("Sprites/time/clock.png");
        rainy = loadRegion("Sprites/time/rainy.png");
        stormy = loadRegion("Sprites/time/stormy.png");
        snowy = loadRegion("Sprites/time/snowy.png");
        sunny = loadRegion("Sprites/time/sunny.png");

        grass0 = new Sprite(new Texture("Sprites/Map/grass0.png"));
        grass1 = new Sprite(new Texture("Sprites/Map/grass1.png"));
        grass2 = new Sprite(new Texture("Sprites/Map/grass2.png"));
        plowed_tile = new Sprite(new Texture("Sprites/Map/plowed_tile.png"));

        land0 = new Sprite(new Texture("Sprites/Map/land0.png"));
        land1 = new Sprite(new Texture("Sprites/Map/land1.png"));
        land2 = new Sprite(new Texture("Sprites/Map/land2.png"));
        land3 = new Sprite(new Texture("Sprites/Map/land3.png"));

        quarry0 = new Sprite(new Texture("Sprites/Map/quarry0.png"));
        quarry1 = new Sprite(new Texture("Sprites/Map/quarry1.png"));

        stone = new Sprite(new Texture("Sprites/Map/stone.png"));
        wood = new Sprite(new Texture("Sprites/Map/wood.png"));

        house = new TextureRegion(new Texture("Sprites/Map/house.png"));
        lake = new TextureRegion(new Texture("Sprites/Map/lake.png"));
        broken_greenhouse = new TextureRegion(new Texture("Sprites/Map/broken_greenhouse.png"));
        greenhouse = new TextureRegion(new Texture("Sprites/Map/greenhouse.png"));

        barn = new TextureRegion(new Texture("Sprites/Map/barn.png"));
        coop = new TextureRegion(new Texture("Sprites/Map/coop.png"));

        carpenter_shop = new TextureRegion(new Texture("Sprites/Map/carpenter_shop.png"));
        marnie_ranch = new TextureRegion(new Texture("Sprites/Map/marnies_ranch.png"));
        BlackSmith = new TextureRegion(new Texture("Sprites/stores/Blacksmith.png"));
        FishShop = new TextureRegion(new Texture("Sprites/stores/Fish_Shop.png"));
        JojaMart = new TextureRegion(new Texture("Sprites/stores/Jojamart.png"));
        PierreGeneralStore = new TextureRegion(new Texture("Sprites/stores/Pierres_shop.png"));
        StarDropSaloon = new TextureRegion(new Texture("Sprites/stores/Saloon.png"));

        boy_face = new TextureRegion(new Texture("Sprites/Characters/b_face.png"));
        boy_fainted = new TextureRegion(new Texture("Sprites/Characters/b_fainted.png"));
        boy_walking_up = createAnimation("Sprites/Characters/b", 8, 11);
        boy_walking_up.setPlayMode(Animation.PlayMode.LOOP);
        boy_walking_down = createAnimation("Sprites/Characters/b", 0, 3);
        boy_walking_down.setPlayMode(Animation.PlayMode.LOOP);
        boy_walking_right = createAnimation("Sprites/Characters/b", 4, 7);
        boy_walking_right.setPlayMode(Animation.PlayMode.LOOP);
        boy_walking_left = createAnimation("Sprites/Characters/b", 12, 15);
        boy_walking_left.setPlayMode(Animation.PlayMode.LOOP);
        girl_face = new TextureRegion(new Texture("Sprites/Characters/g_face.png"));
        girl_fainted = new TextureRegion(new Texture("Sprites/Characters/g_fainted.png"));
        girl_walking_up = createAnimation("Sprites/Characters/g", 8, 11);
        girl_walking_up.setPlayMode(Animation.PlayMode.LOOP);
        girl_walking_down = createAnimation("Sprites/Characters/g", 0, 3);
        girl_walking_down.setPlayMode(Animation.PlayMode.LOOP);
        girl_walking_right = createAnimation("Sprites/Characters/g", 4, 7);
        girl_walking_right.setPlayMode(Animation.PlayMode.LOOP);
        girl_walking_left = createAnimation("Sprites/Characters/g", 12, 15);
        girl_walking_left.setPlayMode(Animation.PlayMode.LOOP);

        axe = new Sprite(new Texture("Sprites/Tools/Axe.png"));
        copper_axe = new Sprite(new Texture("Sprites/Tools/Copper_Axe.png"));
        iron_axe = new Sprite(new Texture("Sprites/Tools/Iron_Axe.png"));
        gold_axe = new Sprite(new Texture("Sprites/Tools/Gold_Axe.png"));
        iridium_axe = new Sprite(new Texture("Sprites/Tools/Iridium_Axe.png"));

        hoe = new Sprite(new Texture("Sprites/Tools/Hoe.png"));
        copper_hoe = new Sprite(new Texture("Sprites/Tools/Copper_Hoe.png"));
        iron_hoe = new Sprite(new Texture("Sprites/Tools/Iron_Hoe.png"));
        gold_hoe = new Sprite(new Texture("Sprites/Tools/Gold_Hoe.png"));
        iridium_hoe = new Sprite(new Texture("Sprites/Tools/Iridium_Hoe.png"));

        pickaxe = new Sprite(new Texture("Sprites/Tools/Pickaxe.png"));
        copper_pickaxe = new Sprite(new Texture("Sprites/Tools/Copper_Pickaxe.png"));
        iron_pickaxe = new Sprite(new Texture("Sprites/Tools/Iron_Pickaxe.png"));
        gold_pickaxe = new Sprite(new Texture("Sprites/Tools/Gold_Pickaxe.png"));
        iridium_pickaxe = new Sprite(new Texture("Sprites/Tools/Iridium_Pickaxe.png"));

        scythe = new Sprite(new Texture("Sprites/Tools/Scythe.png"));
        gold_scythe = new Sprite(new Texture("Sprites/Tools/Gold_Scythe.png"));
        iridium_scythe = new Sprite(new Texture("Sprites/Tools/Iridium_Scythe.png"));

        training_rod = new Sprite(new Texture("Sprites/Tools/Training_Rod.png"));
        bamboo_rod = new Sprite(new Texture("Sprites/Tools/Bamboo_Rod.png"));
        fiberglass_rod = new Sprite(new Texture("Sprites/Tools/Fiberglass_Rod.png"));
        iridium_rod = new Sprite(new Texture("Sprites/Tools/Iridium_Rod.png"));

        watering_can = new Sprite(new Texture("Sprites/Tools/Watering_Can.png"));
        copper_watering_can = new Sprite(new Texture("Sprites/Tools/Copper_Watering_Can.png"));
        iron_watering_can = new Sprite(new Texture("Sprites/Tools/Iron_Watering_Can.png"));
        gold_watering_can = new Sprite(new Texture("Sprites/Tools/Gold_Watering_Can.png"));
        iridium_watering_can = new Sprite(new Texture("Sprites/Tools/Iridium_Watering_Can.png"));

        trash_can = new Sprite(new Texture("Sprites/Tools/Trash_Can.png"));
        copper_trash_can = new Sprite(new Texture("Sprites/Tools/Trash_Can_Copper.png"));
        iron_trash_can = new Sprite(new Texture("Sprites/Tools/Trash_Can_Iron.png"));
        gold_trash_can = new Sprite(new Texture("Sprites/Tools/Trash_Can_Gold.png"));
        iridium_trash_can = new Sprite(new Texture("Sprites/Tools/Trash_Can_Iridium.png"));

        milk_pail = new Sprite(new Texture("Sprites/Tools/Milk_Pail.png"));
        shear = new Sprite(new Texture("Sprites/Tools/Shears.png"));
        shipping_bin = new Sprite(new Texture("Sprites/Tools/Shipping_Bin.png"));

        // Chicken
        chicken_walking_up = createAnimation("Sprites/Animals/Chicken/", 8, 11); chicken_walking_up.setPlayMode(Animation.PlayMode.LOOP);
        chicken_walking_down = createAnimation("Sprites/Animals/Chicken/", 0, 3); chicken_walking_down.setPlayMode(Animation.PlayMode.LOOP);
        chicken_walking_right = createAnimation("Sprites/Animals/Chicken/", 4, 7); chicken_walking_right.setPlayMode(Animation.PlayMode.LOOP);
        chicken_walking_left = createAnimation("Sprites/Animals/Chicken/", 12, 15); chicken_walking_left.setPlayMode(Animation.PlayMode.LOOP);
        chicken_eating = createAnimation("Sprites/Animals/Chicken/", 16, 19); chicken_eating.setPlayMode(Animation.PlayMode.LOOP);

        // Duck
        duck_walking_up = createAnimation("Sprites/Animals/Duck/", 8, 11); duck_walking_up.setPlayMode(Animation.PlayMode.LOOP);
        duck_walking_down = createAnimation("Sprites/Animals/Duck/", 0, 3); duck_walking_down.setPlayMode(Animation.PlayMode.LOOP);
        duck_walking_right = createAnimation("Sprites/Animals/Duck/", 4, 7); duck_walking_right.setPlayMode(Animation.PlayMode.LOOP);
        duck_walking_left = createAnimation("Sprites/Animals/Duck/", 12, 15); duck_walking_left.setPlayMode(Animation.PlayMode.LOOP);
        duck_eating = createAnimation("Sprites/Animals/Duck/", 16, 19); duck_eating.setPlayMode(Animation.PlayMode.LOOP);

        // Dinosaur
        dinosaur_walking_up = createAnimation("Sprites/Animals/Dinosaur/", 8, 11); dinosaur_walking_up.setPlayMode(Animation.PlayMode.LOOP);
        dinosaur_walking_down = createAnimation("Sprites/Animals/Dinosaur/", 0, 3); dinosaur_walking_down.setPlayMode(Animation.PlayMode.LOOP);
        dinosaur_walking_right = createAnimation("Sprites/Animals/Dinosaur/", 4, 7); dinosaur_walking_right.setPlayMode(Animation.PlayMode.LOOP);
        dinosaur_walking_left = createAnimation("Sprites/Animals/Dinosaur/", 12, 15); dinosaur_walking_left.setPlayMode(Animation.PlayMode.LOOP);
        dinosaur_eating = createAnimation("Sprites/Animals/Dinosaur/", 16, 19); dinosaur_eating.setPlayMode(Animation.PlayMode.LOOP);

        // Rabbit
        rabbit_walking_up = createAnimation("Sprites/Animals/Rabbit/", 8, 11); rabbit_walking_up.setPlayMode(Animation.PlayMode.LOOP);
        rabbit_walking_down = createAnimation("Sprites/Animals/Rabbit/", 0, 3); rabbit_walking_down.setPlayMode(Animation.PlayMode.LOOP);
        rabbit_walking_right = createAnimation("Sprites/Animals/Rabbit/", 4, 7); rabbit_walking_right.setPlayMode(Animation.PlayMode.LOOP);
        rabbit_walking_left = createAnimation("Sprites/Animals/Rabbit/", 12, 15); rabbit_walking_left.setPlayMode(Animation.PlayMode.LOOP);
        rabbit_eating = createAnimation("Sprites/Animals/Rabbit/", 16, 19); rabbit_eating.setPlayMode(Animation.PlayMode.LOOP);

        // Cow
        cow_walking_up = createAnimation("Sprites/Animals/Cow/", 8, 11); cow_walking_up.setPlayMode(Animation.PlayMode.LOOP);
        cow_walking_down = createAnimation("Sprites/Animals/Cow/", 0, 3); cow_walking_down.setPlayMode(Animation.PlayMode.LOOP);
        cow_walking_right = createAnimation("Sprites/Animals/Cow/", 4, 7); cow_walking_right.setPlayMode(Animation.PlayMode.LOOP);
        cow_walking_left = createAnimation("Sprites/Animals/Cow/", 12, 15); cow_walking_left.setPlayMode(Animation.PlayMode.LOOP);
        cow_eating = createAnimation("Sprites/Animals/Cow/", 16, 19); cow_eating.setPlayMode(Animation.PlayMode.LOOP);

        // Goat
        goat_walking_up = createAnimation("Sprites/Animals/Goat/", 8, 11); goat_walking_up.setPlayMode(Animation.PlayMode.LOOP);
        goat_walking_down = createAnimation("Sprites/Animals/Goat/", 0, 3); goat_walking_down.setPlayMode(Animation.PlayMode.LOOP);
        goat_walking_right = createAnimation("Sprites/Animals/Goat/", 4, 7); goat_walking_right.setPlayMode(Animation.PlayMode.LOOP);
        goat_walking_left = createAnimation("Sprites/Animals/Goat/", 12, 15); goat_walking_left.setPlayMode(Animation.PlayMode.LOOP);
        goat_eating = createAnimation("Sprites/Animals/Goat/", 16, 19); goat_eating.setPlayMode(Animation.PlayMode.LOOP);

        // Pig
        pig_walking_up = createAnimation("Sprites/Animals/Pig/", 8, 11); pig_walking_up.setPlayMode(Animation.PlayMode.LOOP);
        pig_walking_down = createAnimation("Sprites/Animals/Pig/", 0, 3); pig_walking_down.setPlayMode(Animation.PlayMode.LOOP);
        pig_walking_right = createAnimation("Sprites/Animals/Pig/", 4, 7); pig_walking_right.setPlayMode(Animation.PlayMode.LOOP);
        pig_walking_left = createAnimation("Sprites/Animals/Pig/", 12, 15); pig_walking_left.setPlayMode(Animation.PlayMode.LOOP);
        pig_eating = createAnimation("Sprites/Animals/Pig/", 16, 19); pig_eating.setPlayMode(Animation.PlayMode.LOOP);

        // Sheep
        sheep_walking_up = createAnimation("Sprites/Animals/Sheep/", 8, 11); sheep_walking_up.setPlayMode(Animation.PlayMode.LOOP);
        sheep_walking_down = createAnimation("Sprites/Animals/Sheep/", 0, 3); sheep_walking_down.setPlayMode(Animation.PlayMode.LOOP);
        sheep_walking_right = createAnimation("Sprites/Animals/Sheep/", 4, 7); sheep_walking_right.setPlayMode(Animation.PlayMode.LOOP);
        sheep_walking_left = createAnimation("Sprites/Animals/Sheep/", 12, 15); sheep_walking_left.setPlayMode(Animation.PlayMode.LOOP);
        sheep_eating = createAnimation("Sprites/Animals/Sheep/", 16, 19); sheep_eating.setPlayMode(Animation.PlayMode.LOOP);

        heart = new Sprite(new Texture("Sprites/heart.png"));

        // Animal Products
        egg = new Sprite(new Texture("Sprites/AnimalProduct/egg.png"));
        large_egg = new Sprite(new Texture("Sprites/AnimalProduct/large_egg.png"));
        duck_egg = new Sprite(new Texture("Sprites/AnimalProduct/duck_egg.png"));
        duck_feather = new Sprite(new Texture("Sprites/AnimalProduct/duck_feather.png"));
        wool = new Sprite(new Texture("Sprites/AnimalProduct/wool.png"));
        rabbit_leg = new Sprite(new Texture("Sprites/AnimalProduct/rabbit_leg.png"));
        dinosaur_egg = new Sprite(new Texture("Sprites/AnimalProduct/dinosaur_egg.png"));
        cow_milk = new Sprite(new Texture("Sprites/AnimalProduct/cow_milk.png"));
        cow_large_milk = new Sprite(new Texture("Sprites/AnimalProduct/cow_large_milk.png"));
        goat_milk = new Sprite(new Texture("Sprites/AnimalProduct/goat_milk.png"));
        goat_large_milk = new Sprite(new Texture("Sprites/AnimalProduct/goat_large_milk.png"));
        truffle = new Sprite(new Texture("Sprites/AnimalProduct/truffle.png"));

        abigail = new Sprite(new Texture("Sprites/NPCs/Abigail.png"));
        harvey = new Sprite(new Texture("Sprites/NPCs/Harvey.png"));
        leah = new Sprite(new Texture("Sprites/NPCs/Leah.png"));
        robin = new Sprite(new Texture("Sprites/NPCs/Robin.png"));
        sebastian = new Sprite(new Texture("Sprites/NPCs/Sebastian.png"));

        blue_jazz_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blue_Jazz_Stage_1.png")));
        blue_jazz_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blue_Jazz_Stage_2.png")));
        blue_jazz_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blue_Jazz_Stage_3.png")));
        blue_jazz_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blue_Jazz_Stage_4.png")));
        blue_jazz_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blue_Jazz_Stage_5.png")));

        carrot_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Carrot_Stage_1.png")));
        carrot_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Carrot_Stage_2.png")));
        carrot_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Carrot_Stage_3.png")));
        carrot_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Carrot_Stage_4.png")));

        cauliflower_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Stage_1.png")));
        cauliflower_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Stage_2.png")));
        cauliflower_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Stage_3.png")));
        cauliflower_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Stage_4.png")));
        cauliflower_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Stage_5.png")));
        cauliflower_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Stage_6.png")));

        coffee_been_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_1.png")));
        coffee_been_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_2.png")));
        coffee_been_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_3.png")));
        coffee_been_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_4.png")));
        coffee_been_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_5.png")));
        coffee_been_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_6.png")));
        coffee_been_7 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_7.png")));

        garlic_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Garlic_Stage_1.png")));
        garlic_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Garlic_Stage_2.png")));
        garlic_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Garlic_Stage_3.png")));
        garlic_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Garlic_Stage_4.png")));
        garlic_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Garlic_Stage_5.png")));

        green_been_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean.png")));
        green_been_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_2.png")));
        green_been_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_3.png")));
        green_been_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_4.png")));
        green_been_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_5.png")));
        green_been_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_6.png")));
        green_been_7 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_7.png")));
        green_been_8 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_8.png")));

        parsnip_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Parsnip_Stage_1.png")));
        parsnip_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Parsnip_Stage_2.png")));
        parsnip_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Parsnip_Stage_3.png")));
        parsnip_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Parsnip_Stage_4.png")));
        parsnip_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Parsnip_Stage_5.png")));

        kale_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Kale_Stage_1.png")));
        kale_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Kale_Stage_2.png")));
        kale_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Kale_Stage_3.png")));
        kale_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Kale_Stage_4.png")));
        kale_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Kale_Stage_5.png")));

        potato_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Potato_Stage_1.png")));
        potato_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Potato_Stage_2.png")));
        potato_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Potato_Stage_3.png")));
        potato_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Potato_Stage_4.png")));
        potato_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Potato_Stage_5.png")));
        potato_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Potato_Stage_6.png")));

        rhubarb_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Rhubarb_Stage_1.png")));
        rhubarb_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Rhubarb_Stage_2.png")));
        rhubarb_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Rhubarb_Stage_3.png")));
        rhubarb_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Rhubarb_Stage_4.png")));
        rhubarb_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Rhubarb_Stage_5.png")));
        rhubarb_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Rhubarb_Stage_6.png")));

        strawberry_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Strawberry_Stage_1.png")));
        strawberry_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Strawberry_Stage_2.png")));
        strawberry_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Strawberry_Stage_3.png")));
        strawberry_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Strawberry_Stage_4.png")));
        strawberry_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Strawberry_Stage_5.png")));
        strawberry_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Strawberry_Stage_6.png")));
        strawberry_7 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Strawberry_Stage_7.png")));

        tulip_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Tulip_Stage_1.png")));
        tulip_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Tulip_Stage_2.png")));
        tulip_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Tulip_Stage_3.png")));
        tulip_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Tulip_Stage_4.png")));
        tulip_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Tulip_Stage_6.png")));

        un_milled_rice_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Unmilled_Rice_Stage_1.png")));
        un_milled_rice_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Unmilled_Rice_Stage_2.png")));
        un_milled_rice_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Unmilled_Rice_Stage_3.png")));
        un_milled_rice_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Unmilled_Rice_Stage_4.png")));
        un_milled_rice_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Unmilled_Rice_Stage_5.png")));

        blueberry_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blueberry_Stage_1.png")));
        blueberry_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blueberry_Stage_2.png")));
        blueberry_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blueberry_Stage_3.png")));
        blueberry_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blueberry_Stage_4.png")));
        blueberry_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blueberry_Stage_5.png")));
        blueberry_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blueberry_Stage_6.png")));
        blueberry_7 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blueberry_Stage_7.png")));

        corn_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Corn_Stage_1.png")));
        corn_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Corn_Stage_2.png")));
        corn_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Corn_Stage_3.png")));
        corn_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Corn_Stage_4.png")));
        corn_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Corn_Stage_5.png")));
        corn_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Corn_Stage_6.png")));
        corn_7 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Corn_Stage_7.png")));

        hops_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hops_Stage_1.png")));
        hops_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hops_Stage_3.png")));
        hops_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hops_Stage_4.png")));
        hops_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hops_Stage_5.png")));
        hops_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hops_Stage_6.png")));
        hops_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hops_Stage_7.png")));
        hops_7 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hops_Stage_8.png")));

        hot_pepper_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hot_Pepper_Stage_1.png")));
        hot_pepper_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hot_Pepper_Stage_2.png")));
        hot_pepper_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hot_Pepper_Stage_3.png")));
        hot_pepper_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hot_Pepper_Stage_4.png")));
        hot_pepper_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hot_Pepper_Stage_5.png")));
        hot_pepper_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Hot_Pepper_Stage_6.png")));

        melon_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Melon_Stage_1.png")));
        melon_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Melon_Stage_2.png")));
        melon_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Melon_Stage_3.png")));
        melon_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Melon_Stage_4.png")));
        melon_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Melon_Stage_5.png")));
        melon_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Melon_Stage_6.png")));

        poppy_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Poppy_Stage_1.png")));
        poppy_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Poppy_Stage_2.png")));
        poppy_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Poppy_Stage_3.png")));
        poppy_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Poppy_Stage_4.png")));
        poppy_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Poppy_Stage_6.png")));

        radish_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Radish_Stage_1.png")));
        radish_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Radish_Stage_2.png")));
        radish_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Radish_Stage_3.png")));
        radish_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Radish_Stage_4.png")));
        radish_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Radish_Stage_5.png")));

        red_cabbage_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Red_Cabbage_Stage_1.png")));
        red_cabbage_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Red_Cabbage_Stage_2.png")));
        red_cabbage_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Red_Cabbage_Stage_3.png")));
        red_cabbage_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Red_Cabbage_Stage_4.png")));
        red_cabbage_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Red_Cabbage_Stage_5.png")));
        red_cabbage_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Red_Cabbage_Stage_6.png")));

        star_fruit_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Starfruit_Stage_1.png")));
        star_fruit_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Starfruit_Stage_2.png")));
        star_fruit_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Starfruit_Stage_3.png")));
        star_fruit_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Starfruit_Stage_4.png")));
        star_fruit_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Starfruit_Stage_5.png")));
        star_fruit_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Starfruit_Stage_6.png")));

        summer_spangle_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Summer_Spangle_Stage_1.png")));
        summer_spangle_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Summer_Spangle_Stage_2.png")));
        summer_spangle_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Summer_Spangle_Stage_3.png")));
        summer_spangle_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Summer_Spangle_Stage_4.png")));
        summer_spangle_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Summer_Spangle_Stage_5.png")));

        summer_squash_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Summer_Squash_Stage_1.png")));
        summer_squash_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Summer_Squash_Stage_2.png")));
        summer_squash_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Summer_Squash_Stage_3.png")));
        summer_squash_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Summer_Squash_Stage_4.png")));
        summer_squash_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Summer_Squash_Stage_5.png")));
        summer_squash_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Summer_Squash_Stage_6.png")));
        summer_squash_7 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Summer_Squash_Stage_7.png")));

        sunflower_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Sunflower_Stage_1.png")));
        sunflower_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Sunflower_Stage_2.png")));
        sunflower_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Sunflower_Stage_3.png")));
        sunflower_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Sunflower_Stage_4.png")));
        sunflower_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Sunflower_Stage_5.png")));

        tomato_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Tomato_Stage_1.png")));
        tomato_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Tomato_Stage_2.png")));
        tomato_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Tomato_Stage_3.png")));
        tomato_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Tomato_Stage_4.png")));
        tomato_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Tomato_Stage_5.png")));
        tomato_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Tomato_Stage_6.png")));
        tomato_7 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Tomato_Stage_7.png")));

        wheat_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Wheat_Stage_1.png")));
        wheat_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Wheat_Stage_2.png")));
        wheat_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Wheat_Stage_3.png")));
        wheat_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Wheat_Stage_4.png")));
        wheat_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Wheat_Stage_5.png")));

        amaranth_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Amaranth_Stage_1.png")));
        amaranth_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Amaranth_Stage_2.png")));
        amaranth_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Amaranth_Stage_3.png")));
        amaranth_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Amaranth_Stage_4.png")));
        amaranth_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Amaranth_Stage_5.png")));

        artichoke_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Artichoke_Stage_1.png")));
        artichoke_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Artichoke_Stage_2.png")));
        artichoke_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Artichoke_Stage_3.png")));
        artichoke_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Artichoke_Stage_4.png")));
        artichoke_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Artichoke_Stage_5.png")));
        artichoke_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Artichoke_Stage_6.png")));

        beet_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Beet_Stage_1.png")));
        beet_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Beet_Stage_2.png")));
        beet_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Beet_Stage_3.png")));
        beet_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Beet_Stage_4.png")));
        beet_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Beet_Stage_5.png")));

        bok_choy_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Bok_Choy_Stage_1.png")));
        bok_choy_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Bok_Choy_Stage_2.png")));
        bok_choy_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Bok_Choy_Stage_3.png")));
        bok_choy_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Bok_Choy_Stage_4.png")));
        bok_choy_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Bok_Choy_Stage_5.png")));

        broccoli_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Broccoli_Stage_1.png")));
        broccoli_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Broccoli_Stage_2.png")));
        broccoli_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Broccoli_Stage_3.png")));
        broccoli_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Broccoli_Stage_4.png")));
        broccoli_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Broccoli_Stage_5.png")));

        cranberry_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cranberry_Stage_1.png")));
        cranberry_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cranberry_Stage_2.png")));
        cranberry_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cranberry_Stage_3.png")));
        cranberry_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cranberry_Stage_4.png")));
        cranberry_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cranberry_Stage_5.png")));
        cranberry_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cranberry_Stage_6.png")));
        cranberry_7 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cranberry_Stage_7.png")));

        eggplant_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Eggplant_Stage_1.png")));
        eggplant_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Eggplant_Stage_2.png")));
        eggplant_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Eggplant_Stage_3.png")));
        eggplant_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Eggplant_Stage_4.png")));
        eggplant_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Eggplant_Stage_5.png")));
        eggplant_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Eggplant_Stage_6.png")));
        eggplant_7 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Eggplant_Stage_7.png")));

        fairy_rose_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Fairy_Rose_Stage_1.png")));
        fairy_rose_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Fairy_Rose_Stage_2.png")));
        fairy_rose_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Fairy_Rose_Stage_3.png")));
        fairy_rose_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Fairy_Rose_Stage_4.png")));
        fairy_rose_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Fairy_Rose_Stage_5.png")));

        grape_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Grape_Stage_1.png")));
        grape_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Grape_Stage_2.png")));
        grape_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Grape_Stage_3.png")));
        grape_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Grape_Stage_4.png")));
        grape_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Grape_Stage_5.png")));
        grape_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Grape_Stage_6.png")));
        grape_7 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Grape_Stage_7.png")));

        pumpkin_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Pumpkin_Stage_1.png")));
        pumpkin_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Pumpkin_Stage_2.png")));
        pumpkin_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Pumpkin_Stage_3.png")));
        pumpkin_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Pumpkin_Stage_4.png")));
        pumpkin_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Pumpkin_Stage_5.png")));
        pumpkin_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Pumpkin_Stage_6.png")));

        yam_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Yam_Stage_1.png")));
        yam_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Yam_Stage_2.png")));
        yam_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Yam_Stage_3.png")));
        yam_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Yam_Stage_4.png")));
        yam_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Yam_Stage_5.png")));

        sweet_gem_berry_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Sweet_Gem_Berry_Stage_1.png")));
        sweet_gem_berry_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Sweet_Gem_Berry_Stage_2.png")));
        sweet_gem_berry_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Sweet_Gem_Berry_Stage_3.png")));
        sweet_gem_berry_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Sweet_Gem_Berry_Stage_4.png")));
        sweet_gem_berry_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Sweet_Gem_Berry_Stage_5.png")));
        sweet_gem_berry_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Sweet_Gem_Berry_Stage_6.png")));

        powder_melon_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Powdermelon_Stage_1.png")));
        powder_melon_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Powdermelon_Stage_2.png")));
        powder_melon_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Powdermelon_Stage_3.png")));
        powder_melon_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Powdermelon_Stage_4.png")));
        powder_melon_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Powdermelon_Stage_5.png")));
        powder_melon_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Powdermelon_Stage_6.png")));

        ancient_fruit_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Ancient_Fruit_Stage_1.png")));
        ancient_fruit_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Ancient_Fruit_Stage_2.png")));
        ancient_fruit_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Ancient_Fruit_Stage_3.png")));
        ancient_fruit_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Ancient_Fruit_Stage_4.png")));
        ancient_fruit_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Ancient_Fruit_Stage_5.png")));
        ancient_fruit_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Ancient_Fruit_Stage_6.png")));
        ancient_fruit_7 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Ancient_Fruit_Stage_7.png")));

        // Apricot Trees
        apricot_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Apricot_Stage_1.png")));
        apricot_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Apricot_Stage_2.png")));
        apricot_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Apricot_Stage_3.png")));
        apricot_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Apricot_Stage_4.png")));
        apricot_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Apricot_Stage_5.png")));

        // Cherry Trees
        cherry_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Cherry_Stage_1.png")));
        cherry_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Cherry_Stage_2.png")));
        cherry_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Cherry_Stage_3.png")));
        cherry_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Cherry_Stage_4.png")));
        cherry_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Cherry_Stage_5.png")));

        // Banana Trees
        banana_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Banana_Stage_1.png")));
        banana_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Banana_Stage_2.png")));
        banana_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Banana_Stage_3.png")));
        banana_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Banana_Stage_4.png")));
        banana_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Banana_Stage_5.png")));

        // Mango Trees
        mango_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mango_Stage_1.png")));
        mango_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mango_Stage_2.png")));
        mango_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mango_Stage_3.png")));
        mango_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mango_Stage_4.png")));
        mango_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mango_Stage_5.png")));

        // Orange Trees
        orange_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Orange_Stage_1.png")));
        orange_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Orange_Stage_2.png")));
        orange_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Orange_Stage_3.png")));
        orange_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Orange_Stage_4.png")));
        orange_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Orange_Stage_5.png")));

        // Peach Trees
        peach_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Peach_Stage_1.png")));
        peach_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Peach_Stage_2.png")));
        peach_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Peach_Stage_3.png")));
        peach_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Peach_Stage_4.png")));
        peach_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Peach_Stage_5.png")));

        // Apple Trees
        apple_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Apple_Stage_1.png")));
        apple_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Apple_Stage_2.png")));
        apple_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Apple_Stage_3.png")));
        apple_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Apple_Stage_4.png")));
        apple_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Apple_Stage_5.png")));

        // Pomegranate Trees
        pomegranate_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Pomegranate_Stage_1.png")));
        pomegranate_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Pomegranate_Stage_2.png")));
        pomegranate_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Pomegranate_Stage_3.png")));
        pomegranate_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Pomegranate_Stage_4.png")));
        pomegranate_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Pomegranate_Stage_5.png")));

        // Oak Trees
        oak_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Oak_Stage_1.png")));
        oak_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Oak_Stage_2.png")));
        oak_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Oak_Stage_3.png")));
        oak_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Oak_Stage_4.png")));
        oak_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Oak_Stage_5.png")));

        // Maple Trees
        maple_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Maple_Stage_1.png")));
        maple_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Maple_Stage_2.png")));
        maple_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Maple_Stage_3.png")));
        maple_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Maple_Stage_4.png")));
        maple_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Maple_Stage_5.png")));

        // Pine Trees
        pine_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Pine_Stage_1.png")));
        pine_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Pine_Stage_2.png")));
        pine_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Pine_Stage_3.png")));
        pine_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Pine_Stage_4.png")));
        pine_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Pine_Stage_5.png")));

        // Mahogany Trees
        mahogany_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mahogany_Stage_1.png")));
        mahogany_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mahogany_Stage_2.png")));
        mahogany_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mahogany_Stage_3.png")));
        mahogany_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mahogany_Stage_4.png")));
        mahogany_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mahogany_Stage_5.png")));

        // Mushroom Trees
        mushroom_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/MushroomTree_Stage_1.png")));
        mushroom_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/MushroomTree_Stage_2.png")));
        mushroom_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/MushroomTree_Stage_3.png")));
        mushroom_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/MushroomTree_Stage_4.png")));
        mushroom_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/MushroomTree_Stage_5.png")));

        // Mystic Trees
        mystic_tree_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mystic_Tree_Stage_1.png")));
        mystic_tree_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mystic_Tree_Stage_2.png")));
        mystic_tree_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mystic_Tree_Stage_3.png")));
        mystic_tree_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mystic_Tree_Stage_4.png")));
        mystic_tree_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Trees/Mystic_Tree_Stage_5.png")));

        daffodil = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Daffodil.png")));
        common_mushroom = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Common_Mushroom.png")));
        dandelion = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Dandelion.png")));
        leek = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Leek.png")));
        morel = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Morel.png")));
        salmonberry = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Salmonberry.png")));
        spring_union = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Spring_Onion.png")));
        wild_horseradish = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Wild_Horseradish.png")));
        fiddle_head_fern = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Fiddlehead_Fern.png")));
        foraging_grape = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Grape.png")));
        red_mushroom = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Red_Mushroom.png")));
        spice_berry = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Spice_Berry.png")));
        sweet_pea = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Sweet_Pea.png")));
        blackberry = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Blackberry.png")));
        chanterelle = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Chanterelle.png")));
        hazelnut = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Hazelnut.png")));
        purple_mushroom = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Purple_Mushroom.png")));
        wild_plum = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Wild_Plum.png")));
        crocus = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Crocus.png")));
        crystal_fruit = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Crystal_Fruit.png")));
        holley = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Holly.png")));
        snow_yam = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Snow_Yam.png")));
        winter_root = new Sprite(new Texture(Gdx.files.internal("Sprites/Foraging/Winter_Root.png")));


        //emoji
        angry = new Sprite(new Texture("Sprites/Emoji/angry.png"));
        cry = new Sprite(new Texture("Sprites/Emoji/cry.png"));
        laugh = new Sprite(new Texture("Sprites/Emoji/laugh.png"));
        love = new Sprite(new Texture("Sprites/Emoji/love.png"));
        party = new Sprite(new Texture("Sprites/Emoji/party.png"));
        smile = new Sprite(new Texture("Sprites/Emoji/smile.png"));
        thumbs_up = new Sprite(new Texture("Sprites/Emoji/thumbs_up.png"));

    }

    private static TextureRegion loadRegion(String path) {
        return new TextureRegion(new Texture(Gdx.files.internal(path)));
    }

    private static Animation<TextureRegion> createAnimation(String basePath, int startIdx, int endIdx) {
        TextureRegion[] frames = new TextureRegion[endIdx - startIdx + 1];
        for (int i = startIdx; i <= endIdx; i++) {
            frames[i - startIdx] = new TextureRegion(new Texture(basePath + i + ".png"));
        }
        return new Animation<>(0.1f, frames);
    }

    public static Sprite getGrassSprite(int randomizer) {
        switch (randomizer) {
            case 1:
                return grass1;
            default:
                return grass0;
        }
    }

    public static Sprite getLandSprite(int randomizer) {
        return switch (randomizer) {
            case 1 -> land1;
            case 2 -> land2;
            case 3 -> land3;
            default -> land0;
        };
    }

    public static Sprite getQuarrySprite(int randomizer) {
        switch (randomizer) {
            case 1:
                return quarry1;
            default:
                return quarry0;
        }
    }
}


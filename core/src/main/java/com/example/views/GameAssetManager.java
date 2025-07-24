package com.example.views;

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

    public static TextureRegion clock;
    public static TextureRegion spring, summer, autumn, winter;
    public static TextureRegion rainy, stormy, snowy, sunny;

    public static Sprite grass0, grass1, grass2, plowed_tile;
    public static Sprite land0, land1, land2, land3;
    public static Sprite quarry0, quarry1;
    public static Sprite stone, wood;

    public static TextureRegion house, lake, broken_greenhouse, greenhouse;

    public static TextureRegion boy_face, boy_fainted;
    public static Animation<TextureRegion> boy_walking_up, boy_walking_down, boy_walking_right, boy_walking_left;

    // Tools
    public static Sprite axe, copper_axe, iron_axe, gold_axe, iridium_axe;
    public static Sprite hoe, copper_hoe, iron_hoe, gold_hoe, iridium_hoe;
    public static Sprite pickaxe, copper_pickaxe, iron_pickaxe, gold_pickaxe, iridium_pickaxe;
    public static Sprite watering_can, copper_watering_can, iron_watering_can, gold_watering_can, iridium_watering_can;
    public static Sprite trash_can, copper_trash_can, iron_trash_can, gold_trash_can, iridium_trash_can;
    public static Sprite scythe, gold_scythe, iridium_scythe;
    public static Sprite training_rod, bamboo_rod, fiberglass_rod, iridium_rod;
    public static Sprite milk_pail, shear, shipping_bin;

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

        boy_face = new TextureRegion(new Texture("Sprites/Characters/b_face.png"));
        boy_fainted = new TextureRegion(new Texture("Sprites/Characters/b_fainted.png"));
        boy_walking_up = createAnimation("Sprites/Characters/b", 8, 11); boy_walking_up.setPlayMode(Animation.PlayMode.LOOP);
        boy_walking_down = createAnimation("Sprites/Characters/b", 0, 3); boy_walking_down.setPlayMode(Animation.PlayMode.LOOP);
        boy_walking_right = createAnimation("Sprites/Characters/b", 4, 7); boy_walking_right.setPlayMode(Animation.PlayMode.LOOP);
        boy_walking_left = createAnimation("Sprites/Characters/b", 12, 15); boy_walking_left.setPlayMode(Animation.PlayMode.LOOP);

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

        abigail = new Sprite(new Texture("Sprites/NPCs/Abigail.png"));
        harvey = new Sprite(new Texture("Sprites/NPCs/Harvey.png"));
        leah = new Sprite(new Texture("Sprites/NPCs/Leah.png"));
        robin = new Sprite(new Texture("Sprites/NPCs/Robin.png"));
        sebastian = new Sprite(new Texture("Sprites/NPCs/Sebastian.png"));

        blue_jazz     = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blue_Jazz.png")));
        blue_jazz_1   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blue_Jazz_Stage_1.png")));
        blue_jazz_2   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blue_Jazz_Stage_2.png")));
        blue_jazz_3   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blue_Jazz_Stage_3.png")));
        blue_jazz_4   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blue_Jazz_Stage_4.png")));
        blue_jazz_5   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Blue_Jazz_Stage_5.png")));

        carrot        = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Carrot.png")));
        carrot_1      = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Carrot_Stage_1.png")));
        carrot_2      = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Carrot_Stage_2.png")));
        carrot_3      = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Carrot_Stage_3.png")));
        carrot_4      = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Carrot_Stage_4.png")));

        cauliflower   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Seeds.png")));
        cauliflower_1 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Stage_1.png")));
        cauliflower_2 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Stage_2.png")));
        cauliflower_3 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Stage_3.png")));
        cauliflower_4 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Stage_4.png")));
        cauliflower_5 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Stage_5.png")));
        cauliflower_6 = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Cauliflower_Stage_6.png")));

        coffee_been     = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Bean.png")));
        coffee_been_1   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_1.png")));
        coffee_been_2   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_2.png")));
        coffee_been_3   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_3.png")));
        coffee_been_4   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_4.png")));
        coffee_been_5   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_5.png")));
        coffee_been_6   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_6.png")));
        coffee_been_7   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Coffee_Stage_7.png")));

        garlic         = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Garlic_Seeds.png")));
        garlic_1       = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Garlic_Stage_1.png")));
        garlic_2       = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Garlic_Stage_2.png")));
        garlic_3       = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Garlic_Stage_3.png")));
        garlic_4       = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Garlic_Stage_4.png")));
        garlic_5       = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Garlic_Stage_5.png")));

        green_been_1     = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean.png")));
        green_been_2   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_2.png")));
        green_been_3   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_3.png")));
        green_been_4   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_4.png")));
        green_been_5   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_5.png")));
        green_been_6   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_6.png")));
        green_been_7   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_7.png")));
        green_been_8   = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Green_Bean_Stage_8.png")));

        parsnip        = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Parsnip_Seeds.png")));
        parsnip_1      = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Parsnip_Stage_1.png")));
        parsnip_2      = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Parsnip_Stage_2.png")));
        parsnip_3      = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Parsnip_Stage_3.png")));
        parsnip_4      = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Parsnip_Stage_4.png")));
        parsnip_5      = new Sprite(new Texture(Gdx.files.internal("Sprites/Crops/Parsnip_Stage_5.png")));
    }

    private static TextureRegion loadRegion(String path) {
        return new TextureRegion(new Texture(Gdx.files.internal(path)));
    }

    private static Animation<TextureRegion> createAnimation(String basePath, int startIdx, int endIdx) {
        TextureRegion[] frames = new TextureRegion[endIdx - startIdx + 1];
        for (int i = startIdx; i <= endIdx; i++) {
            frames[i - startIdx] = new TextureRegion(new Texture(basePath + i + ".png"));
        }
        return new Animation<>(0.01f, frames);
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


package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAssetManager {
    public static BitmapFont font;

    public static TextureRegion clock;
    public static TextureRegion spring, summer, autumn, winter;
    public static TextureRegion rainy, stormy, snowy, sunny;

    public static Sprite grassSprite, dirtSprite, lakeSprite;

    public static Animation<TextureRegion> boy_walking_up, boy_walking_down, boy_walking_right, boy_walking_left;

    public static void load() {
        font = new BitmapFont(Gdx.files.internal("UI/font.fnt"));

        spring = loadRegion("Sprites/time/spring.png");
        summer = loadRegion("Sprites/time/summer.png");
        autumn = loadRegion("Sprites/time/autumn.png");
        winter = loadRegion("Sprites/time/winter.png");

        clock = loadRegion("Sprites/time/clock.png");
        rainy = loadRegion("Sprites/time/rainy.png");
        stormy = loadRegion("Sprites/time/stormy.png");
        snowy = loadRegion("Sprites/time/snowy.png");
        sunny = loadRegion("Sprites/time/sunny.png");

        grassSprite = new Sprite(new Texture("Sprites/Map/grass.png"));
        dirtSprite = new Sprite(new Texture("Sprites/Map/dirt.png"));
        lakeSprite = new Sprite(new Texture("Sprites/Map/lake.png"));

        boy_walking_up = createAnimation("Sprites/Characters/b", 8, 11); boy_walking_up.setPlayMode(Animation.PlayMode.LOOP);
        boy_walking_down = createAnimation("Sprites/Characters/b", 0, 3); boy_walking_down.setPlayMode(Animation.PlayMode.LOOP);
        boy_walking_right = createAnimation("Sprites/Characters/b", 4, 7); boy_walking_right.setPlayMode(Animation.PlayMode.LOOP);
        boy_walking_left = createAnimation("Sprites/Characters/b", 12, 15); boy_walking_left.setPlayMode(Animation.PlayMode.LOOP);
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
}


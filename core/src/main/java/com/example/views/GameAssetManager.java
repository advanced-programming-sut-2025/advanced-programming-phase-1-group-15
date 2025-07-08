package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAssetManager {
    // font
    public static BitmapFont font;

    // clock and weather assets
    public static TextureRegion clock;

    public static TextureRegion spring;
    public static TextureRegion summer;
    public static TextureRegion autumn;
    public static TextureRegion winter;

    public static TextureRegion rainy;
    public static TextureRegion stormy;
    public static TextureRegion snowy;
    public static TextureRegion sunny;

    public static void load() {
        font = new BitmapFont(Gdx.files.internal("UI/font.fnt"));

        spring = new TextureRegion(new Texture(Gdx.files.internal("Sprites/time/spring.png")));
        summer = new TextureRegion(new Texture(Gdx.files.internal("Sprites/time/summer.png")));
        autumn = new TextureRegion(new Texture(Gdx.files.internal("Sprites/time/autumn.png")));
        winter = new TextureRegion(new Texture(Gdx.files.internal("Sprites/time/winter.png")));

        clock = new TextureRegion(new Texture("Sprites/time/clock.png"));
        rainy = new TextureRegion(new Texture("Sprites/time/rainy.png"));
        stormy = new TextureRegion(new Texture("Sprites/time/stormy.png"));
        snowy = new TextureRegion(new Texture("Sprites/time/snowy.png"));
        sunny = new TextureRegion(new Texture("Sprites/time/sunny.png"));
    }
}

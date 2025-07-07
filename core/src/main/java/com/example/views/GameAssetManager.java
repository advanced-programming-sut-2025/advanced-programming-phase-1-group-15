package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

        spring = new TextureRegion(new Texture(Gdx.files.internal("Sprites/spring.png")));
        summer = new TextureRegion(new Texture(Gdx.files.internal("Sprites/summer.png")));
        autumn = new TextureRegion(new Texture(Gdx.files.internal("Sprites/autumn.png")));
        winter = new TextureRegion(new Texture(Gdx.files.internal("Sprites/winter.png")));

        clock = new TextureRegion(new Texture("Sprites/clock.png"));
        rainy = new TextureRegion(new Texture("Sprites/rainy.png"));
        stormy = new TextureRegion(new Texture("Sprites/stormy.png"));
        snowy = new TextureRegion(new Texture("Sprites/snowy.png"));
        sunny = new TextureRegion(new Texture("Sprites/sunny.png"));
    }
}

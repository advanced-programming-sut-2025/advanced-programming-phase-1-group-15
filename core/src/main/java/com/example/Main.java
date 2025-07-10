package com.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.views.GameAssetManager;
import com.example.views.LoginMenuView;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private SpriteBatch mainBatch;

    @Override
    public void create() {
        mainBatch = new SpriteBatch();
        setScreen(new LoginMenuView(this));
        GameAssetManager.load();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render();
    }

    @Override
    public void dispose() {
        mainBatch.dispose();
    }

    public SpriteBatch getBatch() {
        return mainBatch;
    }
}

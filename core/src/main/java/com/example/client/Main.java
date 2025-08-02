package com.example.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.client.views.GameAssetManager;
import com.example.client.views.LoginMenuView;

import java.io.IOException;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private SpriteBatch mainBatch;

    @Override
    public void create() {
        mainBatch = new SpriteBatch();

        try {
            NetworkClient.get().connect("localhost", 54555);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameAssetManager.load();
        setScreen(new LoginMenuView(this));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render();
    }

    @Override
    public void dispose() {
        NetworkClient.get().disconnect();
        mainBatch.dispose();
        if (screen != null) {
            screen.dispose();
        }
    }

    public SpriteBatch getBatch() {
        return mainBatch;
    }
}

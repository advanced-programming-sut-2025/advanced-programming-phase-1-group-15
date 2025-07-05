package com.example.views;

import com.badlogic.gdx.Screen;
import com.example.Main;
import com.example.models.Game;

public class GameView implements Screen {
    private final Game game;
    private final Main main;

    public GameView(Game game, Main main) {
        this.game = game;
        this.main = main;
        game.build();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

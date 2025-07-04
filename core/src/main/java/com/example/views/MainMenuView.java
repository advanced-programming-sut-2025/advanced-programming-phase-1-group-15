package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.Main;
import com.example.models.App;

public class MainMenuView implements Screen {
    private final Main game;
    private Stage stage;
    private Skin skin;
    private Texture background;

    private Table mainTable;
    private TextButton gameMenuButton, profileMenuButton, logoutButton;

    public MainMenuView(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = new Texture("background.jpg");
        skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));

        createMainMenuUI();

        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        skin.dispose();
        background.dispose();
    }

    private void createMainMenuUI() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        Label gameNameLabel = new Label("Stardew Valley", skin);
        Label titleLabel = new Label("Main Menu", skin);
        Label welcomeLabel = new Label("Welcome " + App.currentUser.getNickname() + "!", skin);

        gameMenuButton = new TextButton("Game Menu", skin);
        profileMenuButton = new TextButton("Profile Menu", skin);
        logoutButton = new TextButton("Logout", skin);

        mainTable.add(gameNameLabel).row();
        mainTable.add(titleLabel).row();
        mainTable.add(welcomeLabel).padBottom(20).row();
        mainTable.add(gameMenuButton).width(300).row();
        mainTable.add(profileMenuButton).width(300).row();
        mainTable.add(logoutButton).width(200).padTop(10).row();

        gameMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameMenuView());
            }
        });

        profileMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new ProfileMenuView(game));
            }
        });

        logoutButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.currentUser = null;
                game.setScreen(new LoginMenuView(game));
            }
        });
    }
}

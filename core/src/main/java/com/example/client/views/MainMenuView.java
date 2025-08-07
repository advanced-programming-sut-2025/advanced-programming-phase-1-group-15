package com.example.client.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.client.Main;
import com.example.client.models.ClientApp;

public class MainMenuView implements Screen {
    private final Main game;
    private Stage stage;
    private Skin skin;
    private Texture background;

    private Table mainTable;
    private TextButton lobbyMenuButton, playersMenuButton, profileMenuButton, logoutButton, exitButton;

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

        Table rightCol = new Table();
        Label welcomeLabel = new Label("Welcome " + ClientApp.user.getNickname() + "!", skin);

        String path = "Sprites/Characters/" + ClientApp.user.getAvatarKey() + "_avatar.png";
        TextureRegion avatarTexture = new TextureRegion(new Texture(Gdx.files.internal(path)));
        Image avatar = new Image(avatarTexture);

        Table leftCol = new Table();
        lobbyMenuButton = new TextButton("Go to Lobbies", skin);
        playersMenuButton = new TextButton("See other Players", skin);
        profileMenuButton = new TextButton("Profile Menu", skin);
        logoutButton = new TextButton("Logout", skin);
        exitButton = new TextButton("Exit", skin);

        mainTable.add(gameNameLabel).colspan(2).row();
        mainTable.add(titleLabel).colspan(2).padBottom(50).row();

        rightCol.add(welcomeLabel).row();
        rightCol.add(new Label("Your Avatar:", skin)).padBottom(10).row();
        rightCol.add(avatar).size(150).center().row();

        leftCol.add(lobbyMenuButton).width(300).row();
        leftCol.add(playersMenuButton).width(300).row();
        leftCol.add(profileMenuButton).width(300).row();
        leftCol.add(logoutButton).width(200).padTop(10).row();
        leftCol.add(exitButton).width(200).padTop(10).row();

        mainTable.add(leftCol).top().padRight(100);
        mainTable.add(rightCol).top();

        lobbyMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LobbyMenuView(game));
            }
        });

        playersMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PlayersMenuView(game));
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
                ClientApp.user = null;
                game.setScreen(new LoginMenuView(game));
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }
}

package com.example.client.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.client.Main;
import com.example.client.NetworkClient;
import com.example.client.controllers.ClientLobbyController;
import com.example.client.controllers.ClientLoginController;
import com.example.client.models.ClientApp;
import com.example.common.Game;
import com.example.common.Lobby;
import com.example.common.Message;
import com.example.common.User;

import java.util.ArrayList;

public class PlayersMenuView implements Screen {
    private final Main main;
    private Stage stage;
    private Skin skin;
    private Texture background;

    private ArrayList<Label> usernameLabels = new ArrayList<>();
    private ArrayList<Label> onlineLabels = new ArrayList<>();
    private ArrayList<Label> lobbyLabels = new ArrayList<>();
    private Table mainTable;
    private ScrollPane playersScroll;
    private TextButton backButton;

    public PlayersMenuView(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        background = new Texture("background.jpg");
        skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));

        mainTable = new Table(skin);
        mainTable.setFillParent(true);
        mainTable.center();

        createLabels();
        createPlayersPanel();

        stage.addActor(mainTable);

        NetworkClient.get().addListener(this::onNetMessage);
    }

    private void createPlayersPanel() {
        mainTable.clearChildren();

        Label titleLabel = new Label("All Players:", skin);
        backButton = new TextButton("Back", skin);

        mainTable.add(titleLabel).padTop(50).row();

        Table playersTable = new Table(skin);
        playersTable.add(new Label("usernames", skin)).width(200).pad(20);
        playersTable.add(new Label("status", skin)).width(200).pad(20);
        playersTable.add(new Label("lobbies", skin)).width(200).pad(20).row();
        for(int i = 0; i < ClientApp.usernames.size(); i++) {
            usernameLabels.get(i).setColor(Color.BLACK);
            lobbyLabels.get(i).setColor(Color.FIREBRICK);
            playersTable.add(usernameLabels.get(i)).width(200).pad(10);
            playersTable.add(onlineLabels.get(i)).width(200).pad(10);
            playersTable.add(lobbyLabels.get(i)).width(200).pad(10).row();
        }

        playersScroll = new ScrollPane(playersTable, skin);
        playersScroll.setFadeScrollBars(false);
        mainTable.add(playersTable).expand().fill().padTop(10).row();
        mainTable.add(backButton).padBottom(50).row();

        backButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                main.setScreen(new MainMenuView(main));
            }
        });
    }

    private void createLabels() {
        ClientLobbyController.updateUsernames();
        ClientLobbyController.updateLobbies();
        for(int i = 0; i < ClientApp.usernames.size(); i++) {
            String username = ClientApp.usernames.get(i);
            if(username.equals(ClientApp.user.getUsername())) {
                usernameLabels.add(new Label("-> " + username, skin));
            }
            else {
                usernameLabels.add(new Label("* " + username, skin));
            }

            boolean isOnline = ClientLobbyController.isOnline(username);
            Label onlineLabel;
            if(isOnline) {
                onlineLabel = new Label("Online", skin);
                onlineLabel.setColor(Color.GREEN);
            }
            else {
                onlineLabel = new Label("Offline", skin);
                onlineLabel.setColor(Color.RED);
            }
            onlineLabels.add(onlineLabel);

            Label lobbyLabel = new Label("lobby: -", skin);
            for(Lobby lobby : ClientApp.lobbies) {
                if(lobby.checkIfUserIsInLobby(username)) {
                    lobbyLabel.setText("lobby: " + lobby.getName());
                    break;
                }
            }
            lobbyLabels.add(lobbyLabel);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        main.getBatch().begin();
        main.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        main.getBatch().end();

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
        NetworkClient.get().removeListener(this::onNetMessage);
        dispose();
    }

    @Override public void dispose(){
        stage.dispose();
        skin.dispose();
        background.dispose();
    }

    private void onNetMessage(Message msg) {
        String action = msg.getFromBody("action");

        switch (action) {
            case "new_signup", "new_login", "new_logout", "new_create_lobby", "new_join_lobby", "new_leave_lobby" -> {
                createLabels();
                createPlayersPanel();
            }
        }
    }
}

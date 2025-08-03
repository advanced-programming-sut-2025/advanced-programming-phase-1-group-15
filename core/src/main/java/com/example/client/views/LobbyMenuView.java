package com.example.client.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.client.Main;
import com.example.client.NetworkClient;
import com.example.client.controllers.ClientLobbyController;
import com.example.client.models.ClientApp;
import com.example.common.Lobby;
import com.example.common.Message;

import java.util.ArrayList;

public class LobbyMenuView implements Screen {
    private final Main game;
    private Stage stage;
    private Skin skin;
    private Texture background;

    private Table mainTable;
    private Table lobbiesPanel;
    private Table createNewLobbyPanel;
    private TextField searchField;
    private TextButton searchButton, refreshButton, createButton;
    private ScrollPane lobbyScroll;
    private Table lobbyListTable;

    public LobbyMenuView(Main game) {
        this.game = game;
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

        createLobbiesPanel();
        createCreateNewLobbyPanel();

        fetchAllLobbies();
        mainTable.add(lobbiesPanel).expand().fill();
        stage.addActor(mainTable);

        NetworkClient.get().addListener(this::onNetMessage);
    }

    private void createLobbiesPanel() {
        lobbiesPanel = new Table();
        lobbiesPanel.center();

        Label titleLabel = new Label("Lobbies:", skin);
        searchField  = new TextField("", skin);
        searchField.setMessageText("Lobby ID…");
        searchButton = new TextButton("Search", skin);
        refreshButton= new TextButton("Refresh", skin);
        createButton = new TextButton("Create a Lobby", skin);

        Table topBar = new Table(skin);
        topBar.add(searchField).width(200).padRight(5);
        topBar.add(searchButton).padRight(50);
        topBar.add(refreshButton).padRight(50);
        topBar.add(createButton);
        lobbiesPanel.add(titleLabel).padBottom(10).row();
        lobbiesPanel.add(topBar).expandX().fillX().row();

        lobbyListTable = new Table(skin);
        lobbyListTable.top().defaults().pad(5);

        lobbyScroll = new ScrollPane(lobbyListTable, skin);
        lobbyScroll.setFadeScrollBars(false);
        lobbiesPanel.add(lobbyScroll).expand().fill().padTop(10).row();

        refreshButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                fetchAllLobbies();
            }
        });
        searchButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                fetchLobbiesById(searchField.getText().trim());
            }
        });
        createButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                showCreateNewLobbyUI();
            }
        });
    }

    private void createCreateNewLobbyPanel() {
        createNewLobbyPanel = new Table(skin);
        createNewLobbyPanel.center();

        Label titleLabel = new Label("Create a new Lobby:", skin);

        Label nameLabel = new Label("Name: ", skin);
        TextField nameField = new TextField("", skin);
        nameField.setMessageText("name");

        Label typeLabel = new Label("Type: ", skin);
        SelectBox<String> typeBox = new SelectBox<>(skin);
        typeBox.setItems("Public", "Private");

        Label passwordLabel = new Label("Password: ", skin);
        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("password"); passwordField.setDisabled(true);

        Label visibilityLabel = new Label("Visibility: ", skin);
        SelectBox<String> visibilityBox = new SelectBox<>(skin);
        visibilityBox.setItems("YES", "NO");

        TextButton createLobbyButton = new TextButton("Create LOBBY", skin);

        createNewLobbyPanel.add(titleLabel).padBottom(10).row();
        createNewLobbyPanel.add(nameLabel).pad(10);
        createNewLobbyPanel.add(nameField).pad(10).row();
        createNewLobbyPanel.add(typeLabel).pad(10);
        createNewLobbyPanel.add(typeBox).pad(10);
        createNewLobbyPanel.add(passwordLabel).pad(10);
        createNewLobbyPanel.add(passwordField).pad(10).row();
        createNewLobbyPanel.add(visibilityLabel).pad(10);
        createNewLobbyPanel.add(visibilityBox).pad(10).row();
        createNewLobbyPanel.add(createLobbyButton).padTop(10).row();

        typeBox.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                if(typeBox.getSelected().equals("Public")) {
                    passwordField.setText("");
                    passwordField.setDisabled(true);
                }
                else {
                    passwordField.setDisabled(false);
                }
            }
        });

        createLobbyButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                String name = nameField.getText();
                boolean isPublic = typeBox.getSelected().equals("Public");
                String password = null;
                if (isPublic) {
                    password = passwordField.getText();
                }
                boolean isVisible = visibilityBox.getSelected().equals("YES");

                ClientLobbyController.sendCreateLobbyMessage(ClientApp.user.getUsername(), name, isPublic, password, isVisible);
            }
        });
    }

    private void showLobbiesUI() {
        mainTable.clearChildren();
        createLobbiesPanel();
        mainTable.add(lobbiesPanel).expand().fill();
    }

    private void showCreateNewLobbyUI() {
        mainTable.clearChildren();
        createCreateNewLobbyPanel();
        mainTable.add(createNewLobbyPanel).expand().fill();
    }

    private void fetchAllLobbies() {
        ClientLobbyController.updateLobbies();
        updateLobbyList(ClientApp.lobbies);
    }

    private void fetchLobbiesById(String id) {
        // send a filter request…
        // NetworkClient.get().sendFetchLobbiesById(id,…)
    }

    private void updateLobbyList(ArrayList<Lobby> lobbies) {
        lobbyListTable.clear();
        for (Lobby l : lobbies) {
            lobbyListTable.add(createLobbyRow(l)).expandX().fillX().row();
        }
    }

    private Table createLobbyRow(Lobby lobby) {
        Table row = new Table(skin);
        Label name = new Label(lobby.getName(), skin);
        Label id   = new Label("ID: " + lobby.getId(), skin);
        Label count= new Label("Players: " + lobby.getUsersCount(), skin);
        TextButton join = new TextButton("Join", skin);

        join.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                attemptJoinLobby(lobby.getId());
            }
        });

        row.add(name).left().width(200);
        row.add(id).left().width(120).padLeft(10);
        row.add(count).left().width(120).padLeft(10);
        row.add(join).right().width(100);

        return row;
    }

    private void attemptJoinLobby(String lobbyId) {
        // NetworkClient.get().sendJoinLobby(lobbyId,…)
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

        if(action.equals("create_lobby")) {
            fetchAllLobbies();
            showLobbiesUI();
        }
    }
}

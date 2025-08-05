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

public class LobbyMenuView implements Screen {
    private final Main main;
    private Stage stage;
    private Skin skin;
    private Texture background;

    private Table mainTable;
    private Table lobbiesPanel;
    private Table createNewLobbyPanel;
    private Table lobbyPanel;
    private TextField searchField;
    private TextButton searchButton, refreshButton, createButton, backButton;
    private ScrollPane lobbyScroll;
    private Table lobbyListTable;
    private Label lobbiesMessageLabel, createNewLobbyMessageLabel, lobbyMessageLabel;

    public LobbyMenuView(Main main) {
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

        createLobbiesPanel();
        createCreateNewLobbyPanel();

        if(ClientApp.getUserLobby() != null) {
            createLobbyPanel(ClientApp.getUserLobby());
            showLobbyUI(ClientApp.getUserLobby());
        }
        else {
            showLobbiesUI();
        }
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
        backButton = new TextButton("Back", skin);

        lobbiesMessageLabel = new Label("", skin); lobbiesMessageLabel.setColor(Color.RED);

        Table topBar = new Table(skin);
        topBar.add(searchField).width(200).padRight(5);
        topBar.add(searchButton).padRight(50);
        topBar.add(refreshButton).padRight(50);
        topBar.add(createButton);
        lobbiesPanel.add(titleLabel).padTop(50).row();
        lobbiesPanel.add(topBar).expandX().fillX().row();
        lobbiesPanel.add(lobbiesMessageLabel).padTop(10).row();

        lobbyListTable = new Table(skin);
        lobbyListTable.defaults().pad(5);
        ClientLobbyController.updateLobbies();
        updateLobbyList(ClientApp.lobbies);

        lobbyScroll = new ScrollPane(lobbyListTable, skin);
        lobbyScroll.setFadeScrollBars(false);
        lobbiesPanel.add(lobbyScroll).expand().fill().padTop(10).row();
        lobbiesPanel.add(backButton).padTop(10).row();

        refreshButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                ClientLobbyController.updateLobbies();
                updateLobbyList(ClientApp.lobbies);
            }
        });
        searchButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                ClientLobbyController.updateLobbies();
                searchLobbyList(ClientApp.lobbies, searchField.getText());
            }
        });
        createButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                showCreateNewLobbyUI();
            }
        });
        backButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                main.setScreen(new MainMenuView(main));
            }
        });
    }

    private void createCreateNewLobbyPanel() {
        createNewLobbyPanel = new Table(skin);
        createNewLobbyPanel.center();

        Label titleLabel = new Label("Create a new Lobby:", skin);

        Table nameTable = new Table(skin);
        Label nameLabel = new Label("Name: ", skin);
        TextField nameField = new TextField("", skin);
        nameField.setMessageText("name");

        Table typeTable = new Table(skin);
        Label typeLabel = new Label("Type: ", skin);
        SelectBox<String> typeBox = new SelectBox<>(skin);
        typeBox.setItems("Public", "Private");

        Label passwordLabel = new Label("Password: ", skin);
        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("password"); passwordField.setDisabled(true);

        Table visibilityTable = new Table(skin);
        Label visibilityLabel = new Label("Visibility: ", skin);
        SelectBox<String> visibilityBox = new SelectBox<>(skin);
        visibilityBox.setItems("YES", "NO");

        TextButton createLobbyButton = new TextButton("Create LOBBY", skin);
        backButton = new TextButton("Back", skin);

        createNewLobbyMessageLabel = new Label("", skin); createNewLobbyMessageLabel.setColor(Color.RED);

        createNewLobbyPanel.add(titleLabel).padBottom(10).row();
        nameTable.add(nameLabel).pad(10);
        nameTable.add(nameField).pad(10);
        createNewLobbyPanel.add(nameTable).pad(10).row();
        typeTable.add(typeLabel).pad(10);
        typeTable.add(typeBox).pad(10);
        typeTable.add(passwordLabel).pad(10);
        typeTable.add(passwordField).pad(10);
        createNewLobbyPanel.add(typeTable).pad(10).row();
        visibilityTable.add(visibilityLabel).pad(10);
        visibilityTable.add(visibilityBox).pad(10);
        createNewLobbyPanel.add(visibilityTable).pad(10).row();
        createNewLobbyPanel.add(createLobbyButton).padTop(10).row();
        createNewLobbyPanel.add(backButton).padTop(10).row();
        createNewLobbyPanel.add(createNewLobbyMessageLabel).padTop(10).row();

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
                if(name.isEmpty()) {
                    createNewLobbyMessageLabel.setText("name field cannot be empty.");
                    return;
                }

                boolean isPublic = typeBox.getSelected().equals("Public");
                String password = null;
                if (!isPublic) {
                    password = passwordField.getText();
                    if(password.isEmpty()) {
                        createNewLobbyMessageLabel.setText("password field cannot be empty.");
                        return;
                    }
                }

                boolean isVisible = visibilityBox.getSelected().equals("YES");

                ClientLobbyController.sendCreateLobbyMessage(ClientApp.user.getUsername(), name, isPublic, password, isVisible);
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                showLobbiesUI();
            }
        });
    }

    private void createLobbyPanel(Lobby lobby) {
        lobbyPanel = new Table(skin);
        lobbyPanel.center();

        Label titleLabel = new Label(lobby.getName(), skin);
        Label idLabel = new Label("ID: " + lobby.getId(), skin);
        Label typeLabel = new Label("Is Public?: " + lobby.isPublic(), skin);
        Label visibilityLabel = new Label("Visibility: " + lobby.isVisible(), skin);
        Label countLabel = new Label("Players Count: " + lobby.getUsersCount(), skin);
        Label adminLabel = new Label("ADMIN: " + lobby.getAdmin().getUsername(), skin);
        Label usersLabel = new Label("users: " + lobby.getUsersString(), skin);
        TextButton startGameButton = new TextButton("Start Game", skin);
        TextButton leaveLobbyButton = new TextButton("Leave Lobby", skin);
        backButton = new TextButton("Back", skin);
        lobbyMessageLabel = new Label("", skin); lobbyMessageLabel.setColor(Color.RED);

        startGameButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                if(!ClientApp.user.getUsername().equals(lobby.getAdmin().getUsername())) {
                    lobbyMessageLabel.setText("You have to be Admin to start the game.");
                }
                else {
                    if(lobby.getUsersCount() == 0) {
                        lobbyMessageLabel.setText("There has to be at least two users in the lobby to start the game.");
                    }
                    else {
                        ClientLobbyController.sendStartGameMessage(lobby.getId(), ClientApp.user.getUsername());
                    }
                }
            }
        });

       leaveLobbyButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                ClientLobbyController.sendLeaveLobbyMessage(lobby.getId(), ClientApp.user.getUsername());
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                main.setScreen(new MainMenuView(main));
            }
        });

        lobbyPanel.add(titleLabel).pad(10).colspan(2).row();
        lobbyPanel.add(idLabel).pad(10).colspan(2).row();
        lobbyPanel.add(typeLabel).pad(10).colspan(2).row();
        lobbyPanel.add(visibilityLabel).pad(10).colspan(2).row();
        lobbyPanel.add(countLabel).pad(10).colspan(2).row();
        lobbyPanel.add(adminLabel).pad(10).colspan(2).row();
        lobbyPanel.add(usersLabel).pad(10).colspan(2).row();
        lobbyPanel.add(new Label("Choose your Maps:", skin)).colspan(2).pad(10).row();
        for (int i = 0; i < lobby.getUsersCount(); i++) {
            User user = lobby.getUsers().get(i);
            lobbyPanel.add(new Label(user.getUsername(), skin)).width(200).pad(5);

            SelectBox<Integer> mapBox = new SelectBox<>(skin);
            mapBox.setItems(1, 2, 3, 4);
            mapBox.setSelected(lobby.getMapNumber(user.getUsername()));
            if(ClientApp.user.getUsername().equals(user.getUsername())) {
                mapBox.addListener(new ChangeListener() {
                    @Override public void changed(ChangeEvent event, Actor actor) {
                        int mapNumber = mapBox.getSelected();
                        if(lobby.getMapNumbers().contains(mapNumber)) {
                            mapBox.setSelected(lobby.getMapNumber(user.getUsername()));
                            lobbyMessageLabel.setText("You can't select a map which has already been chosen!");
                        }
                        else {
                            ClientLobbyController.sendSetMapNumberMessage(lobby.getId(), ClientApp.user.getUsername(), mapNumber);
                        }
                    }
                });
            }
            else {
                mapBox.setDisabled(true);
            }

            lobbyPanel.add(mapBox).width(100).pad(5).row();
        }
        Table buttonsTable = new Table(skin);
        buttonsTable.add(startGameButton).pad(10); buttonsTable.add(leaveLobbyButton).pad(10);
        lobbyPanel.add(buttonsTable).pad(10).colspan(2).row();
        lobbyPanel.add(backButton).padTop(20).colspan(2).row();
        lobbyPanel.add(lobbyMessageLabel).padTop(10).colspan(2).row();
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

    private void showLobbyUI(Lobby lobby) {
        mainTable.clearChildren();
        createLobbyPanel(lobby);
        mainTable.add(lobbyPanel).expand().fill();
    }

    private void updateLobbyList(ArrayList<Lobby> lobbies) {
        lobbyListTable.clear();
        for (Lobby lobby : lobbies) {
            if(lobby.isVisible()) {
                lobbyListTable.add(createLobbyRow(lobby)).expandX().fillX().row();
            }
        }
    }

    private void searchLobbyList(ArrayList<Lobby> lobbies, String id) {
        if(id.isEmpty()) {
            updateLobbyList(lobbies);
            return;
        }

        lobbyListTable.clear();
        lobbyListTable.add(new Label("Oops! there is no lobby with such id!", skin));

        for (Lobby lobby : lobbies) {
            if(lobby.getId().equals(id)) {
                lobbyListTable.clear();
                lobbyListTable.add(createLobbyRow(lobby)).expandX().fillX().row();
            }
        }
    }

    private Table createLobbyRow(Lobby lobby) {
        Table row = new Table(skin);
        row.setBackground(skin.newDrawable("white", Color.BROWN));
        row.defaults().width(1000).height(100);

        Label name = new Label("name: " + lobby.getName(), skin);
        Label id   = new Label("ID: " + lobby.getId(), skin);
        Label count= new Label("Players Count: " + lobby.getUsersCount(), skin);
        Label usersLabel = new Label("users: " + lobby.getUsersString(), skin);
        TextField passwordField = new TextField("", skin); passwordField.setMessageText("password");
        TextButton joinButton = new TextButton("Join", skin);

        joinButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                ClientLobbyController.sendJoinLobbyMessage(lobby.getId(), ClientApp.user.getUsername(), passwordField.getText());
            }
        });

        Table lobbyInfo = new Table(skin);
        lobbyInfo.add(name).left();
        lobbyInfo.add(id).left().width(200).padLeft(20);
        lobbyInfo.add(count).left().width(200).padLeft(20).row();
        lobbyInfo.add(usersLabel).left().row();
        row.add(lobbyInfo).padRight(20);

        if(lobby.getUsersCount() != 4) {
            if(!lobby.isPublic()) {
                row.add(passwordField).left().width(150).height(60).padLeft(20);
            }
            row.add(joinButton).right().width(100).height(60).padLeft(20);
        }

        return row;
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
            case "create_lobby" -> {
                ClientLobbyController.updateLobbies();
                ClientLoginController.updateUser();
                showLobbyUI(ClientApp.getUserLobby());
            }
            case "join_lobby" -> {
                boolean success = msg.getFromBody("success");
                String text = msg.getFromBody("message");

                if (success) {
                    String username = msg.getFromBody("username");
                    if (username.equals(ClientApp.user.getUsername())) {
                        ClientLobbyController.updateLobbies();
                        ClientLoginController.updateUser();
                        showLobbyUI(ClientApp.getUserLobby());
                    } else {
                        ClientLobbyController.updateLobbies();
                        showLobbyUI(ClientApp.getUserLobby());
                    }
                } else {
                    lobbiesMessageLabel.setText(text);
                }
            }
            case "leave_lobby" -> {
                String username = msg.getFromBody("username");

                if (username.equals(ClientApp.user.getUsername())) {
                    ClientLobbyController.updateLobbies();
                    ClientLoginController.updateUser();
                    showLobbiesUI();
                } else {
                    ClientLobbyController.updateLobbies();
                    showLobbyUI(ClientApp.getUserLobby());
                }
            }
            case "set_map_number" -> {
                ClientLobbyController.updateLobbies();
                showLobbyUI(ClientApp.getUserLobby());
            }
            case "start_game" -> {
                ClientLobbyController.updateLobbies();

                Game game = new Game(ClientApp.getUserLobby(), ClientApp.user);
                ClientApp.setupGame(game);
                main.setScreen(new GameView(game, main));
            }
        }
    }
}

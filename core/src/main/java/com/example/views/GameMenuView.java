package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.Main;
import com.example.models.App;
import com.example.models.Game;
import com.example.models.Player;
import com.example.models.User;

import java.util.ArrayList;

public class GameMenuView implements Screen {
    private final Main game;
    private Stage stage;
    private Skin skin;
    private Texture background;

    private Table mainTable;
    private Table gameMenuPanel, newGamePanel, chooseMapPanel;

    public GameMenuView(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = new Texture("background.jpg");
        skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        createGameMenuPanel();
        mainTable.add(gameMenuPanel).expandX().fill();

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

    private void createGameMenuPanel() {
        gameMenuPanel = new Table();
        gameMenuPanel.center();

        Label gameNameLabel = new Label("Stardew Valley", skin);
        Label titleLabel = new Label("Game Menu", skin);

        TextButton startANewGameButton = new TextButton("Start a New Game", skin);
        TextButton loadGameButton = new TextButton("Load Previous Game", skin);
        TextButton showCurrentGameButton = new TextButton("Show Current Game", skin);
        TextButton backButton = new TextButton("Back", skin);
        Label messageLabel = new Label("", skin); messageLabel.setColor(Color.RED);

        gameMenuPanel.add(gameNameLabel).row();
        gameMenuPanel.add(titleLabel).padBottom(20).row();
        gameMenuPanel.add(startANewGameButton).width(300).row();
        gameMenuPanel.add(loadGameButton).width(300).row();
        gameMenuPanel.add(showCurrentGameButton).width(300).row();
        gameMenuPanel.add(backButton).width(150).padTop(10).row();
        gameMenuPanel.add(messageLabel).padTop(10).row();

        startANewGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(App.currentUser.getCurrentGame() == null) {
                    switchToNewGameUI();
                }
                else {
                    messageLabel.setText("you are already participating in a game!");
                }
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuView(game));
            }
        });
    }

    private void createNewGamePanel() {
        newGamePanel = new Table();
        newGamePanel.center();

        ArrayList<User> users = new ArrayList<>(); users.add(App.currentUser);
        ArrayList<Player> players = new ArrayList<>(); players.add(new Player(App.currentUser));

        Label titleLabel = new Label("Create a New Game", skin);
        Label descriptionLabel = new Label("Add up to 4 usernames:", skin);
        TextField usernameField = new TextField("", skin);
        Label playersLabel = new Label("", skin); playersLabel.setColor(Color.BLACK); updatePlayersLabel(players, playersLabel);
        Label messageLabel = new Label("", skin); messageLabel.setColor(Color.RED);

        TextButton addButton = new TextButton("+ ADD", skin);
        TextButton startButton = new TextButton("Start Game", skin);
        TextButton backButton = new TextButton("Back", skin);

        newGamePanel.add(titleLabel).colspan(3).row();
        newGamePanel.add(descriptionLabel).padBottom(20).colspan(3).row();
        newGamePanel.add(new Label("username: ", skin)); newGamePanel.add(usernameField).width(400); newGamePanel.add(addButton).row();
        newGamePanel.add(playersLabel).padTop(10).colspan(3).row();
        newGamePanel.add(startButton).width(200).colspan(3).row();
        newGamePanel.add(backButton).width(150).padTop(10).colspan(3).row();
        newGamePanel.add(messageLabel).padTop(10).colspan(3).row();

        addButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = usernameField.getText();
                User user = App.getUserByUsername(username);

                if(users.size() < 4) {
                    if(user != null) {
                        if(user.getCurrentGame() != null) {
                            messageLabel.setText(username + " is already participating in a game!");
                        }
                        else if(users.contains(user)) {
                            messageLabel.setText("you already chose this user!");
                        }
                        else {
                            users.add(user);
                            players.add(new Player(user));
                            updatePlayersLabel(players, playersLabel);
                        }
                    }
                    else {
                        messageLabel.setText("username not found!");
                    }
                }
                else {
                    addButton.setDisabled(true);
                    messageLabel.setText("you can't add more players!");
                }
            }
        });

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchToChooseMapUI(players);
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchToGameMenuUI();
            }
        });
    }

    private void createChooseMapPanel(ArrayList<Player> players) {
        chooseMapPanel = new Table();
        chooseMapPanel.center();

        Label titleLabel = new Label("Stardew Valley", skin);
        Label descriptionLabel = new Label("Choose your Map:", skin);
        ArrayList<Label> playerLabels = new ArrayList<>();
        ArrayList<SelectBox<Integer>> selectBoxes = new ArrayList<>();

        for(int i = 1; i <= players.size(); i++) {
            Label playerLabel = new Label("Player " + i +": " + players.get(i - 1).getUsername(), skin);
            playerLabel.setColor(Color.BLACK);
            SelectBox<Integer> selectBox = new SelectBox<>(skin);
            selectBox.setItems(1, 2, 3, 4);

            playerLabels.add(playerLabel);
            selectBoxes.add(selectBox);
        }

        Label messageLabel = new Label("", skin); messageLabel.setColor(Color.RED);
        TextButton startButton = new TextButton("Start", skin);

        chooseMapPanel.add(titleLabel).colspan(2).row();
        chooseMapPanel.add(descriptionLabel).padBottom(20).colspan(2).row();
        for(int i = 0; i < players.size(); i++) {
            chooseMapPanel.add(playerLabels.get(i)).left(); chooseMapPanel.add(selectBoxes.get(i)).width(75).height(50).row();
        }
        chooseMapPanel.add(startButton).width(150).padTop(10).colspan(2).row();
        chooseMapPanel.add(messageLabel).padTop(10).colspan(2).row();

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for(int i = 0; i < selectBoxes.size() - 1; i++) {
                    for(int j = i + 1; j < selectBoxes.size(); j++) {
                        if(selectBoxes.get(i).getSelected().equals(selectBoxes.get(j).getSelected())) {
                            messageLabel.setText("Each player has to choose a unique map number.");
                            return;
                        }
                    }
                }

                for(int i = 0; i < players.size(); i++) {
                    players.get(i).setMapNumber(selectBoxes.get(i).getSelected());
                }

                Game newGame = new Game(players);
                App.currentGame = newGame; App.recentGames.add(newGame);
                game.setScreen(new GameView(newGame, game));
            }
        });
    }

    private void switchToGameMenuUI() {
        mainTable.clearChildren();
        mainTable.add(gameMenuPanel).expand().fill();
    }

    private void switchToNewGameUI() {
        mainTable.clearChildren();
        createNewGamePanel();
        mainTable.add(newGamePanel).expand().fill();
    }

    private void switchToChooseMapUI(ArrayList<Player> players) {
        mainTable.clearChildren();
        createChooseMapPanel(players);
        mainTable.add(chooseMapPanel).expand().fill();
    }

    public void updatePlayersLabel(ArrayList<Player> players, Label playersLabel) {
        StringBuilder playersString = new StringBuilder();
        for(int i = 1; i <= 4; i++) {
            if(i <= players.size()) {
                playersString.append("player").append(i).append(": ").append(players.get(i - 1).getUsername()).append(" ");
            }
            else {
                playersString.append("player").append(i).append(": - ");
            }
        }

        playersLabel.setText(playersString.toString());
    }
}

package com.example.client.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.client.Main;
import com.example.client.NetworkClient;
import com.example.client.controllers.ClientLoginController;
import com.example.client.controllers.ClientProfileController;
import com.example.common.Message;
import com.example.server.controllers.ServerProfileController;
import com.example.client.models.ClientApp;
import com.example.common.Result;
import com.example.common.User;

public class ProfileMenuView implements Screen {
    private final Main game;
    private Stage stage;
    private Skin skin;
    private Texture background;

    private Table mainTable;
    private Image avatar;

    private Label usernameLabel;
    private Label messageLabel;

    public ProfileMenuView(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = new Texture("background.jpg");
        skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));

        createProfileMenuUI();

        stage.addActor(mainTable);

        NetworkClient.get().addListener(this::onNetMessage);
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

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        background.dispose();
    }

    private void createProfileMenuUI() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        User user = ClientApp.user;

        Label gameNameLabel = new Label("Stardew Valley", skin);
        Label titleLabel = new Label("Profile Menu", skin);
        usernameLabel = new Label("Username: " + user.getUsername(), skin);
        Label maxEarnedGoldLabel = new Label("Max Earned Gold: " + user.getMaxEarnedGold(), skin);
        Label gameCountLabel = new Label("Game Count: " + user.getGameCount(), skin);
        Label genderLabel = new Label("Gender: " + user.getGender(), skin);

        TextField usernameField = new TextField(user.getUsername(), skin);
        TextField passwordField = new TextField(user.getPassword(), skin);
        TextField nicknameField = new TextField(user.getNickname(), skin);
        TextField emailField = new TextField(user.getEmail(), skin);
        SelectBox<String> avatarBox = new SelectBox<>(skin);
        avatarBox.setItems("Boy Default", "Girl Default", "Abigail", "Marnie", "Sam", "Sebastian");
        String path = "Sprites/Characters/" + ClientApp.user.getAvatarKey() + "_avatar.png";
        TextureRegion avatarTexture = new TextureRegion(new Texture(Gdx.files.internal(path)));
        avatar = new Image(avatarTexture);

        TextButton changeUsernameButton = new TextButton("Change", skin);
        TextButton changePasswordButton = new TextButton("Change", skin);
        TextButton changeNicknameButton = new TextButton("Change", skin);
        TextButton changeEmailButton = new TextButton("Change", skin);
        TextButton backButton = new TextButton("Back", skin);

        messageLabel = new Label("", skin); messageLabel.setColor(Color.RED);

        mainTable.add(gameNameLabel).colspan(3).row();
        mainTable.add(titleLabel).colspan(3).row();
        mainTable.add(usernameLabel).colspan(3).row();
        mainTable.add(maxEarnedGoldLabel).colspan(3).row();
        mainTable.add(gameCountLabel).padBottom(20).colspan(3).row();
        mainTable.add(new Label("Username: ", skin)); mainTable.add(usernameField).width(400); mainTable.add(changeUsernameButton).row();
        mainTable.add(new Label("Password: ", skin)); mainTable.add(passwordField).width(400); mainTable.add(changePasswordButton).row();
        mainTable.add(new Label("Nickname: ", skin)); mainTable.add(nicknameField).width(400); mainTable.add(changeNicknameButton).row();
        mainTable.add(new Label("Email: ", skin)); mainTable.add(emailField).width(400); mainTable.add(changeEmailButton).row();
        mainTable.add(genderLabel).padBottom(20).colspan(3).row();

        Table avatarTable = new Table();
        avatarTable.add(new Label("Avatar: ", skin)).left();
        avatarTable.add(avatarBox).width(200).right();
        mainTable.add(avatarTable).colspan(3).row();
        mainTable.add(avatar).size(150).padTop(10).colspan(3).row();

        mainTable.add(backButton).width(200).padTop(10).colspan(3).row();
        mainTable.add(messageLabel).padTop(10).colspan(3).row();

        changeUsernameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String newUsername = usernameField.getText();
                ClientProfileController.sendChangeUsernameMessage(user.getUsername(), newUsername);
            }
        });

        changePasswordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String newPassword = passwordField.getText();
                ClientProfileController.sendChangePasswordMessage(user.getUsername(), newPassword);
            }
        });

        changeNicknameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String newNickname = nicknameField.getText();
                ClientProfileController.sendChangeNicknameMessage(user.getUsername(), newNickname);
            }
        });

        changeEmailButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String newEmail = emailField.getText();
                ClientProfileController.sendChangeEmailMessage(user.getUsername(), newEmail);
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuView(game));
            }
        });

        avatarBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selection = avatarBox.getSelected().toLowerCase().replaceAll(" ", "_");
                String path = "Sprites/Characters/" + selection + "_avatar.png";
                TextureRegion newAvatar = new TextureRegion(new Texture(Gdx.files.internal(path)));

                ClientProfileController.sendChangeAvatarKeyMessage(user.getUsername(), selection);
                avatar.setDrawable(new Image(newAvatar).getDrawable());
            }
        });
    }

    private void onNetMessage(Message msg) {
        String action = msg.getFromBody("action");

        switch (action) {
            case "change_username" -> {
                boolean success = msg.getFromBody("success");
                String text = msg.getFromBody("message");

                messageLabel.setText(text);
                if (success) {
                    ClientLoginController.updateUser();
                    usernameLabel.setText("Username: " + ClientApp.user.getUsername());
                }
            }
            case "change_password", "change_nickname", "change_email", "change_avatar_key" -> {
                boolean success = msg.getFromBody("success");
                String text = msg.getFromBody("message");

                messageLabel.setText(text);
                if (success) {
                    ClientLoginController.updateUser();
                }
            }
        }
    }
}

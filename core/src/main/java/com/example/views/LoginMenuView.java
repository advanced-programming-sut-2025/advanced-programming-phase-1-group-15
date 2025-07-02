package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.Main;
import com.example.controllers.LoginMenuController;
import com.example.models.App;
import com.example.models.RandomGenerator;
import com.example.models.Result;
import com.example.models.enums.Gender;

public class LoginMenuView implements Screen {
    private final Main game;
    private Stage stage;
    private Skin skin;
    private Texture background;

    // UI components
    private Table mainTable;
    private Table registerTable;
    private TextField usernameField, passwordField, confirmPasswordField,
        nicknameField, emailField;
    private SelectBox<Gender> genderBox;
    private Label messageLabel;
    private TextButton loginButton, registerButton, forgotPasswordButton;
    private boolean registerMode = false;

    public LoginMenuView(Main game) {
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

        createLoginUI();
        createRegisterUI();
        createForgotPasswordUI();

        stage.addActor(mainTable);
    }

    private void createLoginUI() {
        Table loginTable = new Table();

        Label titleLabel = new Label("Welcome to StardewValley!", skin);
        usernameField = new TextField("Username", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Password");

        loginButton = new TextButton("Login", skin);
        forgotPasswordButton = new TextButton("Forgot Password?", skin);
        TextButton switchToRegisterButton = new TextButton("Create Account", skin);
        messageLabel = new Label("", skin);

        loginTable.add(titleLabel).padBottom(20).row();
        loginTable.add(usernameField).width(400).padBottom(15).row();
        loginTable.add(passwordField).width(400).padBottom(15).row();
        loginTable.add(loginButton).width(200).padBottom(10).row();
        loginTable.add(forgotPasswordButton).width(300).padBottom(5).row();
        loginTable.add(switchToRegisterButton).width(200).padBottom(15).row();
        loginTable.add(messageLabel).width(400).row();

        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                handleLogin();
            }
        });

        forgotPasswordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showForgotPasswordUI();
            }
        });

        switchToRegisterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showRegisterUI();
            }
        });

        mainTable.add(loginTable);
    }

    private void createRegisterUI() {
        registerTable = new Table();
        registerTable.setVisible(true);

        Label titleLabel = new Label("Create Account", skin);
        usernameField = new TextField("Username", skin);

        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Password");
        confirmPasswordField = new TextField("", skin);
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');
        confirmPasswordField.setMessageText("Confirm Password");

        nicknameField = new TextField("Nickname", skin);
        emailField = new TextField("Email", skin);
        genderBox = new SelectBox<>(skin);
        genderBox.setItems(Gender.BOY, Gender.GIRL);

        registerButton = new TextButton("Register", skin);
        TextButton generatePasswordButton = new TextButton("Generate Password", skin);
        TextButton switchToLoginButton = new TextButton("Back to Login", skin);

        registerTable.add(titleLabel).padBottom(20).row();
        registerTable.add(usernameField).width(400).padBottom(10).row();
        registerTable.add(passwordField).width(400).padBottom(10).row();
        registerTable.add(confirmPasswordField).width(400).padBottom(10).row();
        registerTable.add(nicknameField).width(400).padBottom(10).row();
        registerTable.add(emailField).width(400).padBottom(10).row();
        registerTable.add(genderBox).width(200).padBottom(15).row();

        Table passwordButtons = new Table();
        passwordButtons.add(registerButton).width(180).padRight(10);
        passwordButtons.add(generatePasswordButton).width(180);
        registerTable.add(passwordButtons).padBottom(10).row();
        registerTable.add(switchToLoginButton).width(200).padBottom(10).row();
        registerTable.add(messageLabel).width(400).row();

        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                handleRegistration();
            }
        });

        generatePasswordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String password = LoginMenuController.randomPasswordGenerator();
                passwordField.setText(password);
                confirmPasswordField.setText(password);
                messageLabel.setText("Generated password: " + password);
            }
        });

        switchToLoginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showLoginUI();
            }
        });
    }

    private void createForgotPasswordUI() {
        // Similar structure to createRegisterUI
        // Would contain username field, security question display, answer field, etc.
    }

    private void showRegisterUI() {
        mainTable.clear();
        mainTable.add(registerTable);
        registerTable.setVisible(true);
        registerMode = true;
    }

    private void showLoginUI() {
        mainTable.clear();
        createLoginUI();
        registerMode = false;
    }

    private void showForgotPasswordUI() {
        // Implementation for forgot password flow
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Result result = LoginMenuController.loginUser(username, password, false);
        messageLabel.setText(result.message());

        if (result.success()) {
            // Proceed to main game screen
        }
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        Gender gender = genderBox.getSelected();

        Result result = LoginMenuController.registerUser(
            username, password, confirmPassword, nickname, email, gender
        );

        messageLabel.setText(result.message());

        if (result.success()) {
            showSecurityQuestionDialog();
        } else {
            handleRegistrationErrors(result, username, password);
        }
    }

    private void handleRegistrationErrors(Result result, String username, String password) {
        if (result.message().equals("This username is already taken!")) {
            suggestAlternativeUsername(username);
        } else if (result.message().contains("weak")) {
            offerPasswordGeneration();
        }
    }

    private void suggestAlternativeUsername(final String originalUsername) {
        String newUsername;
        do {
            int randomSuffix = RandomGenerator.getInstance().randomInt(0, 999);
            newUsername = originalUsername + randomSuffix;
        } while (App.checkUsernameExists(newUsername));

        String finalNewUsername = newUsername;
        Dialog dialog = new Dialog("Username Taken", skin) {
            public void result(Object obj) {
                if ((Boolean) obj) {
                    usernameField.setText(finalNewUsername);
                    handleRegistration();
                }
            }
        };

        dialog.text("Do you want to use " + newUsername + " instead?");
        dialog.button("Yes", true);
        dialog.button("No", false);
        dialog.show(stage);
    }

    private void offerPasswordGeneration() {
        Dialog dialog = new Dialog("Weak Password", skin) {
            public void result(Object obj) {
                if ((Boolean) obj) {
                    String password = LoginMenuController.randomPasswordGenerator();
                    passwordField.setText(password);
                    confirmPasswordField.setText(password);
                    messageLabel.setText("Generated password: " + password);
                }
            }
        };

        dialog.text("Do you want to generate a strong password?");
        dialog.button("Generate", true);
        dialog.button("Cancel", false);
        dialog.show(stage);
    }

    private void showSecurityQuestionDialog() {
        // Implementation for security question selection
        // Would show dialog with question selection and answer fields
    }

    private void handleForgotPassword() {
        // Implementation for password recovery flow
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

    // Other required methods (resize, pause, etc.) remain the same
}

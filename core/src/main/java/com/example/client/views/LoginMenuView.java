package com.example.client.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.client.Main;
import com.example.client.controllers.ClientLoginController;
import com.example.client.models.ClientApp;
import com.example.common.User;
import com.example.common.enums.Gender;
import com.example.common.Message;
import com.example.client.NetworkClient;

public class LoginMenuView implements Screen {
    private final Main game;
    private Stage stage;
    private Skin skin;
    private Texture background;

    private Table mainTable;
    private Table loginPanel;
    private Table registerPanel;
    private Table securityQuestionPanel;
    private Table forgotPasswordPanel;

    private TextField usernameFieldLogin, passwordFieldLogin;
    private TextField usernameFieldRegister, passwordFieldRegister, confirmPasswordField,
        nicknameField, emailField, answerField, newPasswordField, confirmNewPasswordField;
    private SelectBox<Gender> genderBox;
    private SelectBox<String> questionBox;
    private Label messageLabelLogin, messageLabelRegister, messageLabelForget;

    private TextButton loginButton, registerButton, forgotPasswordButton, changePasswordButton;

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

        createLoginPanel();
        createRegisterPanel();
        createSecurityQuestionPanel();
        createForgotPasswordUI();

        mainTable.add(loginPanel).expand().fill();
        stage.addActor(mainTable);

        NetworkClient.get().addListener(this::onNetMessage);
    }

    private void createLoginPanel() {
        loginPanel = new Table();
        loginPanel.center();

        Label titleLabel = new Label("Welcome to StardewValley!", skin);
        usernameFieldLogin = new TextField("", skin);
        usernameFieldLogin.setMessageText("Username");
        passwordFieldLogin = new TextField("", skin);
        passwordFieldLogin.setPasswordMode(true);
        passwordFieldLogin.setPasswordCharacter('*');
        passwordFieldLogin.setMessageText("Password");

        CheckBox stayLoggedInBox = new CheckBox("Stay Logged In", skin);

        loginButton = new TextButton("Login", skin);
        forgotPasswordButton = new TextButton("Forgot Password?", skin);
        TextButton switchToRegisterButton = new TextButton("Create Account", skin);
        messageLabelLogin = new Label("", skin);
        messageLabelLogin.setColor(Color.RED);

        loginPanel.add(titleLabel).padBottom(20).row();
        loginPanel.add(usernameFieldLogin).width(400).padBottom(15).row();
        loginPanel.add(passwordFieldLogin).width(400).padBottom(15).row();
        loginPanel.add(stayLoggedInBox).width(400).padBottom(15).row();
        loginPanel.add(loginButton).width(200).padBottom(10).row();
        loginPanel.add(forgotPasswordButton).width(300).padBottom(5).row();
        loginPanel.add(switchToRegisterButton).width(250).padBottom(15).row();
        loginPanel.add(messageLabelLogin).width(400).row();

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
    }

    private void createRegisterPanel() {
        registerPanel = new Table();
        registerPanel.center();

        Label titleLabel = new Label("Create Account", skin);
        usernameFieldRegister = new TextField("", skin);
        usernameFieldRegister.setMessageText("Username");

        passwordFieldRegister = new TextField("", skin);
        passwordFieldRegister.setMessageText("Password");

        confirmPasswordField = new TextField("", skin);
        confirmPasswordField.setMessageText("Confirm Password");

        nicknameField = new TextField("", skin);
        nicknameField.setMessageText("Nickname");
        emailField = new TextField("", skin);
        emailField.setMessageText("Email");
        genderBox = new SelectBox<>(skin);
        genderBox.setItems(Gender.BOY, Gender.GIRL);

        registerButton = new TextButton("Register", skin);
        TextButton generatePasswordButton = new TextButton("Generate Password", skin);
        TextButton switchToLoginButton = new TextButton("Back to Login", skin);
        messageLabelRegister = new Label("", skin);
        messageLabelRegister.setColor(Color.RED);

        registerPanel.add(titleLabel).padBottom(20).row();
        registerPanel.add(usernameFieldRegister).width(400).padBottom(10).row();
        registerPanel.add(passwordFieldRegister).width(400).padBottom(10).row();
        registerPanel.add(confirmPasswordField).width(400).padBottom(10).row();
        registerPanel.add(nicknameField).width(400).padBottom(10).row();
        registerPanel.add(emailField).width(400).padBottom(10).row();
        registerPanel.add(genderBox).width(200).padBottom(15).row();

        Table passwordButtons = new Table();
        passwordButtons.add(registerButton).width(200).padRight(10);
        passwordButtons.add(generatePasswordButton).width(300);
        registerPanel.add(passwordButtons).padBottom(10).row();
        registerPanel.add(switchToLoginButton).width(250).padBottom(10).row();
        registerPanel.add(messageLabelRegister).width(400).row();

        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                handleRegistration();
            }
        });

        generatePasswordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String password = ClientLoginController.randomPasswordGenerator();
                passwordFieldRegister.setText(password);
                confirmPasswordField.setText(password);
                messageLabelRegister.setText("Generated password: " + password);
            }
        });

        switchToLoginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showLoginUI();
            }
        });
    }

    private void createSecurityQuestionPanel() {
        securityQuestionPanel = new Table();
        securityQuestionPanel.center();

        Label titleLabel = new Label("Choose a Security Question.", skin);
        answerField = new TextField("", skin);
        answerField.setMessageText("answer");

        questionBox = new SelectBox<>(skin);
        questionBox.setItems(ClientApp.securityQuestions);

        TextButton okButton = new TextButton("OK", skin);

        securityQuestionPanel.add(titleLabel).padBottom(20).row();
        securityQuestionPanel.add(questionBox).padBottom(15).row();
        securityQuestionPanel.add(answerField).width(400).padBottom(10).row();
        securityQuestionPanel.add(okButton).width(200).padRight(10);

        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String question = questionBox.getSelected();
                String answer = answerField.getText();

                ClientLoginController.sendSecurityQuestionMessage(question, answer);
            }
        });
    }

    private void createForgotPasswordUI() {
        forgotPasswordPanel = new Table();
        forgotPasswordPanel.center();

        Label usernameLabel = new Label("Enter your username:", skin);
        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");

        Label questionLabel = new Label("", skin);
        TextField answerField = new TextField("", skin);
        answerField.setMessageText("answer");

        Label chooseANewPasswordLabel = new Label("Choose a new password:", skin);
        newPasswordField = new TextField("", skin);
        newPasswordField.setMessageText("New Password");
        confirmNewPasswordField = new TextField("", skin);
        confirmNewPasswordField.setMessageText("Confirm New Password");

        messageLabelForget = new Label("", skin);
        messageLabelForget.setColor(Color.RED);

        TextButton okButton = new TextButton("OK", skin);
        TextButton submitButton = new TextButton("Submit", skin);
        changePasswordButton = new TextButton("Change Password", skin);
        TextButton backToLogin = new TextButton("Back to Login", skin);

        forgotPasswordPanel.add(usernameLabel).padBottom(20).row();
        forgotPasswordPanel.add(usernameField).width(400).padBottom(20).row();
        forgotPasswordPanel.add(okButton).width(100).row();
        forgotPasswordPanel.add(backToLogin).padBottom(10).row();
        forgotPasswordPanel.add(messageLabelForget).row();

        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = usernameField.getText();
                User user = ClientLoginController.getUserByUsername(username);
                if (user != null) {
                    forgotPasswordPanel.clearChildren();

                    usernameField.setDisabled(true); okButton.setDisabled(true);
                    questionLabel.setText(user.getSecurityQuestion()); messageLabelForget.setText("");
                    forgotPasswordPanel.add(usernameLabel).padBottom(20).row();
                    forgotPasswordPanel.add(usernameField).width(400).padBottom(20).row();
                    forgotPasswordPanel.add(okButton).width(100).padBottom(20).row();
                    forgotPasswordPanel.add(questionLabel).padBottom(20).row();
                    forgotPasswordPanel.add(answerField).width(400).padBottom(20).row();
                    forgotPasswordPanel.add(submitButton).width(150).row();

                    forgotPasswordPanel.add(backToLogin).padBottom(10).row();
                    forgotPasswordPanel.add(messageLabelForget).row();
                }
                else {
                    messageLabelForget.setText("username not found!");
                }
            }
        });

        submitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                User user = ClientLoginController.getUserByUsername(usernameField.getText());
                String answer = answerField.getText();

                if(answer.equals(user.getSecurityQuestionAnswer())) {
                    forgotPasswordPanel.clearChildren();

                    answerField.setDisabled(true); submitButton.setDisabled(true); messageLabelForget.setText("");
                    forgotPasswordPanel.add(usernameLabel).padBottom(20).row();
                    forgotPasswordPanel.add(usernameField).width(400).padBottom(20).row();
                    forgotPasswordPanel.add(okButton).width(100).padBottom(20).row();
                    forgotPasswordPanel.add(questionLabel).padBottom(20).row();
                    forgotPasswordPanel.add(answerField).width(400).padBottom(20).row();
                    forgotPasswordPanel.add(submitButton).width(150).padBottom(20).row();
                    forgotPasswordPanel.add(chooseANewPasswordLabel).padBottom(20).row();
                    forgotPasswordPanel.add(newPasswordField).width(400).row();
                    forgotPasswordPanel.add(confirmNewPasswordField).width(400).padBottom(20).row();
                    forgotPasswordPanel.add(changePasswordButton).padBottom(20).row();

                    forgotPasswordPanel.add(backToLogin).padBottom(10).row();
                    forgotPasswordPanel.add(messageLabelForget).row();
                }
                else {
                    messageLabelForget.setText("Oops! Wrong Answer.");
                }
            }
        });

        changePasswordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String newPassword = newPasswordField.getText();
                String confirmNewPassword = confirmNewPasswordField.getText();

                if(newPassword.equals(confirmNewPassword)) {
                    ClientLoginController.sendChangePasswordMessage(usernameField.getText(), newPassword);
                }
                else {
                    messageLabelForget.setText("password confirmation doesn't match!");
                }
            }
        });

        backToLogin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showLoginUI();
            }
        });
    }

    private void showRegisterUI() {
        mainTable.clearChildren();
        mainTable.add(registerPanel).expand().fill();

        usernameFieldRegister.setText("");
        passwordFieldRegister.setText("");
        confirmPasswordField.setText("");
        nicknameField.setText("");
        emailField.setText("");
        genderBox.setSelected(Gender.BOY);
        messageLabelRegister.setText("");
    }

    private void showLoginUI() {
        mainTable.clearChildren();
        mainTable.add(loginPanel).expand().fill();

        usernameFieldLogin.setText("");
        passwordFieldLogin.setText("");
        messageLabelLogin.setText("");
    }

    private void showForgotPasswordUI() {
        mainTable.clearChildren();
        createForgotPasswordUI();
        mainTable.add(forgotPasswordPanel).expand().fill();
    }

    private void showSecurityQuestionUI() {
        mainTable.clearChildren();
        mainTable.add(securityQuestionPanel).expand().fill();

        questionBox.setSelected(ClientApp.securityQuestions[0]);
        answerField.setText("");
    }

    private void handleLogin() {
        String username = usernameFieldLogin.getText();
        String password = passwordFieldLogin.getText();
        loginButton.setDisabled(true);

        ClientLoginController.sendLoginMessage(username, password);
    }

    private void handleRegistration() {
        String username = usernameFieldRegister.getText();
        String password = passwordFieldRegister.getText();
        String confirmPassword = confirmPasswordField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        Gender gender = genderBox.getSelected();

        ClientLoginController.sendSignupMessage(username, password, confirmPassword, nickname, email, gender);
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

    private void onNetMessage(Message msg) {
        String action = msg.getFromBody("action");

        switch (action) {
            case "login" -> {
                boolean success = msg.getFromBody("success");
                String text = msg.getFromBody("message");
                messageLabelLogin.setText(text);
                messageLabelLogin.setColor(success ? Color.GREEN : Color.RED);
                loginButton.setDisabled(false);
                if (success) {
                    ClientLoginController.updateUser();
                    game.setScreen(new MainMenuView(game));
                }
            }
            case "signup" -> {
                boolean success = msg.getFromBody("success");
                String text = msg.getFromBody("message");
                messageLabelRegister.setText(text);
                messageLabelRegister.setColor(success ? Color.GREEN : Color.RED);
                registerButton.setDisabled(false);
                if (success) {
                    ClientLoginController.updateUser();
                    showSecurityQuestionUI();
                }
            }
            case "security_question" -> {
                ClientLoginController.updateUser();
                game.setScreen(new MainMenuView(game));
            }
            case "change_password" -> {
                boolean success = msg.getFromBody("success");
                String text = msg.getFromBody("message");

                messageLabelForget.setText(text);

                if (success) {
                    newPasswordField.setDisabled(true);
                    confirmNewPasswordField.setDisabled(true);
                    changePasswordButton.setDisabled(true);
                    messageLabelForget.setColor(Color.BLUE);
                }
            }
        }
    }
}

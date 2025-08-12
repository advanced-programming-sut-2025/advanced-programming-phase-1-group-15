package com.example.client.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.example.client.Main;
import com.example.client.NetworkClient;
import com.example.common.Game;
import com.example.common.Message;
import com.example.common.Player;
import com.example.common.cooking.Food;
import com.example.common.cooking.FoodType;
import com.example.common.tools.BackPackable;
import com.example.common.tools.Fridge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ChatMenu {
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));
    private boolean visible ;
    private final Table rootTable;
    private final Table recipe;
    private final Table fridge;
    private final Main main;
    private final Game game;
    private final Runnable onHideCallback;
    private final Label tooltipLabel = new Label("", skin);
    private final Container<Label> tooltipContainer = new Container<>(tooltipLabel);
    public ChatMenu(Main main, Game game, Runnable onHideCallback) {
        game.getCurrentPlayer().getInventory().addToBackPack(new Food(FoodType.SALMON_DINNER) , 3);
        this.main = main;
        this.game = game;
        this.onHideCallback = onHideCallback;
        this.stage = new Stage(new ScreenViewport(), main.getBatch());
        rootTable = new Table(skin);
        rootTable.setFillParent(false);
        rootTable.setSize(800, 700);
        rootTable.setPosition(Gdx.graphics.getWidth() / 2f - 400, Gdx.graphics.getHeight() / 2f - 350);
        rootTable.setVisible(false);
        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));
        Table tabBar = new Table(skin);
        TextButton recipeTab = new TextButton("Public", skin);
        TextButton fridgeTab = new TextButton("private", skin);
        tabBar.add(recipeTab).pad(5);
        tabBar.add(fridgeTab).pad(5);
        rootTable.add(tabBar).expandX().top().padTop(10).row();
        recipe = createCookingContent();
        fridge = createFridgeContent();
        Stack stack = new Stack();
        stack.add(recipe);
        stack.add(fridge);
        changeTab(recipe);
        rootTable.add(stack).expand().fill().row();
        TextButton closeButton = new TextButton("X Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setVisible(false , 0);
                if (onHideCallback != null) {
                    onHideCallback.run();
                }
            }
        });
        rootTable.add(closeButton).right().pad(10);
        tooltipLabel.setColor(Color.BROWN);
        tooltipContainer.background(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));
        tooltipContainer.pad(20);
        tooltipContainer.setVisible(false);
        stage.addActor(rootTable);
        stage.addActor(tooltipContainer);
        recipeTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(recipe);
            }
        });
        fridgeTab.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(fridge);
            }
        });
    }
    public void draw(float delta) {
        if (!visible) return;
        stage.act(delta);

        if (tooltipContainer.isVisible()) {
            tooltipContainer.pack();
            tooltipContainer.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        }

        stage.draw();
    }
    private Table createCookingContent() {
        Table table = new Table(skin);
        ArrayList<String> publicChat = new ArrayList<>(game.getCurrentPlayer().getPublicChat());
        Collections.reverse(publicChat);
        List<String> itemList = new List<>(skin);
        String[] items = publicChat.toArray(new String[0]);
        itemList.setItems(items);
        ScrollPane scrollPane = new ScrollPane(itemList, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        table.add(scrollPane).expand().fill().pad(10).row();
        TextField Message = new TextField("Enter Message", skin);
        Message.getStyle().fontColor = Color.BLACK;
        table.add(Message).left().width(800).row();
        TextButton sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                HashMap<String , Object> body = new HashMap<>();
                body.put("action", "chat");
                body.put("username", game.getCurrentPlayer().getUsername());
                body.put("type", "public");
                body.put("message" ,game.getCurrentPlayer().getUsername()+": "+ Message.getText());
                NetworkClient.get().sendMessage(new Message(body , com.example.common.Message.Type.COMMAND));
                game.getCurrentPlayer().getPublicChat().add(Message.getText());
                game.getCurrentPlayer().setUpdateMessage(true);
            }
        });
        table.add(sendButton).right().pad(10).row();
        return table;
    }
    private Table createFridgeContent() {
        Table table = new Table(skin);
        Label errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);
        ArrayList<String> privateChat = new ArrayList<>(game.getCurrentPlayer().getPrivateChat());
        Collections.reverse(privateChat);
        List<String> itemList = new List<>(skin);
        String[] items = privateChat.toArray(new String[0]);
        itemList.setItems(items);
        ScrollPane scrollPane = new ScrollPane(itemList, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        table.add(scrollPane).expand().fill().pad(10).row();
        TextField Message = new TextField("Enter Message", skin);
        TextField Username = new TextField("Username", skin);
        Message.getStyle().fontColor = Color.BLACK;
        Username.getStyle().fontColor = Color.BLACK;
        table.add(Message).left().width(800).row();
        table.add(Username).left().width(400).row();
        TextButton sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Player player = game.getPlayerByUsername(Username.getText());
                if (player != null) {
                    showError("User not find" , errorLabel);
                }
                HashMap<String , Object> body = new HashMap<>();
                body.put("action", "chat");
                body.put("username", game.getCurrentPlayer().getUsername());
                body.put("target", Username.getText());
                body.put("type", "private");
                body.put("message" , game.getCurrentPlayer().getUsername()+": "+Message.getText());
                game.getCurrentPlayer().getPrivateChat().add(Message.getText()+"    to:"+Username.getText());
                game.getCurrentPlayer().setUpdateMessage(true);
                NetworkClient.get().sendMessage(new Message(body , com.example.common.Message.Type.COMMAND));
            }
        });
        table.add(errorLabel).expand().fill().row();
        table.add(sendButton).right().pad(10).row();
        return table;
    }
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible, int tabNumber) {
        this.visible = visible;
        rootTable.setVisible(visible);
        if (visible) {
            refresh();
            switch (tabNumber) {
                case 1:
                    changeTab(recipe);
                    break;
                case 2:
                    changeTab(fridge);
                    break;
                default:
                    break;
            }
        }
    }

    public Stage getStage() {
        return stage;
    }
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    public void refresh() {
        recipe.clear(); fridge.clear();
        recipe.add(createCookingContent()).expand().fill();
        fridge.add(createFridgeContent()).expand().fill();
    }
    private void changeTab(Table content) {
        recipe.setVisible(false);
        fridge.setVisible(false);
        content.setVisible(true);
    }
    private void showError(String message, Label errorLabel) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                errorLabel.setVisible(false);
            }
        }, 2);
    }
}

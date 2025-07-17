package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.example.Main;
import com.example.models.Game;
import com.example.models.crafting.CraftItem;
import com.example.models.tools.BackPack;

import java.util.ArrayList;

public class CraftingMenu {
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));
    private boolean visible = false;
    private final Table table;
    private final Main main;
    private final Game game;
    private final Runnable onHideCallback;
    public CraftingMenu(Main main, Game game, Runnable onHideCallback) {
        this.main = main;
        this.game = game;
        this.onHideCallback = onHideCallback;
        stage = new Stage(new ScreenViewport(), main.getBatch());
        this.table = createCraftingContent();
        stage.addActor(table);
    }
    private Table createCraftingContent() {
        Table table = new Table(skin);
        BackPack inventory = game.getCurrentPlayer().getInventory();
        ArrayList<CraftItem> craftItems = game.getCurrentPlayer().getAvailableCrafts();
        Array<String> itemName = new Array<>();
        for (CraftItem availableCraft : game.getCurrentPlayer().getAvailableCrafts()) {
            itemName.add(availableCraft.getName());
        }
        List<String> itemList = new List<>(skin);
        itemList.setItems(itemName);
        ScrollPane scrollPane = new ScrollPane(itemList,skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        Label titleLabel = new Label("Crafting Item:", skin); titleLabel.setColor(Color.FIREBRICK);
        Label descriptionLabel = new Label("Desc: ", skin);
        TextButton backButton = new TextButton("exit", skin);
        descriptionLabel.setColor(Color.FIREBRICK); descriptionLabel.setWrap(true); descriptionLabel.setWidth(700);

        itemList.addListener(new InputListener() {
            public boolean mouseMoved(InputEvent event, float x, float y) {
                int index = itemList.getSelectedIndex();
                if (index >= 0) {
                    String item = itemList.getItems().get(index);
                    if (item != null && !item.isEmpty()) {
                        String itemName = item.split(" x")[0];
                        CraftItem current = null ;
                        for (CraftItem craftItem : craftItems) {
                            if(craftItem.getName().equals(itemName)) {
                                current = craftItem;
                            }
                        }
                        if (current != null) {
                            descriptionLabel.setText("Desc: " + current.getDescription());
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setVisible(false); // Hide menu
                onHideCallback.run(); // Restore game input
                return true;
            }
        });
        Table bottomRow = new Table();
        bottomRow.add(descriptionLabel).right().padLeft(10).width(700);
        bottomRow.add(exitButton).right().padLeft(20).size(100, 40);
        table.add(titleLabel).padBottom(10).row();
        table.add(scrollPane).expand().fill().pad(10).row();
        table.add(bottomRow).bottom();

        return table;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Stage getStage() {
        return stage;
    }
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    public void draw(float delta) {
        if (!visible) return;
        stage.act(delta);

        if (table.isVisible()) {
            table.pack();
            table.setPosition(
                (Gdx.graphics.getWidth() - table.getPrefWidth()) / 2f,
                (Gdx.graphics.getHeight() - table.getPrefHeight()) / 2f
            );
        }
        stage.draw();
    }

}

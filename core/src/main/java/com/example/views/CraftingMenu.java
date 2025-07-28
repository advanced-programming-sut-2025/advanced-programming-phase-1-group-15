package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.example.Main;
import com.example.models.Game;
import com.example.models.crafting.CraftItem;
import com.example.models.crafting.CraftItemType;
import com.example.models.tools.BackPackable;

public class CraftingMenu {
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));
    private boolean visible = false;
    private final Table rootTable;
    private final Table table;
    private final Main main;
    private final Game game;
    private final Runnable onHideCallback;
    public CraftingMenu(Main main, Game game, Runnable onHideCallback) {
        game.getCurrentPlayer().addToAvailableCraftsRecipe(new CraftItem(CraftItemType.BEE_HOUSE));
        game.getCurrentPlayer().addToAvailableCraftsRecipe(new CraftItem(CraftItemType.DEHYDRATOR));
        this.main = main;
        this.game = game;
        this.onHideCallback = onHideCallback;
        stage = new Stage(new ScreenViewport(), main.getBatch());
        rootTable = new Table(skin);
        rootTable.setFillParent(false);
        rootTable.setSize(800, 700);
        rootTable.setPosition(Gdx.graphics.getWidth() / 2f - 400, Gdx.graphics.getHeight() / 2f - 350);
        rootTable.setVisible(false);
        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));
        TextButton craftButton = new TextButton("Craft", skin);
        craftButton.setVisible(false);
        final Label errorLabel = new Label("", skin);
        this.table = createCraftingContent(craftButton , errorLabel);
        rootTable.add(table).expand().fill().pad(20).row();
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setVisible(false);
                onHideCallback.run();
                return true;
            }
        });
        rootTable.add(errorLabel).width(700).row();
        rootTable.add(craftButton).right().pad(10).row();
        rootTable.add(exitButton).right().pad(10);
        stage.addActor(rootTable);
    }
    private Table createCraftingContent(TextButton craftButton , Label errorLabel) {
        Table table = new Table(skin);
        Label titleLabel = new Label("Crafting Item:", skin); titleLabel.setColor(Color.FIREBRICK);
        Label descriptionLabel = new Label("Desc: ", skin);
        Image craftIcon = new Image();
        craftIcon.setSize(48, 48);
        craftIcon.setVisible(false);
        final CraftItem[] current = new CraftItem[1];
        craftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (current[0] != null) {
                    if (isAvailable(current[0])) {
                        showError("You make it successfully" , errorLabel);
                        errorLabel.setColor(Color.GREEN);
                    } else {
                        if (game.getCurrentPlayer().getInventory().checkFilled()){
                            showError("You don't have enough capacity" , errorLabel);
                            errorLabel.setColor(Color.RED);
                        }
                        else{
                            showError("You don't have enough material" , errorLabel);
                            errorLabel.setColor(Color.RED);
                        }
                    }
                }
            }
        });
        Table itemTable = new Table(skin);
        for (CraftItem craft : game.getCurrentPlayer().getAvailableCraftsRecipe()) {
            Label label = new Label(craft.getName(), skin);
            if (!craft.isAvailable()) {
                label.setColor(Color.LIGHT_GRAY);
            }
            else {
                label.setColor(Color.BROWN);
            }
            label.addListener(new InputListener() {
                @Override
                public boolean mouseMoved(InputEvent event, float x, float y) {
                    current[0] = craft;
                    if (!craft.isAvailable()) {
                        craftButton.setVisible(false);
                    } else {
                        craftButton.setVisible(true);
                    }
                    descriptionLabel.setText("Desc: " + craft.getCraftItemType().getRecipe());
                    Sprite sprite = craft.getSprite();
                    sprite.setSize(48, 48);
                    craftIcon.setDrawable(new TextureRegionDrawable(sprite));
                    craftIcon.setVisible(true);
                    return true;
                }
            });

            itemTable.add(label).left().pad(5).row();
        }
        ScrollPane scrollPane = new ScrollPane(itemTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        errorLabel.setVisible(false);
        descriptionLabel.setColor(Color.FIREBRICK); descriptionLabel.setWrap(true); descriptionLabel.setWidth(700);
        Table bottomRow = new Table();
        bottomRow.top().right();
        bottomRow.add(craftIcon).size(60, 60).left();
        bottomRow.add(descriptionLabel).right().padLeft(10).width(700);
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
        rootTable.setVisible(visible);
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
        stage.draw();
    }
    public boolean isAvailable(CraftItem craftItem) {
        if (craftItem == null) return false;
        if (game.getCurrentPlayer().getInventory().checkFilled()){
            return false;
        }
        for (BackPackable item : craftItem.getCraftItemType().getIngredients().keySet()) {
            if (game.getCurrentPlayer().getInventory().getItemByName(item.getName()) == null) {
                return false;
            }
            int num = craftItem.getCraftItemType().getIngredients().get(item.getName());
            int total = game.getCurrentPlayer().getInventory().getItemCount(item.getName());
            if (total < num) {
                return false;
            }
        }
        for (BackPackable item : craftItem.getCraftItemType().getIngredients().keySet()) {
            int num = craftItem.getCraftItemType().getIngredients().get(item.getName());
            game.getCurrentPlayer().getInventory().removeCountFromBackPack(item , num);
        }
        game.getCurrentPlayer().getInventory().addToBackPack(new CraftItem(craftItem.getCraftItemType()) , 1);
        return true;
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

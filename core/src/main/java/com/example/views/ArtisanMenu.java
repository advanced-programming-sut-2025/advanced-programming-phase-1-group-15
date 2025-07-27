package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import com.example.models.artisanry.ArtisanItem;
import com.example.models.artisanry.ArtisanItemType;
import com.example.models.crafting.CraftItem;
import java.util.ArrayList;
import java.util.Arrays;

public class ArtisanMenu {
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));
    private boolean visible = false;
    private final Table rootTable;
    private final Table table;
    private final Main main;
    private final Game game;
    private final Runnable onHideCallback;
    private final CraftItem craftItem;
    private boolean empty = true;
    public ArtisanMenu(Main main, Game game, Runnable onHideCallback , CraftItem craftItem) {
        this.main = main;
        this.game = game;
        this.onHideCallback = onHideCallback;
        this.craftItem = craftItem;
        stage = new Stage(new ScreenViewport(), main.getBatch());
        rootTable = new Table(skin);
        rootTable.setFillParent(false);
        rootTable.setSize(800, 700);
        rootTable.setPosition(Gdx.graphics.getWidth() / 2f - 400, Gdx.graphics.getHeight() / 2f - 350);
        rootTable.setVisible(false);
        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));
        table = creatArtisanTable();
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setVisible(false);
                onHideCallback.run();
                return true;
            }
        });
        rootTable.add(table).expand().fill().pad(20).row();
        rootTable.add(exitButton).right().pad(10);
        stage.addActor(rootTable);
    }
    private Table creatArtisanTable() {
        Label titleLabel = new Label("Artisan Items:", skin); titleLabel.setColor(Color.FIREBRICK);
        Label descriptionLabel = new Label("Desc: ", skin);
        Image artisanIcon = new Image();
        artisanIcon.setSize(48, 48);
        artisanIcon.setVisible(false);
        TextButton artisanButton = new TextButton("Build", skin);
        final Label errorLabel = new Label("", skin);
        final ArtisanItem[] current = new ArtisanItem[1];
        artisanButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (current[0] != null) {
                    if (!empty){
                        showError("You must wait to progress finish" , errorLabel);
                    }
                    else if (isAvailable(current[0])) {
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
        ArrayList<ArtisanItem> available = availableItems();
        String[] items = available.stream().map(ArtisanItem::getName).toArray(String[]::new);
        List<String> itemList = new List<>(skin);
        itemList.setItems(items);
        itemList.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                int index = itemList.getSelectedIndex();
                if (index >= 0) {
                    String item = itemList.getItems().get(index);
                    if (item != null && !item.isEmpty()) {
                        String itemName = item.split(" x")[0];
                        current[0] = getArtisanItem(itemName);
                        if (current[0] != null) {
                            descriptionLabel.setText("Desc: " + current[0].getArtisanItemType().getIngredients().getName());
                            return true;
                        }
                        else {
                            descriptionLabel.setText("Desc: ");
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        ScrollPane scrollPane = new ScrollPane(itemList, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        errorLabel.setVisible(false);
        descriptionLabel.setColor(Color.FIREBRICK); descriptionLabel.setWrap(true); descriptionLabel.setWidth(700);
        Table bottomRow = new Table();
        bottomRow.top().right();
        bottomRow.add(artisanIcon).size(60, 60).left();
        bottomRow.add(descriptionLabel).right().padLeft(10).width(700);
        Table table = new Table(skin);
        table.add(titleLabel).padBottom(10).row();
        table.add(scrollPane).expand().fill().pad(10).row();
        table.add(bottomRow).bottom().row();
        table.add(errorLabel).width(700).row();
        table.add(artisanButton).right().row();
        return table;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public CraftItem getCraftItem() {
        return craftItem;
    }

    public Stage getStage() {
        return stage;
    }
    public boolean isAvailable(ArtisanItem artisanItem) {
        return true;
    }
    public ArrayList<ArtisanItem> availableItems() {
        ArrayList<ArtisanItemType> allTypes = new ArrayList<>(Arrays.asList(ArtisanItemType.values()));
        ArrayList<ArtisanItem> available = new ArrayList<>();
        for (ArtisanItemType Type : allTypes) {
            if (Type.craftItemType == craftItem.getCraftItemType()){
                available.add(new ArtisanItem(Type));
            }
        }
        return available;
    }
    public ArtisanItem getArtisanItem(String itemName) {
        ArrayList<ArtisanItemType> allTypes = new ArrayList<>(Arrays.asList(ArtisanItemType.values()));
        for (ArtisanItemType Type : allTypes) {
            if (new ArtisanItem(Type).getName().equals(itemName)){
                return new ArtisanItem(Type);
            }
        }
        return null;
    }
}

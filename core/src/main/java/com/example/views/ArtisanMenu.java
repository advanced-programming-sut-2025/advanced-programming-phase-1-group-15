package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.example.Main;
import com.example.models.Game;
import com.example.models.Player;
import com.example.models.artisanry.ArtisanItem;
import com.example.models.artisanry.ArtisanItemType;
import com.example.models.crafting.CraftItem;
import com.example.models.crafting.CraftItemType;
import com.example.models.foraging.ForagingCrop;
import com.example.models.foraging.ForagingCropsType;
import com.example.models.tools.BackPackable;

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
    private boolean ready = false;
    private ArtisanItem currentArtisanItem = null;
    private TextButton getArtisanButton = new TextButton("Get Item", skin);
    private final Label madeLabel = new Label("", skin);
    private TextButton madeFast = new TextButton("Ready Fast", skin);
    Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    private Stack progressStack = new Stack();
    Image background = new Image(skin.newDrawable("white", Color.DARK_GRAY));
    Image fill = new Image(skin.newDrawable("white", Color.LIME));
    public ArtisanMenu(Main main, Game game, Runnable onHideCallback , CraftItem craftItem) {
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        progressStack.setSize(200, 20);
        fill.setSize(0, 20);

        progressStack.add(background);
        progressStack.add(fill);
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
        game.getCurrentPlayer().getInventory().addToBackPack(new ForagingCrop(ForagingCropsType.RED_MUSHROOM), 10);
    }
    private Table creatArtisanTable() {
        Label titleLabel = new Label("Artisan Items:", skin); titleLabel.setColor(Color.FIREBRICK);
        Label descriptionLabel = new Label("Desc: ", skin);
        Image artisanIcon = new Image();
        artisanIcon.setSize(48, 48);
        artisanIcon.setVisible(false);
        TextButton artisanButton = new TextButton("Build", skin);
        madeFast.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                ready = true;
                refresh(madeLabel);
            }
        });
        final Label errorLabel = new Label("", skin);
        madeLabel.setVisible(false);
        final ArtisanItem[] current = new ArtisanItem[1];
        artisanButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (current[0] != null) {
                    if (!empty){
                        showError("You must wait to progress finish" , errorLabel);
                        errorLabel.setColor(Color.RED);
                    }
                    else if (isAvailable(current[0])) {
                        showError("You going to make it" , errorLabel);
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
                    refresh(madeLabel);
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
                        refresh(madeLabel);
                        String itemName = item.split(" x")[0];
                        current[0] = getArtisanItem(itemName);
                        if (current[0] != null) {
                            if (current[0].getArtisanItemType().getIngredients() != null) {
                                descriptionLabel.setText("Desc: " + current[0].getArtisanItemType().getIngredients().getName()
                                + "  x" + current[0].getArtisanItemType().getNumber());
                                artisanIcon.setDrawable(new TextureRegionDrawable(current[0].getSprite()));
                                artisanIcon.setVisible(true);
                                return true;
                            }
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
        getArtisanButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getCurrentPlayer().getInventory().addToBackPack(currentArtisanItem ,1);
                currentArtisanItem = null;
                empty = true;
                ready = false;
                refresh(madeLabel);
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
        table.add(madeLabel).width(700).row();
        table.add(artisanButton).right().row();
        table.add(progressStack).width(200).height(20).pad(10).row();
        table.add(madeFast).right().row();
        table.add(getArtisanButton).right().row();
        return table;
    }
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    public void draw(float delta) {
        if (!visible) return;
        refreshProgress();
        refresh(madeLabel);
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
        rootTable.setVisible(visible);
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
        BackPackable item = artisanItem.getArtisanItemType().getIngredients();
        Player player = game.getCurrentPlayer();
        if (item == null){
            artisanItem.setHour(game.getDateAndTime().getHour());
            artisanItem.setDay(game.getDateAndTime().getDay());
            player.getArtisanItems().add(artisanItem);
            currentArtisanItem = artisanItem;
            empty = false;
            return true;
        }
        int number = artisanItem.getArtisanItemType().getNumber();
        if (player.getInventory().checkFilled()){
            return false;
        }
        if (player.getInventory().getItemByName(item.getName()) != null) {
            if (player.getInventory().getItemCount(item.getName())<number) {
                return false;
            }
            artisanItem.setHour(game.getDateAndTime().getHour());
            artisanItem.setDay(game.getDateAndTime().getDay());
            player.getArtisanItems().add(artisanItem);
            empty = false;
            currentArtisanItem = artisanItem;
            return true;
        }
        if (player.getFridge().getItemByName(item.getName()) != null) {
            if (player.getFridge().getItemCount(item.getName())<number) {
                return false;
            }
            artisanItem.setHour(game.getDateAndTime().getHour());
            artisanItem.setDay(game.getDateAndTime().getDay());
            empty = false;
            currentArtisanItem = artisanItem;
            return true;
        }
        return false;
    }
    public boolean isReady(){
        if (currentArtisanItem == null) return false;
        Player player = game.getCurrentPlayer();
        if (currentArtisanItem.getArtisanItemType().productionTimeInHours==0){
            if (currentArtisanItem.getDay()<game.getDateAndTime().getDay()) {
                ready = true;
                return true;
            }
            return false;
        }
        int hour = 0;
        hour += (game.getDateAndTime().getHour()-currentArtisanItem.getHour());
        hour += (game.getDateAndTime().getDay()-currentArtisanItem.getDay())*24;
        if(currentArtisanItem.getArtisanItemType().productionTimeInHours>hour) {
            return false;
        }
        ready = true;
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
    private void refresh(Label label) {
        isReady();
        if (ready){
            label.setText("Take your item");
            label.setVisible(true);
            label.setColor(Color.GREEN);
            getArtisanButton.setVisible(true);
            madeFast.setVisible(false);
            progressStack.setVisible(false);
            return;
        }
        if (!empty){
            label.setText("You going to made "+currentArtisanItem.getName());
            getArtisanButton.setVisible(false);
            madeFast.setVisible(true);
            label.setVisible(true);
            label.setColor(Color.GREEN);
        }
        else {
            label.setVisible(false);
            getArtisanButton.setVisible(false);
            madeFast.setVisible(false);
            label.setText("");
        }
    }
    private void refreshProgress(){
        if (ready){
            progressStack.setVisible(false);
        }
        if (currentArtisanItem != null) {
            float elapsed = (game.getDateAndTime().getDay() - currentArtisanItem.getDay()) * 24
                + game.getDateAndTime().getHour() - currentArtisanItem.getHour();

            float total = currentArtisanItem.getArtisanItemType().productionTimeInHours;
            float progress = Math.min(elapsed / total, 1f);
            fill.setWidth(progress * progressStack.getWidth());
            fill.setHeight(progressStack.getHeight());
            progressStack.setVisible(true);
        }
        else {
            progressStack.setVisible(false);
        }
    }
}

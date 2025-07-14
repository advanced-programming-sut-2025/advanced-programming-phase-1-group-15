package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.example.Main;
import com.example.models.Game;
import com.example.models.tools.BackPack;
import com.example.models.tools.BackPackable;

public class PauseMenuOverlay {
    private final Stage stage;
    private final Table rootTable;
    private final Skin skin;
    private boolean visible;
    private final Game game;

    private final Table inventoryContent;
    private final Table skillsContent = new Table();
    private final Table friendshipContent = new Table();
    private final Table mapContent = new Table();
    private final Table settingsContent = new Table();

    private final Label tooltipLabel = new Label("", new Skin(Gdx.files.internal("UI/StardewValley.json")));
    private final Container<Label> tooltipContainer = new Container<>(tooltipLabel);

    public PauseMenuOverlay(Main main, Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport(), main.getBatch());
        skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));

        rootTable = new Table(skin);
        rootTable.setFillParent(false);
        rootTable.setSize(800, 600);
        rootTable.setPosition(Gdx.graphics.getWidth() / 2f - 400, Gdx.graphics.getHeight() / 2f - 300);
        rootTable.setVisible(false);
        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));

        Table tabBar = new Table(skin);
        TextButton inventoryTab = new TextButton("Inventory", skin);
        TextButton skillsTab = new TextButton("Skills", skin);
        TextButton friendshipTab = new TextButton("Friendship", skin);
        TextButton mapTab = new TextButton("Map", skin);
        TextButton settingsTab = new TextButton("Settings", skin);

        tabBar.add(inventoryTab).pad(5);
        tabBar.add(skillsTab).pad(5);
        tabBar.add(friendshipTab).pad(5);
        tabBar.add(mapTab).pad(5);
        tabBar.add(settingsTab).pad(5);
        rootTable.add(tabBar).expandX().top().row();

        inventoryContent = createInventoryContent();
        Stack contentStack = new Stack();
        contentStack.add(inventoryContent);
        contentStack.add(skillsContent);
        contentStack.add(friendshipContent);
        contentStack.add(mapContent);
        contentStack.add(settingsContent);
        rootTable.add(contentStack).expand().fill().row();

        TextButton closeButton = new TextButton("X Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setVisible(false);
            }
        });
        rootTable.add(closeButton).right().pad(10);

        tooltipLabel.setColor(Color.BLACK);
        tooltipContainer.background(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));
        tooltipContainer.setWidth(600); tooltipContainer.setHeight(50); tooltipContainer.setVisible(false);
        stage.addActor(rootTable);
        stage.addActor(tooltipContainer);

        inventoryTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(inventoryContent);
            }
        });

        skillsTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeTab(skillsContent);
            }
        });

        friendshipTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeTab(friendshipContent);
            }
        });

        mapTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeTab(mapContent);
            }
        });

        settingsTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeTab(settingsContent);
            }
        });
    }

    public void draw(float delta) {
        if (!visible) return;
        stage.act(delta);

        if (tooltipContainer.isVisible()) {
            tooltipContainer.pack();
            tooltipContainer.setWidth(600); tooltipContainer.setHeight(50);
            tooltipContainer.setPosition(Gdx.input.getX() - tooltipContainer.getWidth() / 2f,
                Gdx.graphics.getHeight() - Gdx.input.getY());
        }

        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        rootTable.setVisible(visible);
        if (visible) {
            refresh();
            Gdx.input.setInputProcessor(stage);
        } else {
            Gdx.input.setInputProcessor(null);
        }
    }

    private Table createInventoryContent() {
        Table table = new Table(skin);
        BackPack inventory = game.getCurrentPlayer().getInventory();

        List<String> itemList = new List<>(skin);
        String[] items = inventory.getItems().entrySet().stream()
            .map(entry -> entry.getKey().getName() + " x" + entry.getValue())
            .toArray(String[]::new);
        itemList.setItems(items);

        ScrollPane scrollPane = new ScrollPane(itemList, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        Label titleLabel = new Label("Inventory Items:", skin);
        titleLabel.setColor(Color.FIREBRICK);

        ImageButton trashButton = new ImageButton(new TextureRegionDrawable(new Texture("Sprites/trashcan.png")));
        trashButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = itemList.getSelected();
                if (selected != null && !selected.isEmpty()) {
                    String itemName = selected.split(" x")[0];
                    BackPackable item = inventory.getItemByName(itemName);
                    if (item != null) {
                        inventory.removeFromBackPack(item);
                        refresh();
                    }
                }
            }
        });

        itemList.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                int index = itemList.getSelectedIndex();
                if (index >= 0) {
                    String item = itemList.getItems().get(index);
                    if (item != null && !item.isEmpty()) {
                        String itemName = item.split(" x")[0];
                        BackPackable hoveredItem = inventory.getItemByName(itemName);
                        if (hoveredItem != null) {
                            tooltipLabel.setText(hoveredItem.getDescription());
                            tooltipContainer.setVisible(true);
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                tooltipContainer.setVisible(false);
            }
        });

        Table buttonRow = new Table();
        buttonRow.add(trashButton).left().pad(5);

        table.add(titleLabel).padBottom(10).row();
        table.add(scrollPane).expand().fill().pad(10).row();
        table.add(buttonRow).bottom().left();

        return table;
    }

    private void refresh() {
        inventoryContent.clear();
        inventoryContent.add(createInventoryContent()).expand().fill();
    }

    private void changeTab(Table content) {
        inventoryContent.setVisible(false);
        skillsContent.setVisible(false);
        friendshipContent.setVisible(false);
        mapContent.setVisible(false);
        settingsContent.setVisible(false);

        content.setVisible(true);
    }
}

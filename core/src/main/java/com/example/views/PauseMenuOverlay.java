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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.example.Main;
import com.example.models.Game;
import com.example.models.Player;
import com.example.models.tools.BackPack;
import com.example.models.tools.BackPackable;

public class PauseMenuOverlay {
    private final Stage stage;
    private final Table rootTable;
    private final Skin skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));;
    private boolean visible;
    private final Game game;

    private final Table inventoryContent;
    private final Table skillsContent;
    private final Table friendshipContent = new Table();
    private final Table mapContent = new Table();
    private final Table settingsContent = new Table();

    private final Label tooltipLabel = new Label("", skin);
    private final Container<Label> tooltipContainer = new Container<>(tooltipLabel);

    private final Runnable onHideCallback; // field for callback

    public PauseMenuOverlay(Main main, Game game, Runnable onHideCallback) {
        this.game = game;
        this.onHideCallback = onHideCallback;
        stage = new Stage(new ScreenViewport(), main.getBatch());

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
        rootTable.add(tabBar).expandX().top().padTop(10).row();

        inventoryContent = createInventoryContent();
        skillsContent = createSkillsContent();
        Stack contentStack = new Stack();
        contentStack.add(inventoryContent);
        contentStack.add(skillsContent);
        contentStack.add(friendshipContent);
        contentStack.add(mapContent);
        contentStack.add(settingsContent);
        changeTab(inventoryContent);
        rootTable.add(contentStack).expand().fill().row();

        TextButton closeButton = new TextButton("X Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setVisible(false);
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
            tooltipContainer.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
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
        }
    }

    private Table createSkillsContent() {
        Table table = new Table(skin);
        Player pLayer = game.getCurrentPlayer();

        int farmingAbility = pLayer.getFarmingAbility(); int farmingLevel = pLayer.getFarmingLevel();
        int miningAbility = pLayer.getMiningAbility(); int miningLevel = pLayer.getMiningLevel();
        int foragingAbility = pLayer.getForagingAbility(); int foragingLevel = pLayer.getForagingLevel();
        int fishingAbility = pLayer.getFishingAbility(); int fishingLevel = pLayer.getFishingLevel();

        Label titleLabel = new Label("Skills and Abilities:", skin);
        titleLabel.setColor(Color.FIREBRICK);

        String[] skillNames = {"Farming", "Mining", "Foraging", "Fishing"};
        String[] tooltips = {
            "Farming affects crop growth and quality.",
            "Mining improves ore yield and mining speed.",
            "Foraging enhances wild gathering and tree chopping.",
            "Fishing increases fish rarity and catching speed."
        };

        Label[] skillLabels = new Label[4];
        Label[] levelLabels = {
            new Label("Lvl: " + farmingLevel, skin),
            new Label("Lvl: " + miningLevel, skin),
            new Label("Lvl: " + foragingLevel, skin),
            new Label("Lvl: " + fishingLevel, skin)
        };
        ProgressBar[] progressBars = {
            new ProgressBar(0, 100 * farmingLevel + 50, 1, false, skin),
            new ProgressBar(0, 100 * miningLevel + 50, 1, false, skin),
            new ProgressBar(0, 100 * foragingLevel + 50, 1, false, skin),
            new ProgressBar(0, 100 * fishingLevel + 50, 1, false, skin)
        };
        Label[] progressLabels = {
            new Label(farmingAbility + "/" + (100 * farmingLevel + 50), skin),
            new Label(miningAbility + "/" + (100 * miningLevel + 50), skin),
            new Label(foragingAbility + "/" + (100 * foragingLevel + 50), skin),
            new Label(fishingAbility + "/" + (100 * fishingLevel + 50), skin)
        };


        for (int i = 0; i < 4; i++) {
            levelLabels[i].setColor(Color.FOREST);
            progressLabels[i].setColor(Color.BLACK);
            progressBars[i].setValue(new int[]{farmingAbility, miningAbility, foragingAbility, fishingAbility}[i]);

            skillLabels[i] = new Label(skillNames[i] + ":", skin);
            skillLabels[i].setColor(Color.BLACK);

            final String tip = tooltips[i];
            skillLabels[i].addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    tooltipLabel.setText(tip);
                    tooltipContainer.setVisible(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    tooltipContainer.setVisible(false);
                }
            });
        }

        table.add(titleLabel).padBottom(10).colspan(4).row();
        for (int i = 0; i < 4; i++) {
            table.add(skillLabels[i]).right().pad(20);
            table.add(levelLabels[i]).pad(20);
            table.add(progressBars[i]).width(200).pad(20);
            table.add(progressLabels[i]).left().pad(20).row();
        }

        return table;
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

        Label titleLabel = new Label("Inventory Items:", skin); titleLabel.setColor(Color.FIREBRICK);
        Label descriptionLabel = new Label("Desc: ", skin);
        descriptionLabel.setColor(Color.FIREBRICK); descriptionLabel.setWrap(true); descriptionLabel.setWidth(700);

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
                            descriptionLabel.setText("Desc: " + hoveredItem.getDescription());
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        Table bottomRow = new Table();
        bottomRow.add(trashButton).left();
        bottomRow.add(descriptionLabel).right().padLeft(10).width(700);

        table.add(titleLabel).padBottom(10).row();
        table.add(scrollPane).expand().fill().pad(10).row();
        table.add(bottomRow).bottom();

        return table;
    }

    private void refresh() {
        inventoryContent.clear(); skillsContent.clear();
        inventoryContent.add(createInventoryContent()).expand().fill();
        skillsContent.add(createSkillsContent()).expand().fill();
    }

    private void changeTab(Table content) {
        inventoryContent.setVisible(false);
        skillsContent.setVisible(false);
        friendshipContent.setVisible(false);
        mapContent.setVisible(false);
        settingsContent.setVisible(false);

        content.setVisible(true);
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

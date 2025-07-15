package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.example.models.App;
import com.example.models.Game;
import com.example.models.Player;
import com.example.models.map.Map;
import com.example.models.map.Tile;
import com.example.models.tools.BackPack;
import com.example.models.tools.BackPackable;

public class PauseMenuOverlay {
    private final Stage stage;
    private final Table rootTable;
    private final Skin skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));
    private boolean visible;

    private final Main main;
    private final Game game;

    private final Table inventoryContent;
    private final Table skillsContent;
    private final Table friendshipContent = new Table();
    private final Table mapContent;
    private final Table settingsContent;

    private final Label tooltipLabel = new Label("", skin);
    private final Container<Label> tooltipContainer = new Container<>(tooltipLabel);

    private final Runnable onHideCallback; // field for callback

    public PauseMenuOverlay(Main main, Game game, Runnable onHideCallback) {
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
        mapContent = createMapContent();
        settingsContent = createSettingsContent();
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
                setVisible(false, 0);
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
                refresh();
                changeTab(skillsContent);
            }
        });

        friendshipTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(friendshipContent);
            }
        });

        mapTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(mapContent);
            }
        });

        settingsTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
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

    public void setVisible(boolean visible, int tabNumber) {
        this.visible = visible;
        rootTable.setVisible(visible);
        if (visible) {
            refresh();
            switch (tabNumber) {
                case 1:
                    changeTab(inventoryContent);
                    break;
                case 2:
                    changeTab(skillsContent);
                    break;
                case 3:
                    changeTab(friendshipContent);
                    break;
                case 4:
                    changeTab(mapContent);
                    break;
                case 5:
                    changeTab(settingsContent);
                    break;
                default:
                    break;
            }
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

    private Table createMapContent() {
        Table table = new Table(skin);

        Label titleLabel = new Label("World Map:", skin);
        titleLabel.setColor(Color.FIREBRICK);

        // Custom actor that draws the map scaled down
        Actor mapActor = new Actor() {
            private final float scale = 0.1f;
            private final float tileSideLength = 16f;

            @Override
            public void draw(Batch batch, float parentAlpha) {
                super.draw(batch, parentAlpha);
                com.example.models.map.Map currentMap = game.getMap();

                for (int row = 0; row < Map.ROWS; row++) {
                    for (int col = 0; col < Map.COLS; col++) {
                        Tile tile = currentMap.getTile(row, col);
                        float x = getX() + col * tileSideLength * scale;
                        float y = getY() + row * tileSideLength * scale;
                        float size = tileSideLength * scale;

                        if (tile.getAreaSprite() != null) {
                            batch.draw(tile.getAreaSprite(), x, y, size, size);
                        } else {
                            drawArea(batch, tile);
                        }

                        if (tile.getObjectSprite() != null) {
                            batch.draw(tile.getObjectSprite(), x, y, size, size);
                        }
                    }
                }
                drawPlayers(batch);
            }

            @Override
            public float getWidth() {
                return Map.COLS * tileSideLength * scale;
            }

            @Override
            public float getHeight() {
                return Map.ROWS * tileSideLength * scale;
            }

            public void drawArea(Batch batch, Tile tile) {
                com.example.models.map.Area area = tile.getArea();
                float x = getX() + area.getBottomLeftCorner().x * tileSideLength * scale;
                float y = getY() + area.getBottomLeftCorner().y * tileSideLength * scale;
                float sizeX = area.getWidth() * tileSideLength * scale;
                float sizeY = area.getHeight() * tileSideLength * scale;
                if (area.getTexture() != null) {
                    batch.draw(area.getTexture(), x, y, sizeX, sizeY);
                }
            }

            public void drawPlayers(Batch batch) {
                for(Player player : game.getPlayers()) {
                    float x = getX() + player.getPosition().x * tileSideLength * scale;
                    float y = getY() + player.getPosition().y * tileSideLength * scale;
                    float size = 5 * tileSideLength * scale;
                    batch.draw(player.getFace(), x, y, size, size);
                }
            }
        };

        table.add(titleLabel).row();
        table.add(mapActor).expand().fill();

        return table;
    }

    private Table createSettingsContent() {
        Table table = new Table(skin);

        Label titleLabel = new Label("Settings:", skin); titleLabel.setColor(Color.FIREBRICK);
        TextButton terminateGameButton = new TextButton("Terminate Game", skin);
        TextButton quitButton = new TextButton("Quit", skin);
        TextButton removePlayerButton = new TextButton("- Remove Player", skin);
        Label messageLabel = new Label("", skin); messageLabel.setColor(Color.FIREBRICK);


        terminateGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.getCurrentPlayer().equals(game.getMainPlayer())) {
                    App.recentGames.remove(game);
                    App.currentGame = null;
                    main.setScreen(new MainMenuView(main));
                }
                else {
                    messageLabel.setText("You don't have permissions to fully terminate the game!");
                }
            }
        });

        table.add(titleLabel).padBottom(10).row();
        table.add(terminateGameButton).width(300).row();
        table.add(quitButton).width(300).row();
        table.add(removePlayerButton).width(300).row();
        table.add(messageLabel).padTop(40).row();

        return table;
    }

    private void refresh() {
        inventoryContent.clear(); skillsContent.clear(); mapContent.clear(); settingsContent.clear();
        inventoryContent.add(createInventoryContent()).expand().fill();
        skillsContent.add(createSkillsContent()).expand().fill();
        mapContent.add(createMapContent()).expand().fill();
        settingsContent.add(createSettingsContent()).expand().fill();
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

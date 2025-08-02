package com.example.client.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.common.map.Area;
import com.example.client.controllers.GameController;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.map.AreaType;
import com.example.common.map.Farm;
import com.example.common.map.Tile;
import com.example.common.stores.JojaMart;

public class JojaMartMenu extends PopUpMenu {
    private static final float WIDTH = 800f;
    private static final float HEIGHT = 800f;
    private static final float PADDING = 10f;

    private final JojaMart store;
    private Table dynamicContentTable;
    private com.badlogic.gdx.scenes.scene2d.ui.Label messageLabel;

    public JojaMartMenu(Skin skin, String title, Runnable onHideCallback, JojaMart store) {
        super(skin, title, WIDTH, HEIGHT, onHideCallback);
        this.store = store;
    }

    @Override
    protected void populate(com.badlogic.gdx.scenes.scene2d.ui.Window w) {
        dynamicContentTable = new Table();
        dynamicContentTable.pad(PADDING);
        dynamicContentTable.top();

        createInitialMenu();

        w.add(dynamicContentTable).expand().fill().row();

        messageLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("", skin);
        w.add(messageLabel).padTop(PADDING).row();
    }

    private void createInitialMenu() {
        dynamicContentTable.clear();

        TextButton buildBarnButton = new TextButton("Build Barn", skin);
        TextButton buildCoopButton = new TextButton("Build Coop", skin);

        buildBarnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showPlacementMap(AreaType.BARN);
            }
        });

        buildCoopButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showPlacementMap(AreaType.COOP);
            }
        });

        dynamicContentTable.add(buildBarnButton).width(250).height(60).padBottom(PADDING).row();
        dynamicContentTable.add(buildCoopButton).width(250).height(60).padBottom(PADDING).row();
    }

    private void showPlacementMap(AreaType buildingType) {
        dynamicContentTable.clear();

        com.badlogic.gdx.scenes.scene2d.ui.Label instructionLabel = new Label("Click a tile to place the " + buildingType.name().toLowerCase(), skin);
        dynamicContentTable.add(instructionLabel).padBottom(PADDING).row();

        Actor mapActor = new MapClickActor(buildingType);
        dynamicContentTable.add(mapActor).expand().fill().row();

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createInitialMenu();
            }
        });

        dynamicContentTable.add(backButton).padTop(PADDING).row();
    }

    private class MapClickActor extends Actor {
        private final float scale = 0.4f;
        private final float tileSideLength = 16f;
        Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();

        public MapClickActor(AreaType buildingType) {
            addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    int tileX = (int)(x / (tileSideLength * scale));
                    int tileY = (int)(y / (tileSideLength * scale));
                    handleMessage(tileX, tileY, buildingType, currentPlayer.getFarm());
                    return true;
                }
            });
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            Farm playersFarm = currentPlayer.getFarm();
            for (int row = 0; row < Farm.ROWS; row++) {
                for (int col = 0; col < Farm.COLS; col++) {
                    Tile tile = playersFarm.getTile(row, col);
                    float x = getX() + col * tileSideLength * scale;
                    float y = getY() + row * tileSideLength * scale;
                    float size = tileSideLength * scale;

                    if (tile.getAreaSprite() != null) {
                        batch.draw(tile.getAreaSprite(), x, y, size, size);
                    }

                    drawArea(batch, tile);

                    if (tile.getObjectSprite() != null) {
                        batch.draw(tile.getObjectSprite(), x, y, size, size);
                    }
                }
            }
        }

        @Override
        public float getWidth() {
            return Farm.COLS * tileSideLength * scale;
        }

        @Override
        public float getHeight() {
            return Farm.ROWS * tileSideLength * scale;
        }

        public void drawArea(Batch batch, Tile tile) {
            Area area = tile.getArea();
            float x = getX() + area.getBottomLeftCorner().x * tileSideLength * scale;
            float y = getY() + area.getBottomLeftCorner().y * tileSideLength * scale;
            float sizeX = area.getWidth() * tileSideLength * scale;
            float sizeY = area.getHeight() * tileSideLength * scale;
            if (area.getTexture() != null) {
                batch.draw(area.getTexture(), x, y, sizeX, sizeY);
            }
        }
    }

    private void handleMessage(int x, int y, AreaType type, Farm farm) {
        Result res;
        if(type.equals(AreaType.BARN)) {
            res = GameController.buildBarn(x, y, farm);
        }
        else {
            res = GameController.buildCoop(x, y, farm);
        }

        if (res.success()) {
            messageLabel.setColor(com.badlogic.gdx.graphics.Color.GREEN);
        } else {
            messageLabel.setColor(Color.RED);
        }
        messageLabel.setText(res.message());
    }
}

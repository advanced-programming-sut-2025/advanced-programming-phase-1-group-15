package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.controllers.GameController;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.Result;
import com.example.models.map.*;
import com.example.models.stores.CarpenterShop;

public class CarpenterShopMenu extends PopUpMenu {
    private static final float WIDTH = 800f;
    private static final float HEIGHT = 800f;
    private static final float PADDING = 10f;

    private final CarpenterShop store;
    private Table dynamicContentTable;
    private Label messageLabel;

    public CarpenterShopMenu(Skin skin, String title, Runnable onHideCallback, CarpenterShop store) {
        super(skin, title, WIDTH, HEIGHT, onHideCallback);
        this.store = store;
    }

    @Override
    protected void populate(Window w) {
        dynamicContentTable = new Table();
        dynamicContentTable.pad(PADDING);
        dynamicContentTable.top();

        createInitialMenu();

        w.add(dynamicContentTable).expand().fill().row();

        messageLabel = new Label("", skin);
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

        Label instructionLabel = new Label("Click a tile to place the " + buildingType.name().toLowerCase(), skin);
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
        Player currentPlayer = App.currentGame.getCurrentPlayer();

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
            com.example.models.map.Area area = tile.getArea();
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
            messageLabel.setColor(Color.GREEN);
            messageLabel.setText(res.message());
        } else {
            messageLabel.setColor(Color.RED);
            messageLabel.setText(res.message());
        }
    }
}

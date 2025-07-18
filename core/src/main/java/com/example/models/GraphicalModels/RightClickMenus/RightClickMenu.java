package com.example.models.GraphicalModels.RightClickMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class RightClickMenu {
    protected final Skin skin;
    protected final Stage stage;
    protected final Table menuTable;
    protected boolean isVisible = false;
    private Actor backgroundDim;
    private final Runnable onHideCallback;

    public RightClickMenu(Skin skin, Runnable onHideCallback) {
        this.skin = skin;
        this.onHideCallback = onHideCallback;
        this.stage = new Stage(new ScreenViewport());
        this.menuTable = new Table(skin);

        this.menuTable.setBackground(skin.getDrawable("default-round"));
        this.menuTable.pad(10);

        createBackgroundDim();

        this.stage.addActor(backgroundDim);
        this.stage.addActor(menuTable);

        menuTable.setVisible(false);
        backgroundDim.setVisible(false);

        setupMenuContent();
    }

    protected abstract void setupMenuContent();

    private void createBackgroundDim() {
        backgroundDim = new Actor() {
            @Override
            public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float parentAlpha) {
                batch.setColor(0, 0, 0, 0.3f);
                batch.draw(skin.getRegion("white"), 0, 0,
                        Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                batch.setColor(Color.WHITE);
            }
        };
        backgroundDim.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundDim.setVisible(false);

        backgroundDim.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
    }

    public void show(float screenX, float screenY) {
        if (isVisible) return;

        isVisible = true;

        menuTable.pack();
        positionMenu(screenX, screenY);

        backgroundDim.setVisible(true);
        menuTable.setVisible(true);
        menuTable.getColor().a = 0f;
        menuTable.addAction(Actions.fadeIn(0.2f));
    }

    private void positionMenu(float screenX, float screenY) {
        float menuWidth = menuTable.getWidth();
        float menuHeight = menuTable.getHeight();
        float stageWidth = stage.getWidth();
        float stageHeight = stage.getHeight();

        if (screenX + menuWidth > stageWidth) {
            screenX = stageWidth - menuWidth;
        }
        if (screenX < 0) {
            screenX = 0;
        }

        if (screenY + menuHeight > stageHeight) {
            screenY = stageHeight - menuHeight;
        }
        if (screenY < 0) {
            screenY = 0;
        }

        menuTable.setPosition(screenX, screenY);
    }

    public void hide() {
        if (!isVisible) return;

        isVisible = false;
        menuTable.setVisible(false);
        backgroundDim.setVisible(false);

        if (onHideCallback != null) {
            onHideCallback.run();
        }
    }

    public void render(float delta) {
        if (isVisible) {
            stage.act(delta);
            stage.draw();
        }
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        backgroundDim.setBounds(0, 0, width, height);
    }

    public void dispose() {
        stage.dispose();
    }

    public boolean isVisible() {
        return isVisible;
    }

    public Stage getStage() {
        return stage;
    }
}

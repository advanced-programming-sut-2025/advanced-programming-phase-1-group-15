package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class PopUpMenu {
    protected Stage stage;
    protected Window window;
    protected Skin skin;
    private Actor backgroundDim;
    private boolean isVisible = false;
    private InputProcessor previousInputProcessor;

    private final Runnable onHideCallback;

    public PopUpMenu(Skin skin, String title, float width, float height, Runnable onHideCallback) {
        this.skin = skin;

        this.onHideCallback = onHideCallback;
        window = new Window(title, skin);
        window.setSize(width, height);
        window.setModal(true);
        window.setMovable(false);
        window.setResizable(false);

        centerWindow();

        addCloseButton();

        stage = new Stage(new ScreenViewport());

        createBackgroundDim();

        window.setVisible(false);
        stage.addActor(backgroundDim);
        stage.addActor(window);
    }

    protected abstract void populate(Window w);

    private void addCloseButton() {
        TextButton closeButton = new TextButton("X", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });

        window.getTitleTable().add(closeButton).padRight(5).size(20, 20);
    }

    private void createBackgroundDim() {
        backgroundDim = new Actor() {
            @Override
            public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float parentAlpha) {
                batch.setColor(0, 0, 0, 0.5f);
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



    private void centerWindow() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        window.setPosition(
            (screenWidth - window.getWidth()) / 2,
            (screenHeight - window.getHeight()) / 2
        );

    }



    public void show() {
        if (isVisible) return;

        populate(window);

        isVisible = true;

        previousInputProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(stage);

        backgroundDim.setVisible(true);

        window.setVisible(true);
        window.getColor().a = 0f;
        window.addAction(Actions.fadeIn(0.3f));

    }



    public void hide() {
        if (!isVisible) return;

        isVisible = false;

        window.setVisible(false);
        backgroundDim.setVisible(false);

        if (previousInputProcessor != null) {
            Gdx.input.setInputProcessor(previousInputProcessor);
            previousInputProcessor = null;
        }

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
        centerWindow();
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

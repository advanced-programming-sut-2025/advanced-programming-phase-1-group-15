package com.example.client.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.example.client.Main;
import com.example.client.NetworkClient;
import com.example.client.controllers.ClientGameController;
import com.example.client.controllers.ClientGameListener;
import com.example.common.Game;
import com.example.common.cooking.Food;
import com.example.common.cooking.FoodType;
import com.example.common.tools.BackPackable;
import com.example.common.tools.Fridge;

public class ReActMenu {
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));
    private boolean visible ;
    private final Table rootTable;
    private final Table recipe;
    private final Table fridge;
    private final Main main;
    private final Game game;
    private final Runnable onHideCallback;
    private final Label tooltipLabel = new Label("", skin);
    private final Container<Label> tooltipContainer = new Container<>(tooltipLabel);
    private Label errorLabel = new Label("",skin);
    public ReActMenu(Main main, Game game, Runnable onHideCallback) {
        game.getCurrentPlayer().getInventory().addToBackPack(new Food(FoodType.SALMON_DINNER) , 3);
        this.main = main;
        this.game = game;
        this.onHideCallback = onHideCallback;
        this.stage = new Stage(new ScreenViewport(), main.getBatch());
        rootTable = new Table(skin);
        rootTable.setFillParent(false);
        rootTable.setSize(800, 700);
        rootTable.setPosition(Gdx.graphics.getWidth() / 2f - 400, Gdx.graphics.getHeight() / 2f - 350);
        rootTable.setVisible(false);
        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));
        Table tabBar = new Table(skin);
        TextButton recipeTab = new TextButton("Message", skin);
        TextButton fridgeTab = new TextButton("Emoji", skin);
        tabBar.add(recipeTab).pad(5);
        tabBar.add(fridgeTab).pad(5);
        rootTable.add(tabBar).expandX().top().padTop(10).row();
        recipe = createCookingContent();
        fridge = createFridgeContent();
        Stack stack = new Stack();
        stack.add(recipe);
        stack.add(fridge);
        changeTab(recipe);
        rootTable.add(stack).expand().fill().row();
        TextButton closeButton = new TextButton("X Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setVisible(false , 0);
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
        recipeTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(recipe);
            }
        });
        fridgeTab.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(fridge);
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
    private Table createCookingContent() {
        Label titleLabel = new Label("Message: ", skin); titleLabel.setColor(Color.FIREBRICK);
        Table table = new Table(skin);
        TextButton sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        return table;
    }
    private Table createFridgeContent() {
        Table table = new Table(skin);
        Label titleLabel = new Label("Emoji: ", skin);
        titleLabel.setColor(Color.FIREBRICK);
        table.add(titleLabel).left();

        HorizontalGroup emojiGroup = new HorizontalGroup();
        emojiGroup.space(10);

        final String[] selectedEmoji = {null};

        ImageButton angryButton = new ImageButton(new SpriteDrawable(GameAssetManager.angry));
        angryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedEmoji[0] = "angry";
                highlightSelected(angryButton, emojiGroup);
            }
        });
        emojiGroup.addActor(angryButton);

        ImageButton cryButton = new ImageButton(new SpriteDrawable(GameAssetManager.cry));
        angryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedEmoji[0] = "cry";
                highlightSelected(cryButton, emojiGroup);
            }
        });
        emojiGroup.addActor(cryButton);

        ImageButton laughButton = new ImageButton(new SpriteDrawable(GameAssetManager.laugh));
        angryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedEmoji[0] = "laugh";
                highlightSelected(laughButton, emojiGroup);
            }
        });
        emojiGroup.addActor(laughButton);

        ImageButton loveButton = new ImageButton(new SpriteDrawable(GameAssetManager.love));
        angryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedEmoji[0] = "love";
                highlightSelected(loveButton, emojiGroup);
            }
        });
        emojiGroup.addActor(loveButton);

        ImageButton partyButton = new ImageButton(new SpriteDrawable(GameAssetManager.party));
        angryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedEmoji[0] = "party";
                highlightSelected(partyButton, emojiGroup);
            }
        });
        emojiGroup.addActor(partyButton);

        ImageButton smileButton = new ImageButton(new SpriteDrawable(GameAssetManager.smile));
        angryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedEmoji[0] = "smile";
                highlightSelected(smileButton, emojiGroup);
            }
        });
        emojiGroup.addActor(smileButton);

        ImageButton thumbsButton = new ImageButton(new SpriteDrawable(GameAssetManager.thumbs_up));
        angryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedEmoji[0] = "thumbs_up";
                highlightSelected(thumbsButton, emojiGroup);
            }
        });
        emojiGroup.addActor(thumbsButton);

        table.row().padTop(10);
        table.add(emojiGroup).colspan(2);

        TextButton sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedEmoji[0] == null) {
                    showError("No emoji selected!" , errorLabel);
                    return;
                }
                showError("Sent emoji: " + selectedEmoji[0] , errorLabel);
            }
        });
        table.row().padTop(10);
        table.add(sendButton).left();

        return table;
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
                    changeTab(recipe);
                    break;
                case 2:
                    changeTab(fridge);
                    break;
                default:
                    break;
            }
        }
    }

    public Stage getStage() {
        return stage;
    }
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    private void refresh() {
        recipe.clear(); fridge.clear();
        recipe.add(createCookingContent()).expand().fill();
        fridge.add(createFridgeContent()).expand().fill();
    }
    private void changeTab(Table content) {
        recipe.setVisible(false);
        fridge.setVisible(false);
        content.setVisible(true);
    }
    private void highlightSelected(ImageButton selected, HorizontalGroup group) {
        for (Actor actor : group.getChildren()) {
            actor.setColor(Color.WHITE);
        }
        selected.setColor(Color.LIGHT_GRAY);
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

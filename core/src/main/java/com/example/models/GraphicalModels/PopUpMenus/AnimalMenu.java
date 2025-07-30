package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.example.controllers.GameController;
import com.example.models.Result;
import com.example.models.animals.Animal;
import com.example.views.GameAssetManager;

public class AnimalMenu extends PopUpMenu {
    private static final float WIDTH = 600f;
    private static final float HEIGHT = 600f;
    private static final float PADDING = 10f;

    private final Animal animal;
    private Table contentTable;
    private Label messageLabel;

    public AnimalMenu(Skin skin, String title, Runnable onHideCallback, Animal animal) {
        super(skin, title, WIDTH, HEIGHT, onHideCallback);
        this.animal = animal;
    }

    @Override
    protected void populate(Window w) {
        contentTable = new Table();
        contentTable.pad(PADDING);
        contentTable.top();

        createInitialMenu();
        w.add(contentTable).row();

        messageLabel = new Label("", skin);
        w.add(messageLabel).colspan(3).padTop(PADDING).row();
    }

    public void createInitialMenu() {
        contentTable.clear();
        Image animalImage = new Image(animal.getSprite());
        animalImage.setSize(64, 64);
        contentTable.add(animalImage).size(64, 64).colspan(3).padBottom(PADDING).row();

        contentTable.add(new Label("Name:", skin));
        TextField nameField = new TextField(animal.getName(), skin);
        contentTable.add(nameField);

        TextButton editButton = new TextButton("Edit", skin);
        contentTable.add(editButton).padBottom(PADDING).row();
        editButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                animal.setName(nameField.getText());
                messageLabel.setColor(Color.GREEN);
                messageLabel.setText("Animal's name changed to " + animal.getName() + "!");
            }
        });

        Table friendshipTable = new Table();
        friendshipTable.add(new Label("Friendship:", skin)).padRight(PADDING);
        Label friendshipLabel = new Label("" + animal.getFriendship(), skin);
        friendshipTable.add(friendshipLabel).padRight(PADDING);
        final Image heartIcon = new Image(GameAssetManager.heart);
        heartIcon.setSize(32, 32);
        heartIcon.setOrigin(Align.center);
        friendshipTable.add(heartIcon);
        contentTable.add(friendshipTable).colspan(3).padBottom(PADDING).row();

        TextButton petButton = new TextButton("Pet", skin);
        petButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Result res = GameController.petAnimal(animal);
                if (res.success()) {
                    friendshipLabel.setText("" + animal.getFriendship());
                    messageLabel.setColor(Color.GREEN);
                    heartIcon.clearActions();
                    heartIcon.addAction(
                        Actions.sequence(
                            Actions.repeat(6, Actions.sequence(
                                Actions.scaleTo(1.2f, 1.2f, 0.15f),
                                Actions.scaleTo(1f, 1f, 0.15f)
                            ))
                        )
                    );
                } else {
                    messageLabel.setColor(Color.RED);
                }
                messageLabel.setText(res.getMessage());
            }
        });

        TextButton feedButton = new TextButton("Feed", skin);
        feedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Result res = GameController.feedHayAnimal(animal);

                if (res.success()) {
                    hide();
                } else {
                    messageLabel.setColor(Color.RED);
                }
                messageLabel.setText(res.getMessage());
            }
        });

        TextButton shepherdButton = new TextButton("Shepherd", skin);

        contentTable.add(petButton).width(150);
        contentTable.add(feedButton).width(150);
        contentTable.add(shepherdButton).width(150).padBottom(PADDING).row();

        Table bottomButtons = new Table();
        TextButton productsButton = new TextButton("Products", skin);
        productsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createProductContent();
            }
        });

        TextButton sellButton = new TextButton("Sell", skin);
        sellButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Result res = GameController.sellAnimal(animal);

                if (res.success()) {
                    messageLabel.setColor(Color.GREEN);
                    sellButton.setDisabled(true);
                } else {
                    messageLabel.setColor(Color.RED);
                }
                messageLabel.setText(res.getMessage());
            }
        });

        bottomButtons.add(productsButton).width(150);
        bottomButtons.add(sellButton).width(150).row();
        contentTable.add(bottomButtons).colspan(3).padBottom(PADDING).row();
    }

    public void createProductContent() {
        contentTable.clear();
        Image animalImage = new Image(animal.getSprite());
        animalImage.setSize(64, 64);
        contentTable.add(animalImage).size(64, 64).padBottom(PADDING).row();

        contentTable.add(new Label("Today Products:", skin)).padBottom(PADDING).row();
        if(animal.getCurrentProduct() == null) {
            contentTable.add(new Label("There is nothing to collect for now!", skin)).padBottom(PADDING).row();
        }
        else {
            Table productTable = new Table();

            Image productIcon = new Image(animal.getCurrentProduct().getSprite());
            productIcon.setSize(32, 32);
            productTable.add(productIcon).size(32, 32).padRight(PADDING);

            productTable.add(new Label("1 " + animal.getCurrentProduct().getName(), skin)).padRight(PADDING);
            Label qualityLabel = new Label("Quality: " + animal.getCurrentProduct().getProductQuality(), skin);
            switch (animal.getCurrentProduct().getProductQuality()) {
                case NORMAL -> qualityLabel.setColor(Color.BLACK);
                case SILVER -> qualityLabel.setColor(Color.DARK_GRAY);
                case GOLD -> qualityLabel.setColor(Color.GOLD);
                case IRIDIUM -> qualityLabel.setColor(Color.MAGENTA);
            }
            productTable.add(qualityLabel);

            contentTable.add(productTable).padBottom(PADDING).row();

            TextButton collectButton = new TextButton("Collect", skin);
            collectButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Result res = GameController.collectProduce(animal);
                    if (res.success()) {
                        messageLabel.setColor(Color.GREEN);
                    } else {
                        messageLabel.setColor(Color.RED);
                    }
                    messageLabel.setText(res.getMessage());
                }
            });
            contentTable.add(collectButton).padTop(PADDING).row();
        }

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createInitialMenu();
            }
        });

        contentTable.add(backButton).padTop(PADDING).row();
    }
}

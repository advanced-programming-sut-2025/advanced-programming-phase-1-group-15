package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.animals.AnimalType;
import com.example.models.stores.MarnieRanchItems;
import com.example.models.tools.MilkPail;
import com.example.models.tools.Shear;
import com.example.models.tools.ToolType;
import com.example.views.GameAssetManager;

import java.util.ArrayList;

public class MarnieRanchMenu extends PopUpMenu {
    private static final float WIDTH = 600f;
    private static final float HEIGHT = 800f;
    private static final float PADDING = 10f;

    private final ArrayList<Image> sprites = new ArrayList<>();
    private final ArrayList<Label> nameLabels = new ArrayList<>();
    private final ArrayList<Label> priceLabels = new ArrayList<>();
    private final ArrayList<TextButton> buyButtons = new ArrayList<>();
    private Label messageLabel;

    public MarnieRanchMenu(Skin skin, String title, Runnable onHideCallback) {
        super(skin, title, WIDTH, HEIGHT, onHideCallback);

        Player currentPlayer = App.currentGame.getCurrentPlayer();

        for(MarnieRanchItems item : MarnieRanchItems.values()) {
            Sprite toolSprite = switch (item.toolType) {
                case SHEAR -> GameAssetManager.shear;
                default -> GameAssetManager.milk_pail;
            };
            toolSprite.setSize(48, 48);
            Image toolIcon = new Image(new TextureRegionDrawable(toolSprite));
            sprites.add(toolIcon);

            Label nameLabel = new Label(item.toolType.name().toLowerCase(), skin);
            nameLabels.add(nameLabel);

            Label priceLabel = new Label("price: " + item.price + "G", skin);
            priceLabel.setAlignment(Align.left);
            priceLabels.add(priceLabel);

            TextButton buyButton = new TextButton("BUY", skin);
            if(item.price <= currentPlayer.getGold()) {
                buyButton.setColor(Color.GREEN);
            }
            else {
                buyButton.setColor(Color.RED);
            }
            buyButtons.add(buyButton);
        }

        for(AnimalType animalType : AnimalType.values()) {
            TextureRegion animalTexture = switch (animalType) {
                case COW -> GameAssetManager.cow_walking_down.getKeyFrame(0);
                case GOAT -> GameAssetManager.goat_walking_down.getKeyFrame(0);
                case PIG -> GameAssetManager.pig_walking_down.getKeyFrame(0);
                case SHEEP -> GameAssetManager.sheep_walking_down.getKeyFrame(0);
                case CHICKEN -> GameAssetManager.chicken_walking_down.getKeyFrame(0);
                case DUCK -> GameAssetManager.duck_walking_down.getKeyFrame(0);
                case RABBIT -> GameAssetManager.rabbit_walking_down.getKeyFrame(0);
                case DINOSAUR -> GameAssetManager.dinosaur_walking_down.getKeyFrame(0);
            };

            animalTexture.setRegionWidth(48);
            animalTexture.setRegionHeight(48);
            Image animalIcon = new Image(new TextureRegionDrawable(animalTexture));
            sprites.add(animalIcon);

            Label nameLabel = new Label(animalType.name().toLowerCase(), skin);
            nameLabels.add(nameLabel);

            Label priceLabel = new Label("price: " + animalType.price + "G", skin);
            priceLabel.setAlignment(Align.left);
            priceLabels.add(priceLabel);

            TextButton buyButton = new TextButton("BUY", skin);
            if(animalType.price <= currentPlayer.getGold()) {
                buyButton.setColor(Color.GREEN);
            }
            else {
                buyButton.setColor(Color.RED);
            }
            buyButtons.add(buyButton);
        }
    }

    @Override
    protected void populate(Window w) {
        Player currentPlayer = App.currentGame.getCurrentPlayer();
        Table contentTable = new Table();
        contentTable.pad(PADDING);
        contentTable.top();

        Label header1Label = new Label("Tools:", skin);
        contentTable.add(header1Label).colspan(4).padBottom(PADDING).row();
        int i = 0;
        for (MarnieRanchItems item : MarnieRanchItems.values()) {
            Table toolRow = new Table();
            toolRow.left().padBottom(10);

            toolRow.add(sprites.get(i)).size(48, 48).left().padRight(PADDING);
            toolRow.add(nameLabels.get(i)).width(200).padRight(PADDING);
            toolRow.add(priceLabels.get(i)).width(200).left().padRight(PADDING);

            buyButtons.get(i).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(item.price <= currentPlayer.getGold()) {
                        messageLabel.setColor(Color.GREEN);
                        messageLabel.setText("you've bought 1 " + item.getName() + " with price " + item.price + ".");

                        if(item.toolType == ToolType.MILK_PAIL) {
                            currentPlayer.getInventory().addToBackPack(new MilkPail(), 1);
                        }
                        else {
                            currentPlayer.getInventory().addToBackPack(new Shear(), 1);
                        }
                    }
                    else {
                        messageLabel.setColor(Color.RED);
                        messageLabel.setText("not enough gold to buy 1 " + item.getName());
                    }
                }
            });

            toolRow.add(buyButtons.get(i)).width(150).height(60).right();

            contentTable.add(toolRow).expandX().fillX().center().padBottom(5).row();
            i++;
        }

        Label header2Label = new Label("Animals:", skin);
        contentTable.add(header2Label).colspan(4).padBottom(PADDING).row();
        for (AnimalType animalType : AnimalType.values()) {
            Table animalRow = new Table();
            animalRow.left().padBottom(10);

            animalRow.add(sprites.get(i)).size(48, 48).left().padRight(PADDING);
            animalRow.add(nameLabels.get(i)).width(200).padRight(PADDING);
            animalRow.add(priceLabels.get(i)).width(200).left().padRight(PADDING);

            buyButtons.get(i).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(animalType.price <= currentPlayer.getGold()) {
                        messageLabel.setColor(Color.GREEN);
                        messageLabel.setText("you've bought 1 " + animalType.name().toLowerCase() + " with price " + animalType.price + ".");
                    }
                    else {
                        messageLabel.setColor(Color.RED);
                        messageLabel.setText("not enough gold to buy 1 " + animalType.name().toLowerCase());
                    }
                }
            });

            animalRow.add(buyButtons.get(i)).width(150).height(60).right();

            contentTable.add(animalRow).expandX().fillX().center().padBottom(5).row();
            i++;
        }

        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(false, true);

        w.add(scrollPane).expand().fill().row();

        messageLabel = new Label("", skin);
        w.add(messageLabel).colspan(4).padTop(PADDING).row();
    }
}

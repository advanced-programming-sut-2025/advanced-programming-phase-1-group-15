package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.npcs.NPC;
import com.example.models.npcs.NPCFriendShip;
import com.example.models.tools.BackPackable;
import com.example.models.tools.Tool;

import java.util.HashMap;
import java.util.Map;

public class GiftSelectionMenu extends PopUpMenu {
    private final NPC targetNPC;
    private final Player currentPlayer;
    private ScrollPane scrollPane;
    private Table inventoryTable;
    private Label statusLabel;

    private static final float DEFAULT_WIDTH = 500;
    private static final float DEFAULT_HEIGHT = 400;
    private static final float PADDING = 15f;

    public GiftSelectionMenu(Skin skin, String title, Runnable onHideCallback, NPC targetNPC) {
        super(skin, title, DEFAULT_WIDTH, DEFAULT_HEIGHT, onHideCallback);
        this.targetNPC = targetNPC;
        this.currentPlayer = App.currentGame.getCurrentPlayer();
    }

    @Override
    protected void populate(Window w) {
        Table mainContentTable = new Table(skin);
        mainContentTable.pad(PADDING);
        mainContentTable.top();

        Table headerTable = new Table();
        Label titleLabel = new Label("Select an item to gift to " + targetNPC.getName(), skin);
        titleLabel.setFontScale(1.1f);
        headerTable.add(titleLabel).pad(5).row();

        NPCFriendShip friendship = targetNPC.getFriendships().get(currentPlayer);
        if (friendship != null && friendship.hasGiftedToday()) {
            Label giftedLabel = new Label("⚠️ Already gave gift today", skin);
            giftedLabel.setColor(Color.ORANGE);
            headerTable.add(giftedLabel).pad(2).row();
        }

        statusLabel = new Label("", skin);
        statusLabel.setColor(Color.LIGHT_GRAY);
        headerTable.add(statusLabel).pad(2);

        mainContentTable.add(headerTable).pad(10).row();

        if (inventoryTable == null) {
            inventoryTable = new Table(skin);
            inventoryTable.top();
        } else {
            inventoryTable.clear();
        }

        if (scrollPane == null) {
            scrollPane = new ScrollPane(inventoryTable, skin);
            scrollPane.setFadeScrollBars(false);
            scrollPane.setScrollingDisabled(true, false);
        }

        mainContentTable.add(scrollPane)
            .width(DEFAULT_WIDTH - (PADDING * 2))
            .height(DEFAULT_HEIGHT - 150 - (PADDING * 2))
            .pad(10).row();

        Table buttonTable = new Table();
        TextButton refreshButton = new TextButton("Refresh", skin);
        TextButton closeButton = new TextButton("Close", skin);

        refreshButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                populateInventory();
            }
        });

        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });

        buttonTable.add(refreshButton).pad(5);
        buttonTable.add(closeButton).pad(5);
        mainContentTable.add(buttonTable).pad(10);

        w.add(mainContentTable).expand().fill().row();

        populateInventory();
    }

    private void populateInventory() {
        inventoryTable.clear();

        HashMap<BackPackable, Integer> inventory = currentPlayer.getInventory().getItems();

        if (inventory.isEmpty()) {
            Table emptyTable = new Table();
            Label emptyLabel = new Label("📦 Your inventory is empty!", skin);
            emptyLabel.setColor(Color.LIGHT_GRAY);
            emptyTable.add(emptyLabel).pad(20);
            inventoryTable.add(emptyTable).expand().center().row();
            statusLabel.setText("No items available to gift");
            return;
        }

        int giftableItems = 0;
        for (Map.Entry<BackPackable, Integer> entry : inventory.entrySet()) {
            BackPackable item = entry.getKey();
            int quantity = entry.getValue();

            if (item instanceof Tool) {
                continue;
            }

            giftableItems++;

            Table itemRow = new Table(skin);
            itemRow.pad(8);

            Table itemInfoTable = new Table();
            Label itemNameLabel = new Label(item.getName(), skin);
            itemNameLabel.setFontScale(1.0f);

            Label quantityLabel = new Label("Qty: " + quantity, skin);
            quantityLabel.setColor(Color.LIGHT_GRAY);
            quantityLabel.setFontScale(0.9f);

            itemInfoTable.add(itemNameLabel).left().row();
            itemInfoTable.add(quantityLabel).left();

            itemRow.add(itemInfoTable).expandX().left().pad(5);

            boolean isFavorite = targetNPC.getFavourites().contains(item);
            if (isFavorite) {
                Label favoriteLabel = new Label("⭐ Favorite!", skin);
                favoriteLabel.setColor(Color.GOLD);
                favoriteLabel.setFontScale(0.8f);
                itemRow.add(favoriteLabel).pad(5);
            }

            TextButton giftButton = new TextButton("🎁 Gift", skin);
            giftButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    giftItem(item);
                }
            });

            itemRow.add(giftButton).pad(5);

            inventoryTable.add(itemRow).fillX().pad(2).row();

            if (giftableItems < inventory.size() - countTools(inventory)) {
                Label separator = new Label("─────────────────────", skin);
                separator.setColor(Color.GRAY);
                separator.setFontScale(0.5f);
                inventoryTable.add(separator).pad(1).row();
            }
        }

        if (giftableItems == 0) {
            Table noGiftableTable = new Table();
            Label noGiftableLabel = new Label("Only tools in inventory\n(Tools cannot be gifted)", skin);
            noGiftableLabel.setColor(Color.ORANGE);
            noGiftableLabel.setAlignment(1); // CENTER
            noGiftableTable.add(noGiftableLabel).pad(20);
            inventoryTable.add(noGiftableTable).expand().center().row();
            statusLabel.setText("No giftable items available");
        } else {
            statusLabel.setText(giftableItems + " items available to gift");
        }
    }

    private int countTools(HashMap<BackPackable, Integer> inventory) {
        int toolCount = 0;
        for (BackPackable item : inventory.keySet()) {
            if (item instanceof Tool) {
                toolCount++;
            }
        }
        return toolCount;
    }

    private void giftItem(BackPackable item) {
        // Remove item from player inventory first
        currentPlayer.getInventory().removeCountFromBackPack(item, 1);

        // Give gift to NPC
        String result = targetNPC.gift(currentPlayer, item);

        showResultDialog(result);
        populateInventory(); // Refresh the inventory display
    }

    private void showResultDialog(String message) {
        Dialog resultDialog = new Dialog("🎁 Gift Result", skin) {
            @Override
            protected void result(Object object) {
                remove();
            }
        };

        Table contentTable = new Table();
        Label messageLabel = new Label(message, skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(1); // CENTER

        contentTable.add(messageLabel).width(300).pad(20);

        resultDialog.getContentTable().add(contentTable);
        resultDialog.button("OK", true);
        resultDialog.show(stage);
    }

    @Override
    public void show() {
        super.show();
        if (scrollPane != null) {
            stage.setScrollFocus(scrollPane);
        }
    }
}

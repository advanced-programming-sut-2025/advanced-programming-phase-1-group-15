package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.npcs.NPC;
import com.example.models.tools.BackPackable;
import com.example.models.tools.Tool;

import java.util.HashMap;
import java.util.Map;

public class GiftSelectionMenu extends PopUpMenu {
    private final NPC targetNPC;
    private final Player currentPlayer;
    private ScrollPane scrollPane;
    private Table inventoryTable;

    private static final float DEFAULT_WIDTH = 450;
    private static final float DEFAULT_HEIGHT = 350;
    private static final float PADDING = 10f;

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

        mainContentTable.add(new Label("Select an item to gift to " + targetNPC.getName(), skin)).pad(10).row();
        mainContentTable.add(scrollPane).width(DEFAULT_WIDTH - (PADDING * 2)).height(DEFAULT_HEIGHT - 100 - (PADDING * 2)).pad(10).row();

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        mainContentTable.add(closeButton).pad(10);

        w.add(mainContentTable).expand().fill().row();

        populateInventory();
    }

    private void populateInventory() {
        inventoryTable.clear();

        HashMap<BackPackable, Integer> inventory = currentPlayer.getInventory().getItems();

        if (inventory.isEmpty()) {
            inventoryTable.add(new Label("Your inventory is empty!", skin)).pad(5).row();
            return;
        }

        for (Map.Entry<BackPackable, Integer> entry : inventory.entrySet()) {
            BackPackable item = entry.getKey();
            int quantity = entry.getValue();

            if (item instanceof Tool) {
                continue;
            }

            Table itemRow = new Table(skin);
            itemRow.add(new Label(item.getName() + " (" + quantity + ")", skin)).expandX().left().pad(5);

            TextButton giftButton = new TextButton("Gift", skin);
            giftButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    giftItem(item);
                }
            });

            itemRow.add(giftButton).pad(5);
            inventoryTable.add(itemRow).fillX().pad(2).row();
        }
    }

    private void giftItem(BackPackable item) {
        String result = targetNPC.gift(currentPlayer, item);
        showResultDialog(result);
        populateInventory();
    }

    private void showResultDialog(String message) {
        Dialog resultDialog = new Dialog("Gift Result", skin);
        resultDialog.text(message);
        resultDialog.button("OK");
        resultDialog.show(stage);
    }
}

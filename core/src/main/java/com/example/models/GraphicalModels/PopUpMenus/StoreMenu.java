package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.stores.Store;
import java.util.List;

public class StoreMenu extends PopUpMenu {
    private static final float WIDTH = 700f;
    private static final float HEIGHT = 800f;
    private static final float PADDING = 10f;

    private final Store store;
    private final Player player;
    private final Label messageLabel;
    private final Table contentTable;

    public StoreMenu(Skin skin, Store store, Runnable onHideCallback) {
        super(skin, store.getClass().getSimpleName(), WIDTH, HEIGHT, onHideCallback);
        this.store = store;
        this.player = App.currentGame.getCurrentPlayer();

        contentTable = new Table();
        contentTable.pad(PADDING);
        contentTable.top();

        messageLabel = new Label("", skin);
    }

    @Override
    protected void populate(Window w) {
        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(false, true);

        w.add(scrollPane).expand().fill().row();
        w.add(messageLabel).padTop(PADDING).row();
    }

    private void populateMenu() {
        contentTable.add(new Label("Available Items:", skin)).colspan(3).padBottom(PADDING).row();

        String[] items = store.displayAvailableItems().split("\\n");
        for (String line : items) {
            if (line.trim().isEmpty()) continue;

            final String[] parts = line.split("\\t|");
            if (parts.length < 2) continue;

            final String productName = parts[0].trim();
            final String description = parts.length > 1 ? parts[1].trim() : "";

            Label nameLabel = new Label(productName, skin);
            Label descLabel = new Label(description, skin);
            TextButton buyButton = new TextButton("Buy", skin);

            buyButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    String result = store.sell(player, productName, 1);
                    messageLabel.setText(result);
                    messageLabel.setColor(result.toLowerCase().contains("not enough") ? Color.RED : Color.GREEN);
                }
            });

            contentTable.add(nameLabel).width(200).padRight(PADDING);
            contentTable.add(descLabel).width(300).padRight(PADDING);
            contentTable.add(buyButton).width(100).height(40).row();
        }
    }
}

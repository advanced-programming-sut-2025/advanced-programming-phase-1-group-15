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
import com.example.models.stores.CarpenterShop;
import com.example.models.stores.MarnieRanchItems;
import com.example.models.tools.MilkPail;
import com.example.models.tools.Shear;
import com.example.models.tools.ToolType;
import com.example.views.GameAssetManager;

import java.util.ArrayList;

public class CarpenterShopMenu extends PopUpMenu {
    private static final float WIDTH = 700f;
    private static final float HEIGHT = 800f;
    private static final float PADDING = 10f;

    private final CarpenterShop store;
    private Label messageLabel;

    public CarpenterShopMenu(Skin skin, String title, Runnable onHideCallback, CarpenterShop store) {
        super(skin, title, WIDTH, HEIGHT, onHideCallback);
        this.store = store;
    }

    @Override
    protected void populate(Window w) {
        Player currentPlayer = App.currentGame.getCurrentPlayer();
        Table contentTable = new Table();
        contentTable.pad(PADDING);
        contentTable.top();

        messageLabel = new Label("", skin);
        w.add(messageLabel).colspan(4).padTop(PADDING).row();
    }
}

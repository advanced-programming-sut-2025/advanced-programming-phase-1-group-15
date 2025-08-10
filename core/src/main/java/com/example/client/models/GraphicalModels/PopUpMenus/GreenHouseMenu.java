package com.example.client.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.example.client.controllers.ClientGameController;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.map.GreenHouse;

public class GreenHouseMenu extends PopUpMenu {
    private static final float WIDTH = 800f;
    private static final float HEIGHT = 400f;
    private static final float PADDING = 10f;

    private final Label label;
    private final Player player;
    private TextButton repairButton;
    private Label messageLabel;

    public GreenHouseMenu(Skin skin, String title, Runnable onHideCallback) {
        super(skin, title, WIDTH, HEIGHT, onHideCallback);

        label = new Label("""
                To repair your GREENHOUSE you need to have at least:\s
                * 500 wood\s
                * 1000 gold
                """, skin);
        label.setAlignment(Align.left);
        player = ClientApp.currentGame.getCurrentPlayer();
    }

    @Override
    protected void populate(Window w) {
        w.add(label).padLeft(PADDING).padTop(PADDING).row();

        repairButton = new TextButton("REPAIR", skin);
        if(couldBeRepaired()) {
            repairButton.setColor(Color.GREEN);
        }
        else {
            repairButton.setColor(Color.RED);
        }

        repairButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Result res = ClientGameController.sendBuildGreenHouseMessage();

                if(res.success()) {
                    messageLabel.setColor(Color.GREEN);
                    repairButton.setDisabled(true);
                }
                else {
                    messageLabel.setColor(Color.RED);
                }
                messageLabel.setText(res.getMessage());
            }
        });
        w.add(repairButton).center().padTop(PADDING).row();

        messageLabel = new Label("", skin);
        w.add(messageLabel).padTop(PADDING).row();
    }

    private boolean couldBeRepaired() {
        return player.getGold() >= 1000 && player.getInventory().getItemCount("wood") >= 500;
    }
}

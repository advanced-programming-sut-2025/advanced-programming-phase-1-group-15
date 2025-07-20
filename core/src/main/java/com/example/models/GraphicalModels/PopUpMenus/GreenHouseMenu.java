package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.map.GreenHouse;
import com.example.models.tools.Tool;

public class GreenHouseMenu extends PopUpMenu {
    private static final float WIDTH = 800f;
    private static final float HEIGHT = 400f;
    private static final float PADDING = 10f;

    private final GreenHouse greenHouse;
    private final Label label;
    private final Player player;
    private TextButton repairButton;
    private Label messageLabel;

    public GreenHouseMenu(Skin skin, String title, Runnable onHideCallback, GreenHouse greenHouse) {
        super(skin, title, WIDTH, HEIGHT, onHideCallback);

        this.greenHouse = greenHouse;
        label = new Label("""
                To repair your GREENHOUSE you need to have at least:\s
                * 500 wood\s
                * 1000 gold
                """, skin);
        label.setAlignment(Align.left);
        player = App.currentGame.getCurrentPlayer();
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
                if(couldBeRepaired()) {
                    player.getInventory().removeCountFromBackPack(player.getInventory().getItemByName("wood"), 500);
                    player.subtractGold(1000);
                    greenHouse.buildGreenHouse();

                    messageLabel.setColor(Color.GREEN);
                    messageLabel.setText("Greenhouse repaired successfully!");
                    repairButton.setDisabled(true);
                }
                else {
                    messageLabel.setColor(Color.RED);
                    messageLabel.setText("You don't have enough resources.");
                }
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

package com.example.client.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.tools.Tool;

import java.util.ArrayList;

public class ToolsMenu extends PopUpMenu {
    private static final float WIDTH = 700f;
    private static final float HEIGHT = 800f;
    private static final float PADDING = 10f;

    private final ArrayList<Tool> tools;
    private final ArrayList<Image> sprites = new ArrayList<>();
    private final ArrayList<Label> nameLabels = new ArrayList<>();
    private final ArrayList<Label> levelLabels = new ArrayList<>();
    private final ArrayList<TextButton> upgradeButtons = new ArrayList<>();
    private Label messageLabel;

    public ToolsMenu(Skin skin, String title, Runnable onHideCallback) {
        super(skin, title, WIDTH, HEIGHT, onHideCallback);

        Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();
        tools = currentPlayer.getInventory().getTools();

        for(Tool tool : tools) {
            Sprite toolSprite = tool.getSprite();
            Image toolIcon = new Image(new TextureRegionDrawable(toolSprite));
            toolIcon.setSize(48, 48);
            sprites.add(toolIcon);

            Label nameLabel = new Label(tool.getName(), skin);
            nameLabels.add(nameLabel);

            Label levelLabel = new Label("lvl: " + tool.getToolLevel(), skin);
            levelLabel.setAlignment(Align.left);
            switch(tool.getToolLevel()) {
                case COPPER -> levelLabel.setColor(Color.FIREBRICK);
                case IRON -> levelLabel.setColor(Color.DARK_GRAY);
                case GOLD -> levelLabel.setColor(Color.GOLD);
                case IRIDIUM -> levelLabel.setColor(Color.MAGENTA);
            }
            levelLabels.add(levelLabel);

            TextButton upgradeButton = new TextButton("UPGRADE", skin);
            if(tool.isUpgradable(currentPlayer)) {
                upgradeButton.setColor(Color.GREEN);
            }
            else {
                upgradeButton.setColor(Color.RED);
            }
            upgradeButtons.add(upgradeButton);
        }
    }

    @Override
    protected void populate(Window w) {
        Table contentTable = new Table();
        contentTable.pad(PADDING);
        contentTable.top();

        Label headerLabel = new Label("Your Tools:", skin);
        contentTable.add(headerLabel).colspan(4).padBottom(PADDING).row();

        for (int i = 0; i < tools.size(); i++) {
            contentTable.add(createToolRow(i)).expandX().fillX().center().padBottom(5).row();
        }

        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        w.add(scrollPane).expand().fill().row();

        messageLabel = new Label("", skin);
        w.add(messageLabel).colspan(4).padTop(PADDING).row();
    }

    private Table createToolRow(int i) {
        Player player = ClientApp.currentGame.getCurrentPlayer();
        Table toolRow = new Table();
        toolRow.left().padBottom(10);

        toolRow.add(sprites.get(i)).size(48, 48).left().padRight(PADDING);
        toolRow.add(nameLabels.get(i)).width(200).padRight(PADDING);
        toolRow.add(levelLabels.get(i)).width(200).left().padRight(PADDING);

        upgradeButtons.get(i).addListener(new ChangeListener() {
            final Tool t = tools.get(i);
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(tools.get(i).isUpgradable(player)) {
                    messageLabel.setColor(Color.GREEN);
                    messageLabel.setText(tools.get(i).upgrade(player));

                    Sprite spr = tools.get(i).getSprite();
                    spr.setSize(48,48);
                    sprites.get(i).setDrawable(new TextureRegionDrawable(t.getSprite()));

                    Label lvl = levelLabels.get(i);
                    lvl.setText("lvl: " + t.getToolLevel());
                    switch (t.getToolLevel()) {
                        case COPPER -> lvl.setColor(Color.FIREBRICK);
                        case IRON   -> lvl.setColor(Color.DARK_GRAY);
                        case GOLD   -> lvl.setColor(Color.GOLD);
                        case IRIDIUM-> lvl.setColor(Color.MAGENTA);
                    }

                    for(int i = 0; i < upgradeButtons.size(); i++) {
                        if (tools.get(i).isUpgradable(player)) {
                            upgradeButtons.get(i).setColor(Color.GREEN);
                        } else {
                            upgradeButtons.get(i).setColor(Color.RED);
                        }
                    }
                }
                else {
                    messageLabel.setColor(Color.RED);
                    messageLabel.setText(tools.get(i).upgrade(player));
                }
            }
        });

        sprites.get(i).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.setCurrentItem(tools.get(i));
                hide();
            }
        });

        toolRow.add(upgradeButtons.get(i)).width(150).height(60).right();

        return toolRow;
    }
}

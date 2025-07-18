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
import com.example.models.tools.Tool;

public class ToolsMenu extends PopUpMenu {
    private static final float WIDTH = 700f;
    private static final float HEIGHT = 800f;
    private static final float PADDING = 10f;

    public ToolsMenu(Skin skin, String title, Runnable onHideCallback) {
        super(skin, title, WIDTH, HEIGHT, onHideCallback);
    }

    @Override
    protected void populate(Window w) {
        Player currentPlayer = App.currentGame.getCurrentPlayer();
        Table contentTable = new Table();
        contentTable.pad(PADDING);
        contentTable.top();

        Label headerLabel = new Label("Your Tools:", skin);
        contentTable.add(headerLabel).colspan(4).padBottom(PADDING).row();

        for (Tool tool : currentPlayer.getInventory().getTools()) {
            contentTable.add(createToolRow(tool)).expandX().fillX().padBottom(5).row();
        }


        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(false, true);

        w.add(scrollPane).expand().fill().row();
    }

    private Table createToolRow(Tool tool) {
        Player player = App.currentGame.getCurrentPlayer();
        Table toolRow = new Table();
        toolRow.left().padBottom(10);

        Sprite toolSprite = tool.getSprite();
        toolSprite.setSize(48, 48);
        Image toolIcon = new Image(new TextureRegionDrawable(toolSprite));
        toolRow.add(toolIcon).size(48, 48).left().padRight(PADDING);

        Label nameLabel = new Label(tool.getName(), skin);
        toolRow.add(nameLabel).width(200).padRight(PADDING);

        Label levelLabel = new Label("lvl: " + tool.getToolLevel(), skin);
        levelLabel.setAlignment(Align.left);
        toolRow.add(levelLabel).width(200).left().padRight(PADDING);

        // Upgrade button
        TextButton upgradeButton = new TextButton("UPGRADE", skin);
        upgradeButton.setColor(Color.GREEN);
        upgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // upgrade logic
            }
        });

        toolIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.setCurrentTool(tool);
            }
        });

        toolRow.add(upgradeButton).width(150).height(60).right();

        return toolRow;
    }
}

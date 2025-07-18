package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.tools.Tool;

public class ToolsMenu extends PopUpMenu {
    private static final float WIDTH = 600f;
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
        contentTable.add(headerLabel).colspan(3).padBottom(15).row();

        int toolsCount = 0;
        for (Tool tool : currentPlayer.getInventory().getTools()) {
            contentTable.add(createToolRow(tool)).expandX().fillX().padBottom(5).row();
            toolsCount++;
        }

        if (toolsCount == 0) {
            contentTable.add(new Label("You have no tools!", skin)).colspan(3).center().row();
        }

        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        w.add(scrollPane).expand().fill().row();
    }

    private Table createToolRow(Tool tool) {
        Table toolRow = new Table();
        toolRow.left();

        Label nameLabel = new Label(tool.getName(), skin);
        nameLabel.setAlignment(Align.left);
        toolRow.add(nameLabel).width(200).left().padRight(PADDING);

        Label levelLabel = new Label("lvl: " + tool.getToolLevel(), skin);
        levelLabel.setAlignment(Align.left);
        toolRow.add(levelLabel).width(200).left().padRight(PADDING);

        TextButton upgradeButton = new TextButton("UPGRADE", skin);
        upgradeButton.setColor(Color.GREEN);
        upgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // upgrade logic
            }
        });

        toolRow.add(upgradeButton).width(150).height(60).right();

        return toolRow;
    }
}

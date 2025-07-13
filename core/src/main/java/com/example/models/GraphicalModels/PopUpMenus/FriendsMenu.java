package com.example.models.GraphicalModels.PopUpMenus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.relation.PlayerFriendship;

public class FriendsMenu extends PopUpMenu {
    private static final float MIN_WIDTH = 400f;
    private static final float MIN_HEIGHT = 300f;
    private static final float MAX_WIDTH_RATIO = 0.7f; // 70% of screen width
    private static final float MAX_HEIGHT_RATIO = 0.8f; // 80% of screen height
    private static final float ITEM_HEIGHT = 40f;
    private static final float PADDING = 20f;

    Player currentPlayer;

    public FriendsMenu(Skin skin, String title) {
        super(skin, title, calculateWidth(), calculateHeight());
        currentPlayer= App.currentGame.getCurrentPlayer();
    }

    private static float calculateWidth() {
        float screenWidth = Gdx.graphics.getWidth();
        float calculatedWidth = Math.max(MIN_WIDTH, screenWidth * 0.4f);
        return Math.min(calculatedWidth, screenWidth * MAX_WIDTH_RATIO);
    }

    private static float calculateHeight() {
        float screenHeight = Gdx.graphics.getHeight();

        int friendshipCount = App.currentGame.getFriendships().size();
        float contentHeight = friendshipCount * ITEM_HEIGHT + PADDING * 2;

        contentHeight += 60f;

        float calculatedHeight = Math.max(MIN_HEIGHT, contentHeight);
        return Math.min(calculatedHeight, screenHeight * MAX_HEIGHT_RATIO);
    }

    @Override
    protected void populate(Window w) {
        Table contentTable = new Table();
        contentTable.pad(PADDING);
        contentTable.top();

        contentTable.add(new Label("Friends List", skin)).colspan(2).padBottom(10).row();

        int friendCount = 0;
        for (PlayerFriendship f : App.currentGame.getFriendships()) {
            Player theOtherPlayer;
            if(f.getPlayer1().equals(currentPlayer)) {
                theOtherPlayer = f.getPlayer2();
            }
            else if(f.getPlayer2().equals(currentPlayer)) {
                theOtherPlayer = f.getPlayer1();
            }
            else{
                continue;
            }

            Table friendRow = new Table();
            friendRow.left();

            Label nameLabel = new Label(theOtherPlayer.getNickname(), skin);
            Label levelLabel = new Label("Level: " + f.getLevel(), skin);

            friendRow.add(nameLabel).expandX().left().padRight(10);
            friendRow.add(levelLabel).right();

            contentTable.add(friendRow).fillX().height(ITEM_HEIGHT).row();
            friendCount++;
        }

        if (friendCount == 0) {
            contentTable.add(new Label("No friends yet!", skin)).colspan(2).center().row();
        }

        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        w.add(scrollPane).expand().fill().row();

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                hide();
            }
        });

        w.add(closeButton).bottom().padTop(10).row();
    }

}

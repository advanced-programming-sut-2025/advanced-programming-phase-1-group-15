package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.npcs.NPC;
import com.example.models.npcs.NPCFriendShip;

public class FriendshipStatusMenu extends PopUpMenu {
    private final NPC targetNPC;
    private final Player currentPlayer;

    private static final float DEFAULT_WIDTH = 500;
    private static final float DEFAULT_HEIGHT = 400;

    public FriendshipStatusMenu(Skin skin, String title, Runnable onHideCallback, NPC targetNPC) {
        super(skin, title, DEFAULT_WIDTH, DEFAULT_HEIGHT, onHideCallback);
        this.targetNPC = targetNPC;
        this.currentPlayer = App.currentGame.getCurrentPlayer();
    }

    @Override
    protected void populate(Window w) {
        Table contentTable = new Table(skin);
        contentTable.top();

        NPCFriendShip friendship = targetNPC.getFriendships().get(currentPlayer);

        contentTable.add(new Label("Friendship with " + targetNPC.getName(), skin)).pad(10).row();

        if (friendship == null) {
            contentTable.add(new Label("No friendship established", skin)).pad(5).row();
            contentTable.add(new Label("Talk to " + targetNPC.getName() + " to start a friendship!", skin)).pad(5).row();
        } else {
            int currentLevel = friendship.getLevel();
            int currentPoints = friendship.getPoints();
            int pointsForNextLevel = (currentLevel + 1) * NPCFriendShip.POINTS_PER_LEVEL;
            int pointsNeeded = pointsForNextLevel - currentPoints;

            contentTable.add(new Label("Friendship Level: " + currentLevel, skin)).pad(5).row();
            contentTable.add(new Label("Friendship Points: " + currentPoints, skin)).pad(5).row();

            if (currentLevel < (NPCFriendShip.MAX_POINTS / NPCFriendShip.POINTS_PER_LEVEL)) {
                int progressInCurrentLevel = currentPoints - (currentLevel * NPCFriendShip.POINTS_PER_LEVEL);
                float progressPercent = (float) progressInCurrentLevel / NPCFriendShip.POINTS_PER_LEVEL;

                ProgressBar progressBar = new ProgressBar(0, 1, 0.01f, false, skin);
                progressBar.setValue(progressPercent);

                contentTable.add(new Label("Progress to Level " + (currentLevel + 1) + ":", skin)).pad(5).row();
                contentTable.add(progressBar).width(200).pad(5).row();
                contentTable.add(new Label(pointsNeeded + " points needed", skin)).pad(5).row();
            } else {
                contentTable.add(new Label("Maximum friendship level reached!", skin)).pad(5).row();
            }

            String levelDescription = getFriendshipDescription(currentLevel);
            Label descLabel = new Label(levelDescription, skin);
            descLabel.setWrap(true);
            contentTable.add(descLabel).width(250).pad(10).row();

            contentTable.add(new Label("Today's Status:", skin)).pad(5).row();

            String talkStatus = friendship.hasTalkedToday() ? "Already talked today" : "Haven't talked today";
            Label talkLabel = new Label("• " + talkStatus, skin);
            talkLabel.setColor(friendship.hasTalkedToday() ? Color.GREEN : Color.ORANGE);
            contentTable.add(talkLabel).left().pad(2).row();

            String giftStatus = friendship.hasGiftedToday() ? "Already gave gift today" : "Haven't given gift today";
            Label giftLabel = new Label("• " + giftStatus, skin);
            giftLabel.setColor(friendship.hasGiftedToday() ? Color.GREEN : Color.ORANGE);
            contentTable.add(giftLabel).left().pad(2).row();
        }

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        contentTable.add(closeButton).pad(10).row();

        w.add(contentTable).expand().fill().row();
    }

    private String getFriendshipDescription(int level) {
        switch (level) {
            case 0:
                return "Strangers - You barely know each other.";
            case 1:
                return "familiar - You've started to get to know each other.";
            case 2:
                return "Friends - You have a good friendship developing.";
            case 3:
                return "Close Friends - You're very close friends! They might send you gifts.";
            default:
                return "Best Friends Forever - Maximum friendship level! They regularly send you their favorite items.";
        }
    }
}

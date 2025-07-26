package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.Result;
import com.example.models.npcs.NPC;
import com.example.models.npcs.NPCFriendShip;
import com.example.models.npcs.Quest;

import java.util.HashMap;
import java.util.Map;

public class QuestDisplayMenu extends PopUpMenu {
    private final NPC targetNPC;
    private final Player currentPlayer;
    private ScrollPane scrollPane;
    private Table questTable;

    private static final float DEFAULT_WIDTH = 500;
    private static final float DEFAULT_HEIGHT = 400;

    public QuestDisplayMenu(Skin skin, String title, Runnable onHideCallback, NPC targetNPC) {
        super(skin, title, DEFAULT_WIDTH, DEFAULT_HEIGHT, onHideCallback);
        this.targetNPC = targetNPC;
        this.currentPlayer = App.currentGame.getCurrentPlayer();
    }

    @Override
    protected void populate(Window w) {
        if (questTable == null) {
            questTable = new Table(skin);
            questTable.top();
        } else {
            questTable.clear();
        }

        if (scrollPane == null) {
            scrollPane = new ScrollPane(questTable, skin);
            scrollPane.setFadeScrollBars(false);
            scrollPane.setScrollingDisabled(true, false);
        }

        Table mainContentTable = new Table(skin);
        mainContentTable.top();

        mainContentTable.add(new Label("Available Quests from " + targetNPC.getName(), skin)).pad(10).row();
        mainContentTable.add(scrollPane).width(DEFAULT_WIDTH - 40).height(DEFAULT_HEIGHT - 100).pad(10).row();

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        mainContentTable.add(closeButton).pad(10);

        w.add(mainContentTable).expand().fill().row();

        populateQuests();
    }

    private void populateQuests() {
        questTable.clear();

        NPCFriendShip friendship = targetNPC.getFriendships().get(currentPlayer);
        if (friendship == null) {
            questTable.add(new Label("No friendship established with " + targetNPC.getName(), skin)).pad(5).row();
            return;
        }

        HashMap<Quest, Boolean> playerQuests = friendship.getPlayerQuests();

        if (playerQuests.isEmpty()) {
            questTable.add(new Label("No quests available!", skin)).pad(5).row();
            return;
        }

        boolean hasActiveQuests = false;

        for (Map.Entry<Quest, Boolean> entry : playerQuests.entrySet()) {
            Quest quest = entry.getKey();
            Boolean isActive = entry.getValue();

            if (!isActive || quest.isDoneBySomeone()) {
                continue;
            }

            hasActiveQuests = true;

            Table questRow = new Table();
            questRow.pad(5);

            String questText = "Deliver " + quest.getRequestAmount() + " x " + quest.getRequest().getName();
            String rewardText = "Reward: " + quest.getRewardAmount() + " x " + quest.getReward().getName();

            Label questLabel = new Label(questText, skin);
            Label rewardLabel = new Label(rewardText, skin);

            questRow.add(questLabel).left().pad(2).row();
            questRow.add(rewardLabel).left().pad(2).row();

            int playerItemCount = currentPlayer.getInventory().getItemCount(quest.getRequest().getName());
            boolean canComplete = playerItemCount >= quest.getRequestAmount();

            if (canComplete) {
                TextButton completeButton = new TextButton("Complete Quest", skin);
                completeButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        completeQuest(quest);
                    }
                });
                questRow.add(completeButton).pad(2).row();
            } else {
                Label statusLabel = new Label("Need " + (quest.getRequestAmount() - playerItemCount) + " more", skin);
                questRow.add(statusLabel).pad(2).row();
            }

            questTable.add(questRow).fillX().pad(5).row();
            questTable.add(new Label("", skin)).height(10).row();
        }

        if (!hasActiveQuests) {
            questTable.add(new Label("No active quests available!", skin)).pad(5).row();
        }
    }

    private void completeQuest(Quest quest) {
        Result result = targetNPC.getFriendships().get(currentPlayer).finishQuest(quest.getRequest());

        Dialog resultDialog = new Dialog("Quest Result", skin);
        resultDialog.text(result.message());
        resultDialog.button("OK");
        resultDialog.show(stage);

        populateQuests();
    }
}

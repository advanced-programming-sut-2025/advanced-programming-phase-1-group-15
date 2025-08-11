package com.example.client.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.npcs.NPC;
import com.example.common.npcs.NPCFriendShip;
import com.example.common.npcs.Quest;

import java.util.HashMap;
import java.util.Map;

public class QuestDisplayMenu extends PopUpMenu {
    private final NPC targetNPC;
    private final Player currentPlayer;
    private ScrollPane scrollPane;
    private Table questTable;
    private Label statusLabel;

    private static final float DEFAULT_WIDTH = 550;
    private static final float DEFAULT_HEIGHT = 450;

    public QuestDisplayMenu(Skin skin, String title, Runnable onHideCallback, NPC targetNPC) {
        super(skin, title, DEFAULT_WIDTH, DEFAULT_HEIGHT, onHideCallback);
        this.targetNPC = targetNPC;
        this.currentPlayer = ClientApp.currentGame.getCurrentPlayer();
    }

    @Override
    protected void populate(Window w) {
        Table mainContentTable = new Table(skin);
        mainContentTable.top();
        mainContentTable.pad(15);

        Table headerTable = new Table();
        Label titleLabel = new Label("📋 Available Quests from " + targetNPC.getName(), skin);
        titleLabel.setFontScale(1.1f);
        headerTable.add(titleLabel).pad(5).row();

        statusLabel = new Label("", skin);
        statusLabel.setColor(Color.LIGHT_GRAY);
        statusLabel.setFontScale(0.9f);
        headerTable.add(statusLabel).pad(2);

        mainContentTable.add(headerTable).pad(10).row();

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

        mainContentTable.add(scrollPane)
            .width(DEFAULT_WIDTH - 40)
            .height(DEFAULT_HEIGHT - 120)
            .pad(10).row();

        Table buttonTable = new Table();
        TextButton refreshButton = new TextButton("Refresh", skin);
        TextButton closeButton = new TextButton("Close", skin);

        refreshButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                populateQuests();
            }
        });

        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });

        buttonTable.add(refreshButton).pad(5);
        buttonTable.add(closeButton).pad(5);
        mainContentTable.add(buttonTable).pad(10);

        w.add(mainContentTable).expand().fill().row();

        populateQuests();
    }

    private void populateQuests() {
        questTable.clear();

        NPCFriendShip friendship = targetNPC.getFriendships().get(currentPlayer);
        if (friendship == null) {
            showNoFriendshipMessage();
            return;
        }

        HashMap<Quest, Boolean> playerQuests = friendship.getPlayerQuests();

        if (playerQuests.isEmpty()) {
            showNoQuestsMessage();
            return;
        }

        int activeQuestCount = 0;
        int completableCount = 0;

        for (Map.Entry<Quest, Boolean> entry : playerQuests.entrySet()) {
            Quest quest = entry.getKey();
            Boolean isActive = entry.getValue();

            if (!isActive || quest.isDoneBySomeone()) {
                continue;
            }

            activeQuestCount++;
            int playerItemCount = currentPlayer.getInventory().getItemCount(quest.getRequest().getName());
            boolean canComplete = playerItemCount >= quest.getRequestAmount();

            if (canComplete) {
                completableCount++;
            }

            createQuestCard(quest, playerItemCount, canComplete);
        }

        updateStatusLabel(activeQuestCount, completableCount);

        if (activeQuestCount == 0) {
            showNoActiveQuestsMessage();
        }
    }

    private void createQuestCard(Quest quest, int playerItemCount, boolean canComplete) {
        Table questCard = new Table(skin);
        questCard.pad(12);

        Table headerTable = new Table();
        Label questNumberLabel = new Label("📋 Quest", skin);
        questNumberLabel.setFontScale(1.0f);
        questNumberLabel.setColor(canComplete ? Color.GREEN : Color.WHITE);
        headerTable.add(questNumberLabel).left().row();

        Table descriptionTable = new Table();
        String questText = "Deliver " + quest.getRequestAmount() + " × " + quest.getRequest().getName();
        Label questLabel = new Label("📦 " + questText, skin);
        questLabel.setFontScale(0.95f);
        descriptionTable.add(questLabel).left().pad(2).row();

        String rewardText = "Reward: " + quest.getRewardAmount() + " × " + quest.getReward().getName();
        Label rewardLabel = new Label("🎁 " + rewardText, skin);
        rewardLabel.setColor(Color.GOLD);
        rewardLabel.setFontScale(0.9f);
        descriptionTable.add(rewardLabel).left().pad(2).row();

        Table progressTable = new Table();

        float progress = Math.min(1.0f, (float) playerItemCount / quest.getRequestAmount());
        ProgressBar progressBar = new ProgressBar(0, 1, 0.01f, false, skin);
        progressBar.setValue(progress);

        String progressText = playerItemCount + " / " + quest.getRequestAmount();
        Label progressLabel = new Label("Progress: " + progressText, skin);
        progressLabel.setFontScale(0.85f);

        if (canComplete) {
            progressLabel.setColor(Color.GREEN);
        } else {
            progressLabel.setColor(Color.ORANGE);
        }

        progressTable.add(progressLabel).left().pad(2).row();
        progressTable.add(progressBar).width(200).height(15).pad(2).row();

        Table actionTable = new Table();
        if (canComplete) {
            TextButton completeButton = new TextButton("✅ Complete Quest", skin);
            completeButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    completeQuest(quest);
                }
            });
            actionTable.add(completeButton).pad(5);
        } else {
            int needed = quest.getRequestAmount() - playerItemCount;
            Label statusLabel = new Label("⏳ Need " + needed + " more items", skin);
            statusLabel.setColor(Color.ORANGE);
            statusLabel.setFontScale(0.8f);
            actionTable.add(statusLabel).pad(5);
        }

        questCard.add(headerTable).left().fillX().row();
        questCard.add(descriptionTable).left().fillX().pad(5, 0, 5, 0).row();
        questCard.add(progressTable).left().fillX().pad(5, 0, 5, 0).row();
        questCard.add(actionTable).left().fillX().row();

        questTable.add(questCard).fillX().pad(8).row();

        Label separator = new Label("─────────────────────────────────────", skin);
        separator.setColor(Color.GRAY);
        separator.setFontScale(0.7f);
        questTable.add(separator).pad(5).row();
    }

    private void showNoFriendshipMessage() {
        Table messageTable = new Table();
        Label noFriendshipLabel = new Label("❌ No friendship established with " + targetNPC.getName(), skin);
        noFriendshipLabel.setColor(Color.RED);
        Label suggestionLabel = new Label("💡 Talk to them first to start a friendship!", skin);
        suggestionLabel.setColor(Color.LIGHT_GRAY);
        suggestionLabel.setFontScale(0.9f);

        messageTable.add(noFriendshipLabel).pad(20).row();
        messageTable.add(suggestionLabel).pad(5);

        questTable.add(messageTable).expand().center().row();
        statusLabel.setText("No friendship established");
    }

    private void showNoQuestsMessage() {
        Table messageTable = new Table();
        Label noQuestsLabel = new Label("📭 No quests available!", skin);
        noQuestsLabel.setColor(Color.ORANGE);
        Label infoLabel = new Label("💡 Increase friendship level to unlock quests", skin);
        infoLabel.setColor(Color.LIGHT_GRAY);
        infoLabel.setFontScale(0.9f);

        messageTable.add(noQuestsLabel).pad(20).row();
        messageTable.add(infoLabel).pad(5);

        questTable.add(messageTable).expand().center().row();
        statusLabel.setText("No quests in quest pool");
    }

    private void showNoActiveQuestsMessage() {
        Table messageTable = new Table();
        Label noActiveLabel = new Label("✅ No active quests available!", skin);
        noActiveLabel.setColor(Color.GREEN);
        Label completedLabel = new Label("🎉 All available quests completed", skin);
        completedLabel.setColor(Color.LIGHT_GRAY);
        completedLabel.setFontScale(0.9f);

        messageTable.add(noActiveLabel).pad(20).row();
        messageTable.add(completedLabel).pad(5);

        questTable.add(messageTable).expand().center().row();
        statusLabel.setText("All quests completed");
    }

    private void updateStatusLabel(int activeQuests, int completableQuests) {
        if (activeQuests > 0) {
            String status = String.format("📊 %d active quest(s) | %d ready to complete",
                activeQuests, completableQuests);
            statusLabel.setText(status);

            if (completableQuests > 0) {
                statusLabel.setColor(Color.GREEN);
            } else {
                statusLabel.setColor(Color.ORANGE);
            }
        }
    }

    private void completeQuest(Quest quest) {
        String resultMessage = targetNPC.finishQuest(currentPlayer, quest);

        Dialog resultDialog = new Dialog("🎉 Quest Result", skin) {
            @Override
            protected void result(Object object) {
                remove();
            }
        };

        Table contentTable = new Table();
        Label messageLabel = new Label(resultMessage, skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(1);

        boolean success = !resultMessage.toLowerCase().contains("don't have") &&
            !resultMessage.toLowerCase().contains("no available") &&
            !resultMessage.toLowerCase().contains("no friendship");

        if (success) {
            messageLabel.setColor(Color.GREEN);
        } else {
            messageLabel.setColor(Color.RED);
        }

        contentTable.add(messageLabel).width(350).pad(20);

        resultDialog.getContentTable().add(contentTable);
        resultDialog.button(" OK", true);
        resultDialog.show(stage);

        populateQuests();
    }

    @Override
    public void show() {
        super.show();
        if (scrollPane != null) {
            stage.setScrollFocus(scrollPane);
        }
    }

    @Override
    public void hide() {
        super.hide();
    }
}

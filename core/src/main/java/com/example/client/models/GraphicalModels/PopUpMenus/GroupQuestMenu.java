package com.example.client.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.example.client.models.ClientApp;
import com.example.common.GroupQuests.GroupQuest;
import com.example.common.GroupQuests.GroupQuestManager;
import com.example.common.Player;

import java.util.List;

public class GroupQuestMenu extends PopUpMenu {
    private static final float WIDTH = 900f;
    private static final float HEIGHT = 700f;
    private static final float PADDING = 15f;

    private GroupQuestManager questManager;
    private Table availableQuestsTable;
    private Table activeQuestsTable;
    private Label statusLabel;
    private TabPane tabPane;

    public GroupQuestMenu(Skin skin, GroupQuestManager questManager, Runnable onHideCallback) {
        super(skin, "Group Quests", WIDTH, HEIGHT, onHideCallback);
        this.questManager = questManager;
    }

    @Override
    protected void populate(Window w) {
        w.clear();
        tabPane = new TabPane(skin);

        Table availableTab = createAvailableQuestsTab();
        tabPane.addTab("Available Quests", availableTab);

        Table activeTab = createActiveQuestsTab();
        tabPane.addTab("My Active Quests", activeTab);

        w.add(tabPane).expand().fill().pad(PADDING).row();

        statusLabel = new Label("", skin);
        statusLabel.setAlignment(Align.center);
        w.add(statusLabel).fillX().pad(PADDING);
    }

    private Table createAvailableQuestsTab() {
        Table tabContent = new Table();
        tabContent.top();

        Label headerLabel = new Label("Available Group Quests", skin, "title");
        headerLabel.setAlignment(Align.center);
        tabContent.add(headerLabel).fillX().padBottom(PADDING).row();

        availableQuestsTable = new Table();
        refreshAvailableQuests();

        ScrollPane scrollPane = new ScrollPane(availableQuestsTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        tabContent.add(scrollPane).expand().fill();

        return tabContent;
    }

    private Table createActiveQuestsTab() {
        Table tabContent = new Table();
        tabContent.top();

        Label headerLabel = new Label("My Active Quests", skin, "title");
        headerLabel.setAlignment(Align.center);
        tabContent.add(headerLabel).fillX().padBottom(PADDING).row();

        activeQuestsTable = new Table();
        refreshActiveQuests();

        ScrollPane scrollPane = new ScrollPane(activeQuestsTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        tabContent.add(scrollPane).expand().fill();

        return tabContent;
    }

    private void refreshAvailableQuests() {
        availableQuestsTable.clear();

        List<GroupQuest> availableQuests = questManager.getAvailableQuests();
        Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();

        if (availableQuests.isEmpty()) {
            Label noQuestsLabel = new Label("No quests available at the moment.", skin);
            noQuestsLabel.setAlignment(Align.center);
            availableQuestsTable.add(noQuestsLabel).fillX().pad(20);
            return;
        }

        for (GroupQuest quest : availableQuests) {
            Table questRow = createAvailableQuestRow(quest, currentPlayer);
            availableQuestsTable.add(questRow).fillX().padBottom(10).row();
        }
    }

    private void refreshActiveQuests() {
        activeQuestsTable.clear();

        Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();
        List<GroupQuest> activeQuests = questManager.getPlayerActiveQuests(currentPlayer.getUsername());

        if (activeQuests.isEmpty()) {
            Label noQuestsLabel = new Label("You have no active quests.", skin);
            noQuestsLabel.setAlignment(Align.center);
            activeQuestsTable.add(noQuestsLabel).fillX().pad(20);
            return;
        }
        for (GroupQuest quest : activeQuests) {
            Table questRow = createActiveQuestRow(quest, currentPlayer);
            activeQuestsTable.add(questRow).fillX().padBottom(10).row();
        }
    }

    private Table createAvailableQuestRow(GroupQuest quest, Player currentPlayer) {
        Table row = new Table();
        row.setBackground(skin.getDrawable("default-round"));
        row.pad(10);

        Table infoSection = new Table();

        Label titleLabel = new Label(quest.getTitle(), skin, "title");
        titleLabel.setColor(Color.CYAN);
        infoSection.add(titleLabel).fillX().row();

        Label descLabel = new Label(quest.getDescription(), skin);
        descLabel.setWrap(true);
        descLabel.setAlignment(Align.left);
        infoSection.add(descLabel).width(400).fillX().padTop(5).row();

        Table detailsTable = new Table();
        detailsTable.add(new Label("Target: " + quest.getTargetAmount() + " items", skin)).left().row();
        detailsTable.add(new Label("Players: " + quest.getParticipantUsernames().size() + "/"
            + quest.getRequiredPlayers() + "-" + quest.getMaxPlayers(), skin)).left().row();
        detailsTable.add(new Label("Reward: " + quest.getRewardPerPlayer() + " coins", skin)).left().row();
        detailsTable.add(new Label("Time Limit: " + quest.getTimeLimit() + " days", skin)).left().row();

        infoSection.add(detailsTable).left().padTop(10);

        row.add(infoSection).expand().fill().left();

        Table buttonSection = new Table();

        TextButton joinButton = new TextButton("JOIN", skin);

        boolean canJoin = quest.canJoin(currentPlayer.getUsername()) &&
            questManager.canPlayerJoinMoreQuests(currentPlayer.getUsername());

        if (canJoin) {
            joinButton.setColor(Color.GREEN);
        } else {
            joinButton.setColor(Color.GRAY);
            joinButton.setDisabled(true);

            if (!questManager.canPlayerJoinMoreQuests(currentPlayer.getUsername())) {
                joinButton.setText("MAX QUESTS");
            } else if (quest.getParticipantUsernames().size() >= quest.getMaxPlayers()) {
                joinButton.setText("FULL");
            } else if (quest.getParticipantUsernames().contains(currentPlayer.getUsername())) {
                joinButton.setText("JOINED");
            }
        }

        joinButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (questManager.joinQuest(quest.getQuestId(), currentPlayer)) {
                    statusLabel.setColor(Color.GREEN);
                    statusLabel.setText("Successfully joined quest: " + quest.getTitle());
                    refreshAvailableQuests();
                    refreshActiveQuests();
                } else {
                    statusLabel.setColor(Color.RED);
                    statusLabel.setText("Failed to join quest. Check requirements.");
                }
            }
        });

        buttonSection.add(joinButton).size(120, 40);
        row.add(buttonSection).right().padLeft(20);

        return row;
    }

    private Table createActiveQuestRow(GroupQuest quest, Player currentPlayer) {
        Table row = new Table();
        row.setBackground(skin.getDrawable("default-round"));
        row.pad(10);

        Table infoSection = new Table();

        Label titleLabel = new Label(quest.getTitle(), skin, "title");
        titleLabel.setColor(Color.YELLOW);
        infoSection.add(titleLabel).fillX().row();

        ProgressBar progressBar = new ProgressBar(0f, 100f, 1f, false, skin);
        progressBar.setValue((float) quest.getCompletionPercentage());
        infoSection.add(progressBar).fillX().height(20).padTop(5).row();

        Label progressLabel = new Label(String.format("Progress: %.1f%% (%d/%d items)",
            quest.getCompletionPercentage(),
            quest.getPlayerProgress().values().stream().mapToInt(Integer::intValue).sum(),
            quest.getTargetAmount()), skin);
        infoSection.add(progressLabel).left().padTop(5).row();

        double yourContribution = quest.getPlayerCompletionPercentage(currentPlayer);
        Label contributionLabel = new Label(String.format("Your contribution: %.1f%%", yourContribution), skin);
        contributionLabel.setColor(Color.LIGHT_GRAY);
        infoSection.add(contributionLabel).left().padTop(2).row();

        int daysRemaining = quest.getEndDay() - questManager.getCurrentDay();
        Label timeLabel = new Label("Days remaining: " + Math.max(0, daysRemaining), skin);
        if (daysRemaining <= 1) {
            timeLabel.setColor(Color.RED);
        } else if (daysRemaining <= 2) {
            timeLabel.setColor(Color.YELLOW);
        }
        infoSection.add(timeLabel).left().padTop(5).row();

        Label participantsLabel = new Label("Participants: " +
            String.join(", ", quest.getParticipantUsernames()), skin);
        participantsLabel.setWrap(true);
        participantsLabel.setAlignment(Align.left);
        infoSection.add(participantsLabel).width(500).left().padTop(5);

        row.add(infoSection).expand().fill().left();

        Table buttonSection = new Table();

        TextButton leaveButton = new TextButton("LEAVE", skin);
        leaveButton.setColor(Color.RED);

        leaveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (questManager.leaveQuest(quest.getQuestId(), currentPlayer)) {
                    statusLabel.setColor(Color.YELLOW);
                    statusLabel.setText("Left quest: " + quest.getTitle());
                    refreshAvailableQuests();
                    refreshActiveQuests();
                } else {
                    statusLabel.setColor(Color.RED);
                    statusLabel.setText("Failed to leave quest.");
                }
            }
        });

        buttonSection.add(leaveButton).size(120, 40);
        row.add(buttonSection).right().padLeft(20);

        return row;
    }

    private static class TabPane extends Table {
        private Table tabButtonsTable;
        private Table contentTable;
        private ButtonGroup<TextButton> tabButtons;

        public TabPane(Skin skin) {
            super(skin);

            tabButtonsTable = new Table();
            contentTable = new Table();
            tabButtons = new ButtonGroup<>();
            tabButtons.setMaxCheckCount(1);
            tabButtons.setMinCheckCount(1);

            add(tabButtonsTable).fillX().row();
            add(contentTable).expand().fill();
        }

        public void addTab(String title, Table content) {
            TextButton tabButton = new TextButton(title, getSkin(), "toggle");
            tabButtons.add(tabButton);

            tabButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (tabButton.isChecked()) {
                        contentTable.clear();
                        contentTable.add(content).expand().fill();
                    }
                }
            });

            tabButtonsTable.add(tabButton).expandX().fillX().height(40);

            if (tabButtons.getButtons().size == 1) {
                tabButton.setChecked(true);
                contentTable.add(content).expand().fill();
            }
        }
    }

    @Override
    public void show() {
        refreshAvailableQuests();
        refreshActiveQuests();
        super.show();
    }
}

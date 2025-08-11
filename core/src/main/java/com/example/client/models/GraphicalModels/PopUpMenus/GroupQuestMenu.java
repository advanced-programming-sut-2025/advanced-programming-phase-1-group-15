package com.example.client.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.example.client.models.ClientApp;
import com.example.common.GroupQuests.GroupQuest;
import com.example.common.GroupQuests.GroupQuestManager;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.tools.BackPackable;

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
        availableQuestsTable = new Table();
        activeQuestsTable = new Table();
        statusLabel = new Label("status", skin);
        tabPane = new TabPane(skin);
    }

    @Override
    protected void populate(Window w) {
        tabPane = new TabPane(skin);

        Table availableTab = createAvailableQuestsTab();
        tabPane.addTab("Available Quests", availableTab);

        Table activeTab = createActiveQuestsTab();
        tabPane.addTab("My Active Quests", activeTab);

        w.add(tabPane).fill().pad(PADDING).padTop(30).row();

        statusLabel = new Label("", skin);
        statusLabel.setAlignment(Align.center);
        w.add(statusLabel).fillX().pad(PADDING);
    }

    private Table createAvailableQuestsTab() {
        Table tabContent = new Table();
        tabContent.top();

        Label headerLabel = new Label("Available Group Quests", skin);
        headerLabel.setColor(Color.BLACK);
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

        Label headerLabel = new Label("My Active Quests", skin);
        headerLabel.setColor(Color.BLACK);
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
            noQuestsLabel.setColor(Color.PURPLE);
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
            noQuestsLabel.setColor(Color.PURPLE);
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
        row.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));
        row.pad(10);

        Table infoSection = new Table();

        Label titleLabel = new Label(quest.getTitle(), skin);
        titleLabel.setColor(Color.BLACK);
        infoSection.add(titleLabel).fillX().row();

        Label descLabel = new Label(quest.getDescription(), skin);
        descLabel.setWrap(true);
        descLabel.setAlignment(Align.left);
        descLabel.setColor(Color.BLACK);
        infoSection.add(descLabel).width(400).fillX().padTop(5).row();

        Table detailsTable = new Table();
        Label targetLabel = new Label("Target: " + quest.getTargetAmount() + " items", skin);
        targetLabel.setColor(Color.BLACK);
        detailsTable.add(targetLabel).left().row();
        Label playersLabel = new Label("Players: " + quest.getParticipantUsernames().size() + "/"
            + quest.getRequiredPlayers() + "-" + quest.getMaxPlayers(), skin);
        playersLabel.setColor(Color.BLACK);
        detailsTable.add(playersLabel).left().row();
        Label rewardLabel = new Label("Reward: " + quest.getRewardPerPlayer() + " coins", skin);
        rewardLabel.setColor(Color.BLACK);
        detailsTable.add(rewardLabel).left().row();
        Label limitLabel = new Label("Time Limit: " + quest.getTimeLimit() + " days", skin);
        limitLabel.setColor(Color.BLACK);
        detailsTable.add(limitLabel).left().row();

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
                joinButton.setText("MAX QUESTS (3/3)");
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
                    statusLabel.setText("Failed to join quest. Check requirements or quest limit (max 3).");
                }
            }
        });

        buttonSection.add(joinButton).size(140, 40);
        row.add(buttonSection).right().padLeft(20);

        return row;
    }

    private Table createActiveQuestRow(GroupQuest quest, Player currentPlayer) {
        Table row = new Table();
        row.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));
        row.pad(10);

        Table infoSection = new Table();

        Label titleLabel = new Label(quest.getTitle(), skin);
        titleLabel.setColor(Color.BLACK);
        infoSection.add(titleLabel).fillX().row();

        ProgressBar progressBar = new ProgressBar(0f, 100f, 1f, false, skin);
        progressBar.setValue((float) quest.getCompletionPercentage());
        infoSection.add(progressBar).fillX().height(20).padTop(5).row();

        Label progressLabel = new Label(String.format("Progress: %.1f%% (%d/%d items)",
            quest.getCompletionPercentage(),
            quest.getPlayerProgress().values().stream().mapToInt(Integer::intValue).sum(),
            quest.getTargetAmount()), skin);
        progressLabel.setColor(Color.BLACK);
        infoSection.add(progressLabel).left().padTop(5).row();

        double yourContribution = quest.getPlayerCompletionPercentage(currentPlayer);
        Label contributionLabel = new Label(String.format("Your contribution: %.1f%%", yourContribution), skin);
        contributionLabel.setColor(Color.BLACK);
        infoSection.add(contributionLabel).left().padTop(2).row();

        BackPackable item = currentPlayer.getInventory().getItemByName(quest.getTitle());
        int count = currentPlayer.getInventory().getItemCount(quest.getTitle());
        int availableItems = item != null ? count : 0;
        int maxDeliverable = questManager.getMaxDeliverable(quest.getQuestId(), currentPlayer);

        Label inventoryLabel = new Label(String.format("Available: %d items (Max deliverable: %d)",
            availableItems, maxDeliverable), skin);
        inventoryLabel.setColor(availableItems > 0 ? Color.BLACK : Color.RED);
        infoSection.add(inventoryLabel).left().padTop(2).row();

        int daysRemaining = quest.getEndDay() - questManager.getCurrentDay();
        Label timeLabel = new Label("", skin);
        if (daysRemaining <= 0) {
            timeLabel.setText("Status: FAILED");
            timeLabel.setColor(Color.RED);
        } else if (daysRemaining <= 1) {
            timeLabel.setText("Days remaining: " + daysRemaining);
            timeLabel.setColor(Color.YELLOW);
        } else {
            timeLabel.setText("Days remaining: " + daysRemaining);
            timeLabel.setColor(Color.BLACK);
        }
        infoSection.add(timeLabel).left().padTop(5).row();

        Label participantsLabel = new Label("Participants: " +
            String.join(", ", quest.getParticipantUsernames()), skin);
        participantsLabel.setWrap(true);
        participantsLabel.setAlignment(Align.left);
        participantsLabel.setColor(Color.BLACK);
        infoSection.add(participantsLabel).width(500).left().padTop(5).row();

        Table deliverySection = new Table();
        Label deliverLabel = new Label("Deliver items:", skin);
        deliverLabel.setColor(Color.BLACK);
        deliverySection.add(deliverLabel).left().padRight(10);

        TextField amountField = new TextField("1", skin);
        amountField.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return Character.isDigit(c);
            }
        });
        amountField.setAlignment(Align.center);
        deliverySection.add(amountField).size(80, 30).padRight(10);

        TextButton deliverButton = new TextButton("DELIVER", skin);
        deliverButton.setColor(Color.CYAN);

        if (daysRemaining <= 0) {
            deliverButton.setDisabled(true);
            deliverButton.setColor(Color.GRAY);
            deliverButton.setText("FAILED");
        } else if (maxDeliverable <= 0) {
            deliverButton.setDisabled(true);
            deliverButton.setColor(Color.GRAY);
        }

        deliverButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    int amount = Integer.parseInt(amountField.getText().trim());
                    if (amount <= 0) {
                        statusLabel.setColor(Color.RED);
                        statusLabel.setText("Amount must be greater than 0");
                        return;
                    }

                    Result result = questManager.deliverItems(
                        quest.getQuestId(), currentPlayer, amount);

                    if (result.isSuccessFull()) {
                        statusLabel.setColor(Color.GREEN);
                        statusLabel.setText(result.getMessage());
                        refreshActiveQuests();
                        refreshAvailableQuests();
                    } else {
                        statusLabel.setColor(Color.RED);
                        statusLabel.setText(result.getMessage());
                    }
                } catch (NumberFormatException e) {
                    statusLabel.setColor(Color.RED);
                    statusLabel.setText("Please enter a valid number");
                }
            }
        });

        deliverySection.add(deliverButton).size(80, 30);
        infoSection.add(deliverySection).left().padTop(10).row();

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
            TextButton tabButton = new TextButton(title, getSkin());
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

package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
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

    private static final float DEFAULT_WIDTH = 550;
    private static final float DEFAULT_HEIGHT = 450;

    public FriendshipStatusMenu(Skin skin, String title, Runnable onHideCallback, NPC targetNPC) {
        super(skin, title, DEFAULT_WIDTH, DEFAULT_HEIGHT, onHideCallback);
        this.targetNPC = targetNPC;
        this.currentPlayer = App.currentGame.getCurrentPlayer();
    }

    @Override
    protected void populate(Window w) {
        Table mainContentTable = new Table(skin);
        mainContentTable.top();
        mainContentTable.pad(20);

        NPCFriendShip friendship = targetNPC.getFriendships().get(currentPlayer);

        Table headerTable = new Table();
        Label titleLabel = new Label("❤️ Friendship with " + targetNPC.getName(), skin);
        titleLabel.setFontScale(1.2f);
        headerTable.add(titleLabel).pad(10).row();

        mainContentTable.add(headerTable).row();

        Table contentTable = new Table(skin);
        contentTable.top();

        if (friendship == null) {
            createNoFriendshipContent(contentTable);
        } else {
            createFriendshipContent(contentTable, friendship);
        }

        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        // bounds for the scroll pane
        scrollPane.setSize(DEFAULT_WIDTH - 40, DEFAULT_HEIGHT - 140);

        mainContentTable.add(scrollPane)
            .width(DEFAULT_WIDTH - 40)
            .height(DEFAULT_HEIGHT - 140)
            .pad(10).row();

        TextButton closeButton = new TextButton("❌ Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        mainContentTable.add(closeButton).pad(10).row();

        w.add(mainContentTable).expand().fill().row();
    }

    private void createNoFriendshipContent(Table contentTable) {
        Table noFriendshipTable = new Table();

        Label noFriendshipLabel = new Label("❌ No friendship established", skin);
        noFriendshipLabel.setColor(Color.ORANGE);
        noFriendshipLabel.setFontScale(1.1f);
        noFriendshipTable.add(noFriendshipLabel).pad(20).row();

        Label suggestionLabel = new Label("💡 Talk to " + targetNPC.getName() + " to start a friendship!", skin);
        suggestionLabel.setColor(Color.LIGHT_GRAY);
        suggestionLabel.setWrap(true);
        noFriendshipTable.add(suggestionLabel).width(300).pad(10).row();

        Label tipLabel = new Label("🎯 Friendship Tips:\n• Talk daily for points\n• Give gifts for bonus points\n• Some items are favorites!", skin);
        tipLabel.setColor(Color.CYAN);
        tipLabel.setFontScale(0.9f);
        tipLabel.setWrap(true);
        noFriendshipTable.add(tipLabel).width(300).pad(15);

        contentTable.add(noFriendshipTable).expand().center().row();
    }

    private void createFriendshipContent(Table contentTable, NPCFriendShip friendship) {
        int currentLevel = friendship.getLevel();
        int currentPoints = friendship.getPoints();
        int maxLevel = NPCFriendShip.MAX_POINTS / NPCFriendShip.POINTS_PER_LEVEL;

        Table levelCard = createLevelCard(currentLevel, currentPoints, maxLevel);
        contentTable.add(levelCard).fillX().pad(10).row();

        if (currentLevel < maxLevel) {
            Table progressCard = createProgressCard(currentLevel, currentPoints);
            contentTable.add(progressCard).fillX().pad(10).row();
        } else {
            Table maxLevelCard = createMaxLevelCard();
            contentTable.add(maxLevelCard).fillX().pad(10).row();
        }

        Table descriptionCard = createDescriptionCard(currentLevel);
        contentTable.add(descriptionCard).fillX().pad(10).row();

        Table dailyStatusCard = createDailyStatusCard(friendship);
        contentTable.add(dailyStatusCard).fillX().pad(10).row();

        Table tipsCard = createTipsCard(friendship);
        contentTable.add(tipsCard).fillX().pad(10).row();
    }

    private Table createLevelCard(int currentLevel, int currentPoints, int maxLevel) {
        Table card = new Table(skin);
        card.pad(15);

        Label levelTitle = new Label("📊 Friendship Statistics", skin);
        levelTitle.setFontScale(1.0f);
        levelTitle.setColor(Color.GOLD);
        card.add(levelTitle).left().row();

        Table statsTable = new Table();

        Label levelLabel = new Label("Level: " + currentLevel + " / " + maxLevel, skin);
        levelLabel.setFontScale(1.1f);
        levelLabel.setColor(getLevelColor(currentLevel));
        statsTable.add(levelLabel).left().pad(5).row();

        Label pointsLabel = new Label("Total Points: " + currentPoints, skin);
        pointsLabel.setFontScale(0.95f);
        statsTable.add(pointsLabel).left().pad(5);

        card.add(statsTable).left().pad(10);
        return card;
    }

    private Table createProgressCard(int currentLevel, int currentPoints) {
        Table card = new Table(skin);
        card.pad(15);

        Label progressTitle = new Label("📈 Progress to Next Level", skin);
        progressTitle.setFontScale(1.0f);
        progressTitle.setColor(Color.CYAN);
        card.add(progressTitle).left().row();

        int progressInCurrentLevel = currentPoints - (currentLevel * NPCFriendShip.POINTS_PER_LEVEL);
        float progressPercent = (float) progressInCurrentLevel / NPCFriendShip.POINTS_PER_LEVEL;
        int pointsNeeded = NPCFriendShip.POINTS_PER_LEVEL - progressInCurrentLevel;

        ProgressBar progressBar = new ProgressBar(0, 1, 0.01f, false, skin);
        progressBar.setValue(progressPercent);

        Table progressTable = new Table();
        Label nextLevelLabel = new Label("Progress to Level " + (currentLevel + 1) + ":", skin);
        nextLevelLabel.setFontScale(0.9f);
        progressTable.add(nextLevelLabel).left().pad(2).row();

        progressTable.add(progressBar).width(250).height(20).pad(5).row();

        Label progressText = new Label(progressInCurrentLevel + " / " + NPCFriendShip.POINTS_PER_LEVEL + " points", skin);
        progressText.setFontScale(0.85f);
        progressText.setColor(Color.LIGHT_GRAY);
        progressTable.add(progressText).left().pad(2).row();

        Label neededLabel = new Label("⏳ " + pointsNeeded + " points needed", skin);
        neededLabel.setColor(Color.ORANGE);
        neededLabel.setFontScale(0.9f);
        progressTable.add(neededLabel).left().pad(2);

        card.add(progressTable).left().pad(10);
        return card;
    }

    private Table createMaxLevelCard() {
        Table card = new Table(skin);
        card.pad(15);

        Label maxTitle = new Label("🏆 Maximum Friendship Level!", skin);
        maxTitle.setFontScale(1.1f);
        maxTitle.setColor(Color.GOLD);
        card.add(maxTitle).left().row();

        Label congratsLabel = new Label("🎉 You've reached the highest friendship level possible!", skin);
        congratsLabel.setColor(Color.GREEN);
        congratsLabel.setFontScale(0.9f);
        congratsLabel.setWrap(true);
        card.add(congratsLabel).width(350).left().pad(10);

        return card;
    }

    private Table createDescriptionCard(int currentLevel) {
        Table card = new Table(skin);
        card.pad(15);

        Label descTitle = new Label("💭 Relationship Status", skin);
        descTitle.setFontScale(1.0f);
        descTitle.setColor(Color.MAGENTA);
        card.add(descTitle).left().row();

        String levelDescription = getFriendshipDescription(currentLevel);
        Label descLabel = new Label(levelDescription, skin);
        descLabel.setWrap(true);
        descLabel.setColor(getLevelColor(currentLevel));
        descLabel.setFontScale(0.95f);
        card.add(descLabel).width(350).left().pad(10);

        return card;
    }

    private Table createDailyStatusCard(NPCFriendShip friendship) {
        Table card = new Table(skin);
        card.pad(15);

        Label dailyTitle = new Label("Today's Status", skin);
        dailyTitle.setFontScale(1.0f);
        dailyTitle.setColor(Color.SKY);
        card.add(dailyTitle).left().row();

        Table statusTable = new Table();

        String talkStatus = friendship.hasTalkedToday() ? "Already talked today" : "Haven't talked today";
        Label talkLabel = new Label(talkStatus, skin);
        talkLabel.setColor(friendship.hasTalkedToday() ? Color.GREEN : Color.ORANGE);
        talkLabel.setFontScale(0.9f);
        statusTable.add(talkLabel).left().pad(3).row();

        String giftStatus = friendship.hasGiftedToday() ? "Already gave gift today" : "Haven't given gift today";
        Label giftLabel = new Label(giftStatus, skin);
        giftLabel.setColor(friendship.hasGiftedToday() ? Color.GREEN : Color.ORANGE);
        giftLabel.setFontScale(0.9f);
        statusTable.add(giftLabel).left().pad(3).row();

        if (friendship.getLevel() >= 3) {
            String dailyGiftStatus = friendship.hasReceivedDailyGift() ? "Received daily gift" : "No daily gift yet";
            Label dailyGiftLabel = new Label(dailyGiftStatus, skin);
            dailyGiftLabel.setColor(friendship.hasReceivedDailyGift() ? Color.GREEN : Color.LIGHT_GRAY);
            dailyGiftLabel.setFontScale(0.9f);
            statusTable.add(dailyGiftLabel).left().pad(3);
        }

        card.add(statusTable).left().pad(10);
        return card;
    }

    private Table createTipsCard(NPCFriendShip friendship) {
        Table card = new Table(skin);
        card.pad(15);

        Label tipsTitle = new Label("💡 Friendship Tips", skin);
        tipsTitle.setFontScale(1.0f);
        tipsTitle.setColor(Color.YELLOW);
        card.add(tipsTitle).left().row();

        StringBuilder tips = new StringBuilder();
        tips.append("• Talk daily for +20 friendship points\n");
        tips.append("• Give gifts for +50 points (favorites give +200!)\n");

        if (friendship.getLevel() < 3) {
            tips.append("• Reach level 3 to receive daily gifts\n");
        } else {
            tips.append("• Level 3+ friends send you daily gifts!\n");
        }

        tips.append("• Complete quests for rewards and friendship");

        Label tipsLabel = new Label(tips.toString(), skin);
        tipsLabel.setWrap(true);
        tipsLabel.setColor(Color.LIGHT_GRAY);
        tipsLabel.setFontScale(0.85f);
        card.add(tipsLabel).width(350).left().pad(10);

        return card;
    }

    private String getFriendshipDescription(int level) {
        switch (level) {
            case 0:
                return " Strangers . Start conversations to build friendship!";
            case 1:
                return " Acquaintances . Keep talking!";
            case 2:
                return " Friends . Quests may become available!";
            case 3:
                return " Close Friends . They might send you gifts daily.";
            default:
                return " Best Friends Forever ! They regularly send you their favorite items.";
        }
    }

    private Color getLevelColor(int level) {
        switch (level) {
            case 0: return Color.GRAY;
            case 1: return Color.WHITE;
            case 2: return Color.CYAN;
            case 3: return Color.GREEN;
            default: return Color.GOLD;
        }
    }

    @Override
    public void show() {
        super.show();
    }
}

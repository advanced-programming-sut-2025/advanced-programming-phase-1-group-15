package com.example.client.models.GraphicalModels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.example.common.Game;
import com.example.common.ScoreboardInfo;
import com.example.common.npcs.DefaultNPCs;
import com.example.common.npcs.NPC;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreboardWidget extends Table {
    private final Game game;
    private final Skin skin;
    private boolean isVisible = false;
    private float updateTimer = 0f;
    private static final float UPDATE_INTERVAL = 0.5f;

    private Table scoreboardTable;
    private ScrollPane scrollPane;

    // Sorting options
    private enum SortType {
        RANK, GOLD, QUESTS, ABILITIES
    }
    private SortType currentSortType = SortType.RANK;
    private boolean sortAscending = false; // false = descending (default)

    // Color scheme for yellow background
    private static final Color HEADER_COLOR = Color.NAVY;
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color CURRENT_PLAYER_COLOR = Color.BLUE;
    private static final Color RANK_1_COLOR = new Color(0.6f, 0.2f, 0.8f, 1f); // Purple
    private static final Color RANK_2_COLOR = new Color(0.2f, 0.4f, 0.8f, 1f); // Blue
    private static final Color RANK_3_COLOR = new Color(0.8f, 0.4f, 0.2f, 1f); // Orange

    public ScoreboardWidget(Game game, Skin skin) {
        this.game = game;
        this.skin = skin;

        setupWidget();
        refreshScoreboard();
    }

    private void setupWidget() {
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));
        this.pad(10);

        Label titleLabel = new Label("LIVE SCOREBOARD", skin);
        titleLabel.setColor(HEADER_COLOR);
        titleLabel.setAlignment(Align.center);
        titleLabel.setFontScale(1.2f);

        // Sorting buttons
        Table sortButtonsTable = new Table();
        createSortButtons(sortButtonsTable);

        scoreboardTable = new Table();
        scoreboardTable.top();

        scrollPane = new ScrollPane(scoreboardTable, skin);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);

        this.add(titleLabel).colspan(2).padBottom(5).row();
        this.add(new Label("─────────────────", skin)).colspan(2).padBottom(5).row();
        this.add(sortButtonsTable).colspan(2).padBottom(5).row();
        this.add(scrollPane).width(400).height(250).colspan(2).row();

        TextButton toggleButton = new TextButton("Hide", skin);
        toggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleVisibility();
                ((TextButton) actor).setText("Hide");
            }
        });

        this.add(toggleButton).colspan(2).padTop(5);

        this.setSize(420, 350);
        this.setPosition(25, 250);
    }

    private void createSortButtons(Table buttonTable) {
        TextButton rankSortBtn = new TextButton("Rank", skin);
        TextButton goldSortBtn = new TextButton("Gold", skin);
        TextButton questSortBtn = new TextButton("Quests", skin);
        TextButton abilitySortBtn = new TextButton("Abilities", skin);

        setButtonColors(rankSortBtn);
        setButtonColors(goldSortBtn);
        setButtonColors(questSortBtn);
        setButtonColors(abilitySortBtn);

        rankSortBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleSort(SortType.RANK);
            }
        });

        goldSortBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleSort(SortType.GOLD);
            }
        });

        questSortBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleSort(SortType.QUESTS);
            }
        });

        abilitySortBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleSort(SortType.ABILITIES);
            }
        });

        buttonTable.add(rankSortBtn).padRight(3);
        buttonTable.add(goldSortBtn).padRight(3);
        buttonTable.add(questSortBtn).padRight(3);
        buttonTable.add(abilitySortBtn);
    }

    private void setButtonColors(TextButton button) {
        button.setColor(TEXT_COLOR);
    }

    private void toggleSort(SortType sortType) {
        if (currentSortType == sortType) {
            sortAscending = !sortAscending;
        } else {
            currentSortType = sortType;
            sortAscending = false;
        }
        refreshScoreboard();
    }

    public void update(float delta) {
        updateTimer += delta;
        if (updateTimer >= UPDATE_INTERVAL) {
            refreshScoreboard();
            updateTimer = 0f;
        }
    }

    private void refreshScoreboard() {
        scoreboardTable.clear();

        scoreboardTable.add(createHeaderLabel("Rank")).width(45).padRight(3);
        scoreboardTable.add(createHeaderLabel("Player")).width(80).padRight(3);
        scoreboardTable.add(createHeaderLabel("Gold")).width(60).padRight(3);
        scoreboardTable.add(createHeaderLabel("Quests")).width(60).padRight(3);
        scoreboardTable.add(createHeaderLabel("Avg Abilities")).width(80);
        scoreboardTable.row();

        scoreboardTable.add(new Label("─", skin)).colspan(5).padBottom(3).row();

        ArrayList<ScoreboardInfo> scoreboard = new ArrayList<>(game.getScoreboardInfoList());
        sortScoreboard(scoreboard);

        for (int i = 0; i < scoreboard.size(); i++) {
            ScoreboardInfo info = scoreboard.get(i);
            int displayRank = currentSortType == SortType.RANK ? i + 1 : getOriginalRank(info);
            addPlayerRow(displayRank, info, i + 1);
        }
    }

    private int getOriginalRank(ScoreboardInfo info) {
        ArrayList<ScoreboardInfo> original = new ArrayList<>(game.getScoreboardInfoList());
        original.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        for (int i = 0; i < original.size(); i++) {
            if (original.get(i).getPlayerName().equals(info.getPlayerName())) {
                return i + 1;
            }
        }
        return 1;
    }

    private void sortScoreboard(ArrayList<ScoreboardInfo> scoreboard) {
        Comparator<ScoreboardInfo> comparator = switch (currentSortType) {
            case RANK -> Comparator.comparingInt(info -> -info.getScore());
            case GOLD -> Comparator.comparingInt(ScoreboardInfo::getGold);
            case QUESTS -> Comparator.comparingInt(this::getQuestCount);
            case ABILITIES -> Comparator.comparingDouble(this::getAverageAbilities);
        };

        if (sortAscending) {
            scoreboard.sort(comparator);
        } else {
            scoreboard.sort(comparator.reversed());
        }
    }

    private int getQuestCount(ScoreboardInfo info) {
        int questCount = 0;
        for(NPC npc : DefaultNPCs.getInstance().defaultOnes.values()) {
            questCount += npc.getFriendships().get(game.getCurrentPlayer()).getPlayerQuests().size();
        }
        return questCount;
    }

    private double getAverageAbilities(ScoreboardInfo info) {
        return (info.getFarmingLevel() + info.getMiningLevel() +
            info.getForagingLevel() + info.getFishingLevel()) / 4.0;
    }

    private Label createHeaderLabel(String text) {
        Label label = new Label(text, skin);
        label.setColor(HEADER_COLOR);
        label.setAlignment(Align.center);
        label.setFontScale(0.8f);
        return label;
    }

    private void addPlayerRow(int originalRank, ScoreboardInfo info, int currentPosition) {
        Color rankColor = getRankColor(originalRank);

        // Rank
        Label rankLabel = new Label(String.valueOf(originalRank), skin);
        rankLabel.setColor(rankColor);
        rankLabel.setAlignment(Align.center);

        // Player name
        String displayName = info.getPlayerName();
        if (displayName.length() > 8) {
            displayName = displayName.substring(0, 7) + "...";
        }
        Label nameLabel = new Label(displayName, skin);
        nameLabel.setColor(TEXT_COLOR);

        if (info.getPlayerName().equals(game.getCurrentPlayer().getNickname())) {
            nameLabel.setColor(CURRENT_PLAYER_COLOR);
        }

        // Gold
        Label goldLabel = new Label(String.valueOf(info.getGold()), skin);
        goldLabel.setColor(getGoldColor(info.getGold()));
        goldLabel.setAlignment(Align.center);

        // Quest count
        int questCount = getQuestCount(info);
        Label questLabel = new Label(String.valueOf(questCount), skin);
        questLabel.setColor(TEXT_COLOR);
        questLabel.setAlignment(Align.center);

        // Average abilities
        double avgAbilities = getAverageAbilities(info);
        Label abilitiesLabel = new Label(String.format("%.1f", avgAbilities), skin);
        abilitiesLabel.setColor(getAbilitiesColor(avgAbilities));
        abilitiesLabel.setAlignment(Align.center);

        scoreboardTable.add(rankLabel).width(45).padRight(3);
        scoreboardTable.add(nameLabel).width(80).padRight(3);
        scoreboardTable.add(goldLabel).width(60).padRight(3);
        scoreboardTable.add(questLabel).width(60).padRight(3);
        scoreboardTable.add(abilitiesLabel).width(80);
        scoreboardTable.row();

        // abilities for top 3 players
        if (originalRank <= 3) {
            Label skillsLabel = new Label(
                String.format("F:%d M:%d Fo:%d Fi:%d",
                    info.getFarmingLevel(), info.getMiningLevel(),
                    info.getForagingLevel(), info.getFishingLevel()),
                skin);
            skillsLabel.setColor(new Color(0.4f, 0.4f, 0.4f, 1f)); // gray
            skillsLabel.setFontScale(0.7f);
            skillsLabel.setAlignment(Align.center);

            scoreboardTable.add(skillsLabel).colspan(5).padBottom(3).row();
        }

        // Add energy info
        int energyPercent = (int) info.getEnergy();
        Label energyLabel = new Label("Energy: " + energyPercent + "%", skin);
        energyLabel.setColor(getEnergyColor(energyPercent));
        energyLabel.setFontScale(0.7f);
        energyLabel.setAlignment(Align.center);

        scoreboardTable.add(energyLabel).colspan(5).padBottom(5).row();
    }

    private Color getRankColor(int rank) {
        return switch (rank) {
            case 1 -> RANK_1_COLOR;
            case 2 -> RANK_2_COLOR;
            case 3 -> RANK_3_COLOR;
            default -> TEXT_COLOR;
        };
    }

    private Color getGoldColor(int gold) {
        if (gold >= 1000) return new Color(0.8f, 0.6f, 0.0f, 1f); // gold
        if (gold >= 500) return new Color(0.6f, 0.4f, 0.0f, 1f); // Medium gold
        return TEXT_COLOR;
    }

    private Color getEnergyColor(int energy) {
        if (energy >= 70) return new Color(0.0f, 0.6f, 0.0f, 1f); // green
        if (energy >= 40) return new Color(0.8f, 0.6f, 0.0f, 1f); // yellow
        return new Color(0.8f, 0.0f, 0.0f, 1f); // Dark red
    }

    private Color getAbilitiesColor(double average) {
        if (average >= 15.0) return new Color(0.6f, 0.0f, 0.8f, 1f); // Purple
        if (average >= 10.0) return new Color(0.0f, 0.4f, 0.8f, 1f); // Blue
        if (average >= 5.0) return new Color(0.8f, 0.4f, 0.0f, 1f); // Orange
        return TEXT_COLOR;
    }

    public void toggleVisibility() {
        isVisible = !isVisible;
        this.setVisible(isVisible);
    }
}

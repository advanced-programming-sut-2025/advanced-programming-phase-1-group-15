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

import java.util.ArrayList;

public class ScoreboardWidget extends Table {
    private final Game game;
    private final Skin skin;
    private boolean isVisible = false;
    private float updateTimer = 0f;
    private static final float UPDATE_INTERVAL = 0.5f;

    private Table scoreboardTable;
    private ScrollPane scrollPane;

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
        titleLabel.setColor(Color.GOLD);
        titleLabel.setAlignment(Align.center);
        titleLabel.setFontScale(1.2f);

        scoreboardTable = new Table();
        scoreboardTable.top();

        scrollPane = new ScrollPane(scoreboardTable, skin);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);

        this.add(titleLabel).colspan(2).padBottom(5).row();
        this.add(new Label("─────────────────", skin)).colspan(2).padBottom(5).row();
        this.add(scrollPane).width(300).height(200).colspan(2).row();

        TextButton toggleButton = new TextButton("Hide", skin);
        toggleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleVisibility();
                ((TextButton) actor).setText(isVisible ? "Hide" : "Show");
            }
        });

        this.add(toggleButton).colspan(2).padTop(5);

        this.setSize(320, 280);
        this.setPosition(75, 500);
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

        scoreboardTable.add(createHeaderLabel("Rank")).width(40).padRight(5);
        scoreboardTable.add(createHeaderLabel("Player")).width(80).padRight(5);
        scoreboardTable.add(createHeaderLabel("Score")).width(60).padRight(5);
        scoreboardTable.add(createHeaderLabel("Gold")).width(50).padRight(5);
        scoreboardTable.add(createHeaderLabel("Energy")).width(50);
        scoreboardTable.row();

        scoreboardTable.add(new Label("─", skin)).colspan(5).padBottom(3).row();

        ArrayList<ScoreboardInfo> scoreboard = game.getScoreboardInfoList();

        for (int i = 0; i < scoreboard.size(); i++) {
            ScoreboardInfo info = scoreboard.get(i);
            addPlayerRow(i + 1, info);
        }
    }

    private Label createHeaderLabel(String text) {
        Label label = new Label(text, skin);
        label.setColor(Color.YELLOW);
        label.setAlignment(Align.center);
        label.setFontScale(0.8f);
        return label;
    }

    private void addPlayerRow(int rank, ScoreboardInfo info) {
        Color rankColor = getRankColor(rank);

        Label rankLabel = new Label(String.valueOf(rank), skin);
        rankLabel.setColor(rankColor);
        rankLabel.setAlignment(Align.center);

        String displayName = info.getPlayerName();
        if (displayName.length() > 8) {
            displayName = displayName.substring(0, 7) + "...";
        }
        Label nameLabel = new Label(displayName, skin);
        nameLabel.setColor(Color.WHITE);

        if (info.getPlayerName().equals(game.getCurrentPlayer().getNickname())) {
            nameLabel.setColor(Color.CYAN);
        }

        Label scoreLabel = new Label(String.valueOf(info.getScore()), skin);
        scoreLabel.setColor(Color.WHITE);
        scoreLabel.setAlignment(Align.center);

        Label goldLabel = new Label(String.valueOf(info.getGold()), skin);
        goldLabel.setColor(Color.GOLD);
        goldLabel.setAlignment(Align.center);

        int energyPercent = (int) info.getEnergy();
        Label energyLabel = new Label(energyPercent + "%", skin);
        energyLabel.setColor(getEnergyColor(energyPercent));
        energyLabel.setAlignment(Align.center);

        scoreboardTable.add(rankLabel).width(40).padRight(5);
        scoreboardTable.add(nameLabel).width(80).padRight(5);
        scoreboardTable.add(scoreLabel).width(60).padRight(5);
        scoreboardTable.add(goldLabel).width(50).padRight(5);
        scoreboardTable.add(energyLabel).width(50);
        scoreboardTable.row();

        if (rank <= 3) {
            Label skillsLabel = new Label(
                String.format("F:%d M:%d Fo:%d Fi:%d",
                    info.getFarmingLevel(), info.getMiningLevel(),
                    info.getForagingLevel(), info.getFishingLevel()),
                skin);
            skillsLabel.setColor(Color.LIGHT_GRAY);
            skillsLabel.setFontScale(0.7f);
            skillsLabel.setAlignment(Align.center);

            scoreboardTable.add(skillsLabel).colspan(5).padBottom(3).row();
        }
    }

    private Color getRankColor(int rank) {
        return switch (rank) {
            case 1 -> Color.GOLD;
            case 2 -> Color.LIGHT_GRAY;
            case 3 -> new Color(0.8f, 0.5f, 0.2f, 1f);
            default -> Color.WHITE;
        };
    }

    private Color getEnergyColor(int energy) {
        if (energy >= 70) return Color.GREEN;
        if (energy >= 40) return Color.YELLOW;
        return Color.RED;
    }

    public void toggleVisibility() {
        isVisible = !isVisible;
        this.setVisible(isVisible);
    }

    public void setScoreboardVisible(boolean visible) {
        isVisible = visible;
        this.setVisible(visible);
    }

    public boolean isScoreboardVisible() {
        return isVisible;
    }
}

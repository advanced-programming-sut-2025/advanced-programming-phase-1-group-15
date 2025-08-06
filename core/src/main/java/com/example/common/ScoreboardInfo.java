package com.example.common;

import java.util.Comparator;
import java.util.Objects;

public class ScoreboardInfo {
    private String playerName;
    private int score;
    private int gold;
    private double energy;
    private int farmingLevel;
    private int miningLevel;
    private int foragingLevel;
    private int fishingLevel;

    public ScoreboardInfo(String playerName, int score, int gold, double energy, int farmingLevel, int miningLevel, int foragingLevel, int fishingLevel) {
        this.playerName = playerName;
        this.score = score;
        this.gold = gold;
        this.energy = energy;
        this.farmingLevel = farmingLevel;
        this.miningLevel = miningLevel;
        this.foragingLevel = foragingLevel;
        this.fishingLevel = fishingLevel;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public int getGold() {
        return gold;
    }

    public double getEnergy() {
        return energy;
    }

    public int getFarmingLevel() {
        return farmingLevel;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public int getForagingLevel() {
        return foragingLevel;
    }

    public int getFishingLevel() {
        return fishingLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreboardInfo that = (ScoreboardInfo) o;
        return Objects.equals(playerName, that.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName);
    }
}

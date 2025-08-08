package com.example.common.GroupQuests;

import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.time.DateAndTime;
import com.example.common.time.TimeObserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GroupQuest implements TimeObserver {
    private String questId;
    private String title;
    private String description;
    private QuestType type;
    private QuestStatus status;
    private int requiredPlayers;
    private int maxPlayers;
    private int targetAmount;
    private int rewardPerPlayer;
    private int timeLimit;
    private int startday;
    private int endDay;

    private Set<String> participantUsernames;
    private HashMap<Player, Integer> playerProgress;
    private String creatorUsername;

    public GroupQuest(String questId, String title, String description, QuestType type,
                      int requiredPlayers, int maxPlayers, int targetAmount,
                      int rewardPerPlayer, int timeLimit) {
        this.questId = questId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.status = QuestStatus.AVAILABLE;
        this.requiredPlayers = requiredPlayers;
        this.maxPlayers = maxPlayers;
        this.targetAmount = targetAmount;
        this.rewardPerPlayer = rewardPerPlayer;
        this.timeLimit = timeLimit;
        this.participantUsernames = new HashSet<>();
        this.playerProgress = new HashMap<>();
    }

    public boolean canJoin(String username) {
        return status == QuestStatus.AVAILABLE &&
            !participantUsernames.contains(username) &&
            participantUsernames.size() < maxPlayers;
    }

    public boolean addPlayer(Player player) {
        if (canJoin(player.getUsername())) {
            participantUsernames.add(player.getUsername());
            playerProgress.put(player, 0);
            return true;
        }
        return false;
    }

    public void removePlayer(Player player) {
        participantUsernames.remove(player.getUsername());
        playerProgress.remove(player);
        if (participantUsernames.size() < requiredPlayers) {
            status = QuestStatus.AVAILABLE;
        }
    }

    public boolean activate() {
        if (participantUsernames.size() >= requiredPlayers) {
            status = QuestStatus.ACTIVE;
            startday= ClientApp.currentGame.getDateAndTime().getDay();
            endDay = startday+ timeLimit;
            return true;
        }
        return false;
    }

    public void updateProgress(Player player, int amount) {
        if (status == QuestStatus.ACTIVE && participantUsernames.contains(player.getUsername())) {
            playerProgress.put(player, playerProgress.get(player) + amount);
            checkCompletion();
        }
    }

    private void checkCompletion() {
        int totalProgress = playerProgress.values().stream().mapToInt(Integer::intValue).sum();
        if (totalProgress >= targetAmount) {
            status = QuestStatus.COMPLETED;
        }
    }


    public double getCompletionPercentage() {
        int totalProgress = playerProgress.values().stream().mapToInt(Integer::intValue).sum();
        return Math.min(100.0, (double) totalProgress / targetAmount * 100.0);
    }

    public double getPlayerCompletionPercentage(Player player) {
        int playerContribution = playerProgress.getOrDefault(player, 0);
        return Math.min(100.0, (double) playerContribution / targetAmount * 100.0);
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

package com.example.common.GroupQuests;

import com.example.client.models.ClientApp;
import com.example.common.Player;

import java.util.*;
import java.util.stream.Collectors;

public class GroupQuestManager {
    private Map<String, GroupQuest> allQuests;
    private Map<String, Set<String>> playerActiveQuests;
    private static final int MAX_QUESTS_PER_PLAYER = 3;
    private static final int TOTAL_PRIMARY_QUESTS = 8;

    public GroupQuestManager() {
        this.allQuests = new HashMap<>();
        this.playerActiveQuests = new HashMap<>();
        initializePrimaryQuests();
    }

    private void initializePrimaryQuests() {
        GroupQuest[] primaryQuests = {};
        for (GroupQuest quest : primaryQuests) {
            allQuests.put(quest.getQuestId(), quest);
        }
    }

    public List<GroupQuest> getAvailableQuests() {
        return allQuests.values().stream()
            .filter(quest -> quest.getStatus() == QuestStatus.AVAILABLE)
            .collect(Collectors.toList());
    }

    public List<GroupQuest> getPlayerActiveQuests(String username) {
        Set<String> questIds = playerActiveQuests.getOrDefault(username, new HashSet<>());
        return questIds.stream()
            .map(allQuests::get)
            .filter(Objects::nonNull)
            .filter(quest -> quest.getStatus() == QuestStatus.ACTIVE)
            .collect(Collectors.toList());
    }

    public boolean canPlayerJoinMoreQuests(String username) {
        Set<String> activeQuests = playerActiveQuests.getOrDefault(username, new HashSet<>());
        long actualActiveCount = activeQuests.stream()
            .map(allQuests::get)
            .filter(Objects::nonNull)
            .filter(quest -> quest.getStatus() == QuestStatus.ACTIVE)
            .count();

        return actualActiveCount < MAX_QUESTS_PER_PLAYER;
    }

    public boolean joinQuest(String questId, Player player) {
        GroupQuest quest = allQuests.get(questId);
        if (quest == null || !canPlayerJoinMoreQuests(player.getUsername())) {
            return false;
        }

        if (quest.addPlayer(player)) {
            playerActiveQuests.computeIfAbsent(player.getUsername(), k -> new HashSet<>())
                .add(questId);

            quest.activate();

            return true;
        }
        return false;
    }

    public boolean leaveQuest(String questId, Player player) {
        GroupQuest quest = allQuests.get(questId);
        if (quest == null) {
            return false;
        }

        quest.removePlayer(player);

        Set<String> userQuests = playerActiveQuests.get(player.getUsername());
        if (userQuests != null) {
            userQuests.remove(questId);
        }

        return true;
    }

    public void updateQuestProgress(String questId, Player player, int amount) {
        GroupQuest quest = allQuests.get(questId);
        if (quest != null) {
            quest.updateProgress(player, amount);

            if (quest.getStatus() == QuestStatus.COMPLETED) {
                distributeRewards(quest);
                cleanupCompletedQuest(questId);
            }
        }
    }

    private void distributeRewards(GroupQuest quest) {
        for (String username : quest.getParticipantUsernames()) {
            Player player = getPlayerByUsername(username);
            if (player != null) {
                // player.addCoins(quest.getRewardPerPlayer());

                // TODO : send notification to player
                System.out.println("Player " + username + " received " +
                    quest.getRewardPerPlayer() + " coins from completing quest: " + quest.getTitle());
            }
        }
    }

    private void cleanupCompletedQuest(String questId) {
        GroupQuest quest = allQuests.get(questId);
        if (quest == null) return;

        for (String username : quest.getParticipantUsernames()) {
            Set<String> userQuests = playerActiveQuests.get(username);
            if (userQuests != null) {
                userQuests.remove(questId);
            }
        }

        resetQuestToAvailable(questId);
    }

    private void resetQuestToAvailable(String questId) {
        GroupQuest oldQuest = allQuests.get(questId);
        if (oldQuest == null) return;

        GroupQuest newQuest = new GroupQuest(
            oldQuest.getQuestId(),
            oldQuest.getTitle(),
            oldQuest.getDescription(),
            oldQuest.getRequiredPlayers(),
            oldQuest.getMaxPlayers(),
            oldQuest.getTargetAmount(),
            oldQuest.getRewardPerPlayer(),
            oldQuest.getTimeLimit()
        );

        allQuests.put(questId, newQuest);
    }

    public void checkQuestTimeouts() {
        int currentDay = getCurrentDay();

        for (GroupQuest quest : allQuests.values()) {
            if (quest.getStatus() == QuestStatus.ACTIVE &&
                currentDay > quest.getEndDay()) {

                quest.setStatus(QuestStatus.FAILED);
                cleanupFailedQuest(quest.getQuestId());
            }
        }
    }

    private void cleanupFailedQuest(String questId) {
        GroupQuest quest = allQuests.get(questId);
        if (quest == null) return;

        for (String username : quest.getParticipantUsernames()) {
            Set<String> userQuests = playerActiveQuests.get(username);
            if (userQuests != null) {
                userQuests.remove(questId);
            }
        }

        resetQuestToAvailable(questId);
    }

    public int getCurrentDay() {
        if (ClientApp.currentGame != null && ClientApp.currentGame.getDateAndTime() != null) {
            return ClientApp.currentGame.getDateAndTime().getDay();
        }
        return 0;
    }

    public Player getPlayerByUsername(String username) {
        return ClientApp.currentGame.getPlayerByUsername(username);
    }

    public GroupQuest getQuest(String questId) {
        return allQuests.get(questId);
    }

    public Map<String, GroupQuest> getAllQuests() {
        return new HashMap<>(allQuests);
    }

    public void update() {
        checkQuestTimeouts();
    }

    public void printQuestStatus() {
        System.out.println("=== Quest Status ===");
        for (GroupQuest quest : allQuests.values()) {
            System.out.printf("Quest: %s | Status: %s | Players: %d/%d | Progress: %.1f%%\n",
                quest.getTitle(), quest.getStatus(),
                quest.getParticipantUsernames().size(), quest.getMaxPlayers(),
                quest.getCompletionPercentage());
        }
    }
}

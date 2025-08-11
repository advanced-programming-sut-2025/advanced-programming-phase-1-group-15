package com.example.common.GroupQuests;

import com.example.client.NetworkClient;
import com.example.client.models.ClientApp;
import com.example.common.Message;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.relation.PlayerFriendship;
import com.example.common.tools.BackPackable;

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
        GroupQuest[] primaryQuests = {
            new GroupQuest("MILK_PALE", "milk_pale",
                "Collect 20 milk_pales together to make your own milk",
                3, 3, 20, 150, 7),

            new GroupQuest("STONE", "stone",
                "Mine 300 stones for the new fortress walls",
                2, 2, 300, 200, 5),

            new GroupQuest("SALMON", "salmon",
                "Catch 100 salmons to feed the hungry townspeople",
                2, 2, 30, 100, 4),

            new GroupQuest("ORANGE", "orange",
                "Harvest 400 oranges before the season ends",
                3, 3, 20, 120, 6),

            new GroupQuest("COMMON_MUSHROOM", "common_mushroom",
                "Gather 150 common mushrooms from the dangerous mountain mines",
                4, 4, 30, 300, 8),

            new GroupQuest("GARLIC", "garlic",
                "Gather 250 garlic for the kingdom's medicine supply",
                2, 2, 20, 80, 5),

            new GroupQuest("EGG", "egg",
                "Collect 100 eggs for the royal kitchen",
                3, 3, 20, 250, 6),

            new GroupQuest("AXE", "axe",
                "Find 50 axes for the kingdom's lumberjacks",
                2, 2, 30, 500, 10)
        };

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

        // Count both ACTIVE and AVAILABLE quests that the player has joined
        long totalActiveQuests = activeQuests.stream()
            .map(allQuests::get)
            .filter(Objects::nonNull)
            .filter(quest -> quest.getStatus() == QuestStatus.ACTIVE ||
                (quest.getStatus() == QuestStatus.AVAILABLE &&
                    quest.getParticipantUsernames().contains(username)))
            .count();

        return totalActiveQuests < MAX_QUESTS_PER_PLAYER;
    }

    public boolean joinQuest(String questId, Player player) {
        GroupQuest quest = allQuests.get(questId);
        if (quest == null || !canPlayerJoinMoreQuests(player.getUsername())) {
            return false;
        }

        if (quest.addPlayer(player)) {
            playerActiveQuests.computeIfAbsent(player.getUsername(), k -> new HashSet<>())
                .add(questId);

            sendJoinQuestMessage(questId, player.getUsername());

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

        sendLeaveQuestMessage(questId, player.getUsername());

        return true;
    }

    public void updateQuestProgress(String questId, Player player, int amount) {
        GroupQuest quest = allQuests.get(questId);
        if (quest != null) {
            quest.updateProgress(player, amount);
            sendQuestProgressMessage(questId, player.getUsername(), amount);
            if (quest.getStatus() == QuestStatus.COMPLETED) {
                distributeRewards(quest);
                cleanupCompletedQuest(questId);
            }
        }
    }

    public Result deliverItems(String questId, Player player, int amount) {
        GroupQuest quest = allQuests.get(questId);
        if (quest == null) {
            return new Result(false, "Quest not found");
        }

        if (quest.getStatus() != QuestStatus.ACTIVE) {
            return new Result(false, "Quest is not active");
        }

        if (!quest.getParticipantUsernames().contains(player.getUsername())) {
            return new Result(false, "You are not part of this quest");
        }

        if (amount <= 0) {
            return new Result(false, "Amount must be greater than 0");
        }

        BackPackable item = player.getInventory().getItemByName(quest.getTitle());
        int count = player.getInventory().getItemCount(quest.getTitle());
        if (item == null || count < amount) {
            int availableCount = item != null ? count : 0;
            return new Result(false,
                String.format("Insufficient items. You have %d but need %d", availableCount, amount));
        }
        player.getInventory().removeCountFromBackPack(item, amount);
        quest.updateProgress(player, amount);

        sendQuestProgressMessage(questId, player.getUsername(), amount);

        if (quest.getStatus() == QuestStatus.COMPLETED) {
            distributeRewards(quest);
            cleanupCompletedQuest(questId);
            return new Result(true,
                String.format("Successfully delivered %d items! Quest completed!", amount));
        }

        return new Result(true,
            String.format("Successfully delivered %d items!", amount));
    }

    public int getMaxDeliverable(String questId, Player player) {
        GroupQuest quest = allQuests.get(questId);
        if (quest == null) return 0;

        BackPackable item = player.getInventory().getItemByName(quest.getTitle());
        int availableItems = item != null ? player.getInventory().getItemCount(quest.getTitle()) : 0;

        int totalProgress = quest.getPlayerProgress().values().stream().mapToInt(Integer::intValue).sum();
        int remainingTarget = quest.getTargetAmount() - totalProgress;

        return Math.min(availableItems, Math.max(0, remainingTarget));
    }

    private void distributeRewards(GroupQuest quest) {
        for (String username : quest.getParticipantUsernames()) {
            Player player = getPlayerByUsername(username);
            if (player != null) {
                player.addGold(quest.getRewardPerPlayer());
                sendQuestRewardMessage(quest.getQuestId(), username, quest.getRewardPerPlayer());
                System.out.println("Player " + username + " received " +
                    quest.getRewardPerPlayer() + " coins from completing quest: " + quest.getDescription());
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

    private void sendJoinQuestMessage(String questId, String username) {
        HashMap<String, Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "joinQuest");
        cmdBody.put("questId", questId);
        cmdBody.put("username", username);
        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    private void sendLeaveQuestMessage(String questId, String username) {
        HashMap<String, Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "leaveQuest");
        cmdBody.put("questId", questId);
        cmdBody.put("username", username);
        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    private void sendQuestProgressMessage(String questId, String username, int amount) {
        HashMap<String, Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "questProgress");
        cmdBody.put("questId", questId);
        cmdBody.put("username", username);
        cmdBody.put("amount", amount);
        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    private void sendQuestRewardMessage(String questId, String username, int reward) {
        HashMap<String, Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "questReward");
        cmdBody.put("questId", questId);
        cmdBody.put("username", username);
        cmdBody.put("reward", reward);
        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    private void sendQuestFailedMessage(String questId, String username) {
        HashMap<String, Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "questFailed");
        cmdBody.put("questId", questId);
        cmdBody.put("username", username);
        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public void handleJoinQuest(String questId, String username) {
        GroupQuest quest = allQuests.get(questId);
        Player player = getPlayerByUsername(username);

        if (quest != null && player != null && quest.canJoin(username)) {
            quest.addPlayer(player);
            playerActiveQuests.computeIfAbsent(username, k -> new HashSet<>()).add(questId);
            quest.activate();
        }
    }

    public void handleLeaveQuest(String questId, String username) {
        GroupQuest quest = allQuests.get(questId);
        Player player = getPlayerByUsername(username);

        if (quest != null && player != null) {
            quest.removePlayer(player);
            Set<String> userQuests = playerActiveQuests.get(username);
            if (userQuests != null) {
                userQuests.remove(questId);
            }
        }
    }

    public void handleQuestProgress(String questId, String username, int amount) {
        GroupQuest quest = allQuests.get(questId);
        Player player = getPlayerByUsername(username);

        if (quest != null && player != null) {
            quest.updateProgress(player, amount);
            BackPackable item = player.getInventory().getItemByName(quest.getTitle());
            if (item != null) {
                player.getInventory().removeCountFromBackPack(item, amount);
                if (quest.getStatus() == QuestStatus.COMPLETED) {
                    distributeRewards(quest);
                    cleanupCompletedQuest(questId);
                }
            }
        }
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

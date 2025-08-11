package com.example.common.npcs;

import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.tools.BackPackable;

import java.util.HashMap;
import java.util.Map;
import com.example.common.time.DateAndTime;

public class NPCFriendShip {
    NPC npc;
    Player player;
    private boolean receivedDailyGift = false;
    private int lastQuestResetDay = -1;

    private DateAndTime levelOneReachedDate = null;

    public NPCFriendShip(NPC npc, Player player) {
        this.npc = npc;
        this.player = player;
        activateQuests();
    }

    public int getPoints() {
        return points;
    }

    private int points = 0;
    private boolean talkedToday = false;
    private boolean giftedToday = false;

    public static final int MAX_POINTS = 799;
    public static final int POINTS_PER_LEVEL = 200;

    private HashMap<Quest,Boolean> playerQuests = new HashMap<>();

    void addPoints(int points) {
        int oldLevel = getLevel();
        this.points += points;
        if(this.points > MAX_POINTS) {
            this.points = MAX_POINTS;
        }
        int newLevel = getLevel();

        if (oldLevel < 1 && newLevel >= 1) {
            levelOneReachedDate = new DateAndTime();
            levelOneReachedDate.setDay(ClientApp.currentGame.getDateAndTime().getDay());
        }

        if (newLevel > oldLevel) {
            activateQuests();
        }
    }

    public int getLevel() {
        return points / POINTS_PER_LEVEL;
    }

    public boolean hasTalkedToday() {
        return talkedToday;
    }

    public boolean hasGiftedToday() {
        return giftedToday;
    }

    void markTalked() {
        talkedToday = true;
    }

    void markGifted() {
        giftedToday = true;
    }

    void resetDaily() {
        talkedToday = false;
        giftedToday = false;
        receivedDailyGift = false;

        int currentDay = ClientApp.currentGame.getDateAndTime().getDay();
        if (lastQuestResetDay != currentDay) {
            lastQuestResetDay = currentDay;
            activateQuests();
        }
    }

    void activateQuests() {
        int lvl = getLevel();
        DateAndTime now = ClientApp.currentGame.getDateAndTime();

        for (Quest quest : npc.questTemplates.keySet()) {
            int required = npc.questTemplates.get(quest);

            if (playerQuests.containsKey(quest) && playerQuests.get(quest)) {
                continue;
            }

            boolean shouldActivate = false;

            if (required == 0) {
                shouldActivate = true;
            }
            else if (required == 1 && lvl >= 1) {
                shouldActivate = true;
            }
            else if (required == 2 && lvl >= 1
                && levelOneReachedDate != null
                && levelOneReachedDate.getSeason() != now.getSeason()) {
                shouldActivate = true;
            }
            else if (required == 3 && lvl >= 2) {
                shouldActivate = true;
            }
            if (shouldActivate) {
                if (!quest.isDoneBySomeone()) {
                    playerQuests.put(quest, true);
                }
            }
        }
    }

    public HashMap<Quest, Boolean> getPlayerQuests() {
        return playerQuests;
    }

    String showQuests() {
        StringBuilder sj = new StringBuilder("\n");
        int questNumber = 1;
        for (Quest pq : playerQuests.keySet()) {
            if (playerQuests.get(pq) && !pq.isDoneBySomeone()) {
                sj.append(questNumber).append(". Deliver ")
                    .append(pq.getRequestAmount())
                    .append(" x ").append(pq.getRequest().getName())
                    .append(" → Reward: ").append(pq.getRewardAmount())
                    .append(" x ").append(pq.getReward().getName())
                    .append("\n");
                questNumber++;
            }
        }
        return sj.toString();
    }

    public Result finishQuest(Quest quest) {
        Quest found = null;
        for (Quest pq : playerQuests.keySet()) {
            if (pq.equals(quest) && playerQuests.get(pq) && !pq.isDoneBySomeone()) {
                found = pq;
                break;
            }
        }

        if (found == null) {
            return new Result(false, "No available quest found!");
        }

        if (player.getInventory().getItemByName(found.getRequest().getName()) == null) {
            return new Result(false, "You don't have this item!");
        }

        if (player.getInventory().getItemCount(found.getRequest().getName()) < found.getRequestAmount()) {
            return new Result(false, "You don't have enough of this item!");
        }

        found.setDoneBySomeone(true);
        found.incrementCompletionCount();

        playerQuests.put(found, false);
        player.getInventory().removeCountFromBackPack(found.getRequest(), found.getRequestAmount());

        int rewardMultiplier = Math.max(1, (getLevel() + 1) / 2);
        player.getInventory().addToBackPack(found.getReward(), found.getRewardAmount() * rewardMultiplier);

        addPoints(75 + (found.getRequestAmount() * 5));

        String successMessage = String.format("Quest completed! Received %d x %s. (+%d friendship points)",
            found.getRewardAmount() * rewardMultiplier,
            found.getReward().getName(),
            75 + (found.getRequestAmount() * 5));

        return new Result(true, successMessage);
    }

    public Result finishQuest(BackPackable item) {
        Quest questToComplete = null;
        for (Quest pq : playerQuests.keySet()) {
            if (pq.getRequest().getName().equals(item.getName())
                && playerQuests.get(pq)
                && !pq.isDoneBySomeone()) {
                questToComplete = pq;
                break;
            }
        }

        if (questToComplete == null) {
            return new Result(false, "No available quest for this item!");
        }

        return finishQuest(questToComplete);
    }

    public boolean hasReceivedDailyGift() {
        return receivedDailyGift;
    }

    public void markDailyGiftReceived() {
        receivedDailyGift = true;
    }

    public boolean hasActiveQuests() {
        for (Map.Entry<Quest, Boolean> entry : playerQuests.entrySet()) {
            if (entry.getValue() && !entry.getKey().isDoneBySomeone()) {
                return true;
            }
        }
        return false;
    }

    public int getActiveQuestCount() {
        int count = 0;
        for (Map.Entry<Quest, Boolean> entry : playerQuests.entrySet()) {
            if (entry.getValue() && !entry.getKey().isDoneBySomeone()) {
                count++;
            }
        }
        return count;
    }
}

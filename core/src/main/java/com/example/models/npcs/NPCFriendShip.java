package com.example.models.npcs;

import com.example.models.App;
import com.example.models.Player;
import com.example.models.Result;
import com.example.models.tools.BackPackable;

import java.util.HashMap;
import com.example.models.time.DateAndTime;

public class NPCFriendShip {
    NPC npc;
    Player player;

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

    static final int MAX_POINTS = 799;
    static final int POINTS_PER_LEVEL = 200;

    private HashMap<Quest,Boolean> playerQuests = new HashMap<>();

    void addPoints(int points) {
        int oldLevel = getLevel();
        this.points += points;
        if(points > MAX_POINTS) {
            this.points = MAX_POINTS;
        }
        int newLevel = getLevel();

        if (oldLevel < 1 && newLevel >= 1) {
            levelOneReachedDate = new DateAndTime();
            levelOneReachedDate.setDay(App.currentGame.getDateAndTime().getDay());
        }

        activateQuests();
    }

    public int getLevel() {
        return points / POINTS_PER_LEVEL;
    }

    boolean hasTalkedToday() {
        return talkedToday;
    }

    boolean hasGiftedToday() {
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
    }

    void activateQuests() {
        int lvl = getLevel();
        DateAndTime now = App.currentGame.getDateAndTime();

        for (Quest quest : npc.questTemplates.keySet()) {
            int required = npc.questTemplates.get(quest);
            if (playerQuests.containsKey(quest)) continue;

            if (required == 0) {
                playerQuests.put(quest, true);
            }
            else if (required == 1 && lvl >= 1) {
                playerQuests.put(quest, true);
            }
            else if (required == 2 && lvl >= 1
                    && levelOneReachedDate != null
                    && levelOneReachedDate.getSeason() != now.getSeason()) {
                playerQuests.put(quest, true);
            }
        }
    }

    public HashMap<Quest, Boolean> getPlayerQuests() {
        return playerQuests;
    }

    String showQuests() {
        StringBuilder sj = new StringBuilder("\n");
        for (Quest pq : playerQuests.keySet()) {
            if (playerQuests.get(pq)) {
                sj.append("Deliver " + pq.getRequestAmount() +
                        " x " + pq.getRequest().getName() + "\n");
            }
        }
        return sj.toString();
    }

    public Result finishQuest(BackPackable item) {
        Quest found = null;

        for (Quest pq : playerQuests.keySet()) {
            if (pq.getRequest().getName().equals(item.getName())
                    && playerQuests.get(pq)
                    && !pq.isDoneBySomeone()) {
                found = pq;
                break;
            }
        }

        if (found == null) {
            return new Result(false, "no available quest for this NPC!");
        }
        if (player.getInventory().getItemByName(item.getName()) == null) {
            return new Result(false, "you don't have this item!");
        }
        if (player.getInventory().getItemCount(item.getName()) < found.getRequestAmount()) {
            return new Result(false, "you don't have enough of this item!");
        }

        found.setDoneBySomeone(true);

        playerQuests.put(found, false);

        player.getInventory().removeCountFromBackPack(item, found.getRequestAmount());
        player.getInventory()
                .addToBackPack(found.getReward(),
                        found.getRewardAmount() * ((getLevel() + 1) / 2));

        return new Result(true, "well done! quest completed!");
    }


}


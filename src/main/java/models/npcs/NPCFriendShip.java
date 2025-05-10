package models.npcs;

import models.Player;
import models.Result;
import models.tools.BackPackable;

import java.util.HashMap;
import java.util.Iterator;

public class NPCFriendShip {
    NPC npc;
    Player player;

    public NPCFriendShip(NPC npc, Player player) {
        this.npc = npc;
        this.player = player;
    }

    private int points = 0;
    private boolean talkedToday = false;
    private boolean giftedToday = false;

    static final int MAX_POINTS = 799;
    static final int POINTS_PER_LEVEL = 200;

    private HashMap<Quest,Boolean> playerQuests = new HashMap<>();

    void addPoints(int points) {
        this.points += points;
        if(points > MAX_POINTS) {
            this.points = MAX_POINTS;
        }
        activateQuests();
    }

    int getLevel() {
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
        for (Quest quest : npc.questTemplates.keySet())  {
            if (!playerQuests.containsKey(quest) && lvl >= npc.questTemplates.get(quest)) {
                playerQuests.put(quest, true);
            }
        }
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

    void finishQuest(BackPackable item) {
        Quest quest = null;
        for (Quest pq : playerQuests.keySet()) {
            if (pq.request.getName().equals(item.getName())) {
                if (!playerQuests.get(pq)) {
                    quest = pq;
                    break;
                }
            }
        }
        // TODO: check if Player has that Item
        playerQuests.remove(quest);
        playerQuests.put(quest, false);
    }


}


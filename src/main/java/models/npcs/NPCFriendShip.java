package models.npcs;

import models.Player;

import java.util.HashMap;

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

    void showQuests(Player player) {
        StringBuilder sj = new StringBuilder("\n");
        for (Quest pq : playerQuests.keySet()) {
            if (playerQuests.get(pq)) {
                sj.append("Deliver " + pq.getRequestAmount() +
                        " x " + pq.getRequest().getName());
            }
        }
    }


}


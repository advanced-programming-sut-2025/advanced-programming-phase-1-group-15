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

    Result finishQuest(BackPackable item) {
        Quest quest = null;
        for (Quest pq : playerQuests.keySet()) {
            if (pq.request.getName().equals(item.getName())) {
                if (!playerQuests.get(pq)) {
                    if(!pq.doneBySomeone) {
                        quest = pq;
                        break;
                    }
                }
            }
        }
        if(quest == null) {
            return new Result(false,"no quest for this NPC!");
        }
        if(player.getInventory().getItemByName(item.getName()) == null){
            return new Result(false,"does not have this item );");
        }
        if(player.getInventory().getItemCount(item.getName()) < quest.getRequestAmount()){
            return new Result(false,"you do not have enough number of this item!");
        }
        quest.setDoneBySomeone(true);

        playerQuests.remove(quest);
        playerQuests.put(quest, false);
        player.getInventory().removeCountFromBackPack(item,quest.getRequestAmount());
        player.getInventory().addToBackPack(quest.reward,quest.getRewardAmount()*((getLevel()+1)/2));
        return new Result(true,"well done! quest done!");
    }


}


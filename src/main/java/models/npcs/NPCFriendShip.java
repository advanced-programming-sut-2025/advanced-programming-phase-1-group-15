package models.npcs;

public class NPCFriendShip {
    private int points = 0;
    private boolean talkedToday = false;
    private boolean giftedToday = false;

    static final int MAX_POINTS = 799;
    static final int POINTS_PER_LEVEL = 200;

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


}


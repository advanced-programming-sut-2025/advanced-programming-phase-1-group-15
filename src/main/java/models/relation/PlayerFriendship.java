package models.relation;

import models.Player;
import models.time.DateAndTime;
import models.time.TimeObserver;
import models.tools.BackPackable;

import java.util.ArrayList;
import java.util.HashMap;



public class PlayerFriendship implements TimeObserver {
    public record Message(Player sender, String message) {
    }
    public static class Gift {
        private final BackPackable item;
        private int rate;

        public Gift(BackPackable item) {
            this.item = item;
            this.rate = 0;
        }
        public Gift(BackPackable item, int rate) {
            this.item = item;
            this.rate = rate;
        }

        public BackPackable getItem() {
            return item;
        }
        public int getRate() {
            return rate;
        }
        public void setRate(int rate) {
            this.rate = rate;
        }
    }
    Player player1;
    Player player2;
    private int xp = 0;
    private int level = 0;

    boolean talkToday;
    boolean tradeToday;
    boolean giftToday;
    boolean hugToday;

    private final ArrayList<Message> messages = new ArrayList<>();

    private final HashMap<Player, ArrayList<Gift>> gifts = new HashMap<>();

    public PlayerFriendship(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public boolean isFriendship(Player p1, Player p2) {
        return (player1.equals(p1) && player2.equals(p2)) || (player1.equals(p2) && player2.equals(p1));
    }

    public void upgradeXP(int amount) {
        xp += amount;

        if(xp > (level + 1) * 100 && level != 2) {
            level++;
            xp = 0;
        }
    }
    public void downgradeXP(int amount) {
        if(xp - amount < 0 && level > 0) {
            level--;
            xp = (level + 1) * 100 - amount;
        }
        else if(xp - amount < 0 && level == 0) {
            xp = 0;
        }
        else {
            xp -= amount;
        }
    }

    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }

    public void setXP(int xp) {
        this.xp = xp;
    }
    public int getXP() {
        return xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<Gift> getGifts(Player sender) {
        return gifts.get(sender);
    }

    public void talk(Player sender, String message) {
        messages.add(new Message(sender, message));
        if(!talkToday) {
            upgradeXP(20);
            talkToday = true;
        }
    }

    public void gift(Player sender, BackPackable gift) {
        gifts.putIfAbsent(sender, new ArrayList<>());
        gifts.get(sender).add(new Gift(gift));
        giftToday = true;
    }

    public void hug() {
        upgradeXP(60);
        hugToday = true;
    }

    public void flower() {
        level = 3;
        xp = 0;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == 9) {
            if(!talkToday && !tradeToday && !giftToday && !hugToday) {
                downgradeXP(10);
            }
            talkToday = false;
            tradeToday = false;
            giftToday = false;
            hugToday = false;
        }
    }
}

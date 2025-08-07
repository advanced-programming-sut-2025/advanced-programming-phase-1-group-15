package com.example.common.relation;

import com.example.common.Message;
import com.example.common.Player;
import com.example.common.time.DateAndTime;
import com.example.common.time.TimeObserver;
import com.example.common.tools.BackPackable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

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
    private boolean marry = false;

    boolean talkToday;
    boolean tradeToday;
    public boolean giftToday;
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

        if(xp >= (level + 1) * 100 && level < 2) {
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
            if(marry) {
                upgradeXP(50);
            }
            else {
                upgradeXP(20);
            }
            talkToday = true;
        }
    }

    public void gift(Player sender, BackPackable gift) {
        gifts.putIfAbsent(sender, new ArrayList<>());
        gifts.get(sender).add(new Gift(gift));
        Message message = new Message(sender,
            String.format("You have received a nice gift! : " + gift.getName()));
        if(player1.equals(sender)) {
            player2.addMessage(message);
            player2.addNotification(message);
        }
        else{
            player1.addMessage(message);
            player1.addNotification(message);
        }
    }
    public void rateGift(int rate) {
        int amount = (rate - 3) * 30 + 15;
        if(amount > 0) {
            upgradeXP(amount);
        }
        else {
            downgradeXP(-amount);
        }
    }

    public void hug() {
        if(!hugToday) {
            upgradeXP(60);
            hugToday = true;
        }
    }

    public void flower() {
        if(marry) {
            return;
        }
        level = 3;
        xp = 0;
    }

    public void marry() {
        level = 4;
        xp = 0;

        marry = true;
        player1.marry(player2);
        player2.marry(player1);

        int g1 = player1.getGold();
        int g2 = player2.getGold();
        player1.setGold(g1 + g2);
        player2.setGold(g1 + g2);
    }
    public void reject() {
        level = 0;
        xp = 0;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == 9) {
            if(!marry && !talkToday && !tradeToday && !giftToday && !hugToday) {
                downgradeXP(10);
            }
            talkToday = false;
            tradeToday = false;
            giftToday = false;
            hugToday = false;
        }
    }

    public boolean isMarry(){
        return marry;
    }

    public boolean giftToday() {
        return giftToday;
    }


}

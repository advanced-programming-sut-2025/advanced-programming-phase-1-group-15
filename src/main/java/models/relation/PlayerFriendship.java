package models.relation;

import models.Player;
import models.time.DateAndTime;
import models.time.TimeObserver;
import models.tools.BackPackable;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerFriendship implements TimeObserver {
    Player player1;
    Player player2;
    private int xp = 0;
    private int level = 0;

    boolean talkToday;
    boolean tradeToday;
    boolean giftToday;
    boolean hugToday;

    private HashMap<String, Player> messages = new HashMap<>();
    private HashMap<BackPackable,Integer> gifts = new HashMap<>();

    public PlayerFriendship(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public boolean isFriendship(Player p1, Player p2) {
        return (player1.equals(p1) && player2.equals(p2)) || (player1.equals(p2) && player2.equals(p1));
    }

    public void upgradeXP(int amount) {
        xp += amount;

        if(xp > (level + 1) * 100) {
            level++;
            xp = 0;
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

    public HashMap<String, Player> getMessages() {
        return messages;
    }

    public void talk(Player sender, String message) {
        messages.put(message, sender);
        if(!talkToday) {
            upgradeXP(20);
            talkToday = true;
        }
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

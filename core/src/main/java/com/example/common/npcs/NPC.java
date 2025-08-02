package com.example.common.npcs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.RandomGenerator;
import com.example.common.map.Tile;
import com.example.common.time.DateAndTime;
import com.example.common.time.Season;
import com.example.common.time.TimeObserver;
import com.example.common.tools.BackPackable;
import com.example.common.tools.Tool;
import com.example.common.weather.WeatherOption;

import java.util.ArrayList;
import java.util.HashMap;

public class NPC implements TimeObserver {
    protected String name;
    protected String job;
    private Tile homeLocation;
    private int lastDayUpdate = ClientApp.currentGame.getDateAndTime().getDay();
    private TextureRegion sprite;

    protected ArrayList<BackPackable> favourites = new ArrayList<>();

    protected HashMap<Quest,Integer> questTemplates = new HashMap<>();

    public HashMap<Player, NPCFriendShip> getFriendships() {
        return friendships;
    }

    public void addToFriendShip(Player player) {
        friendships.put(player,new NPCFriendShip(this,player));
    }

    protected HashMap<Player, NPCFriendShip> friendships = new HashMap<>();

    public NPC(String name, String job, Tile homeLocation, TextureRegion sprite) {
        this.name = name;
        this.job = job;
        this.homeLocation = homeLocation;
        this.sprite = sprite;
    }

    public Tile getHomeLocation() {
        return homeLocation;
    }

    public String meet(Player player) {
        NPCFriendShip fs = friendships.computeIfAbsent(player, k -> new NPCFriendShip(this,player));
        if (!fs.hasTalkedToday()) {
            fs.addPoints(20);
            fs.markTalked();
        }


        StringBuilder message = new StringBuilder();

        message.append(getHiBasedOnFriendShipLevel(fs));

        message.append("it's ")
                .append(getWeatherDescriptor(ClientApp.currentGame.getWeather().getCurrentWeather()))
                .append(" ")
                .append(getTimeOfDayPhrase())
                .append(" of ")
                .append(getSeasonPhrase())
                .append(", isn't it?");

        return message.toString();
    }

    private String getHiBasedOnFriendShipLevel(NPCFriendShip fs) {
        switch (fs.getLevel()) {
            case 0: return "Hello, ";
            case 1: return "Hi my friend, ";
            case 2: return "Hi bro, ";
            default: return "Hey bestie, ";
        }
    }

    private String getWeatherDescriptor(WeatherOption weather) {
        switch (weather) {
            case SUNNY: return "a good sunny";
            case SNOW:  return "a snowy";
            case RAINY: return "a rainy";
            case STORM: return "a stormy";
            default:    return "nice";
        }
    }

    private String getTimeOfDayPhrase() {
        int hour = ClientApp.currentGame.getDateAndTime().getHour();
        if (hour < 6)  return "night";
        if (hour < 12) return "morning";
        if (hour < 18) return "afternoon";
        return "day";
    }

    private String getSeasonPhrase() {
        Season season = ClientApp.currentGame.getDateAndTime().getSeason();
        return season.displaySeason();
    }

    public String gift(Player player, BackPackable item) {
        if (item instanceof Tool) {
            return "Cannot gift tools to NPCs";
        }
        NPCFriendShip friendShip = friendships.computeIfAbsent(player, k -> new NPCFriendShip(this,player));
        if (!friendShip.hasGiftedToday()) {
            friendShip.addPoints(50);
            friendShip.markGifted();
        }
        if (favourites.contains(item)) {
            friendShip.addPoints(150);
            return "thank you for your gift! 200 points added to our friendship!";
        }
        return "thank you for your gift! 50 points added to our friendship!";
    }

    public void addQuestTemplate(BackPackable quest,int questAmount, BackPackable reward, int rewardAmount,int level) {
        questTemplates.put(new Quest(quest,reward,questAmount,rewardAmount),level);
    }

    public String showQuests(Player player) {
        NPCFriendShip fs = friendships.get(player);
        if (fs != null) return fs.showQuests();
        return "no quest for this player";
    }

    public void finishQuest(Player player,BackPackable item) {
        NPCFriendShip fs = friendships.get(player);
        if (fs != null) fs.finishQuest(item);
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(lastDayUpdate != dateAndTime.getDay()){
            lastDayUpdate = dateAndTime.getDay();

            // Reset daily flags for all friendships
            for(NPCFriendShip fs : friendships.values()){
                fs.resetDaily();

                // Give daily gifts to high-level friends
                if(fs.getLevel() >= 3){
                    if(RandomGenerator.getInstance().randomInt(0,21) % 2 == 0){
                        BackPackable gift = favourites.get(RandomGenerator.getInstance().randomInt(0,favourites.size()-1));
                        fs.player.getInventory().addToBackPack(gift, 1);
                        fs.markDailyGiftReceived();
                    }
                }
            }
        }
    }

    public TextureRegion getSprite() {
        return sprite;
    }


    public boolean hasDailyGiftAvailable(Player player) {
        NPCFriendShip friendship = friendships.get(player);
        if (friendship == null || friendship.getLevel() < 3) {
            return false;
        }

        // Check if it's a new day and they haven't received their random gift yet
        int currentDay = ClientApp.currentGame.getDateAndTime().getDay();
        return lastDayUpdate == currentDay && !friendship.hasReceivedDailyGift();
    }

    public boolean hasMessageForToday(Player player) {
        NPCFriendShip friendship = friendships.get(player);
//        if (friendship == null) {
//            return true; // First time meeting, always has a message
//        }

        boolean hasntTalkedToday = !friendship.hasTalkedToday();
        boolean hasActiveQuests = friendship.getPlayerQuests().values().contains(true);
        boolean hasDailyGift = hasDailyGiftAvailable(player);

        return hasntTalkedToday;
    }

    public String getInteractionSummary(Player player) {
        StringBuilder summary = new StringBuilder();
        NPCFriendShip friendship = friendships.get(player);

        if (friendship == null) {
            summary.append("• Talk to start friendship\n");
            return summary.toString();
        }

        if (!friendship.hasTalkedToday()) {
            summary.append("• Talk for friendship points\n");
        }

        if (!friendship.hasGiftedToday()) {
            summary.append("• Give a gift for extra points\n");
        }

        long activeQuests = friendship.getPlayerQuests().values().stream()
            .filter(active -> active).count();
        if (activeQuests > 0) {
            summary.append("• ").append(activeQuests).append(" quest(s) available\n");
        }

        if (hasDailyGiftAvailable(player)) {
            summary.append("• Daily gift available!\n");
        }

        return summary.toString();
    }

    public ArrayList<BackPackable> getFavourites() {
        return favourites;
    }
}

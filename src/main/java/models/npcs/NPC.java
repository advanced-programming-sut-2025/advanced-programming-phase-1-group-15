package models.npcs;

import models.App;
import models.Player;
import models.RandomGenerator;
import models.map.Tile;
import models.time.DateAndTime;
import models.time.Season;
import models.time.TimeObserver;
import models.tools.BackPackable;
import models.tools.Tool;
import models.weather.WeatherManagement;
import models.weather.WeatherOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NPC implements TimeObserver {
    protected String name;
    protected String job;
    private Tile homeLocation;
    private int lastDayUpdate = App.currentGame.getDateAndTime().getDay();

    protected ArrayList<BackPackable> favourites = new ArrayList<>();

    protected HashMap<Quest,Integer> questTemplates = new HashMap<>();

    public HashMap<Player, NPCFriendShip> getFriendships() {
        return friendships;
    }

    protected HashMap<Player, NPCFriendShip> friendships = new HashMap<>();

    public NPC(String name, String job, Tile homeLocation) {
        this.name = name;
        this.job = job;
        this.homeLocation = homeLocation;
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
                .append(getWeatherDescriptor(App.currentGame.getWeather().getCurrentWeather()))
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
        int hour = App.currentGame.getDateAndTime().getHour();
        if (hour < 6) return "night";
        if (hour < 12) return "morning";
        if (hour < 18) return "afternoon";
        return "day";
    }

    private String getSeasonPhrase() {
        Season season = App.currentGame.getDateAndTime().getSeason();
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
            return "thank you for your gift! 200 points added to our firendship!";
        }
        return "thank you for your gift! 50 points added to our firendship!";
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
            for(NPCFriendShip fs : friendships.values()){
                if(fs.getLevel()>=3){
                    if(RandomGenerator.getInstance().randomInt(0,21) % 2 == 0){
                        Random rand = new Random();
                        fs.player.getInventory().addToBackPack(favourites.get(rand.nextInt(favourites.size())),1);
                    }
                }
            }
        }
    }
}
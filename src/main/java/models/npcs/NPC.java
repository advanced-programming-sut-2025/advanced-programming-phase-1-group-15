package models.npcs;

import models.App;
import models.Player;
import models.map.Tile;
import models.time.Season;
import models.tools.BackPackable;
import models.weather.WeatherManagement;
import models.weather.WeatherOption;

import java.util.ArrayList;
import java.util.HashMap;

public class NPC {
    protected String name;
    protected String job;

    private Tile homeLocation;

    protected ArrayList<String> dialogues = new ArrayList<>();

    protected ArrayList<BackPackable> favourites = new ArrayList<>();

    protected ArrayList<Boolean> questsAvailability = new ArrayList<>();
    protected HashMap<BackPackable, Integer> quests = new HashMap<>();
    protected HashMap<BackPackable, Integer> rewards = new HashMap<>();

    protected HashMap<Player, NPCFriendShip> friendships = new HashMap<>();

    public NPC(String name, String job, Tile homeLocation) {
        this.name = name;
        this.job = job;
        this.homeLocation = homeLocation;
    }

    public String meet(Player player) {
        NPCFriendShip fs = friendships.computeIfAbsent(player, k -> new NPCFriendShip());
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

    /**
     * Returns a short weather descriptor, e.g. "good sunny", "horrible storm".
     */
    private String getWeatherDescriptor(WeatherOption weather) {
        switch (weather) {
            case SUNNY: return "a good sunny";
            case SNOW:  return "a snowy";
            case RAINY: return "a rainy";
            case STORM: return "a stormy";
            default:    return "nice";
        }
    }

    /**
     * Determines time-of-day phrase based on local time.
     * @return one of "morning", "afternoon", "evening", "night"
     */
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

    public void gift(Player player, BackPackable item) {

    }

    public void showQuests() {

    }
    public void finishQuest(BackPackable item) {

    }
}
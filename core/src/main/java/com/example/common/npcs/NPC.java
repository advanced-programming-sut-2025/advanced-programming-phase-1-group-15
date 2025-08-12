package com.example.common.npcs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.RandomGenerator;
import com.example.common.Result;
import com.example.common.map.Tile;
import com.example.common.time.DateAndTime;
import com.example.common.time.Season;
import com.example.common.time.TimeObserver;
import com.example.common.tools.BackPackable;
import com.example.common.tools.Tool;
import com.example.common.weather.WeatherOption;
import com.example.server.models.OllamaClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class NPC implements TimeObserver {
    protected String name;
    protected String job;
    private final String personality;
    private Tile homeLocation;
    private int lastDayUpdate = ClientApp.currentGame.getDateAndTime().getDay();
    private final TextureRegion sprite;

    protected ArrayList<BackPackable> favourites = new ArrayList<>();
    protected HashMap<Quest, Integer> questTemplates = new HashMap<>();
    protected HashMap<Player, NPCFriendShip> friendships = new HashMap<>();

    private HashMap<Player, List<String>> interactionHistory = new HashMap<>();
    private final boolean useLLM = true;
    private int maxHistoryEntries = 10;

    public NPC(String name, String job, Tile homeLocation, TextureRegion sprite) {
        this(name, job, homeLocation, sprite, "friendly and helpful");
    }

    public NPC(String name, String job, Tile homeLocation, TextureRegion sprite, String personality) {
        this.name = name;
        this.job = job;
        this.homeLocation = homeLocation;
        this.sprite = sprite;
        this.personality = personality;
    }

    public HashMap<Player, NPCFriendShip> getFriendships() {
        return friendships;
    }

    public void addToFriendShip(Player player) {
        friendships.put(player, new NPCFriendShip(this, player));
        interactionHistory.putIfAbsent(player, new ArrayList<>());
    }

    public Tile getHomeLocation() {
        return homeLocation;
    }

    public String meet(Player player) {
        NPCFriendShip fs = friendships.computeIfAbsent(player, k -> {
            interactionHistory.putIfAbsent(player, new ArrayList<>());
            return new NPCFriendShip(this, player);
        });

        boolean firstInteractionToday = !fs.hasTalkedToday();
        if (firstInteractionToday) {
            fs.addPoints(20);
            fs.markTalked();
        }

        String response = generateTraditionalGreeting(fs);
        recordInteraction(player, "Player talked to " + name + ". " + name + " responded: " + response);
        return response;
    }

    public void meetAsync(Player player, Consumer<String> onSuccess, Consumer<String> onError) {
        NPCFriendShip fs = friendships.computeIfAbsent(player, k -> {
            interactionHistory.putIfAbsent(player, new ArrayList<>());
            return new NPCFriendShip(this, player);
        });

        boolean firstInteractionToday = !fs.hasTalkedToday();
        if (firstInteractionToday) {
            fs.addPoints(20);
            fs.markTalked();
        }

        if (useLLM) {
            String context = buildContextForLLM(player);
            OllamaClient.generateNPCDialogAsync(name, job, context)
                .whenComplete((response, throwable) -> {
                    if (throwable != null) {
                        System.err.println("Ollama API call failed: " + throwable.getMessage());
                        onError.accept(generateTraditionalGreeting(fs));
                    } else {
                        recordInteraction(player, "Player talked to " + name + ". " + name + " responded: " + response);
                        onSuccess.accept(response);
                    }
                });
        } else {
            String response = generateTraditionalGreeting(fs);
            recordInteraction(player, "Player talked to " + name + ". " + name + " responded: " + response);
            onSuccess.accept(response);
        }
    }

    public String gift(Player player, BackPackable item) {
        if (item instanceof Tool) {
            return "Cannot gift tools to NPCs";
        }

        NPCFriendShip friendShip = friendships.computeIfAbsent(player, k -> {
            interactionHistory.putIfAbsent(player, new ArrayList<>());
            return new NPCFriendShip(this, player);
        });

        boolean firstGiftToday = !friendShip.hasGiftedToday();
        int pointsGained = 0;

        if (firstGiftToday) {
            pointsGained += 50;
            friendShip.addPoints(50);
            friendShip.markGifted();
        }
        if (favourites.contains(item)) {
            pointsGained += 150;
            friendShip.addPoints(150);
        }

        String response;
        if (favourites.contains(item)) {
            response = "Thank you for your gift! 200 points added to our friendship!";
        } else {
            response = "Thank you for your gift! 50 points added to our friendship!";
        }

        recordInteraction(player, "Player gave " + item.getName() + " to " + name + ". " +
            name + " responded: " + response + " (+" + pointsGained + " friendship points)");
        return response;
    }

    private String buildContextForLLM(Player player) {
        StringBuilder context = new StringBuilder();

        context.append("Character: You are ").append(name).append(", a ").append(job).append(".\n");
        context.append("Personality: ").append(personality).append(".\n");

        DateAndTime currentTime = ClientApp.currentGame.getDateAndTime();
        WeatherOption weather = ClientApp.currentGame.getWeather().getCurrentWeather();

        context.append("Current situation: It's ").append(getTimeOfDayPhrase())
            .append(" in ").append(currentTime.getSeason().displaySeason())
            .append(", and the weather is ").append(weather.toString().toLowerCase()).append(".\n");

        NPCFriendShip friendship = friendships.get(player);
        if (friendship != null) {
            context.append("Friendship with ").append(player.getNickname()).append(": Level ")
                .append(friendship.getLevel()).append(" (").append(friendship.getPoints()).append(" points).\n");

            if (friendship.hasTalkedToday()) {
                context.append("You've already talked to this player today.\n");
            }
            if (friendship.hasGiftedToday()) {
                context.append("This player has already given you a gift today.\n");
            }
        }

        List<String> history = interactionHistory.get(player);
        if (history != null && !history.isEmpty()) {
            context.append("Recent interactions:\n");
            int startIndex = Math.max(0, history.size() - 3);
            for (int i = startIndex; i < history.size(); i++) {
                context.append("- ").append(history.get(i)).append("\n");
            }
        }

        if (friendship != null) {
            long activeQuests = friendship.getPlayerQuests().values().stream()
                .filter(active -> active).count();
            if (activeQuests > 0) {
                context.append("You have ").append(activeQuests).append(" active quest(s) for this player.\n");
            }
        }

        if (hasDailyGiftAvailable(player)) {
            context.append("You have a daily gift available for this player.\n");
        }

        return context.toString();
    }

    private String generateTraditionalGreeting(NPCFriendShip fs) {

        return getHiBasedOnFriendShipLevel(fs) +
            "it's " +
            getWeatherDescriptor(ClientApp.currentGame.getWeather().getCurrentWeather()) +
            " " +
            getTimeOfDayPhrase() +
            " of " +
            getSeasonPhrase() +
            ", isn't it?";
    }

    private void recordInteraction(Player player, String interaction) {
        List<String> history = interactionHistory.computeIfAbsent(player, k -> new ArrayList<>());

        DateAndTime currentTime = ClientApp.currentGame.getDateAndTime();
        String timestampedInteraction = String.format("[Day %d, %s, %s] %s",
            currentTime.getDay(),
            currentTime.getSeason().displaySeason(),
            getTimeOfDayPhrase(),
            interaction);

        history.add(timestampedInteraction);

        while (history.size() > maxHistoryEntries) {
            history.remove(0);
        }
    }

    private String getHiBasedOnFriendShipLevel(NPCFriendShip fs) {
        return switch (fs.getLevel()) {
            case 0 -> "Hello, ";
            case 1 -> "Hi my friend, ";
            case 2 -> "Hi bro, ";
            default -> "Hey bestie, ";
        };
    }

    private String getWeatherDescriptor(WeatherOption weather) {
        return switch (weather) {
            case SUNNY -> "a good sunny";
            case SNOW -> "a snowy";
            case RAINY -> "a rainy";
            case STORM -> "a stormy";
        };
    }

    private String getTimeOfDayPhrase() {
        int hour = ClientApp.currentGame.getDateAndTime().getHour();
        if (hour < 6)  return "night";
        if (hour < 12) return "morning";
        if (hour < 18) return "afternoon";
        return "evening";
    }

    private String getSeasonPhrase() {
        Season season = ClientApp.currentGame.getDateAndTime().getSeason();
        return season.displaySeason();
    }

    public void addQuestTemplate(BackPackable quest, int questAmount, BackPackable reward, int rewardAmount, int level) {
        Quest questObj = new Quest(quest, reward, questAmount, rewardAmount);
        questTemplates.put(questObj, level);
    }

    public String finishQuest(Player player, Quest quest) {
        NPCFriendShip fs = friendships.get(player);
        if (fs != null) {
            Result result = fs.finishQuest(quest);
            String interaction = "Player completed quest: " + quest.getRequest().getName() +
                ". Result: " + result.getMessage();
            recordInteraction(player, interaction);
            return result.getMessage();
        }
        return "No friendship established with this player.";
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if (lastDayUpdate != dateAndTime.getDay()) {
            lastDayUpdate = dateAndTime.getDay();

            for (NPCFriendShip fs : friendships.values()) {
                fs.resetDaily();

                if (fs.getLevel() >= 3) {
                    if (RandomGenerator.getInstance().randomInt(0, 21) % 2 == 0) {
                        if (!favourites.isEmpty()) {
                            BackPackable gift = favourites.get(RandomGenerator.getInstance().randomInt(0, favourites.size() - 1));
                            fs.player.getInventory().addToBackPack(gift, 1);
                            fs.markDailyGiftReceived();
                            recordInteraction(fs.player, name + " gave a daily gift: " + gift.getName());
                        }
                    }
                }

                if (fs.getLevel() >= 1 && fs.getActiveQuestCount() < 3) {
                    fs.activateQuests();
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

        int currentDay = ClientApp.currentGame.getDateAndTime().getDay();
        return lastDayUpdate == currentDay && !friendship.hasReceivedDailyGift();
    }

    public boolean hasMessageForToday(Player player) {
        NPCFriendShip friendship = friendships.get(player);
        if (friendship == null) return true;

        return !friendship.hasTalkedToday();
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

        int activeQuests = friendship.getActiveQuestCount();
        if (activeQuests > 0) {
            summary.append("• ").append(activeQuests).append(" quest(s) available\n");

            int completable = 0;
            for (Quest quest : friendship.getPlayerQuests().keySet()) {
                if (friendship.getPlayerQuests().get(quest) && !quest.isDoneBySomeone()) {
                    int playerHas = player.getInventory().getItemCount(quest.getRequest().getName());
                    if (playerHas >= quest.getRequestAmount()) {
                        completable++;
                    }
                }
            }

            if (completable > 0) {
                summary.append("• ").append(completable).append(" quest(s) ready to complete!\n");
            }
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

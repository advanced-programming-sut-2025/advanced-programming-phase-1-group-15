package com.example.models;

import com.example.models.map.Map;
import com.example.models.map.Position;
import com.example.models.map.Tile;
import com.example.models.npcs.DefaultNPCs;
import com.example.models.npcs.NPC;
import com.example.models.relation.PlayerFriendship;
import com.example.models.time.DateAndTime;
import com.example.models.time.TimeObserver;
import com.example.models.weather.WeatherManagement;
import java.util.ArrayList;


public class Game implements TimeObserver {
    private ArrayList<Player> players = new ArrayList<>();
    private Player mainPlayer;
    private Player currentPlayer;

    private DateAndTime dateAndTime;
    private WeatherManagement weather;

    private ArrayList<ArrayList<Tile>> mapTiles;
    private Map map;

    private final ArrayList<PlayerFriendship> friendships = new ArrayList<>();

    private boolean finished = false;

    public Game(ArrayList<Player> players) {
        this.players = players;
        for (Player player : players) {
            player.setGame(this);
        }
        this.currentPlayer = players.get(0);
    }
    public void build() {
        dateAndTime = new DateAndTime();
        weather = new WeatherManagement();

        dateAndTime.addObserver(weather);
        for(Player player : players) {
            dateAndTime.addObserver(player);
        }
        for(int i = 0; i < players.size() - 1; i++) {
            for (int j = i + 1; j < players.size(); j++) {
                PlayerFriendship friendship = new PlayerFriendship(players.get(i), players.get(j));
                dateAndTime.addObserver(friendship);
                friendships.add(friendship);
            }
        }

        mapTiles = Tile.buildMapTiles();
        map = new Map(mapTiles);
        map.build();
        dateAndTime.addObserver(map);
        for(Player player : players) {
            for(NPC npc: DefaultNPCs.getInstance().getDefaultOnes().values()) {
                npc.addToFriendShip(player);
            }
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    public Player getMainPlayer() {
        return mainPlayer;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public Player getPlayerByUsername(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public void setMainPlayer(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }
    public void nextTurn() {
        boolean allFainted = true;
        for(Player player : players) {
            allFainted = allFainted && player.isFainted();
        }
        if(allFainted) {
            dateAndTime.nextDay();
            return;
        }

        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        while (players.get(nextIndex).isFainted()) {
            nextIndex = (nextIndex + 1) % players.size();
        }

        if(nextIndex == 0) {
            dateAndTime.nextHour();
        }

        currentPlayer.unlock();
        currentPlayer = players.get(nextIndex);
    }

    public DateAndTime getDateAndTime() {
        return dateAndTime;
    }
    public WeatherManagement getWeather() {
        return weather;
    }

    public boolean isFinished() {
        return finished;
    }
    public void finish() {
        this.finished = true;
    }

    public Tile getTile(int x, int y) {
        return mapTiles.get(y).get(x);
    }
    public Tile getTile(Position position) {
        return mapTiles.get(position.y).get(position.x);
    }
    public Map getMap() {
        return map;
    }

    public ArrayList<PlayerFriendship> getFriendships() {
        return friendships;
    }
    public PlayerFriendship getFriendshipByPlayers(Player p1, Player p2) {
        for(PlayerFriendship friendship : friendships) {
            if(friendship.isFriendship(p1, p2)) {
                return friendship;
            }
        }
        return null;
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

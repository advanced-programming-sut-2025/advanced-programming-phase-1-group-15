package com.example.common;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.example.client.controllers.ClientGameController;
import com.example.client.models.ClientApp;
import com.example.common.GroupQuests.GroupQuestManager;
import com.example.common.map.Map;
import com.example.common.map.Position;
import com.example.common.map.Tile;
import com.example.common.npcs.DefaultNPCs;
import com.example.common.npcs.NPC;
import com.example.common.relation.PlayerFriendship;
import com.example.common.time.DateAndTime;
import com.example.common.time.TimeObserver;
import com.example.common.weather.WeatherManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Game implements TimeObserver {
    private ArrayList<Player> players = new ArrayList<>();
    private Player adminPlayer;
    private Player currentPlayer;
    private boolean isAdmin;

    private DateAndTime dateAndTime;
    private WeatherManagement weather;

    private ArrayList<ArrayList<Tile>> mapTiles;
    private Map map;

    private final ArrayList<PlayerFriendship> friendships = new ArrayList<>();

    private GroupQuestManager groupQuestManager;

    private boolean finished = false;

    public Game(Lobby lobby, User currentUser) {
        ArrayList<Player> players = new ArrayList<>();
        for(User user : lobby.getUsers()) {
            Player player = new Player(user);
            player.setMapNumber(lobby.getMapNumber(user.getUsername()));
            players.add(player);

            if(user.getUsername().equals(currentUser.getUsername())) {
                this.currentPlayer = player;
            }
        }

        this.players = players;
        this.adminPlayer = players.get(0);
        if(adminPlayer.equals(currentPlayer)) {
            isAdmin = true;
        }

        groupQuestManager = new GroupQuestManager();
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
                System.err.println(players.get(i)+"....."+players.get(j));
                dateAndTime.addObserver(friendship);
                friendships.add(friendship);
            }
        }

        mapTiles = Tile.buildMapTiles();
        if(isAdmin) {
            ClientGameController.sendSetRandomizersMessage();
        }
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
    public Player getAdminPlayer() {
        return adminPlayer;
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

    public void setAdminPlayer(Player adminPlayer) {
        this.adminPlayer = adminPlayer;
    }
    public boolean isAdmin() {
        return isAdmin;
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

    public ArrayList<ScoreboardInfo> getScoreboardInfoList() {
        ArrayList<ScoreboardInfo> scoreboard = new ArrayList<>();
        for (Player player : players) {
            scoreboard.add(new ScoreboardInfo(
                player.getNickname(),
                player.calculateScore(),
                player.getGold(),
                player.getEnergy(),
                player.getFarmingLevel(),
                player.getMiningLevel(),
                player.getForagingLevel(),
                player.getFishingLevel()
            ));
        }

        Collections.sort(scoreboard, Comparator.comparingInt(ScoreboardInfo::getScore).reversed());
        return scoreboard;
    }

    public GroupQuestManager getGroupQuestManager() {
        return groupQuestManager;
    }

    public ArrayList<ArrayList<Tile>> getMapTiles() {
        return mapTiles;
    }
}

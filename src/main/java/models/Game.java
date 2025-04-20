package models;

import models.map.Map;
import models.map.Position;
import models.map.Tile;
import models.relation.Trade;
import models.time.DateAndTime;
import models.time.TimeObserver;
import models.weather.WeatherObserver;
import models.weather.WeatherOption;

import java.util.ArrayList;


public class Game implements TimeObserver {
    private ArrayList<Player> players = new ArrayList<>();
    private Player mainPlayer;
    private Player currentPlayer;

    private Map map;
    private ArrayList<Trade> trades = new ArrayList<>();

    private boolean finished = false;

    public Game(ArrayList<Player> players) {
        this.players = players;
        this.currentPlayer = players.get(0);
        map = new Map();
        map.build();
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

    public void setMainPlayer(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }
    public void nextTurn() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();

        currentPlayer.unlock();
        currentPlayer = players.get(nextIndex);
    }

    public boolean isFinished() {
        return finished;
    }
    public void finish() {
        this.finished = true;
    }

    public void printMap(Position position, int size) {

    }
    public void guide() {

    }
    public void showTrades() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

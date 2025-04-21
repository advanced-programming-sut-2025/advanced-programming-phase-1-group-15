package models;

import models.map.Map;
import models.map.Position;
import models.relation.Trade;
import models.time.DateAndTime;
import models.time.TimeObserver;
import models.weather.WeatherManagement;

import java.util.ArrayList;


public class Game implements TimeObserver {
    private ArrayList<Player> players = new ArrayList<>();
    private Player mainPlayer;
    private Player currentPlayer;

    private final DateAndTime dateAndTime;
    private final WeatherManagement weather;

    private final Map map;

    private ArrayList<Trade> trades = new ArrayList<>();

    private boolean finished = false;

    public Game(ArrayList<Player> players) {
        this.players = players;
        this.currentPlayer = players.get(0);

        dateAndTime = new DateAndTime();
        weather = new WeatherManagement();
        dateAndTime.addObserver(weather);

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

    public Map getMap() {
        return map;
    }

    public void showTrades() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

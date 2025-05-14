package models.weather;

import models.map.Position;
import models.time.DateAndTime;
import models.time.Season;
import models.time.TimeObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class WeatherManagement implements TimeObserver {
    private WeatherOption currentWeather = WeatherOption.SUNNY;
    private WeatherOption tomorrowWeather = WeatherOption.SUNNY;

    List<WeatherObserver> observers = new ArrayList<>();
    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }
    private void notifyObservers() {
        List<WeatherObserver> snapshot = new ArrayList<>(observers);
        for (WeatherObserver obs : snapshot) {
            obs.update(currentWeather);
        }
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == 9) {
            currentWeather = tomorrowWeather;
            predictWeather(dateAndTime);
            notifyObservers();
        }
    }

    private void predictWeather(DateAndTime dateAndTime) {
        Random rand = new Random();
        Season season = dateAndTime.getSeason();

        List<WeatherOption> possibleWeathers = new ArrayList<>();
        switch (season) {
            case SPRING, SUMMER, AUTUMN -> {
                possibleWeathers.add(WeatherOption.SUNNY);
                possibleWeathers.add(WeatherOption.RAINY);
                possibleWeathers.add(WeatherOption.STORM);
            }
            case WINTER -> {
                possibleWeathers.add(WeatherOption.SUNNY);
                possibleWeathers.add(WeatherOption.SNOW);
            }
        }

        if(rand.nextBoolean()) {
            tomorrowWeather = WeatherOption.SUNNY;
        }
        else {
            tomorrowWeather = possibleWeathers.get(rand.nextInt(possibleWeathers.size()));
        }
    }

    public void setForecast(WeatherOption weather) {
        tomorrowWeather = weather;
    }

    public void setThor(Position position) {

    }

    public WeatherOption getCurrentWeather() {
        return currentWeather;
    }
    public String displayWeather() {
        return currentWeather.displayWeather();
    }
    public String displayForecast() {
        return "forecast: " + tomorrowWeather.displayWeather();
    }

    public boolean couldShepherdAnimals() {
        return switch (currentWeather) {
            case SUNNY -> true;
            case RAINY, STORM, SNOW -> false;
        };
    }
}

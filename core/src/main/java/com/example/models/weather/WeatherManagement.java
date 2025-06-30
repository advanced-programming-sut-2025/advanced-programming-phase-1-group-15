package com.example.models.weather;

import com.example.models.RandomGenerator;
import com.example.models.map.Position;
import com.example.models.time.DateAndTime;
import com.example.models.time.Season;
import com.example.models.time.TimeObserver;

import java.util.ArrayList;
import java.util.List;

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

        if(RandomGenerator.getInstance().randomBoolean()) {
            tomorrowWeather = WeatherOption.SUNNY;
        }
        else {
            tomorrowWeather = possibleWeathers.get(RandomGenerator.getInstance().randomInt(0,possibleWeathers.size()-1));
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

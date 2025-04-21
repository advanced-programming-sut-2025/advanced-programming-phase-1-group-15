package models.weather;

import models.map.Position;
import models.time.DateAndTime;
import models.time.TimeObserver;

import java.util.ArrayList;
import java.util.List;

public class WeatherManagement implements TimeObserver {
//        private final WeatherManagement instance = new WeatherManagement(WeatherOption.SUNNY);
////
//////    private WeatherManagement (WeatherOption currentWeather){
//////        DateAndTime dateAndTime = DateAndTime.getInstance();
//////        dateAndTime.addObserver(this);
//////        this.currentWeather = currentWeather;
//////    }
//
////    public WeatherManagement getInstance(){
////        return instance;
////    }

    WeatherOption currentWeather;
    WeatherOption tomorrowWeather;

    @Override
    public void update(DateAndTime dateAndTime) {
        // if day is changed , currentWeather will be tomorrow weather
        // and tomorrow weather will be predicted
    }

    List<WeatherObserver> observers = new ArrayList<>();
    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(currentWeather);
        }
    }

    private void predictWeather(DateAndTime tomorrow) {

    }

    public void setThor(Position position) {

    }
}

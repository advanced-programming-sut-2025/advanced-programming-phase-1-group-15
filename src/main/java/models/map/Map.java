package models.map;

import models.time.DateAndTime;
import models.time.TimeObserver;
import models.weather.WeatherObserver;
import models.weather.WeatherOption;

public class Map extends Area implements TimeObserver, WeatherObserver {
    public Map() {

    }

    public void build() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
    @Override
    public void update(WeatherOption weatherOption) {

    }
}

package controllers;

import models.App;
import models.Result;
import models.map.Position;
import models.weather.WeatherOption;

import java.util.Objects;

public class CheatCodeController {
    public static Result cheatAdvanceTime(int hours) {
        if(hours < 0) {
            return new Result(false, "you can't travel back in time!");
        }

        App.currentGame.getDateAndTime().nextNHours(hours);
        return new Result(true, App.currentGame.getDateAndTime().displayDateTime());
    }
    public static Result cheatAdvanceDate(int days) {
        if(days < 0) {
            return new Result(false, "you can't travel back in time!");
        }

        App.currentGame.getDateAndTime().nextNDays(days);
        return new Result(true, App.currentGame.getDateAndTime().displayDateTime());
    }

    public static Result cheatSetWeather(String weatherType) {
        WeatherOption weather = WeatherOption.getWeather(weatherType.toLowerCase());

        if(weather == null) {
            return new Result(false, "invalid weather type");
        }

        App.currentGame.getWeather().setForecast(weather);
        return new Result(true, "Tomorrow's forecast changed to " + App.currentGame.getWeather().displayForecast());
    }

    public static Result cheatSetEnergy(int value) {
        if(value < 0 || value > 200) {
            return new Result(false, "choose between [0,200]");
        }

        App.currentGame.getCurrentPlayer().setEnergy(value);
        return new Result(true, "energy: " + App.currentGame.getCurrentPlayer().getEnergy());
    }
    public static Result cheatUnlimitedEnergy() {
        App.currentGame.getCurrentPlayer().unlimitedEnergy();
        return new Result(true, "energy: " + App.currentGame.getCurrentPlayer().getEnergy());
    }

    public static Result cheatThor(Position position) {
        return null;
    }

    public static Result cheatAddItem(Position position) {
        return null;
    }

    public static Result cheatSetFriendship(String animalName, int amount) {
        return null;
    }

    public static Result cheatAddProduct(String productName, int amount) {
        return null;
    }
}

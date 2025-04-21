package controllers;

import models.App;
import models.Result;
import models.map.Position;

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

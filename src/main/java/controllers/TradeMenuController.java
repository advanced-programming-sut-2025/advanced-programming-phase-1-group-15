package controllers;

import models.App;
import models.Player;
import models.Result;
import models.tools.BackPackable;

public class TradeMenuController {
    public static Result tradeWithMoney(String username, String type, String itemName, String amount , String price) {
        int Amount = Integer.parseInt(amount);
        int Price = Integer.parseInt(price);
        Player user = (Player)App.getUserByUsername(username);
        if (user == null) {
            return new Result(false, "User not found");
        }
        Player player = App.currentGame.getCurrentPlayer();
        BackPackable item = null;
        if(type.trim().equals("offer")) {
            for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
                if (backPackable.getName().equals(itemName.trim())) {
                    item = backPackable;
                    if(player.getInventory().getItemCount(backPackable.getName())>Amount) {
                        return new Result(false, "You dont have enough item");
                    }
                }
            }
            if (item == null) {
                return new Result(false, "Item not found");
            }
        }
        int id = user.getCurrentId();
        return null;
    }
    public static Result tradeWithItem(String username, String type, String itemName , String amount , String targetItem , String number) {
        int Amount = Integer.parseInt(amount);
        int Number = Integer.parseInt(number);
        Player user = (Player)App.getUserByUsername(username);
        if (user == null) {
            return new Result(false, "User not found");
        }
        Player player = App.currentGame.getCurrentPlayer();
        BackPackable item = null;
        if(type.trim().equals("offer")) {
            for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
                if (backPackable.getName().equals(itemName.trim())) {
                    item = backPackable;
                    if(player.getInventory().getItemCount(backPackable.getName())>Amount) {
                        return new Result(false, "You dont have enough item");
                    }
                }
            }
            if (item == null) {
                return new Result(false, "Item not found");
            }
        }
        BackPackable target = null;
        int id = user.getCurrentId();
        return null;
    }
    public static Result tradeResponse(String response, int id) {
        return null;
    }
}

package controllers;

import models.App;
import models.Player;
import models.Result;
import models.relation.TradeWhitMoney;
import models.relation.TradeWithItem;
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
            int id = user.getCurrentId();
            TradeWhitMoney trade = new TradeWhitMoney(id , player , user , "offer" , itemName , Amount , Price);
            user.getTradesWhitMoney().add(trade);
            user.setCurrentId(id+1);
            return new Result(true, "Your offer sent to user successfully");
        }
        else if (type.trim().equals("request")) {
            if(player.getGold()<Price)
                return new Result(false, "You dont have enough gold");
            int id = user.getCurrentId();
            TradeWhitMoney trade = new TradeWhitMoney(id , user , player , "request" , itemName , Amount , Price);
            user.getTradesWhitMoney().add(trade);
            user.setCurrentId(id+1);
            return new Result(true, "Your request sent to user successfully");
        }
        return new Result(false, "invalid type trade");
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
            int id = user.getCurrentId();
            TradeWithItem trade = new TradeWithItem(id , player , user , "offer" , itemName , Amount , targetItem.trim() , Number);
            user.getTradesWithItem().add(trade);
            user.setCurrentId(id+1);
            return new Result(true, "Your offer sent to user successfully");
        }
        else if (type.trim().equals("request")) {
            BackPackable target = null;
            for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
                if (backPackable.getName().equals(itemName.trim())) {
                    target = backPackable;
                    if(player.getInventory().getItemCount(backPackable.getName())>Amount) {
                        return new Result(false, "You dont have enough item to request");
                    }
                }
            }
            if (target == null) {
                return new Result(false, "You dont have this item to request");
            }
            int id = user.getCurrentId();
            TradeWithItem trade = new TradeWithItem(id , user , player , "request" , itemName , Amount , targetItem.trim() , Number);
            user.getTradesWithItem().add(trade);
            user.setCurrentId(id+1);
            return new Result(true, "Your request sent to user successfully");
        }
        return new Result(false, "invalid type trade");
    }
    public static Result tradeResponse(String response, int id) {
        return null;
    }
}

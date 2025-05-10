package controllers;

import models.App;
import models.Player;
import models.Result;
import models.relation.Trade;
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
    public static Result tradeResponse(String response, String id) {
        int Id = Integer.parseInt(id);
        Player player = App.currentGame.getCurrentPlayer();
        TradeWhitMoney tradeWhitMoney = null;
        TradeWithItem tradeWithItem = null;
        for (TradeWhitMoney trade : player.getTradesWhitMoney()) {
            if (trade.getId() == Id) {
                tradeWhitMoney = trade;
                break;
            }
        }
        if (tradeWhitMoney != null) {
            if (response.equals("accept")){
                if(tradeWhitMoney.getType().equals("offer")){
                    if(player.getGold()>= tradeWhitMoney.getMoney()){
                        BackPackable item = null;
                        for (BackPackable backPackable : tradeWhitMoney.getSeller().getInventory().getItems().keySet()) {
                            if (backPackable.getName().equals(tradeWhitMoney.getName())) {
                                item = backPackable;
                                int num = player.getInventory().getItemCount(backPackable.getName()) - tradeWhitMoney.getAmount();
                                tradeWhitMoney.getSeller().getInventory().getItems().put(item ,num);
                                tradeWhitMoney.getSeller().setGold(tradeWhitMoney.getMoney()+tradeWhitMoney.getSeller().getGold());
                                break;
                            }
                        }
                        player.getInventory().getItems().put(item,tradeWhitMoney.getAmount());
                        player.setGold(player.getGold()-tradeWhitMoney.getMoney());
                        return new Result(true, "You accept this offer");
                    }
                    BackPackable item = null;
                    for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
                        if (backPackable.getName().equals(tradeWhitMoney.getName())){
                            item = backPackable;
                            if (player.getInventory().getItemCount(item.getName())>tradeWhitMoney.getAmount()) {
                                return new Result(false, "You dont have enough item to accept request");
                            }
                            player.getInventory().getItems().put(item,player.getInventory().getItemCount(item.getName())-tradeWhitMoney.getAmount());
                            player.setGold(player.getGold()+tradeWhitMoney.getMoney());
                            tradeWhitMoney.getBuyer().getInventory().getItems().put(item,tradeWhitMoney.getAmount());
                            break;
                        }
                    }
                    if (item == null) {
                        return new Result(false, "You dont have this item to accept request");
                    }
                    return new Result(true , "You accept this request");
                }
            }
        }
        for (TradeWithItem trade : player.getTradesWithItem()) {
            if (trade.getId() == Id) {
                tradeWithItem = trade;
                break;
            }
        }
        if (tradeWithItem != null) {

        }
        if(tradeWhitMoney ==null && tradeWithItem == null)
            return new Result(false, "id doesnt exist");
        if (response.equals("reject")){
            if (tradeWhitMoney == null){
                player.getTradesWithItem().remove(tradeWithItem);
                if (tradeWithItem.getType().equals("offer"))
                    return new Result(true, "You reject offer successfully");
                return new Result(false, "You reject request successfully");
            }
            player.getTradesWhitMoney().remove(tradeWhitMoney);
            if (tradeWhitMoney.getType().equals("offer"))
                return new Result(true, "You reject offer successfully");
            return new Result(true, "you reject this request");
        }
        return new Result(true, "invalid response type");
    }
    public static void tradeList() {
        Player player = App.currentGame.getCurrentPlayer();
        System.out.println("Offers:");
        for (TradeWhitMoney trade : player.getTradesWhitMoney()) {
            if(trade.getType().equals("offer")) {
                System.out.println("id "+ trade.getId() + " username: " + trade.getSeller().getUsername()+" item: " + trade.getName() +
                        " number: "+ trade.getAmount() + " price: " + trade.getMoney());
            }
        }
        for (TradeWithItem trade : player.getTradesWithItem()) {
            if(trade.getType().equals("offer")) {
                System.out.println("id "+ trade.getId() + " username: " + trade.getSeller().getUsername()+" item: " + trade.getName() +
                        " number: "+ trade.getAmount() + " target item: "+ trade.getTargetName() + " number of target item: " +trade.getAmount());
            }
        }
        System.out.println("Requests:");
        for (TradeWhitMoney trade : player.getTradesWhitMoney()) {
            if(trade.getType().equals("request")) {
                System.out.println("id "+ trade.getId() + " username: " + trade.getSeller().getUsername()+" item: " + trade.getName() +
                        " number: "+ trade.getAmount() + " price: " + trade.getMoney());
            }
        }
        for (TradeWithItem trade : player.getTradesWithItem()) {
            if(trade.getType().equals("request")) {
                System.out.println("id "+ trade.getId() + " username: " + trade.getSeller().getUsername()+" item: " + trade.getName() +
                        " number: "+ trade.getAmount() + " target item: "+ trade.getTargetName() + " number of target item: " +trade.getAmount());
            }
        }
    }
}

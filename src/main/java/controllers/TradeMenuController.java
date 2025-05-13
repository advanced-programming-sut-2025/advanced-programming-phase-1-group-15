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
        Player user = App.currentGame.getPlayerByUsername(username);
        if (user == null) {
            return new Result(false, "User not found");
        }
        Player player = App.currentGame.getCurrentPlayer();
        BackPackable item = null;
        if(type.trim().equals("offer")) {
            for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
                if (backPackable.getName().equals(itemName.trim())) {
                    item = backPackable;
                    if(player.getInventory().getItemCount(backPackable.getName())<Amount) {
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
        Player user = App.currentGame.getPlayerByUsername(username);
        if (user == null) {
            return new Result(false, "User not found");
        }
        Player player = App.currentGame.getCurrentPlayer();
        BackPackable item = null;
        if(type.trim().equals("offer")) {
            for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
                if (backPackable.getName().equals(itemName.trim())) {
                    item = backPackable;
                    if(player.getInventory().getItemCount(backPackable.getName())<Amount) {
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
                if (backPackable.getName().equals(targetItem.trim())) {
                    target = backPackable;
                    if(player.getInventory().getItemCount(backPackable.getName())<Number) {
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
                                tradeWhitMoney.getSeller().getInventory().removeCountFromBackPack(item , tradeWhitMoney.getAmount());
                                tradeWhitMoney.getSeller().addGold(tradeWhitMoney.getMoney());
                                break;
                            }
                        }
                        player.getInventory().addToBackPack(item , tradeWhitMoney.getAmount());
                        player.subtractGold(tradeWhitMoney.getMoney());
                        tradeWhitMoney.getSeller().getTradesWithMoneyHistory().add(tradeWhitMoney);
                        tradeWhitMoney.getBuyer().getTradesWithMoneyHistory().add(tradeWhitMoney);
                        player.getTradesWhitMoney().remove(tradeWhitMoney);
                        return new Result(true, "You accept this offer");
                    }
                    BackPackable item = null;
                    for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
                        if (backPackable.getName().equals(tradeWhitMoney.getName())){
                            item = backPackable;
                            if (player.getInventory().getItemCount(item.getName())<tradeWhitMoney.getAmount()) {
                                return new Result(false, "You don't have enough item to accept request");
                            }
                            player.getInventory().removeCountFromBackPack(item, tradeWhitMoney.getAmount());
                            player.addGold(tradeWhitMoney.getMoney());
                            tradeWhitMoney.getBuyer().getInventory().addToBackPack(item,tradeWhitMoney.getAmount());
                            break;
                        }
                    }
                    if (item == null) {
                        return new Result(false, "You dont have this item to accept request");
                    }
                    tradeWhitMoney.getSeller().getTradesWithMoneyHistory().add(tradeWhitMoney);
                    tradeWhitMoney.getBuyer().getTradesWithMoneyHistory().add(tradeWhitMoney);
                    player.getTradesWhitMoney().remove(tradeWhitMoney);
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
            if(response.equals("accept")){
                if(tradeWithItem.getType().equals("offer")){
                    BackPackable item = null;
                    for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
                        if(tradeWithItem.getTargetName().equals(backPackable.getName())){
                            item = backPackable;
                            if(player.getInventory().getItemCount(item.getName())<tradeWithItem.getTargetAmount()){
                                return new Result(false, "You dont have enough item to accept offer");
                            }
                            player.getInventory().removeCountFromBackPack(item , tradeWithItem.getTargetAmount());
                            tradeWithItem.getSeller().getInventory().addToBackPack(item,tradeWithItem.getAmount());
                            break;
                        }
                    }
                    if (item == null) {
                        return new Result(false, "You dont have this item to accept offer");
                    }
                    BackPackable temp = null;
                    for (BackPackable backPackable : tradeWithItem.getSeller().getInventory().getItems().keySet()) {
                        if(backPackable.getName().equals(tradeWithItem.getTargetName())){
                            temp = backPackable;
                            tradeWithItem.getSeller().getInventory().removeCountFromBackPack(temp , tradeWithItem.getAmount());
                            break;
                        }
                    }
                    player.getInventory().addToBackPack(temp, tradeWithItem.getAmount());
                    tradeWithItem.getSeller().getTradesWithItemHistory().add(tradeWithItem);
                    tradeWithItem.getBuyer().getTradesWithItemHistory().add(tradeWithItem);
                    player.getTradesWithItem().remove(tradeWithItem);
                    return new Result(true, "You accept this offer");
                }
                BackPackable item = null;
                for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
                    if(backPackable.getName().equals(tradeWithItem.getName())) {
                        item = backPackable;
                        if (player.getInventory().getItemCount(item.getName()) < tradeWithItem.getAmount()) {
                            return new Result(false, "You dont have enough item to accept request");
                        }
                        tradeWithItem.getBuyer().getInventory().addToBackPack(item , tradeWithItem.getAmount());
                        player.getInventory().removeCountFromBackPack(item , tradeWithItem.getAmount());
                        break;
                    }
                }
                if (item == null) {
                    return new Result(false, "You dont have this item to accept request");
                }
                BackPackable temp = null;
                for (BackPackable backPackable : tradeWithItem.getBuyer().getInventory().getItems().keySet()) {
                    if (backPackable.getName().equals(tradeWithItem.getTargetName())) {
                        temp = backPackable;
                        player.getInventory().addToBackPack(temp , tradeWithItem.getTargetAmount());
                        tradeWithItem.getBuyer().getInventory().removeCountFromBackPack(temp , tradeWithItem.getTargetAmount());
                        break;
                    }
                }
                player.getTradesWithItemHistory().add(tradeWithItem);
                tradeWithItem.getBuyer().getTradesWithItemHistory().add(tradeWithItem);
                player.getTradesWithItem().remove(tradeWithItem);
                return new Result(true, "You accept this request");
            }
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
    public static void showTradeHistory(){
        Player player = App.currentGame.getCurrentPlayer();
        for (TradeWhitMoney trade : player.getTradesWithMoneyHistory()) {
            System.out.println("id: "+ trade.getId() +" buyer: " + trade.getBuyer().getUsername() +
                    " seller: " + trade.getSeller().getUsername() + " itemName: " + trade.getName()+
            " itemAmount: "+trade.getAmount() + " price: "+trade.getMoney());
        }
        for (TradeWithItem trade : player.getTradesWithItemHistory()) {
            System.out.println("id: "+ trade.getId() +" buyer: " + trade.getBuyer().getUsername() +
                    " seller: " + trade.getSeller().getUsername() + " itemName: " + trade.getName()+
                    " itemAmount: "+trade.getAmount() + " target itemName: "+ trade.getTargetName() + " number of target item: " +trade.getAmount());
        }
    }
}

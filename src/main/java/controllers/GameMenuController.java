package controllers;

import models.App;
import models.Game;
import models.Player;
import models.Result;
import models.animals.*;
import models.artisanry.ArtisanItem;
import models.artisanry.ArtisanItemType;
import models.cooking.Food;
import models.cooking.FoodType;
import models.crafting.CraftItem;
import models.crafting.CraftItemType;
import models.enums.Gender;
import models.farming.*;
import models.farming.GeneralPlants.PloughedPlace;
import models.map.*;
import models.npcs.DefaultNPCs;
import models.npcs.NPC;
import models.npcs.NPCFriendShip;
import models.npcs.Quest;
import models.relation.PlayerFriendship;
import models.relation.TradeWhitMoney;
import models.relation.TradeWithItem;
import models.stores.*;
import models.tools.*;

import java.util.ArrayList;
import java.util.List;

public class GameMenuController {
    public static Player getCurrentPlayer() {
        return App.currentGame.getCurrentPlayer();
    }
    
    public static Result walk(int x, int y) {
        if(x >= Map.COLS || y >= Map.ROWS || x < 0 || y < 0) {
            return new Result(false, "you are out of bounds!");
        }

        Tile tile = App.currentGame.getTile(x, y);
        if(!tile.isEmpty()) {
            return new Result(false, "you can't stand on a tile which is not empty.");
        }
        else if(tile.getAreaType().equals(AreaType.LAKE)) {
            return new Result(false, "you're destination is in the lake!");
        }
        else if(tile.getAreaType().equals(AreaType.FARM)) {
            Farm farm = (Farm) tile.getArea();
            if(!getCurrentPlayer().checkTerritory(farm)) {
                return new Result(false, "you cannot enter other players' territory.");
            }
        }

        int energyNeeded = getCurrentPlayer().calculateWalkingEnergy(new Position(x, y));

        if(energyNeeded == -1) {
            return new Result(false, "tile is unreachable!");
        }
        return new Result(true, energyNeeded + " energy would be consumed. Do you agree? (y/n)");
    }

    public static Result setPosition(int x, int y) {
        Position position = new Position(x, y);

        getCurrentPlayer().walk(position);
        if(getCurrentPlayer().isFainted()) {
            return new Result(false, "Oops! you've fainted!");
        }

        return new Result(true, "moved to position " + position + " successfully.");
    }

    public static Result removeFromInventory(String itemName, int count) {
        BackPackable item = getCurrentPlayer().getInventory().getItemByName(itemName);
        int availableCount = getCurrentPlayer().getInventory().getItemCount(itemName);
        if(item == null) {
            return new Result(false, "You don't have that item.");
        }
        else if(count > availableCount) {
            return new Result(false, "You only have " + availableCount + " " + item.getName() + " in your inventory.");
        }

        TrashCan trashCan = getCurrentPlayer().getTrashCan();
        if(count == -1) {
            int returnedAmount = trashCan.use(item, availableCount, getCurrentPlayer());
            getCurrentPlayer().getInventory().removeFromBackPack(item);
            return new Result(true, item.getName() + " moved to trash can.\n" +
                    returnedAmount + " gold added to your account.");
        }
        else {
            int returnedAmount = trashCan.use(item, count, getCurrentPlayer());
            getCurrentPlayer().getInventory().removeCountFromBackPack(item, count);
            return new Result(true, count + " " + item.getName() + " moved to trash can.\n" +
                    returnedAmount + " gold added to your account.");
        }
    }

    public static Result equipTool(String toolName) {
        Tool tool = (Tool) getCurrentPlayer().getInventory().getItemByName(toolName);

        if(tool == null) {
            return new Result(false, "You don't have that tool.");
        }

        getCurrentPlayer().setCurrentTool(tool);
        return new Result(true, "equipped tool " + tool.getName() + " successfully.");
    }
    public static Result showCurrentTool() {
        Tool tool = getCurrentPlayer().getCurrentTool();
        if(tool == null) {
            return new Result(false, "you're not holding any tool!");
        }

        return new Result(true, tool.getName());
    }
    public static Result upgradeTool(String toolName) {
        Tool tool = (Tool) getCurrentPlayer().getInventory().getItemByName(toolName);

        if(tool == null) {
            return new Result(false, "You don't have that tool.");
        }

        return new Result(true, "Tool upgraded successfully.");
    }
    public static Result useTool(int dx, int dy) {
        Tool tool = getCurrentPlayer().getCurrentTool();
        if(tool == null) {
            return new Result(false, "choose a tool first");
        }
        Position usePosition = new Position(getCurrentPlayer().getPosition().x + dx, getCurrentPlayer().getPosition().y + dy);
        if(!Map.isBoundValid(usePosition)) {
            return new Result(false, "you are out of bounds!");
        }

        Tile useTile = App.currentGame.getTile(usePosition);

        return new Result(true, tool.use(useTile, getCurrentPlayer()));
    }

    public static Result putInFridge(String itemName) {
        Tile tile = App.currentGame.getTile(getCurrentPlayer().getPosition());
        if(!(tile.getArea() instanceof House playersHouse)) {
            return new Result(false, "you should be in your house to use fridge!");
        }
        Fridge fridge = playersHouse.getFridge();

        BackPackable item = getCurrentPlayer().getInventory().getItemByName(itemName);
        if(item == null) {
            return new Result(false, "You don't have that item in your inventory.");
        }

        int itemCount = getCurrentPlayer().getInventory().getItemCount(itemName);
        fridge.addToFridge(item, itemCount);
        getCurrentPlayer().getInventory().removeFromBackPack(item);

        return new Result(true, item.getName() + " moved to fridge.");
    }
    public static Result pickFromFridge(String itemName) {
        Tile tile = App.currentGame.getTile(getCurrentPlayer().getPosition());
        if(!(tile.getArea() instanceof House playersHouse)) {
            return new Result(false, "you should be in your house to use fridge!");
        }
        Fridge fridge = playersHouse.getFridge();

        BackPackable item = fridge.getItemByName(itemName);
        if(item == null) {
            return new Result(false, "You don't have that item in your fridge.");
        }

        int itemCount = fridge.getItemCount(itemName);
        getCurrentPlayer().getInventory().addToBackPack(item, itemCount);
        fridge.removeFromFridge(item);

        return new Result(true, item.getName() +  " moved to inventory.");
    }
    public static Result eatFood(String foodName) {
        Food food = (Food) getCurrentPlayer().getInventory().getItemByName(foodName);
        if(food == null) {
            return new Result(false, "You don't have that food.");
        }

        getCurrentPlayer().eat(food);
        getCurrentPlayer().getInventory().removeCountFromBackPack(food, 1);
        return new Result(true, "You ate " + food.getName() + ". " + food.getEnergy() + " energy added.");
    }

    public static Result buildBarn(int x, int y) {
        Tile playerTile = App.currentGame.getTile(getCurrentPlayer().getPosition());
        if(!(playerTile.getArea() instanceof CarpenterShop)) {
            return new Result(false, "you should be inside carpenter shop to run this command.");
        }

        boolean buildable = true;
        for(int row = y; row < y + 2; row++) {
            for(int col = x; col < x + 2; col++) {
                Tile tile = App.currentGame.getTile(col, row);
                if(!tile.isBuildable()) {
                    buildable = false;
                }
            }
        }

        if(!buildable) {
            return new Result(false, "You can't build a barn in this Area.");
        }
        else {
            Barn barn = new Barn();
            for(int row = y; row < y + 2; row++) {
                for(int col = x; col < x + 2; col++) {
                    Tile tile = App.currentGame.getTile(col, row);
                    tile.setArea(barn);
                }
            }
            return new Result(true, "barn built successfully.");
        }
    }
    public static Result buildCoop(int x, int y) {
        Tile playerTile = App.currentGame.getTile(getCurrentPlayer().getPosition());
        if(!(playerTile.getArea() instanceof CarpenterShop)) {
            return new Result(false, "you should be inside carpenter shop to run this command.");
        }

        boolean buildable = true;
        for(int row = y; row < y + 2; row++) {
            for(int col = x; col < x + 2; col++) {
                Tile tile = App.currentGame.getTile(col, row);
                if(!tile.isBuildable()) {
                    buildable = false;
                }
            }
        }

        if(!buildable) {
            return new Result(false, "You can't build a coop in this Area.");
        }
        else {
            Coop coop = new Coop();
            for(int row = y; row < y + 2; row++) {
                for(int col = x; col < x + 2; col++) {
                    Tile tile = App.currentGame.getTile(col, row);
                    tile.setArea(coop);
                }
            }
            return new Result(true, "coop built successfully.");
        }
    }
    public static Result buyAnimal(String animalType, String name) {
        Tile playerTile = App.currentGame.getTile(getCurrentPlayer().getPosition());
        if(!(playerTile.getArea() instanceof MarnieRanch)) {
            return new Result(false, "you have to be inside marnie's ranch to run this command.");
        }

        Animal animal = Animal.animalFactory(animalType, name);
        if(animal == null) {
            return new Result(false, "invalid animal type!");
        }

        for(Animal playerAnimal : getCurrentPlayer().getAnimals()) {
            if(animal.getName().equals(playerAnimal.getName())) {
                return new Result(false, "each animal must have a unique name.");
            }
        }

        boolean placed = getCurrentPlayer().getFarm().place(animal);

        if(placed) {
            App.currentGame.getDateAndTime().addObserver(animal);
            getCurrentPlayer().getAnimals().add(animal);
            getCurrentPlayer().subtractGold(animal.getBasePrice());

            return new Result(true, "a new " + animal.getAnimalTypeName() + " named " + animal.getName() + " has been bought.");
        }
        else {
            return new Result(false, "not enough " + animal.getMaintenance() + " space to buy this animal.");
        }
    }
    public static Result petAnimal(String name) {
        Animal animal = getCurrentPlayer().getAnimalByName(name);

        if(animal == null) {
            return new Result(false, "animal name is not correct.");
        }
        else if(!getCurrentPlayer().getPosition().isAdjacent(animal.getPosition())) {
            return new Result(false, "your position is not adjacent!");
        }

        animal.pet();
        return new Result(true, "you pet " + animal.getName() + ".");
    }
    public static Result shepherdAnimal(String name, int x, int y) {
        Animal animal = getCurrentPlayer().getAnimalByName(name);
        if(animal == null) {
            return new Result(false, "animal name is not correct.");
        }

        if(x >= Map.COLS || y >= Map.ROWS || x < 0 || y < 0) {
            return new Result(false, "invalid x or y!");
        }

        Tile tile = App.currentGame.getTile(x, y);
        if(!App.currentGame.getWeather().couldShepherdAnimals()) {
            return new Result(false, "you cannot shepherd animals in this weather!");
        }
        else if(!tile.isEmpty()) {
            return new Result(false, "animal can't stand on a tile which is not empty.");
        }
        else if(tile.getAreaType().equals(AreaType.LAKE) && !animal.getAnimalType().equals(AnimalType.DUCK)) {
            return new Result(false, "only ducks can swim.");
        }
        else if(tile.getAreaType().equals(AreaType.BARN) && !animal.getMaintenance().equals(Maintenance.BARN)) {
            return new Result(false, "you can't put a " + animal.getAnimalType() + " in a barn.");
        }
        else if(tile.getAreaType().equals(AreaType.COOP) && !animal.getMaintenance().equals(Maintenance.COOP)) {
            return new Result(false, "you can't put a " + animal.getAnimalType() + " in a coop.");
        }
        else if(tile.getAreaType().equals(AreaType.FARM)) {
            Farm farm = (Farm) tile.getArea();
            if(!getCurrentPlayer().checkTerritory(farm)) {
                return new Result(false, "your animals cannot enter other players' territory.");
            }
        }

        Tile initialTile = App.currentGame.getTile(animal.getPosition());
        animal.setPosition(tile.getPosition());

        tile.put(animal);
        initialTile.empty();

        animal.feed();
        return new Result(true, "shepherd " + animal.getName() + " successfully.");
    }
    public static Result feedHayAnimal(String name) {
        Animal animal = getCurrentPlayer().getAnimalByName(name);
        if(animal == null) {
            return new Result(false, "animal name is not correct.");
        }
        else if(!getCurrentPlayer().getPosition().isAdjacent(animal.getPosition())) {
            return new Result(false, "your position is not adjacent!");
        }
        GeneralItem Hay = (GeneralItem) getCurrentPlayer().getInventory().getItemByName("hay");
        if(Hay == null) {
            return new Result(false, "you don't have any hay.");
        }

        animal.feed();
        getCurrentPlayer().getInventory().removeCountFromBackPack(Hay, 1);
        return new Result(true, animal.getName() + " fed with hay.");
    }
    public static Result showAnimalProducts() {
        StringBuilder sb = new StringBuilder();
        sb.append("Available animal products: \n");
        for(Animal animal : getCurrentPlayer().getAnimals()) {
            if(animal.getCurrentProduct() != null) {
                sb.append(animal.getName()).append("    ");
                sb.append(animal.getCurrentProduct().getName()).append("  quality: ");
                sb.append(animal.getCurrentProduct().getProductQuality()).append("\n");
            }
        }

        return new Result(true, sb.toString());
    }
    public static Result collectProduce(String name) {
        Animal animal = getCurrentPlayer().getAnimalByName(name);
        if(animal == null) {
            return new Result(false, "animal name is not correct.");
        }
        else if(!getCurrentPlayer().getPosition().isAdjacent(animal.getPosition())) {
            return new Result(false, "your position is not adjacent!");
        }

        else if(animal.getCurrentProduct() == null) {
            return new Result(false, "no product is available for this animal!");
        }
        else if(animal.getAnimalType().equals(AnimalType.COW) || animal.getAnimalType().equals(AnimalType.GOAT)) {
            return new Result(false, "you have to use milk pail to collect these products.");
        }
        else if(animal.getAnimalType().equals(AnimalType.SHEEP)) {
            return new Result(false, "you have to use shear to collect this product.");
        }
        getCurrentPlayer().getInventory().addToBackPack(animal.getCurrentProduct(), 1);
        animal.setCurrentProduct(null);

        return new Result(true, "product added to the inventory.");
    }
    public static Result sellAnimal(String name) {
        Animal animal = getCurrentPlayer().getAnimalByName(name);
        if(animal == null) {
            return new Result(false, "animal name is not correct.");
        }
        Tile animalTile = App.currentGame.getTile(animal.getPosition());

        App.currentGame.getDateAndTime().removeObserver(animal);
        getCurrentPlayer().getAnimals().remove(animal);
        getCurrentPlayer().addGold(animal.getPrice());
        animalTile.empty();

        return new Result(true,  animal.getName() + " has been sold with price " + animal.getPrice());
    }
    public static Result fishing(String material) {
        Lake lake = null;
        for(int row = getCurrentPlayer().getPosition().y - 1; row <= getCurrentPlayer().getPosition().y + 1; row++) {
            for(int col = getCurrentPlayer().getPosition().x - 1; col <= getCurrentPlayer().getPosition().x + 1; col++) {
                if(App.currentGame.getTile(col, row).getArea() instanceof Lake) {
                    lake = (Lake) App.currentGame.getTile(col, row).getArea();
                    break;
                }
            }
        }

        if(lake == null) {
            return new Result(false, "You need to stand adjacent to lake.");
        }
        else {
            FishingPole fishingPole = getCurrentPlayer().getInventory().getFishingPole(material);

            if(fishingPole == null) {
                return new Result(false, "fishing pole not found.");
            }

            return new Result(true, fishingPole.use(lake, getCurrentPlayer(), App.currentGame.getWeather().getCurrentWeather()));
        }
    }

    public static Result showStoreProducts() {
        Tile playerTile = App.currentGame.getTile(getCurrentPlayer().getPosition());

        if(!playerTile.getAreaType().equals(AreaType.STORE)) {
            return new Result(false, "You need to be in a store to run this command.\n");
        }

        Store store = (Store) playerTile.getArea();
        if(!store.isOpen(App.currentGame.getDateAndTime().getHour())) {
            return new Result(false, "store is closed now!\n");
        }

        return new Result(true, "All Items: \n" + store.displayItems());
    }
    public static Result showAvailableStoreProducts() {
        Tile playerTile = App.currentGame.getTile(getCurrentPlayer().getPosition());

        if(!playerTile.getAreaType().equals(AreaType.STORE)) {
            return new Result(false, "You need to be in a store to run this command.\n");
        }

        Store store = (Store) playerTile.getArea();
        if(!store.isOpen(App.currentGame.getDateAndTime().getHour())) {
            return new Result(false, "store is closed now!\n");
        }

        return new Result(true, "All Available Items Fot You: \n" + store.displayAvailableItems());
    }
    public static Result purchaseProduct(String productName, int count) {
        Tile playerTile = App.currentGame.getTile(getCurrentPlayer().getPosition());

        if(!playerTile.getAreaType().equals(AreaType.STORE)) {
            return new Result(false, "You need to be in a store to run this command.");
        }

        Store store = (Store) playerTile.getArea();
        if(!store.isOpen(App.currentGame.getDateAndTime().getHour())) {
            return new Result(false, "store is closed now!");
        }

        if(!store.checkAvailable(productName)) {
            return new Result(false, "product is not available!");
        }
        else if(!store.checkAmount(productName, count)) {
            return new Result(false, "daily limit exceeded!");
        }

        return new Result(true, store.sell(getCurrentPlayer(), productName, count));
    }
    public static Result sellProduct(String productName, int count) {
        BackPackable item = getCurrentPlayer().getInventory().getItemByName(productName);
        int availableCount = getCurrentPlayer().getInventory().getItemCount(productName);

        if(item == null) {
            return new Result(false, "You don't have that item.");
        }
        else if(count > availableCount) {
            return new Result(false, "You only have " + availableCount + " " + item.getName() + " in your inventory.");
        }
        else if(item.getPrice() == 0) {
            return new Result(false, "this item is not sellable.");
        }

        if(availableCount == -1) {
            getCurrentPlayer().addGold(availableCount * item.getPrice());
            getCurrentPlayer().getInventory().removeFromBackPack(item);
            return new Result(true, "Sold all of your " + item.getName() + ". You earned " + availableCount * item.getPrice() + "gold.");
        }
        else {
            getCurrentPlayer().addGold(count * item.getPrice());
            getCurrentPlayer().getInventory().removeCountFromBackPack(item, count);
            return new Result(true, "Sold " + count + " of your " + item.getName() + ". You earned " + count * item.getPrice() + "gold.");
        }
    }

    public static Result showFriendships() {
        StringBuilder sb = new StringBuilder();
        for (PlayerFriendship friendship : App.currentGame.getFriendships()) {
            if(friendship.getPlayer1().equals(getCurrentPlayer())) {
                sb.append("with ").append(friendship.getPlayer2().getUsername());
                sb.append(" xp: ").append(friendship.getXP()).append(" level: ").append(friendship.getLevel()).append("\n");
            }
            else if(friendship.getPlayer2().equals(getCurrentPlayer())) {
                sb.append("with ").append(friendship.getPlayer1().getUsername());
                sb.append(" xp: ").append(friendship.getXP()).append(" level: ").append(friendship.getLevel()).append("\n");
            }
        }

        return new Result(true, sb.toString());
    }
    public static Result talkFriendship(String username, String message) {
        Player receiver = App.currentGame.getPlayerByUsername(username);
        if(receiver == null) {
            return new Result(false, "invalid player username!");
        }
        if(getCurrentPlayer().equals(receiver)) {
            return new Result(false, "you can't message yourself!");
        }

        Tile playerTile = App.currentGame.getTile(getCurrentPlayer().getPosition());
        Tile recieverTile = App.currentGame.getTile(receiver.getPosition());
        if(!playerTile.isAdjacent(recieverTile)) {
            return new Result(false, "you have to be next to a player to talk to them.");
        }

        PlayerFriendship friendship = App.currentGame.getFriendshipByPlayers(getCurrentPlayer(), receiver);
        friendship.talk(getCurrentPlayer(), message);

        return new Result(true, "your message sent successfully.");
    }
    public static Result talkHistory(String username) {
        Player target = App.currentGame.getPlayerByUsername(username);
        if(target == null) {
            return new Result(false, "invalid player username!\n");
        }
        if(getCurrentPlayer().equals(target)) {
            return new Result(false, "you don't have any messages with yourself!\n");
        }

        PlayerFriendship friendship = App.currentGame.getFriendshipByPlayers(getCurrentPlayer(), target);
        StringBuilder sb = new StringBuilder();

        for(PlayerFriendship.Message message : friendship.getMessages()) {
            if(message.sender().equals(getCurrentPlayer())) {
                sb.append("from: you    message: \"").append(message.message()).append("\"\n");
            }
            else {
                sb.append("from: ").append(message.sender().getUsername()).append("    message: \"").append(message.message()).append("\"\n");
            }
        }

        return new Result(true, sb.toString());
    }
    public static Result gift(String username, String itemName, int amount) {
        Player receiver = App.currentGame.getPlayerByUsername(username);
        if(receiver == null) {
            return new Result(false, "invalid player username!");
        }
        if(getCurrentPlayer().equals(receiver)) {
            return new Result(false, "you can't gift yourself!");
        }

        BackPackable item = getCurrentPlayer().getInventory().getItemByName(itemName);
        int availableCount = getCurrentPlayer().getInventory().getItemCount(itemName);
        if(item == null) {
            return new Result(false, "you don't have that item.");
        }
        if(amount > availableCount) {
            return new Result(false, "you don't have enough amount of that item.");
        }

        Player sender = getCurrentPlayer();
        PlayerFriendship friendship = App.currentGame.getFriendshipByPlayers(getCurrentPlayer(), receiver);
        if(friendship.getLevel() < 1) {
            return new Result(false, "at least 1 level of friendship required!");
        }
        friendship.gift(sender, item);
        receiver.addToBackPack(item, amount);
        sender.getInventory().removeCountFromBackPack(item, amount);

        return new Result(true, "you gave "  + amount + " " + itemName + " to " + receiver.getUsername() + "!");
    }
    public static Result giftList(String username) {
        Player sender = App.currentGame.getPlayerByUsername(username);
        if(sender == null) {
            return new Result(false, "invalid player username!");
        }
        if(getCurrentPlayer().equals(sender)) {
            return new Result(false, "Oops!");
        }

        PlayerFriendship friendship = App.currentGame.getFriendshipByPlayers(sender, getCurrentPlayer());
        StringBuilder sb = new StringBuilder();
        sb.append("gift list: \n");
        if(friendship.getGifts(sender) != null) {
            for (int i = 0; i < friendship.getGifts(sender).size(); i++) {
                PlayerFriendship.Gift gift = friendship.getGifts(sender).get(i);
                sb.append((i + 1)).append(".  ").append(gift.getItem().getName());
                if (gift.getRate() == 0) {
                    sb.append("    unrated\n");
                } else {
                    sb.append("    rate: ").append(gift.getRate()).append("/5\n");
                }
            }
        }

        return new Result(true, sb.toString());
    }
    public static Result rateGift(String username, int giftNumber, int rate) {
        Player sender = App.currentGame.getPlayerByUsername(username);
        if(sender == null) {
            return new Result(false, "invalid player username!");
        }
        if(getCurrentPlayer().equals(sender)) {
            return new Result(false, "Oops!");
        }

        PlayerFriendship friendship = App.currentGame.getFriendshipByPlayers(sender, getCurrentPlayer());
        if(giftNumber <= 0 || giftNumber > friendship.getGifts(sender).size()) {
            return new Result(false, "invalid gift number!");
        }
        else if(!(1 <= rate && rate <= 5)) {
            return new Result(false, "choose between 1 to 5.");
        }
        else if(friendship.getGifts(sender).get(giftNumber - 1).getRate() != 0) {
            return new Result(false, "you can rate each gift once.");
        }

        friendship.getGifts(sender).get(giftNumber - 1).setRate(rate);
        friendship.rateGift(rate);
        return new Result(true, "you rated " + friendship.getGifts(sender).get(giftNumber - 1).getItem().getName()
                + " with " + rate + "/5");
    }
    public static Result giftHistory(String username) {
        Player sender = App.currentGame.getPlayerByUsername(username);
        if(sender == null) {
            return new Result(false, "invalid player username!");
        }
        if(getCurrentPlayer().equals(sender)) {
            return new Result(false, "Oops!");
        }

        PlayerFriendship friendship = App.currentGame.getFriendshipByPlayers(sender, getCurrentPlayer());
        StringBuilder sb = new StringBuilder();
        if(friendship.getGifts(sender) != null) {
            sb.append("gifts from ").append(sender.getUsername()).append(": \n");
            for(PlayerFriendship.Gift gift : friendship.getGifts(sender)) {
                sb.append(gift.getItem().getName());
                if(gift.getRate() == 0) {
                    sb.append("    unrated\n");
                }
                else {
                    sb.append("    rate: ").append(gift.getRate()).append("/5\n");
                }
            }
        }
        else {
            sb.append("no gifts from ").append(sender.getUsername()).append("!\n");
        }

        if(friendship.getGifts(getCurrentPlayer()) != null) {
            sb.append("gifts from you: \n");
            for(PlayerFriendship.Gift gift : friendship.getGifts(getCurrentPlayer())) {
                sb.append(gift.getItem().getName());
                if(gift.getRate() == 0) {
                    sb.append("    unrated\n");
                }
                else {
                    sb.append("    rate: ").append(gift.getRate()).append("/5\n");
                }
            }
        }
        else {
            sb.append("no gifts from you!\n");
        }

        return new Result(true, sb.toString());
    }
    public static Result hug(String username) {
        Player receiver = App.currentGame.getPlayerByUsername(username);
        if(receiver == null) {
            return new Result(false, "invalid player username!");
        }
        if(getCurrentPlayer().equals(receiver)) {
            return new Result(false, "you can't hug yourself!");
        }

        Tile playerTile = App.currentGame.getTile(getCurrentPlayer().getPosition());
        Tile recieverTile = App.currentGame.getTile(receiver.getPosition());
        if(!playerTile.isAdjacent(recieverTile)) {
            return new Result(false, "you have to be next to a player to hug them.");
        }

        PlayerFriendship friendship = App.currentGame.getFriendshipByPlayers(getCurrentPlayer(), receiver);
        if(friendship.getLevel() < 2) {
            return new Result(false, "at least 2 levels of friendship required!");
        }

        friendship.hug();
        return new Result(true, "you hugged "  + receiver.getUsername() + "!");
    }
    public static Result flower(String username) {
        Player receiver = App.currentGame.getPlayerByUsername(username);
        if(receiver == null) {
            return new Result(false, "invalid player username!");
        }
        if(getCurrentPlayer().equals(receiver)) {
            return new Result(false, "you can't give flower to yourself!");
        }

        Tile playerTile = App.currentGame.getTile(getCurrentPlayer().getPosition());
        Tile recieverTile = App.currentGame.getTile(receiver.getPosition());
        if(!playerTile.isAdjacent(recieverTile)) {
            return new Result(false, "you have to be next to a player to give flower to them.");
        }

        PlayerFriendship friendship = App.currentGame.getFriendshipByPlayers(getCurrentPlayer(), receiver);
        if(friendship.getLevel() < 2) {
            return new Result(false, "at least 2 levels of friendship required!");
        }

        friendship.flower();
        return new Result(true, "you gave flower to "  + receiver.getUsername() + ". friendship upgraded to level 3.");
    }
    public static Result marry(String username) {
        Player target = App.currentGame.getPlayerByUsername(username);
        if(target == null) {
            return new Result(false, "invalid player username!\n");
        }

        Tile playerTile = App.currentGame.getTile(getCurrentPlayer().getPosition());
        Tile targetTile = App.currentGame.getTile(target.getPosition());
        if(!playerTile.isAdjacent(targetTile)) {
            return new Result(false, "you have to be next to a player to ask for marriage!");
        }

        if(getCurrentPlayer().getGender().equals(Gender.GIRL)) {
            return new Result(false, "you have to be a boy to ask for marriage!");
        }
        else if(target.getGender().equals(Gender.BOY)) {
            return new Result(false, "Why are you gay?");
        }
        PlayerFriendship friendship = App.currentGame.getFriendshipByPlayers(getCurrentPlayer(), target);
        if(friendship.getLevel() < 3) {
            return new Result(false, "at least 3 levels of friendship required!");
        }

        target.addMessage(new PlayerFriendship.Message(getCurrentPlayer(), "Will you Marry me?"));
        return new Result(true, "You're proposal has been sent to " + target.getUsername() + ".");
    }
    public static Result respondMarriage(String username, String answer) {
        Player husband = App.currentGame.getPlayerByUsername(username);
        if(husband == null) {
            return new Result(false, "invalid player username!\n");
        }

        PlayerFriendship friendship = App.currentGame.getFriendshipByPlayers(husband, getCurrentPlayer());
        if(answer.equals("-accept")) {
            friendship.marry();
            return new Result(true, "CONGRATS ON YOUR WEDDING!");
        }
        else {
            friendship.reject();
            return new Result(true, "you broke his heart :(");
        }
    }

    public static Result showCropInfo(String name) {
        Crops crop = Crops.getByName(name);
        if(crop != null)
            return new Result(true,crop.toString());
        TreeType tree = TreeType.getTreeTypeByName(name);
        if(tree != null)
            return new Result(true,tree.toString());
        return new Result(false, "No such crop '" + name + "'.");
    }

    public static Result plant(String seedName, int dx, int dy) {
        Game current = App.currentGame;
        int nextX = current.getCurrentPlayer().getPosition().x + dx;
        int nextY = current.getCurrentPlayer().getPosition().y + dy;

        if(dx==0 && dy==0) {return new Result(false,"this is not a valid direction!");}

        if(nextX>Map.COLS||nextY>Map.ROWS||nextX<0||nextY<0) {
            return new Result(false,"you are going out of bounds!");}

        Tile goalTile = App.currentGame.getMap().getTile(new Position(nextX,nextY));

        if(!goalTile.getObjectInTile().getClass().equals(PloughedPlace.class))
            return new Result(false,"you should plough the tile first!");

        PloughedPlace tobeSeeded = (PloughedPlace) goalTile.getObjectInTile();

        if(CropSeeds.getByName(seedName) != null){
            return tobeSeeded.seed(CropSeeds.getByName(seedName));
        }

        if(SeedType.getByName(seedName) != null){
            return tobeSeeded.seed(SeedType.getByName(seedName));
        }

        return new Result(false,"no seed found with this name");
    }

    public static Result fertilize(String fertilizerName, Position position) {
        Tile goalTile = App.currentGame.getTile(position.x,position.y);
        if(goalTile.getObjectInTile() == null) return new Result(false,"goal tile is empty");
        if(!goalTile.getObjectInTile().getClass().equals(PloughedPlace.class))
            return new Result(false,"goal tile is not a PloughedPlace");
        PloughedPlace goalPlace = (PloughedPlace) goalTile.getObjectInTile();
        if(fertilizerName.equals("water fertilizer")){
            return goalPlace.getCurrentState().fertilize(Fertilizer.Water);
        }
        else if(fertilizerName.equals("growth fertilizer")){
            return goalPlace.getCurrentState().fertilize(Fertilizer.Growth);
        }
        else {
            return new Result(false,"fertilizer not found");
        }
    }

    public static Result plantMixedSeed(int dx,int dy) {

        CropSeeds randomSeed = MixedSeedCrop.getRandomSeed(App.currentGame.getDateAndTime().getSeason());

        return plant(randomSeed.name(), dx, dy); // if incorrect errors are shown you should check plant method
    }


    public static Result showPlant(int x, int y) {
        Position position = new Position(x, y);
        if(position.outOfBounds()) {
            return new Result(false,"this position is out of bounds!");
        }
        Tile tile = App.currentGame.getMap().getTile(position);
        if(!tile.getObjectInTile().getClass().equals(PloughedPlace.class))
            return new Result(false,"this is not a ploughed tile!");
        PloughedPlace toBeShown = (PloughedPlace) tile.getObjectInTile();

        if(!toBeShown.hasTreeOrCrop())
            return new Result(false,"there is not any plant here!");
        return new Result(true,toBeShown.printInfo());

    }

    public static Result placeItem(String itemName, Position position) {
        return null;
    }


    public static Result askMarriage(String username, String ringName) {
        return null;
    }
    public static void AddRecipe(String recipeName) {
        recipeName = recipeName.trim().toLowerCase().replaceAll("_", " ");
        Player player = App.currentGame.getCurrentPlayer();
        for(FoodType foodType : FoodType.values()) {
            if(foodType.getName().equals(recipeName)) {
                player.getAvailableFoods().add(new Food(foodType));
                System.out.println("recipe add to inventory:" + foodType.getName());
                return;
            }
        }
        for (CraftItemType craftItem : CraftItemType.values()) {
            if (craftItem.getName().equals(recipeName)){
                player.getAvailableCrafts().add(new CraftItem(craftItem));
                System.out.println("recipe add to inventory:" + craftItem.getName());
                return;
            }
        }
        System.out.println("invalid recipe type");
    }
    public static Result meetNPC(String npcName) {
        Player player = App.currentGame.getCurrentPlayer();
        NPC npc = DefaultNPCs.getInstance().getNPCByName(npcName);
        if(npc == null){
            return new Result(false,"invalid name for npc");
        }
        return new Result(true,npc.meet(player));
    }

    public static Result giftNPC(String NPCName, String itemName) {
        Player player = App.currentGame.getCurrentPlayer();
        NPC npc = DefaultNPCs.getInstance().getNPCByName(NPCName);
        if(npc == null){
            return new Result(false,"invalid name for npc");
        }
        if(!player.getPosition().isAdjacent(npc.getHomeLocation().getPosition())){
            return new Result(false,"you are not adjacent with this npc");
        }
        return new Result(true,npc.gift(player,getCurrentPlayer().getInventory().getItemByName(itemName)));
    }

    public static Result friendShipNPCList(){
        Player player = getCurrentPlayer();
        StringBuilder answer = new StringBuilder();
        for(NPC npc : DefaultNPCs.getInstance().defaultOnes().values()){
            if(npc.getFriendships().getOrDefault(player,null)!=null){
                NPCFriendShip friendship = npc.getFriendships().get(player);
                answer.append("npc name: "+npc.getName()+"\n"
                        +"your points: "+friendship.getPoints()+"\n"+
                        "your friendship level: "+friendship.getLevel()+"\n"+
                        "--------------------------------------\n");
            }
        }
        if(answer.isEmpty()) return new Result(false,"you have not started any relationship \n");
        return new Result(true,answer.toString());
    }

    public static Result questLists() {
        Player player = App.currentGame.getCurrentPlayer();
        StringBuilder answer = new StringBuilder();
        List<Quest> quests = new ArrayList<>();
        int number = 1;
        for (NPC npc : DefaultNPCs.getInstance().defaultOnes().values()) {
            NPCFriendShip fs = npc.getFriendships().get(player);
            if (fs != null) {
                for (Quest q : fs.getPlayerQuests().keySet()) {
                    if (fs.getPlayerQuests().get(q)) {
                        answer.append(q.getInfo(number));
                        number ++;
                    }
                }
            }
        }
        return new Result(true, answer.toString());
    }

    public static Result finishQuest(int index) {
        Player player = App.currentGame.getCurrentPlayer();
        ArrayList<Quest> quests = new ArrayList<>();
        ArrayList<NPCFriendShip> friendships = new ArrayList<>();
        for(NPC npc : DefaultNPCs.getInstance().defaultOnes().values()){
            NPCFriendShip fs = npc.getFriendships().get(player);
            if (fs != null) {
                for (Quest q : fs.getPlayerQuests().keySet()) {
                    if (fs.getPlayerQuests().get(q)) {
                        quests.add(q);
                        friendships.add(fs);
                    }
                }
            }
        }

        return friendships.get(index-1).finishQuest(quests.get(index-1).getRequest());
    }
    public static void ShowRecipe() {
        Player player = getCurrentPlayer();
        for (CraftItem availableCraft : player.getAvailableCrafts()) {
            System.out.println(availableCraft.getCraftItemType().recipe);
        }
    }
    public static Result crafting(String craftingName) {
        Player player = App.currentGame.getCurrentPlayer();
        craftingName = craftingName.trim().toLowerCase().replaceAll("_", " ");
        if (player.getInventory().getItems().size()==player.getInventory().getCapacity()){
            return new Result(false , "your inventory is full!");
        }
        CraftItem temp = null;
        for (CraftItem availableCraft : player.getAvailableCrafts()) {
            if(availableCraft.getName().equals(craftingName)){
                temp = availableCraft;
            }
        }
        if(temp == null) {
            return new Result(false, "You can't craft this item");
        }
        for (BackPackable backPackable : temp.getCraftItemType().ingredients.keySet()) {
            boolean find = false;
            for (BackPackable packable : player.getInventory().getItems().keySet()) {
                if(packable.getName().equals(backPackable.getName())){
                    find = true;
                    int num = player.getInventory().getItemCount(packable.getName());
                    int number = temp.getCraftItemType().ingredients.get(backPackable);
                    if(number > num)
                        return new Result(false , "You don't have enough material");
                }
                break;
            }
            if(!find) {
                return new Result(false , "You don't have enough material");
            }
        }
        for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
            for (BackPackable packable : temp.getCraftItemType().ingredients.keySet()) {
                if(packable.getName().equals(backPackable.getName())){
                    int num = player.getInventory().getItemCount(packable.getName());
                    int number = temp.getCraftItemType().ingredients.get(backPackable);
                    if(number < num)
                        player.getInventory().removeCountFromBackPack(backPackable , number);
                }
            }
        }
        player.getInventory().getItems().put(temp , player.getInventory().getItems().getOrDefault(temp , 1));
        player.subtractEnergy(2);
        return new Result(true,"craft make successfully");
    }
    public static Result PlaceItem(String itemName, int x , int y) {
        itemName = itemName.trim().toLowerCase().replaceAll("_"," ");
        BackPackable item = getCurrentPlayer().getInventory().getItemByName(itemName);
        if(item == null){
            return new Result(false , "you dont have this item");
        }
        Tile tile = App.currentGame.getTile(getCurrentPlayer().getPosition().x + x, getCurrentPlayer().getPosition().y + y);
        if(tile.isEmpty() && (tile.getArea() instanceof Farm)) {
            tile.setObjectInTile(item);
            getCurrentPlayer().getInventory().removeCountFromBackPack(item, 1);
            return new Result(true , "Item placed successfully");
        }

        return new Result(false , "You can't place item in this tile");
    }
    public static Result UseArtisan(String artisanName , String itemName) {
        artisanName = artisanName.trim().toLowerCase().replaceAll("_"," ");
        itemName = itemName.trim().toLowerCase().replaceAll("_"," ");
        Player player = App.currentGame.getCurrentPlayer();
        CraftItem artisan = null;
        for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
            if (backPackable.getName().equals(artisanName)) {
                artisan = (CraftItem) backPackable;
                break;
            }
        }
        if (artisan == null) {
            return new Result(false,"artisan item not available");
        }
        ArtisanItem artisanItem = null;
        for(TreeType treeType: TreeType.values()) {
            FruitType fruitType = treeType.getFruitType();
            if (fruitType.getName().equals(itemName)) {
                if(artisanName.equals("keg")){
                    artisanItem = new ArtisanItem(ArtisanItemType.WINE);
                    artisanItem.getArtisanItemType().setMoney(treeType.getBasePrice()*3);
                    artisanItem.getArtisanItemType().setEnergy(treeType.getEnergy()*175/100);
                }
                if(artisanName.equals("preserves jar")){
                    artisanItem = new ArtisanItem(ArtisanItemType.JELLY);
                    artisanItem.getArtisanItemType().setMoney(treeType.getBasePrice()*2 + 50);
                    artisanItem.getArtisanItemType().setEnergy(treeType.energy*2);
                }
                if(artisanName.equals("dehydrator")){
                    artisanItem = new ArtisanItem(ArtisanItemType.DRIED_FRUIT);
                    artisanItem.getArtisanItemType().setMoney(treeType.getBasePrice()*75/10 + 25);
                }
                break;
            }
        }
        for(Crops crop: Crops.values()) {
            if (crop.getName().equals(artisanName)) {
                artisanItem = new ArtisanItem(ArtisanItemType.JUICE);
                artisanItem.getArtisanItemType().setMoney(crop.getBasePrice()*225/100);
                artisanItem.getArtisanItemType().setEnergy(2*crop.getEnergy());
                break;
            }
        }
        for(FishType fish: FishType.values()) {
            if(fish.getName().equals(artisanName)) {
                artisanItem = new ArtisanItem(ArtisanItemType.SMOKED_FISH);
                artisanItem.getArtisanItemType().setMoney(fish.basePrice*2);
            }
        }
        if(artisanItem == null) {
            switch (itemName) {
                case "":
                    if(artisan.getName().equals("bee house"))
                        artisanItem = new ArtisanItem(ArtisanItemType.HONEY);
                    break;
                case "milk":
                    if(artisan.getName().equals("cheese press"))
                        artisanItem = new ArtisanItem(ArtisanItemType.CHEESE_MILK);
                    break;
                case "large milk":
                    if(artisan.getName().equals("cheese press"))
                        artisanItem = new ArtisanItem(ArtisanItemType.CHEESE_LARGE_MILK);
                    break;
                case "goat milk":
                    if(artisan.getName().equals("cheese press"))
                        artisanItem = new ArtisanItem(ArtisanItemType.GOAT_CHEESE_MILK);
                    break;
                case "large goat milk":
                    if(artisan.getName().equals("cheese press"))
                        artisanItem = new ArtisanItem(ArtisanItemType.GOAT_CHEESE_LARGE_MILK);
                    break;
                case "wheat" :
                    if(artisan.getName().equals("keg"))
                        artisanItem = new ArtisanItem(ArtisanItemType.BEER);
                    break;
                case "rice" :
                    if(artisan.getName().equals("keg"))
                        artisanItem = new ArtisanItem(ArtisanItemType.VINEGAR);
                    break;
                case "coffee been" :
                    if(artisan.getName().equals("keg"))
                        artisanItem = new ArtisanItem(ArtisanItemType.COFFEE);
                    break;
                case "juice" :

                    if(artisan.getName().equals("keg"))// must change
                        artisanItem = new ArtisanItem(ArtisanItemType.JUICE);
                    break;
                case "honey" :
                    if(artisan.getName().equals("keg"))
                        artisanItem = new ArtisanItem(ArtisanItemType.MEAD);
                    break;
                case "hops" :
                    if(artisan.getName().equals("keg"))
                        artisanItem = new ArtisanItem(ArtisanItemType.PALE_ALE);
                    break;
                case "common mushroom" :
                    if(artisan.getName().equals("dehydrator"))
                        artisanItem = new ArtisanItem(ArtisanItemType.DRIED_COMMON_MUSHROOM);
                    break;
                case "red mushroom" :
                    if(artisan.getName().equals("dehydrator"))
                        artisanItem = new ArtisanItem(ArtisanItemType.DRIED_RED_MUSHROOM);
                    break;
                case "purple mushroom" :
                    if(artisan.getName().equals("dehydrator"))
                        artisanItem = new ArtisanItem(ArtisanItemType.DRIED_PURPLE_MUSHROOM);
                    break;
                case "grapes" :
                    if(artisan.getName().equals("dehydrator"))
                        artisanItem = new ArtisanItem(ArtisanItemType.RAISINS);
                    break;
                case "coal":
                    if(artisan.getName().equals("charcoal klin"))
                        artisanItem = new ArtisanItem(ArtisanItemType.COAL);
                    break;
                case "rabbit wool":
                    if(artisan.getName().equals("loom"))
                        artisanItem = new ArtisanItem(ArtisanItemType.CLOTH_RABBIT);
                    break;
                case "sheep wool":
                    if(artisan.getName().equals("loom"))
                        artisanItem = new ArtisanItem(ArtisanItemType.CLOTH_SHEEP);
                    break;
                case "egg" :
                    if(artisan.getName().equals("mayonnaise machine"))
                        artisanItem = new ArtisanItem(ArtisanItemType.MAYONNAISE_EGG);
                    break;
                case "large egg" :
                    if(artisan.getName().equals("mayonnaise machine"))
                        artisanItem = new ArtisanItem(ArtisanItemType.MAYONNAISE_LARGE_EGG);
                    break;
                case "dinosaur egg" :
                    if(artisan.getName().equals("mayonnaise machine"))
                        artisanItem = new ArtisanItem(ArtisanItemType.DINOSAUR_MAYONNAISE);
                    break;
                case "duck egg" :
                    if(artisan.getName().equals("mayonnaise machine"))
                        artisanItem = new ArtisanItem(ArtisanItemType.DUCK_MAYONNAISE);
                    break;
                case "sunflower" :
                    if(artisan.getName().equals("oil maker"))
                        artisanItem = new ArtisanItem(ArtisanItemType.OIL_SUNFLOWER);
                    break;
                case "sunflower seed" :
                    if(artisan.getName().equals("oil maker"))
                        artisanItem = new ArtisanItem(ArtisanItemType.OIL_SUNFLOWER_SEED);
                    break;
                case "corn" :
                    if(artisan.getName().equals("oil maker"))
                        artisanItem = new ArtisanItem(ArtisanItemType.OIL_CORN);
                    break;
                case "truffle"   :
                    if(artisan.getName().equals("oil maker"))
                        artisanItem = new ArtisanItem(ArtisanItemType.TRUFFLE_OIL);
                    break;
                case "pickles":
                    if(artisan.getName().equals("preserves jar"))
                        artisanItem = new ArtisanItem(ArtisanItemType.PICKLES);
                    break;
                case "smoked fish":
                    //must be fixed
                    if(artisan.getName().equals("fish smoker"))
                        artisanItem = new ArtisanItem(ArtisanItemType.SMOKED_FISH);
                    break;
                case "furnace":
                    artisanItem = new ArtisanItem(ArtisanItemType.METAL_BAR);
                    break;
                default:
                    return new Result(false , "invalid item type");
            }
        }
        if (artisanItem == null)
            return new Result(false,"You cant make this item whit this material");
        if (artisanItem.getName().equals("honey")){
            Game game = App.currentGame;
            artisanItem.setHour(game.getDateAndTime().getHour());
            artisanItem.setDay(game.getDateAndTime().getDay());
            player.getArtisanItems().add(artisanItem);
            return new Result(true , "artisan item made successfully");
        }
        for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
                if(artisanItem.getArtisanItemType().ingredients.getName().trim().toLowerCase().replaceAll("_" , " ").equals(backPackable.getName())) {
                    if (player.getInventory().getItemCount(backPackable.getName())<artisanItem.getArtisanItemType().number) {
                        return new Result(false , "You dont have this material");
                    }
                    Game game = App.currentGame;
                    artisanItem.setHour(game.getDateAndTime().getHour());
                    artisanItem.setDay(game.getDateAndTime().getDay());
                    player.getArtisanItems().add(artisanItem);
                    return new Result(true , "artisan item made successfully");
                }
        }
        return new Result(false , "You dont have this material");
    }
    public static Result GetArtisan(String artisanName) {
        artisanName = artisanName.trim().toLowerCase().replaceAll("_" , " ");
        Player player = App.currentGame.getCurrentPlayer();
        ArtisanItem temp = null;
        for (ArtisanItem artisanItem : player.getArtisanItems()) {
            if (artisanItem.getName().equals(artisanName)) {
                temp = artisanItem;
            }
        }
        if (temp == null) {
            return new Result(false, "Artisan item not found");
        }
        Game game = App.currentGame;
        if (temp.getArtisanItemType().productionTimeInHours==0){
            if (temp.getDay()>game.getDateAndTime().getDay()) {
                player.getInventory().addToBackPack(temp ,1);
                player.getArtisanItems().remove(temp);
                return new Result(true , "You receive Artisan item successfully");
            }
            return new Result(false, "Artisan item is not ready");
        }
        int hour = 0;
        hour += (game.getDateAndTime().getHour()-temp.getHour());
        hour += (game.getDateAndTime().getDay()-temp.getDay())*24;
        if(temp.getArtisanItemType().productionTimeInHours>hour) {
            return new Result(false, "Artisan item is not ready");
        }
        player.getInventory().addToBackPack(temp ,1);
        player.getArtisanItems().remove(temp);
        return new Result(true , "You receive Artisan item successfully");
    }
    public static Result Cooking(String Rcipe){
        Rcipe = Rcipe.trim().toLowerCase().replaceAll("_" , " ");
        Player player = App.currentGame.getCurrentPlayer();
        if (player.getInventory().getCapacity() == player.getInventory().getItems().size()) {
            return new Result(false, "Your inventory is full");
        }
        Food food = null;
        for (Food availableFood : player.getAvailableFoods()) {
            if (availableFood.getName().equals(Rcipe)) {
                food = availableFood;
            }
        }
        if (food == null) {
            return new Result(false, "You can't cook this food");
        }
        FoodType ingredient = null;
        for(FoodType foodRecipe : FoodType.values()) {
            if (foodRecipe.getName().equals(Rcipe)) {
                ingredient = foodRecipe;
            }
        }
        Tile tile = App.currentGame.getTile(getCurrentPlayer().getPosition());
        if(!(tile.getArea() instanceof House playersHouse)){
            return new Result(false, "You must be in your house");
        }
        Fridge fridge = playersHouse.getFridge();
        for (BackPackable backPackable : ingredient.ingredients.keySet()) {
            boolean find = false;
            for (BackPackable packable : player.getInventory().getItems().keySet()) {
                if (backPackable.getName().equals(packable.getName())) {
                    if(player.getInventory().getItemCount(packable.getName())< ingredient.ingredients.get(backPackable)) {
                        return new Result(false, "You dont have enough material");
                    }
                    find = true;
                    break;
                }
            }
            if(!find){
                BackPackable packable = fridge.getItemByName(backPackable.getName());
                if(packable!=null){
                    find = true;
                }
                if(fridge.getItemCount(backPackable.getName())<ingredient.ingredients.get(backPackable)) {
                    return new Result(false, "You dont have enough material");
                }
            }
            if (!find) {
                return new Result(false, "You don't have enough material");
            }
        }
        for (BackPackable backPackable : ingredient.ingredients.keySet()) {
            int num = ingredient.ingredients.get(backPackable);
            player.getInventory().removeCountFromBackPack(backPackable , num);
        }
        for (BackPackable packable : ingredient.ingredients.keySet()) {
            int num = ingredient.ingredients.get(packable);
            int number = fridge.getItemCount(packable.getName());
            fridge.removeFromFridge(packable);
            fridge.addToFridge(packable , number-num);
        }
        player.getInventory().addToBackPack(food ,1);
        return new Result(true , "You cook this food");
    }
    public static void StartTrade(){
        Player player = App.currentGame.getCurrentPlayer();
        System.out.println("new trade offers:");
        for (TradeWhitMoney tradeWhitMoney : player.getTradesWhitMoney()) {
            if (tradeWhitMoney.getType().equals("offer")){
                if (tradeWhitMoney.isNewTrade()){
                    System.out.println("user: "+tradeWhitMoney.getSeller().getUsername()+
                            " item: "+ tradeWhitMoney.getName() + " amount: "+ tradeWhitMoney.getAmount()+
                            " price: "+ tradeWhitMoney.getMoney());
                    tradeWhitMoney.setNewTrade(false);
                }
            }
        }
        for (TradeWithItem tradeWhitItem : player.getTradesWithItem()) {
            if (tradeWhitItem.getType().equals("offer")){
                if (tradeWhitItem.isNewTrade()){
                    System.out.println("user: "+tradeWhitItem.getSeller().getUsername()+
                            " item: "+ tradeWhitItem.getName() + " amount: "+ tradeWhitItem.getAmount()+
                            " target item: "+ tradeWhitItem.getTargetName() + " number of target item: "+ tradeWhitItem.getTargetAmount());
                    tradeWhitItem.setNewTrade(false);
                }
            }
        }
        System.out.println("new trade requests:");
        for (TradeWhitMoney tradeWhitMoney : player.getTradesWhitMoney()) {
            if (tradeWhitMoney.getType().equals("request")){
                if (tradeWhitMoney.isNewTrade()){
                    System.out.println("user: "+tradeWhitMoney.getBuyer().getUsername()+
                            " item: "+ tradeWhitMoney.getName() + " amount: "+ tradeWhitMoney.getAmount()+
                            " price: "+ tradeWhitMoney.getMoney());
                    tradeWhitMoney.setNewTrade(false);
                }
            }
        }
        for (TradeWithItem tradeWhitItem : player.getTradesWithItem()) {
            if (tradeWhitItem.getType().equals("request")){
                if (tradeWhitItem.isNewTrade()){
                    System.out.println("user: "+tradeWhitItem.getBuyer().getUsername()+
                            " item: "+ tradeWhitItem.getName() + " amount: "+ tradeWhitItem.getAmount()+
                            " target item: "+ tradeWhitItem.getTargetName() + " number of target item: "+ tradeWhitItem.getTargetAmount());
                    tradeWhitItem.setNewTrade(false);
                }
            }
        }
    }
}
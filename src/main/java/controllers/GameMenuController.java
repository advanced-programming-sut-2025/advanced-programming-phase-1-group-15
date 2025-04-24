package controllers;

import models.App;
import models.Player;
import models.Result;
import models.map.*;
import models.tools.BackPackable;

public class GameMenuController {
    public static Player getCurrentPlayer() {
        return App.currentGame.getCurrentPlayer();
    }
    
    public static Result walk(int x, int y) {
        if(x >= 100 || y >= 50 || x < 0 || y < 0) {
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
            if(!getCurrentPlayer().equals(farm.getOwner())) {
                return new Result(false, "you cannot enter other players' territory.");
            }
        }

        int energyNeeded = getCurrentPlayer().calculateWalkingEnergy(new Position(x, y));

        return new Result(true, energyNeeded + " energy would be consumed. Do you agree? (y/n)");
    }

    public static Result setPosition(int x, int y) {
        Position position = new Position(x, y);

        getCurrentPlayer().walk(position);
        if(getCurrentPlayer().isFainted()) {
            return new Result(false, "Oops! you've fainted!");
        }

        return new Result(true, "moved to position " + position.toString() + " successfully.");
    }

    public static Result removeFromInventory(String itemName, int count) {
        BackPackable item = getCurrentPlayer().getInventory().getItemByName(itemName);
        int availableCount = getCurrentPlayer().getInventory().getItemCount(itemName);
        if(item == null) {
            return new Result(false, "You don't have that item.");
        }
        else if(count > availableCount) {
            return new Result(false, "You only have " + count + " " + itemName + " in your inventory.");
        }

        if(count == -1) {
            getCurrentPlayer().getInventory().removeFromBackPack(item);
            return new Result(true, "You removed item " + itemName + " from your inventory.");
        }
        else {
            getCurrentPlayer().getInventory().removeCountFromBackPack(item, count);
            return new Result(true, "You removed item " + count + " " + itemName + " from your inventory.");
        }
    }

    public static Result equipItem(String name) {
        return null;
    }

    public static Result craftItem(String craftName) {
        return null;
    }

    public static Result plant(String seedName, Position position) {
        return null;
    }
    public static Result showPlant(Position position) {
        return null;
    }
    public static Result fertilize(String fertilizerName, Position position) {
        return null;
    }

    public static Result placeItem(String itemName, Position position) {
        return null;
    }

    public static Result cookFood(String foodName) {
        return null;
    }
    public static Result eatFood(String foodName) {
        return null;
    }

    public static Result collectProduce(String animalName) {
        return null;
    }
    public static Result sellAnimal(String animalName) {
        return null;
    }
    public static Result fishing(String fishingPole) {
        return null;
    }
    public static Result buildBuilding(String buildingName) {
        return null;
    }


    public static Result askMarriage(String username, String ringName) {
        return null;
    }

    public static Result giftNPC(String NPCName, String itemName) {
        return null;
    }
    public static Result questLists() {
        return null;
    }
    public static Result finishQuest(int index) {
        return null;
    }
}
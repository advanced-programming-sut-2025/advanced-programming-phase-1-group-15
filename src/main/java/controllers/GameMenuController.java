package controllers;

import models.App;
import models.Player;
import models.Result;
import models.map.*;
import models.tools.BackPackable;
import models.tools.Hoe;
import models.tools.Tool;

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
            if(!getCurrentPlayer().equals(farm.getOwner())) {
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

        return new Result(true, "moved to position " + position.toString() + " successfully.");
    }

    public static Result removeFromInventory(String itemName, int count) {
        BackPackable item = getCurrentPlayer().getInventory().getItemByName(itemName);
        int availableCount = getCurrentPlayer().getInventory().getItemCount(itemName);
        if(item == null) {
            return new Result(false, "You don't have that item.");
        }
        else if(count > availableCount) {
            return new Result(false, "You only have " + count + " " + item.getName() + " in your inventory.");
        }

        if(count == -1) {
            getCurrentPlayer().getInventory().removeFromBackPack(item);
            return new Result(true, "You removed item " + item.getName() + " from your inventory.");
        }
        else {
            getCurrentPlayer().getInventory().removeCountFromBackPack(item, count);
            return new Result(true, "You removed item " + count + " " + item.getName() + " from your inventory.");
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

        if(useTile.getAreaType().equals(AreaType.LAKE)) {
            return new Result(false, "you can't use tools on a lake tile!");
        }
        else if(tool instanceof Hoe && !useTile.isEmpty()) {
            return new Result(false, "You can't use hoe on a tile which is not empty.");
        }

        getCurrentPlayer().subtractEnergy(tool.calculateEnergyConsume(useTile));
        BackPackable receivedItem = tool.use(useTile);
        if(receivedItem != null) {
            getCurrentPlayer().addToBackPack(receivedItem, 1);
            return new Result(true, "used tool " + tool.getName() + " on tile " + usePosition + "\n" +
                    receivedItem.getName() + " added to your inventory");
        }
        else {
            return new Result(true, "used tool " + tool.getName() + " on tile " + usePosition);
        }
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
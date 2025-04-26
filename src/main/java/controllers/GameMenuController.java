package controllers;

import models.App;
import models.Game;
import models.Player;
import models.Result;
import models.farming.CropSeeds;
import models.farming.Crops;
import models.farming.PloughedTile;
import models.farming.SeedType;
import models.map.*;
import models.tools.BackPackable;
import models.tools.Hoe;
import models.tools.Tool;
import models.tools.TrashCan;

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

    public static Result showCropInfo(String name) {
        Crops crop = Crops.getByName(name);
        if(crop == null)
            return new Result(false,"no crop with this name exists!");
        return new Result(true,crop.toString());
    }

    public static Result plant(String seedName, int dx, int dy) {
        Game current = App.currentGame;
        int nextX = current.getCurrentPlayer().getPosition().x + dx;
        int nextY = current.getCurrentPlayer().getPosition().y + dy;

        if(dx==0 && dy==0) {return new Result(false,"this is not a valid direction!");}

        if(nextX>Map.COLS||nextY>Map.ROWS||nextX<0||nextY<0) {
            return new Result(false,"you are going out of bounds!");}

        Tile goalTile = App.currentGame.getMap().getTile(new Position(nextX,nextY));

        if(!goalTile.getObjectInTile().getClass().equals(PloughedTile.class))
            return new Result(false,"you should plough the tile first!");

        PloughedTile tobeSeeded = (PloughedTile) goalTile.getObjectInTile();
        if(tobeSeeded.hasTreeOrCrop())
            return new Result(false,"there is already a seed in this tile!");

        if(CropSeeds.getByName(seedName) != null){
            tobeSeeded.seed(CropSeeds.getByName(seedName));
            // TODO: giant plants
            return new Result(true,"congratulations! you seeded this tile!");
        }

        if(SeedType.getByName(seedName) != null){
            tobeSeeded.seed(SeedType.getByName(seedName));
            // TODO: giant plants
            return new Result(true,"congratulations! you seeded this tile!");

        }

        return new Result(false,"no seed found with this name");
    }

    public static Result showPlant(int x, int y) {
        Position position = new Position(x, y);
        if(position.outOfBounds()) {
            return new Result(false,"this position is out of bounds!");
        }
        Tile tile = App.currentGame.getMap().getTile(position);
        if(!tile.getObjectInTile().getClass().equals(PloughedTile.class))
            return new Result(false,"this is not a ploughed tile!");
        PloughedTile toBeShown = (PloughedTile) tile.getObjectInTile();

        if(!toBeShown.hasTreeOrCrop())
            return new Result(false,"there is not any plant here!");
        return new Result(true,toBeShown.getHarvestable().printInfo());
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
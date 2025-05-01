package controllers;

import com.sun.jdi.AbsentInformationException;
import models.App;
import models.Game;
import models.Player;
import models.Result;
import models.cooking.Food;
import models.crafting.CraftItem;
import models.farming.CropSeeds;
import models.farming.Crops;
import models.farming.GeneralPlants.PloughedTile;
import models.farming.SeedType;
import models.map.*;
import models.tools.BackPackable;
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

    public static Result putInFridge(String itemName) {
        BackPackable item = getCurrentPlayer().getInventory().getItemByName(itemName);
        if(item == null) {
            return new Result(false, "You don't have that item in your inventory.");
        }

        int itemCount = getCurrentPlayer().getInventory().getItemCount(itemName);
        getCurrentPlayer().fridge().addToFridge(item, itemCount);
        getCurrentPlayer().getInventory().removeFromBackPack(item);

        return new Result(true, item.getName() + " moved to fridge.");
    }
    public static Result pickFromFridge(String itemName) {
        BackPackable item = getCurrentPlayer().fridge().getItemByName(itemName);
        if(item == null) {
            return new Result(false, "You don't have that item in your fridge.");
        }

        int itemCount = getCurrentPlayer().fridge().getItemCount(itemName);
        getCurrentPlayer().getInventory().addToBackPack(item, itemCount);
        getCurrentPlayer().fridge().removeFromFridge(item);

        return new Result(true, item.getName() + " moved to inventory.");
    }
    public static Result showCookingRecipes() {
        return new Result(true, "Available cooking recipes: \n" + getCurrentPlayer().showAvailableFoods());
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

        if(CropSeeds.getByName(seedName) != null){
            return tobeSeeded.seed(CropSeeds.getByName(seedName));
            // TODO: giant plants
        }

        if(SeedType.getByName(seedName) != null){
            return tobeSeeded.seed(SeedType.getByName(seedName));
            // TODO: giant plants
        }

        return new Result(false,"no seed found with this name");
    }

    public boolean canJoinGiant(Tile tile){
        Position pos = tile.getPosition();
        Tile[] adjacents = new Tile[8];
        //for(int i=0;i<8;)
        return false;
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
    public static Result ShowRecipe() {
        Player player = getCurrentPlayer();
        for (CraftItem availableCraft : player.getAvailableCrafts()) {
            System.out.println(availableCraft.getCraftItemType().recipe);
        }
        return null;
    }
    public static Result crafting(String craftingName) {
        Player player = getCurrentPlayer();
        CraftItem crafting = null;
        for (CraftItem availableCraft : player.getAvailableCrafts()) {
            if(availableCraft.getName().equals(craftingName)){
                crafting = availableCraft;
                for (BackPackable backPackable : availableCraft.getCraftItemType().ingredients.keySet()) {
                    int num = availableCraft.getCraftItemType().ingredients.get(backPackable);
                    for (BackPackable packable : player.getInventory().getItems().keySet()) {
                        int number = player.getInventory().getItems().get(packable);
                        if(packable.getName().equals(backPackable.getName())){
                            if (number < num) {
                                return new Result(false , "you dont have enough ingredients!");
                            }
                        }
                    }
                }
                break;
            }
        }
        if(crafting == null)
            return new Result(false,"craft item not available");
        for (BackPackable backPackable : crafting.getCraftItemType().ingredients.keySet()) {
            int num = player.getInventory().getItems().get(backPackable);
            for (BackPackable temp : player.getInventory().getItems().keySet()) {
                int number = player.getInventory().getItems().get(temp);
                if(backPackable.getName().equals(temp.getName())){
                    player.getInventory().getItems().put(temp, number-num);
                }
            }
        }
        player.getInventory().getItems().put(crafting , 1);
        return new Result(true,"craft make successfully");
    }
}
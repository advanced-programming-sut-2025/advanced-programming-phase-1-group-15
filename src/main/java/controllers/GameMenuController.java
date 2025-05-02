package controllers;

import com.sun.jdi.AbsentInformationException;
import models.App;
import models.Game;
import models.Player;
import models.Result;
import models.animals.Animal;
import models.animals.AnimalType;
import models.animals.Barn;
import models.animals.Coop;
import models.artisanry.ArtisanItem;
import models.artisanry.ArtisanItemType;
import models.cooking.Food;
import models.crafting.CraftItem;
import models.farming.CropSeeds;
import models.farming.Crops;
import models.farming.GeneralPlants.PloughedTile;
import models.farming.SeedType;
import models.map.*;
import models.tools.BackPackable;
import models.tools.Fridge;
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

    public static Result buildBarn(int x, int y) {
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
            getCurrentPlayer().getAnimals().add(animal);
            getCurrentPlayer().subtractGold(animal.getPrice());

            return new Result(true, "a new " + animal.getAnimalTypeName() + " named " + animal.getName() + " has been bought.");
        }
        else {
            return new Result(false, "not enough " + animal.getMaintenance() + " space to buy this animal.");
        }
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
        Player player = App.currentGame.getCurrentPlayer();
        boolean find = false;
        for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
            if (backPackable.getName().equals(craftingName)) {
                find = true;
                break;
            }
        }
        if (!find && player.getInventory().getItems().size()==player.getInventory().getCapacity()){
            return new Result(false , "your inventory is full!");
        }
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
        player.getInventory().getItems().put(crafting , player.getInventory().getItems().getOrDefault(crafting, 0) + 1);
        player.setEnergy(player.getEnergy()-2);
        return new Result(true,"craft make successfully");
    }
    public static Result PlaceItem(String itemName, String direction) {
        itemName = itemName.trim().toLowerCase();
        Player player = App.currentGame.getCurrentPlayer();
        BackPackable item = null;
        for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
            if (backPackable.getName().equals(itemName)) {
                item = backPackable;
            }
        }
        if(item == null){
            return new Result(false , "you dont have this item");
        }
        Tile tile = null;
        switch (direction) {
            case "up":
                tile = new Tile(player.getPosition().x , player.getPosition().y+1);
                break;
            case "down":
                tile = new Tile(player.getPosition().x , player.getPosition().y-1);
                break;
            case "left":
                tile = new Tile(player.getPosition().x-1 , player.getPosition().y);
                break;
            case "right":
                tile = new Tile(player.getPosition().x+1 , player.getPosition().y);
                break;
            default:
                return new Result(false , "unknown direction");
        }
        player.getInventory().getItems().remove(item);
        return new Result(true , "Item placed successfully");
    }
    public static Result UseArtisan(String artisanName , String itemName) {
        artisanName = artisanName.trim().toLowerCase();
        itemName = itemName.trim().toLowerCase();
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
        switch (artisanName) {
            case "":
                artisanItem = new ArtisanItem(ArtisanItemType.HONEY);
                break;
            case "milk":
                artisanItem = new ArtisanItem(ArtisanItemType.CHEESE_MILK);
                break;
            case "large milk":
                artisanItem = new ArtisanItem(ArtisanItemType.CHEESE_LARGE_MILK);
                break;
            case "goat milk":
                artisanItem = new ArtisanItem(ArtisanItemType.GOAT_CHEESE_MILK);
                break;
            case "large goat milk":
                artisanItem = new ArtisanItem(ArtisanItemType.GOAT_CHEESE_LARGE_MILK);
                break;
            case "wheat" :
                artisanItem = new ArtisanItem(ArtisanItemType.BEER);
                break;
            case "rice" :
                artisanItem = new ArtisanItem(ArtisanItemType.VINEGAR);
                break;
            case "coffee been" :
                artisanItem = new ArtisanItem(ArtisanItemType.COFFEE);
                break;
            case "juice" :
                // must change
                artisanItem = new ArtisanItem(ArtisanItemType.JUICE);
                break;
            case "honey" :
                artisanItem = new ArtisanItem(ArtisanItemType.MEAD);
                break;
            case "hops" :
                artisanItem = new ArtisanItem(ArtisanItemType.PALE_ALE);
                break;
            case "wine" :
                // must change
                artisanItem = new ArtisanItem(ArtisanItemType.WINE);
                break;
            case "common mushroom" :
                artisanItem = new ArtisanItem(ArtisanItemType.DRIED_COMMON_MUSHROOM);
                break;
            case "red mushroom" :
                artisanItem = new ArtisanItem(ArtisanItemType.DRIED_RED_MUSHROOM);
                break;
            case "purple mushroom" :
                artisanItem = new ArtisanItem(ArtisanItemType.DRIED_PURPLE_MUSHROOM);
                break;
            case "dried fruit" :
                // must change
                artisanItem = new ArtisanItem(ArtisanItemType.DRIED_FRUIT);
                break;
            case "grapes" :
                artisanItem = new ArtisanItem(ArtisanItemType.RAISINS);
                break;
            case "coal":
                artisanItem = new ArtisanItem(ArtisanItemType.COAL);
                break;
            case "rabbit wool":
                artisanItem = new ArtisanItem(ArtisanItemType.CLOTH_RABBIT);
                break;
            case "sheep wool":
                artisanItem = new ArtisanItem(ArtisanItemType.CLOTH_SHEEP);
                break;
            case "egg" :
                artisanItem = new ArtisanItem(ArtisanItemType.MAYONNAISE_EGG);
                break;
            case "large egg" :
                artisanItem = new ArtisanItem(ArtisanItemType.MAYONNAISE_LARGE_EGG);
                break;
            case "dinosaur egg" :
                artisanItem = new ArtisanItem(ArtisanItemType.DINOSAUR_MAYONNAISE);
                break;
            case "duck egg" :
                artisanItem = new ArtisanItem(ArtisanItemType.DUCK_MAYONNAISE);
                break;
            case "sunflower" :
                artisanItem = new ArtisanItem(ArtisanItemType.OIL_SUNFLOWER);
                break;
            case "sunflower seed" :
                artisanItem = new ArtisanItem(ArtisanItemType.OIL_SUNFLOWER_SEED);
                break;
            case "corn" :
                artisanItem = new ArtisanItem(ArtisanItemType.OIL_CORN);
                break;
            case "truffle"   :
                artisanItem = new ArtisanItem(ArtisanItemType.TRUFFLE_OIL);
                break;
            case "pickles":
                artisanItem = new ArtisanItem(ArtisanItemType.PICKLES);
                break;
            case "jelly" :
                artisanItem = new ArtisanItem(ArtisanItemType.JELLY);
                break;
            case "fish smoker":
                artisanItem = new ArtisanItem(ArtisanItemType.SMOKED_FISH);
                break;
            case "furnace":
                artisanItem = new ArtisanItem(ArtisanItemType.METAL_BAR);
                break;
            default:
                return new Result(false , "Artisan item not available");
        }
        return new Result(true , "Artisan item made successfully");
    }
}
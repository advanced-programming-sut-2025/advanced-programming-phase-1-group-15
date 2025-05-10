package controllers;

import models.App;
import models.Game;
import models.Player;
import models.Result;
import models.animals.*;
import models.artisanry.ArtisanItem;
import models.artisanry.ArtisanItemType;
import models.cooking.Food;
import models.crafting.CraftItem;
import models.farming.*;
import models.farming.GeneralPlants.PloughedPlace;
import models.map.*;
import models.stores.CarpenterShop;
import models.stores.MarnieRanch;
import models.stores.Store;
import models.tools.*;

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
            if(!getCurrentPlayer().equals(farm.getOwner())) {
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

        animal.feed();
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
            return new Result(false, "You need to be in a store to run this command.");
        }

        Store store = (Store) playerTile.getArea();
        if(!store.isOpen(App.currentGame.getDateAndTime().getHour())) {
            return new Result(false, "store is closed now!");
        }

        return new Result(true, "All Items: \n" + store.displayItems());
    }
    public static Result showAvailableStoreProducts() {
        Tile playerTile = App.currentGame.getTile(getCurrentPlayer().getPosition());

        if(!playerTile.getAreaType().equals(AreaType.STORE)) {
            return new Result(false, "You need to be in a store to run this command.");
        }

        Store store = (Store) playerTile.getArea();
        if(!store.isOpen(App.currentGame.getDateAndTime().getHour())) {
            return new Result(false, "store is closed now!");
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

    public static Result meetNPC(String npcName) {
        // TODO : first Default NPC and then this method
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
                        int number = player.getInventory().getItemCount(packable.getName());
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
    public static Result PlaceItem(String itemName, String x , String y) {
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
        switch (x) {
            case "1":
                switch (y){
                    case "1":
                        tile = new Tile(player.getPosition().x+1 , player.getPosition().y+1);
                        break;
                    case "-1":
                        tile = new Tile(player.getPosition().x+1 , player.getPosition().y-1);
                        break;
                    case "0":
                        tile = new Tile(player.getPosition().x+1 , player.getPosition().y);
                        break;
                    default:
                        return new Result(false , "unknown direction");
                }
                break;
            case "-1":
                switch (y){
                    case "1":
                        tile = new Tile(player.getPosition().x-1 , player.getPosition().y+1);
                        break;
                    case "-1":
                        tile = new Tile(player.getPosition().x-1 , player.getPosition().y-1);
                        break;
                    case "0":
                        tile = new Tile(player.getPosition().x-1 , player.getPosition().y);
                        break;
                    default:
                        return new Result(false , "unknown direction");
                }
                break;
            case "0":
                switch (y){
                    case "1":
                        tile = new Tile(player.getPosition().x , player.getPosition().y+1);
                        break;
                    case "-1":
                        tile = new Tile(player.getPosition().x , player.getPosition().y-1);
                        break;
                    case "0":
                        tile = new Tile(player.getPosition().x , player.getPosition().y);
                        break;
                    default:
                        return new Result(false , "unknown direction");
                }
                break;
            default:
                return new Result(false , "unknown direction");
        }
        player.getInventory().getItems().remove(item);
        tile.setObjectInTile(item);
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
        if (artisanItem.getName().equals("honey")){
            Game game = App.currentGame;
            artisanItem.setHour(game.getDateAndTime().getHour());
            artisanItem.setDay(game.getDateAndTime().getDay());
            player.getArtisanItems().add(artisanItem);
            return new Result(true , "Artisan item made successfully");
        }
        for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
            if (backPackable.getName().equals(artisanItem.getName())) {
                if(artisanItem.getArtisanItemType().ingredients.getName().equals(backPackable.getName())) {
                    if (player.getInventory().getItemCount(backPackable.getName())<artisanItem.getArtisanItemType().number) {
                        return new Result(false , "You cant made this artisan item");
                    }
                    Game game = App.currentGame;
                    artisanItem.setHour(game.getDateAndTime().getHour());
                    artisanItem.setDay(game.getDateAndTime().getDay());
                    break;
                }
            }
        }
        player.getArtisanItems().add(artisanItem);
        return new Result(true , "Artisan item made successfully");
    }
    public static Result GetArtisan(String artisanName) {
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
                player.getInventory().getItems().put(temp , 1);
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
        player.getInventory().getItems().put(temp , 1);
        player.getArtisanItems().remove(temp);
        return new Result(true , "You receive Artisan item successfully");
    }
}
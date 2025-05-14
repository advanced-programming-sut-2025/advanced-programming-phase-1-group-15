package controllers;

import models.App;
import models.Player;
import models.Result;
import models.animals.*;
import models.artisanry.ArtisanItem;
import models.artisanry.ArtisanItemType;
import models.crafting.CraftItem;
import models.crafting.CraftItemType;
import models.farming.Crop;
import models.farming.Crops;
import models.farming.Fruit;
import models.farming.FruitType;
import models.farming.GeneralPlants.PloughedPlace;
import models.foraging.*;
import models.map.Position;
import models.map.Tile;
import models.relation.PlayerFriendship;
import models.stores.GeneralItem;
import models.stores.GeneralItemsType;
import models.tools.BackPackable;
import models.weather.WeatherOption;

public class CheatCodeController {
    public static Result cheatAdvanceTime(int hours) {
        if(hours < 0) {
            return new Result(false, "you can't travel back in time!");
        }

        App.currentGame.getDateAndTime().nextNHours(hours);
        return new Result(true, App.currentGame.getDateAndTime().displayDateTime());
    }
    public static Result cheatAdvanceDate(int days) {
        if(days < 0) {
            return new Result(false, "you can't travel back in time!");
        }

        App.currentGame.getDateAndTime().nextNDays(days);
        return new Result(true, App.currentGame.getDateAndTime().displayDateTime());
    }

    public static Result cheatSetWeather(String weatherType) {
        WeatherOption weather = WeatherOption.getWeather(weatherType.toLowerCase());

        if(weather == null) {
            return new Result(false, "invalid weather type");
        }

        App.currentGame.getWeather().setForecast(weather);
        return new Result(true, "Tomorrow's forecast changed to " + App.currentGame.getWeather().displayForecast());
    }

    public static Result cheatSetEnergy(int value) {
        if(value < 0 || value > 200) {
            return new Result(false, "choose between [0,200]");
        }

        App.currentGame.getCurrentPlayer().setEnergy(value);
        return new Result(true, "energy: " + App.currentGame.getCurrentPlayer().getEnergy());
    }
    public static Result cheatUnlimitedEnergy() {
        App.currentGame.getCurrentPlayer().unlimitedEnergy();
        return new Result(true, "energy: " + App.currentGame.getCurrentPlayer().getEnergy());
    }

    public static Result cheatAnimalFriendship(String name, int amount) {
        Animal animal = App.currentGame.getCurrentPlayer().getAnimalByName(name);
        if(animal == null) {
            return new Result(false, "animal name is not correct.");
        }

        if(amount < 0) {
            return new Result(false, "amount cannot be negative.");
        }

        animal.setFriendship(amount);
        return new Result(true, "friendship with " + animal.getName() + " set to " + amount);
    }

    public static Result cheatAddGold(int amount) {
        App.currentGame.getCurrentPlayer().addGold(amount);

        return new Result(true, "gold: " + App.currentGame.getCurrentPlayer().getGold());
    }
    public static Result cheatAddWood(int amount) {
        App.currentGame.getCurrentPlayer().getInventory().addToBackPack(new GeneralItem(GeneralItemsType.WOOD), amount);

        return new Result(true, "wood: " + App.currentGame.getCurrentPlayer().getWood());
    }
    public static Result cheatAddStone(int amount) {
        App.currentGame.getCurrentPlayer().getInventory().addToBackPack(new Stone(), amount);

        return new Result(true, "stone: " + App.currentGame.getCurrentPlayer().getStone());
    }

    public static Result cheatThor(Position position) {
        Tile tile = App.currentGame.getTile(position.x, position.y);
        if(tile.getObjectInTile() instanceof PloughedPlace){
            PloughedPlace p = (PloughedPlace)tile.getObjectInTile();
            p.thor();
            return new Result(true,"thor done on this position(;");
        }
        return new Result(false,"here is not a ploughed place");
    }

    public static Result cheatAddItem(Position position) {
        return null;
    }

    public static Result cheatSetFriendship(String username, int level) {
        Player target = App.currentGame.getPlayerByUsername(username);
        if(target == null) {
            return new Result(false, "invalid player username!");
        }

        PlayerFriendship friendship = App.currentGame.getFriendshipByPlayers(App.currentGame.getCurrentPlayer(), target);
        friendship.setLevel(level);

        return new Result(true, "friendship level set to " + level);
    }

    public static Result cheatAddProduct(String productName, int amount) {
        return null;
    }

    public static Result cheatAddItem(String itemName , String count) {
        itemName = itemName.trim().toLowerCase().replaceAll("_", " ");
        Player player = App.currentGame.getCurrentPlayer();
        boolean find = false;
        for (BackPackable backPackable : player.getInventory().getItems().keySet()) {
            if (backPackable.getName().equals(itemName)) {
                find = true;
                break;
            }
        }
        if (!find && player.getInventory().getItems().size()==player.getInventory().getCapacity()){
            return new Result(false , "your inventory is full!");
        }
        int num = Integer.parseInt(count);
        CraftItem item = null;
        ArtisanItem artisanItem = null;
        switch (itemName){
            case "cherry bomb":
                item = new CraftItem(CraftItemType.CHERRY_BOMB);
                break;
            case "charcoal klin":
                item = new CraftItem(CraftItemType.CHARCOAL_KLIN);
                break;
            case "bomb":
                item = new CraftItem(CraftItemType.BOMB);
                break;
            case "mega bomb":
                item = new CraftItem(CraftItemType.MEGA_BOMB);
                break;
            case "sprinkler":
                item = new CraftItem(CraftItemType.SPRINKLER);
                break;
            case "quality sprinkler":
                item = new CraftItem(CraftItemType.QUALITY_SPRINKLER);
                break;
            case "iridium sprinkler":
                item = new CraftItem(CraftItemType.IRIDIUM_SPRINKLER);
                break;
            case "furnace":
                item = new CraftItem(CraftItemType.FURNACE);
                break;
            case "scarecrow":
                item = new CraftItem(CraftItemType.SCARECROW);
                break;
            case "deluxe sprinkler":
                item = new CraftItem(CraftItemType.DELUXE_SCARECROW);
                break;
            case "bee house":
                item = new CraftItem(CraftItemType.BEE_HOUSE);
                break;
            case "cheese press":
                item = new CraftItem(CraftItemType.CHEESE_PRESS);
                break;
            case "keg":
                item = new CraftItem(CraftItemType.KEG);
                break;
            case "loom":
                item = new CraftItem(CraftItemType.LOOM);
                break;
            case "mayonnaise machine":
                item = new CraftItem(CraftItemType.MAYONNAISE_MACHINE);
                break;
            case "oil maker":
                item = new CraftItem(CraftItemType.OIL_MAKER);
                break;
            case "fish smoker":
                item = new CraftItem(CraftItemType.FISH_SMOKER);
                break;
            case "preserves jar":
                item = new CraftItem(CraftItemType.PRESERVES_JAR);
                break;
            case "dehydrator":
                item = new CraftItem(CraftItemType.DEHYDRATOR);
                break;
            case "mystic tree seed":
                item = new CraftItem(CraftItemType.MYSTIC_TREE_SEED);
                break;
            case "honey":
                artisanItem = new ArtisanItem(ArtisanItemType.HONEY);
                break;
            case "cheese":
                artisanItem = new ArtisanItem(ArtisanItemType.CHEESE_MILK);
                break;
            case "large cheese":
                artisanItem = new ArtisanItem(ArtisanItemType.CHEESE_LARGE_MILK);
                break;
            case "goat cheese":
                artisanItem = new ArtisanItem(ArtisanItemType.GOAT_CHEESE_MILK);
                break;
            case "large goat cheese":
                artisanItem = new ArtisanItem(ArtisanItemType.GOAT_CHEESE_LARGE_MILK);
                break;
            case "beer" :
                artisanItem = new ArtisanItem(ArtisanItemType.BEER);
                break;
            case "vinegar" :
                artisanItem = new ArtisanItem(ArtisanItemType.VINEGAR);
                break;
            case "coffee" :
                artisanItem = new ArtisanItem(ArtisanItemType.COFFEE);
                break;
            case "juice" :
                // must change
                artisanItem = new ArtisanItem(ArtisanItemType.JUICE);
                break;
            case "mead" :
                artisanItem = new ArtisanItem(ArtisanItemType.MEAD);
                break;
            case "pale ale" :
                artisanItem = new ArtisanItem(ArtisanItemType.PALE_ALE);
                break;
            case "wine" :
                // must change
                artisanItem = new ArtisanItem(ArtisanItemType.WINE);
                break;
            case "dired common mushroom" :
                artisanItem = new ArtisanItem(ArtisanItemType.DRIED_COMMON_MUSHROOM);
                break;
            case "dired red mushroom" :
                artisanItem = new ArtisanItem(ArtisanItemType.DRIED_RED_MUSHROOM);
                break;
            case "dired purple mushroom" :
                artisanItem = new ArtisanItem(ArtisanItemType.DRIED_PURPLE_MUSHROOM);
                break;
            case "dried fruit" :
                // must change
                artisanItem = new ArtisanItem(ArtisanItemType.DRIED_FRUIT);
                break;
            case "raisins" :
                artisanItem = new ArtisanItem(ArtisanItemType.RAISINS);
                break;
            case "coal":
                artisanItem = new ArtisanItem(ArtisanItemType.COAL);
                break;
            case "rabbit cloth":
                artisanItem = new ArtisanItem(ArtisanItemType.CLOTH_RABBIT);
                break;
            case "sheep cloth":
                artisanItem = new ArtisanItem(ArtisanItemType.CLOTH_SHEEP);
                break;
            case "mayonnaise egg" :
                artisanItem = new ArtisanItem(ArtisanItemType.MAYONNAISE_EGG);
                break;
            case "mayonnaise large egg" :
                artisanItem = new ArtisanItem(ArtisanItemType.MAYONNAISE_LARGE_EGG);
                break;
            case "mayonnaise dinosaur egg" :
                artisanItem = new ArtisanItem(ArtisanItemType.DINOSAUR_MAYONNAISE);
                break;
            case "mayonnaise duck egg" :
                artisanItem = new ArtisanItem(ArtisanItemType.DUCK_MAYONNAISE);
                break;
            case "sunflower oil" :
                artisanItem = new ArtisanItem(ArtisanItemType.OIL_SUNFLOWER);
                break;
            case "sunflower seed oil" :
                artisanItem = new ArtisanItem(ArtisanItemType.OIL_SUNFLOWER_SEED);
                break;
            case "corn oil" :
                artisanItem = new ArtisanItem(ArtisanItemType.OIL_CORN);
                break;
            case "truffle oil"   :
                artisanItem = new ArtisanItem(ArtisanItemType.TRUFFLE_OIL);
                break;
            case "pickles":
                artisanItem = new ArtisanItem(ArtisanItemType.PICKLES);
                break;
            case "jelly" :
                artisanItem = new ArtisanItem(ArtisanItemType.JELLY);
                break;
            case "smoked fish":
                artisanItem = new ArtisanItem(ArtisanItemType.SMOKED_FISH);
                break;
            case "metal bar":
                artisanItem = new ArtisanItem(ArtisanItemType.METAL_BAR);
                break;
            default:
                return new Result(false , "item not available");
        }
        if(item!=null)
            player.addToBackPack(item, num);
        if(artisanItem!=null)
            player.addToBackPack(artisanItem, num);
        return new Result(true , "item added successfully");
    }
    public static Result AddItem(String name , int num) {
        name = name.trim().toLowerCase().replaceAll("_", " ");
        Player player = App.currentGame.getCurrentPlayer();
        for(FruitType fruitType : FruitType.values()) {
            if (fruitType.getName().equals(name)) {
                Fruit fruit = new Fruit(fruitType);
                player.addToBackPack(fruit, num);
                return new Result(true , "item add successfully");
            }
        }
        for(Crops crop : Crops.values()) {
            if (crop.getName().toLowerCase().replaceAll("_"," ").equals(name)) {
                Crop crop1 = new Crop(crop);
                player.addToBackPack(crop1, num);
                return new Result(true , "item add successfully");
            }
        }
        for(ForagingCropsType foragingCropsType : ForagingCropsType.values()) {
            if (foragingCropsType.getName().equals(name)){
                ForagingCrop temp = new ForagingCrop(foragingCropsType);
                player.addToBackPack(temp, num);
                return new Result(true , "item add successfully");
            }
        }
        for(ForagingMineralType foragingMineralType : ForagingMineralType.values()) {
            if (foragingMineralType.getName().equals(name)){
                ForagingMineral temp = new ForagingMineral(foragingMineralType);
                player.addToBackPack(temp, num);
                return new Result(true , "item add successfully");
            }
        }
        for(AnimalProductType animalProductType : AnimalProductType.values()) {
            if (animalProductType.getName().equals(name)){
                AnimalProduct animalProduct = new AnimalProduct(animalProductType);
                player.addToBackPack(animalProduct, num);
                return new Result(true , "item add successfully");
            }
        }
        for (FishType fishType : FishType.values()) {
            if (fishType.getName().equals(name)){
                Fish fish = new Fish(fishType);
                player.addToBackPack(fish, num);
                return new Result(true , "item add successfully");
            }
        }
        return new Result(false , "item not found");
    }
}

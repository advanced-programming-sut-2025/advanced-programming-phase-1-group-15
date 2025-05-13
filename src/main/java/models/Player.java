package models;

import models.animals.Animal;
import models.artisanry.ArtisanItem;
import models.cooking.Food;
import models.cooking.FoodType;
import models.crafting.CraftItem;
import models.foraging.ForagingSeedsType;
import models.map.AreaType;
import models.map.Farm;
import models.map.Tile;
import models.map.Position;
import models.relation.PlayerFriendship;
import models.relation.TradeWhitMoney;
import models.relation.TradeWithItem;
import models.time.DateAndTime;
import models.time.TimeObserver;
import models.tools.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends User implements TimeObserver {
    private Position homePosition;
    private Position position;
    private Farm farm;
    private int CurrentId;
    private int mapNumber;

    private int gold = 500;
    private int energy = 200;
    private int energyConsumed = 0;
    private boolean unlimitedEnergy = false;
    private boolean fainted = false;

    private BackPack inventory = new BackPack();
    private TrashCan trashCan = new TrashCan();
    private Tool currentTool = null;

    private int farmingAbility = 0;
    private int farmingLevel = 0;
    private int miningAbility = 0;
    private int miningLevel = 0;
    private int foragingAbility = 0;
    private int foragingLevel = 0;
    private int fishingAbility = 0;
    private int fishingLevel = 0;

    private DateAndTime lastUpdate = new DateAndTime();

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    private Game game;

    private ArrayList<CraftItem> availableCrafts = new ArrayList<>();

    private ArrayList<Food> availableFoods = new ArrayList<>(Arrays.asList(new Food(FoodType.FRIED_EGG),
            new Food(FoodType.BACKED_FISH), new Food(FoodType.SALAD)));

    private ArrayList<TradeWhitMoney> tradesWhitMoney = new ArrayList<>();
    private ArrayList<TradeWithItem> tradesWithItem = new ArrayList<>();
    private ArrayList<TradeWithItem> tradesWithItemHistory = new ArrayList<>();
    private ArrayList<TradeWhitMoney> tradesWithMoneyHistory = new ArrayList<>();

    private ArrayList<Animal> animals = new ArrayList<>();

    private ArrayList<ArtisanItem> artisanItems = new ArrayList<>();

    private ArrayList<PlayerFriendship.Message> receivedMessages = new ArrayList<>();
    public void addMessage(PlayerFriendship.Message message) {
        receivedMessages.add(message);
    }
    private Player couple;

    public int getCurrentId() {
        return CurrentId;
    }

    public void setCurrentId(int currentId) {
        CurrentId = currentId;
    }

    public Player(User user) {
        super(user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getGender());
    }

    public ArrayList<ArtisanItem> getArtisanItems() {
        return artisanItems;
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public void setHome(Position homePosition) {
        this.homePosition = homePosition;
        this.position = this.homePosition;
    }
    public void goHome() {
        this.position = homePosition;
    }

    public int calculateWalkingEnergy(Position nextPosition) {
        int tilesNeeded = game.getMap().findShortestPath(this, position, nextPosition);
        if(tilesNeeded == -1) return -1;
        return game.getMap().calculatePath(position,nextPosition) / 5 + 1;
    }
    public void walk(Position position) {
        int energyNeeded = calculateWalkingEnergy(position) - 10;

        if(energyNeeded > energy) {
            faint();
        }
        else {
            subtractEnergy(energyNeeded);
            setPosition(position);
        }
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public boolean checkTerritory(Farm farm) {
        if(this.farm.equals(farm)) return true;
        if(couple != null) {
            return couple.getFarm().equals(farm);
        }
        return false;
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(int mapNumber) {
        this.mapNumber = mapNumber;
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int gold) {
        this.gold += gold;
        if(couple != null) {
            couple.addGold(gold);
        }
    }

    public void subtractGold(int gold) {
        this.gold -= gold;
        if(couple != null) {
            couple.subtractGold(gold);
        }
    }

    public int getEnergy() {
        return energy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    public void subtractEnergy(int amount) {
        if(!unlimitedEnergy) {
            energyConsumed += amount;
            energy -= amount;
        }
    }
    public void addEnergy(int amount) {
        energy = Math.min(200, energy + amount);
    }

    public boolean isLocked() {
        return !unlimitedEnergy && energyConsumed >= 50;
    }
    public void unlock() {
        if(isLocked()) {
            energyConsumed = 0;
        }
    }

    public void unlimitedEnergy() {
        unlimitedEnergy = true;
        energy = Integer.MAX_VALUE;
    }

    public boolean isFainted() {
        return fainted;
    }
    public void faint() {
        energy = 0;
        fainted = true;
    }

    public BackPack getInventory() {
        return inventory;
    }
    public void addToBackPack(BackPackable bp, int count) {
        if(!inventory.checkFilled()) {
            inventory.addToBackPack(bp, count);
        }
    }

    public TrashCan getTrashCan() {
        return trashCan;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }
    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public int getFarmingAbility() {
        return farmingAbility;
    }
    public int getFarmingLevel() {
        return farmingLevel;
    }
    public void upgradeFarmingAbility(int amount) {
        this.farmingAbility += amount;
        if(farmingAbility > 100 * farmingLevel + 50 && farmingLevel != 4) {
            farmingLevel++;
        }
    }

    public int getMiningAbility() {
        return miningAbility;
    }
    public int getMiningLevel() {
        return miningLevel;
    }
    public void upgradeMiningAbility(int amount) {
        this.miningAbility += amount;
        if(miningAbility > 100 * miningLevel + 50 && miningLevel != 4) {
            miningLevel++;
        }
    }

    public int getForagingAbility() {
        return foragingAbility;
    }
    public int getForagingLevel() {
        return foragingLevel;
    }
    public void upgradeForagingAbility(int amount) {
        this.foragingAbility += amount;
        if(foragingAbility > 100 * foragingLevel + 50 && foragingLevel != 4) {
            foragingLevel++;
        }
    }

    public int getFishingAbility() {
        return fishingAbility;
    }
    public int getFishingLevel() {
        return fishingLevel;
    }
    public void upgradeFishingAbility(int amount) {
        this.fishingAbility += amount;
        if(fishingAbility > 100 * fishingLevel + 50 && fishingLevel != 4) {
            fishingLevel++;
        }
    }

    public ArrayList<CraftItem> getAvailableCrafts() {
        return availableCrafts;
    }

    public ArrayList<Food> getAvailableFoods() {
        return availableFoods;
    }

    public Animal getAnimalByName(String name) {
        for(Animal animal : animals) {
            if(animal.getName().equals(name)) {
                return animal;
            }
        }
        return null;
    }
    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public ArrayList<TradeWhitMoney> getTradesWhitMoney() {
        return tradesWhitMoney;
    }

    public ArrayList<TradeWithItem> getTradesWithItem() {
        return tradesWithItem;
    }

    public ArrayList<TradeWithItem> getTradesWithItemHistory() {
        return tradesWithItemHistory;
    }

    public ArrayList<TradeWhitMoney> getTradesWithMoneyHistory() {
        return tradesWithMoneyHistory;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public ArrayList<PlayerFriendship.Message> getReceivedMessages() {
        return receivedMessages;
    }
    public Player getCouple() {
        return couple;
    }
    public void marry(Player couple) {
        this.couple = couple;
    }

    public void eat(Food food) {
        addEnergy(food.getEnergy());
    }

    public void showAvailableCrafts() {

    }
    public String showAvailableFoods() {
        StringBuilder display = new StringBuilder();
        for(Food food : availableFoods) {
            display.append(food.getName()).append("    ");
            display.append(food.getRecipe()).append("\n");
        }

        return display.toString();
    }
    public String showAnimals() {
        StringBuilder display = new StringBuilder();
        for(Animal animal: animals) {
            display.append(animal.getName()).append("    ");
            display.append(animal.getAnimalTypeName()).append("    ");
            display.append("friendship: ").append(animal.getFriendship()).append("  ");
            display.append("petted: ").append(animal.isPetted()).append("  ");
            display.append("fed: ").append(animal.isFed()).append("\n");
        }

        return display.toString();
    }

    public void attackOfCrows(){
        int remainder = RandomGenerator.getInstance().randomInt(0,15);
        for(int i=0;i<farm.getTiles().size();i++) {
            for (int j = 0; j < farm.getTiles().get(i).size(); j++) {
                Tile tile = farm.getTiles().get(i).get(j);
                if(tile.getAreaType() == AreaType.GREENHOUSE) continue;
                if(tile.isPlowed()){
                    // TODO: check if it is in a Green house
                    remainder++;    remainder %= 16;
                    if(remainder == 0){
                        if(RandomGenerator.getInstance().randomInt(0,3) == 1) {
                            tile.unplow();
                            // TODO: check the effect for tree when you implemented tree
                        }
                    }
                }
            }
        }
    }

    public void ForagingSeedsAndCrops(){
        for(int i=0;i<farm.getTiles().size();i++){
            for(int j=0;j<farm.getTiles().get(i).size();j++){
                Tile tile = farm.getTiles().get(i).get(j);
                if(tile.isPlowed()||tile.getAreaType() == AreaType.GREENHOUSE){
                    continue;
                }
                if(RandomGenerator.getInstance().randomInt(0,100)==1){
                    ForagingSeedsType.getSeasonForagingSeed(App.currentGame.getDateAndTime().getSeason());
                }
            }
        }
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == 9) {
            energyConsumed = 0;
            if(fainted) {
                fainted = false;
                energy = 150;
            }
            else {
                energy = 200;
                goHome();
            }
        }
        if(lastUpdate.getDay() != dateAndTime.getDay()) {
            attackOfCrows();
        }
    }
}
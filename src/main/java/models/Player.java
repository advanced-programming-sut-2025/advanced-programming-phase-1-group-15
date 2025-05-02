package models;

import models.animals.Animal;
import models.cooking.Food;
import models.cooking.FoodType;
import models.crafting.CraftItem;
import models.map.Farm;
import models.relation.PlayerFriendShip;
import models.map.Position;
import models.time.DateAndTime;
import models.time.TimeObserver;
import models.tools.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Player extends User implements TimeObserver {
    private Position homePosition;
    private Position position;
    private Farm farm;

    private int mapNumber;

    private int gold = 0;
    private int wood = 0;

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

    private ArrayList<Animal> animals = new ArrayList<>();

    private HashMap<Player,PlayerFriendShip> friendships = new HashMap<>();
    private Player couple;

    public Player(User user) {
        super(user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getGender());
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
        return tilesNeeded / 5 + 1;
    }
    public void walk(Position position) {
        int energyNeeded = calculateWalkingEnergy(position);

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
    }
    public void subtractGold(int gold) {
        this.gold -= gold;
    }

    public int getWood() {
        return wood;
    }
    public void setWood(int wood) {
        this.wood = wood;
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

    public ArrayList<Animal> getAnimals() {
        return animals;
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
        StringBuilder sb = new StringBuilder();
        for(Food food : availableFoods) {
            sb.append(food.getName()).append("    ");
            sb.append(food.getRecipe()).append("\n");
        }

        return sb.toString();
    }
    public void showAnimals() {

    }
    public void showFriendships() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == 9) {
            if(fainted) {
                fainted = false;
                energy = 150;
            }
            else {
                energy = 200;
                goHome();
            }
        }
    }
}
package models;

import models.animals.Animal;
import models.cooking.Food;
import models.cooking.FoodType;
import models.crafting.CraftItemType;
import models.map.Farm;
import models.relation.PlayerFriendShip;
import models.map.Position;
import models.time.DateAndTime;
import models.time.TimeObserver;
import models.tools.BackPack;
import models.tools.BackPackable;
import models.tools.Tool;

import java.util.ArrayList;
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

    private BackPack Inventory = new BackPack();
    private Tool CurrentTool = null;

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

    private ArrayList<CraftItemType> availableCrafts = new ArrayList<>();
    private ArrayList<FoodType> availableFoods = new ArrayList<>();

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

    public int calculateWalkingEnergy( Position nextPosition) {
        return game.getMap().findShortestPath(this,position, nextPosition);
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
    public void setGold(int gold) {
        this.gold = gold;
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
        return Inventory;
    }
    public void setInventory(BackPack inventory) {
        Inventory = inventory;
    }

    public Tool getCurrentTool() {
        return CurrentTool;
    }
    public void setCurrentTool(Tool currentTool) {
        CurrentTool = currentTool;
    }

    public int getFarmingAbility() {
        return farmingAbility;
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
    public void upgradeMiningAbility(int amount) {
        this.miningAbility += amount;
        if(miningAbility > 100 * miningLevel + 50 && miningLevel != 4) {
            miningLevel++;
        }
    }

    public int getForagingAbility() {
        return foragingAbility;
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
    public void upgradeFishingAbility(int amount) {
        this.fishingAbility += amount;
        if(fishingAbility > 100 * fishingLevel + 50 && fishingLevel != 4) {
            fishingLevel++;
        }
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }
    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public Player getCouple() {
        return couple;
    }
    public void marry(Player couple) {
        this.couple = couple;
    }

    public void eat(Food food) {

    }

    public void showCrafts() {

    }
    public void showRecipes() {

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
package models;

import models.animals.Animal;
import models.cooking.Food;
import models.cooking.FoodType;
import models.crafting.CraftItemType;
import models.map.Farm;
import models.relation.PlayerFriendShip;
import models.map.FarmType;
import models.map.Position;
import models.tools.BackPack;
import models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class Player extends User {
    private Position position;
    private Farm farm;

    private int mapNumber;

    private int gold;
    private int wood;

    private int energy;
    private int energyConsumed;
    private boolean fainted;

    private BackPack Inventory;
    private Tool CurrentTool;

    private int farmingAbility;
    private int miningAbility;
    private int foragingAbility;
    private int fishingAbility;

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

    public boolean isLocked() {
        return energyConsumed >= 50;
    }
    public void unlock() {
        energyConsumed = 0;
    }

    public boolean isFainted() {
        return fainted;
    }
    public void faint(boolean fainted) {
        this.fainted = fainted;
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
    public void setFarmingAbility(int farmingAbility) {
        this.farmingAbility = farmingAbility;
    }

    public int getMiningAbility() {
        return miningAbility;
    }
    public void setMiningAbility(int miningAbility) {
        this.miningAbility = miningAbility;
    }

    public int getForagingAbility() {
        return foragingAbility;
    }
    public void setForagingAbility(int foragingAbility) {
        this.foragingAbility = foragingAbility;
    }

    public int getFishingAbility() {
        return fishingAbility;
    }
    public void setFishingAbility(int fishingAbility) {
        this.fishingAbility = fishingAbility;
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

    public void walk(Position position) {

    }
    public void eat(Food food) {

    }

    public void showInventory() {

    }
    public void showTools() {

    }
    public void showCrafts() {

    }
    public void showRecipes() {

    }
    public void showAnimals() {

    }
    public void showFriendships() {

    }

}
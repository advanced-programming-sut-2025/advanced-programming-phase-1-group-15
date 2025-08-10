package com.example.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.example.client.controllers.ClientGameController;
import com.example.client.models.ClientApp;
import com.example.common.animals.Animal;
import com.example.common.artisanry.ArtisanItem;
import com.example.common.cooking.Food;
import com.example.common.cooking.FoodType;
import com.example.common.crafting.CraftItem;
import com.example.common.crafting.CraftItemType;
import com.example.common.enums.Direction;
import com.example.common.enums.Gender;
import com.example.common.farming.GeneralPlants.PloughedPlace;
import com.example.common.farming.Tree;
import com.example.common.foraging.ForagingSeedsType;
import com.example.common.map.AreaType;
import com.example.common.map.Farm;
import com.example.common.map.Map;
import com.example.common.map.Tile;
import com.example.common.map.Position;
import com.example.common.relation.PlayerFriendship;
import com.example.common.relation.TradeWhitMoney;
import com.example.common.relation.TradeWithItem;
import com.example.common.time.DateAndTime;
import com.example.common.time.TimeObserver;
import com.example.common.tools.BackPack;
import com.example.common.tools.BackPackable;
import com.example.common.tools.Fridge;
import com.example.common.tools.TrashCan;
import com.example.common.weather.WeatherOption;
import com.example.client.views.GameAssetManager;

import java.util.*;

public class Player extends User implements TimeObserver {
    private Position homePosition;
    private Position position;
    private Farm farm;
    private int CurrentId;
    private int mapNumber;
    private Sprite activeEmoji = null;
    private String message = null;
    TextureRegion face, faintedAnimation;
    Animation<TextureRegion> walkUpAnimation;
    Animation<TextureRegion> walkDownAnimation;
    Animation<TextureRegion> walkLeftAnimation;
    Animation<TextureRegion> walkRightAnimation;
    TextureRegion currentFrame;
    Direction direction = Direction.DOWN;
    boolean isWalking = false;
    float stateTime = 0f;
    public final static double walkingEnergyConsume = 0.1;

    private int gold = 1000;
    private double energy = 200;
    private boolean unlimitedEnergy = false;
    private boolean fainted = false;
    private Fridge fridge = new Fridge();
    private BackPack inventory = new BackPack();
    private TrashCan trashCan = new TrashCan();
    private BackPackable currentItem = null;

    private int farmingAbility = 0;
    private int farmingLevel = 0;
    private int miningAbility = 0;
    private int miningLevel = 0;
    private int foragingAbility = 0;
    private int foragingLevel = 0;
    private int fishingAbility = 0;
    private int fishingLevel = 0;

    private DateAndTime lastUpdate = new DateAndTime();

    private ArrayList<CraftItem> availableCraftsRecipe = new ArrayList<>();
    private ArrayList<CraftItem> BuildedCrafts = new ArrayList<>();
    private ArrayList<Food> availableFoods = new ArrayList<>();
    private ArrayList<TradeWhitMoney> tradesWhitMoney = new ArrayList<>();
    private ArrayList<TradeWithItem> tradesWithItem = new ArrayList<>();
    private ArrayList<TradeWithItem> tradesWithItemHistory = new ArrayList<>();
    private ArrayList<TradeWhitMoney> tradesWithMoneyHistory = new ArrayList<>();

    private ArrayList<Animal> animals = new ArrayList<>();

    private ArrayList<ArtisanItem> artisanItems = new ArrayList<>();

    private ArrayList<PlayerFriendship.Message> receivedMessages = new ArrayList<>();
    private Queue<PlayerFriendship.Message> notifications = new ArrayDeque<>();

    public void addMessage(PlayerFriendship.Message message) {
        receivedMessages.add(message);
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Sprite getActiveEmoji() {
        return activeEmoji;
    }

    public void setActiveEmoji(Sprite activeEmoji) {
        this.activeEmoji = activeEmoji;
    }

    private Player couple;
    private boolean rejected = false;
    private int rejectDay = 0;

    private boolean notifiedForMarriage = false;
    private Player marriageAsker ;


    public int getCurrentId() {
        return CurrentId;
    }

    public void setCurrentId(int currentId) {
        CurrentId = currentId;
    }

    public Player(User user) {
        super(user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getGender());
        if(user.getGender().equals(Gender.BOY)) {
            face = GameAssetManager.boy_face;
            faintedAnimation = GameAssetManager.boy_fainted;
            walkUpAnimation = GameAssetManager.boy_walking_up;
            walkDownAnimation = GameAssetManager.boy_walking_down;
            walkLeftAnimation = GameAssetManager.boy_walking_left;
            walkRightAnimation = GameAssetManager.boy_walking_right;
            currentFrame = walkDownAnimation.getKeyFrame(0);
        }
        else {
            face = GameAssetManager.girl_face;
            faintedAnimation = GameAssetManager.girl_fainted;
            walkUpAnimation = GameAssetManager.girl_walking_up;
            walkDownAnimation = GameAssetManager.girl_walking_down;
            walkLeftAnimation = GameAssetManager.girl_walking_left;
            walkRightAnimation = GameAssetManager.girl_walking_right;
            currentFrame = walkDownAnimation.getKeyFrame(0);
        }
        createCraftListRecipe();
        createCookList();
    }

    private void askForMarriage(Player player) {
        notifiedForMarriage = true;
        marriageAsker = player;
    }

    public boolean isNotifiedForMarriage() {
        return notifiedForMarriage;
    }

    public Player getMarriageAsker() {
        return marriageAsker;
    }

    public ArrayList<CraftItem> getBuildedCrafts() {
        return BuildedCrafts;
    }
    public void createCraftListRecipe(){
        ArrayList<CraftItemType> allTypes = new ArrayList<>(Arrays.asList(CraftItemType.values()));
        for (CraftItemType Type : allTypes) {
            availableCraftsRecipe.add(new CraftItem(Type));
        }
    }
    public void createCookList(){
        ArrayList<FoodType> allTypes = new ArrayList<>(Arrays.asList(FoodType.values()));
        for (FoodType Type : allTypes) {
            availableFoods.add(new Food(Type));
        }
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
    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }

    public void setHome(Position homePosition) {
        this.homePosition = homePosition;
        this.position = this.homePosition;
    }
    public void goHome() {
        this.position = homePosition;
        this.direction = Direction.DOWN;
    }

    public void walk(int deltaX, int deltaY) {
        int x = position.x + deltaX;
        int y = position.y + deltaY;

        if(fainted) {
            return;
        }
        if(x >= Map.COLS || y >= Map.ROWS || x < 0 || y < 0) {
            return;
        }

        Tile tile = ClientApp.currentGame.getTile(x, y);
        if(!tile.isEmpty() && !(tile.getObjectInTile() instanceof PloughedPlace)) {
            return;
        }
        else if(tile.getAreaType().equals(AreaType.LAKE)) {
            return;
        }
        else if(tile.getAreaType().equals(AreaType.FARM)) {
            Farm farm = (Farm) tile.getArea();
            if(!checkTerritory(farm)) {
                return;
            }
        }

        isWalking = true;
        subtractEnergy(walkingEnergyConsume);
        setPosition(new Position(x, y));
        if(deltaY == 1) {
            direction = Direction.UP;
        }
        else if(deltaY == -1) {
            direction = Direction.DOWN;
        }
        else {
            if(deltaX == 1) {
                direction = Direction.RIGHT;
            }
            else if(deltaX == -1) {
                direction = Direction.LEFT;
            }
        }

        ClientGameController.sendPlayerMovementMessage(position.x, position.y, energy, direction);
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
    public int getWood() {
        return inventory.getItemCount("wood");
    }
    public int getStone() {
        return inventory.getItemCount("stone");
    }

    public void addGold(int gold) {
        this.gold += gold;
        if(couple != null) {
            couple.setGold(gold);
        }
    }

    public void subtractGold(int gold) {
        this.gold -= gold;
        if(couple != null) {
            couple.setGold(gold);
        }
    }
    public void subtractWood(int wood) {
        inventory.removeCountFromBackPack(inventory.getItemByName("wood"), wood);
    }
    public void subtractStone(int stone) {
        inventory.removeCountFromBackPack(inventory.getItemByName("stone"), stone);
    }

    public double getEnergy() {
        return energy;
    }
    public void setEnergy(double energy) {
        this.energy = energy;
    }
    public void subtractEnergy(double amount) {
        if(!unlimitedEnergy) {
            energy -= amount;
            if(energy <= 0) {
                faint();
            }
        }
    }
    public void addEnergy(double amount) {
        energy = Math.min(200, energy + amount);
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

    public BackPackable getCurrentItem() {
        return currentItem;
    }
    public void setCurrentItem(BackPackable currentItem) {
        this.currentItem = currentItem;
    }

    public int getFarmingAbility() {
        return farmingAbility;
    }
    public int getFarmingLevel() {
        return farmingLevel;
    }
    public void upgradeFarmingAbility(int amount) {
        this.farmingAbility += amount;
        if(farmingAbility >= 100 * farmingLevel + 50 && farmingLevel != 4) {
            farmingLevel++;
            farmingAbility = 0;
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
        if(miningAbility >= 100 * miningLevel + 50 && miningLevel != 4) {
            miningLevel++;
            miningAbility = 0;
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
        if(foragingAbility >= 100 * foragingLevel + 50 && foragingLevel != 4) {
            foragingLevel++;
            foragingAbility = 0;
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
        if(fishingAbility >= 100 * fishingLevel + 50 && fishingLevel != 4) {
            fishingLevel++;
            fishingAbility = 0;
        }
    }

    public void addToAvailableCraftsRecipe(CraftItem craft) {
        for (CraftItem crafts : availableCraftsRecipe) {
            if (crafts.getName().equals(craft.getName())) {
                crafts.setAvailable(true);
            }
        }
    }
    public ArrayList<CraftItem> getAvailableCraftsRecipe() {
        availableCraftsRecipe.sort(Comparator.comparing(CraftItem::isAvailable).reversed());
        return availableCraftsRecipe;
    }
    public void addToAvailableFoods(Food food) {
        for (Food foods : availableFoods) {
            if (foods.getName().equals(food.getName())) {
                foods.setAvailable(true);
            }
        }
    }
    public ArrayList<Food> getAvailableFoods() {
        availableFoods.sort(Comparator.comparing(Food::isAvailable).reversed());
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

    public Fridge getFridge() {
        return fridge;
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
    public void reject(int day) {
        rejected = true;
        rejectDay = day;
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
        if(farm.doesHaveScareCrow()) return;
        for(int i=0;i<farm.getTiles().size();i++) {
            for (int j = 0; j < farm.getTiles().get(i).size(); j++) {
                Tile tile = farm.getTiles().get(i).get(j);
                if(tile.getAreaType() == AreaType.GREENHOUSE) continue;
                if(tile.isPlowed()){
                    remainder++;    remainder %= 16;
                    if(remainder == 0){
                        if(RandomGenerator.getInstance().randomInt(0,3) == 1) {
                            if(tile.getObjectInTile() instanceof PloughedPlace) {
                                PloughedPlace place = (PloughedPlace) tile.getObjectInTile();
                                if(place.getHarvestable() instanceof Tree) {
                                    place.setAttackedByCrow(2);
                                }
                                else{
                                    place.unPlough();
                                }
                            }
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
                    ForagingSeedsType.getSeasonForagingSeed(ClientApp.currentGame.getDateAndTime().getSeason());
                }
            }
        }
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getDay() - rejectDay > 7) {
            rejected = false;
        }
        if(dateAndTime.getHour() == 9) {
            if(fainted) {
                fainted = false;
                if(rejected) {
                    energy = 100;
                }
                else {
                    energy = 150;
                }
            }
            else {
                if(rejected) {
                    energy = 100;
                }
                else {
                    energy = 200;
                }
                goHome();
            }
        }
        if(lastUpdate.getDay() != dateAndTime.getDay()) {
            attackOfCrows();
            ForagingSeedsAndCrops(); // check if causes bug
        }
        if(ClientApp.currentGame.getWeather().getCurrentWeather() == WeatherOption.STORM) {
            thorOnThreeTiles();
        }
        lastUpdate.setDay(dateAndTime.getDay());
        lastUpdate.setSeason(dateAndTime.getSeason());
    }

    public void thorOnThreeTiles(){
        List<PloughedPlace> ploughed = new ArrayList<>();
        for (List<Tile> row : farm.getTiles()) {
            for (Tile tile : row) {
                if (tile.getObjectInTile() instanceof PloughedPlace) {
                    ploughed.add((PloughedPlace) tile.getObjectInTile());
                }
            }
        }
        Random random = new Random();
        Collections.shuffle(ploughed, random );
        for (int i = 0; i < Math.min(3, ploughed.size()); i++) {
            ploughed.get(i).thor();
        }
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public void updateAnimation(float delta) {
        if(isWalking) {
            stateTime += delta;
            switch (direction) {
                case UP:
                    currentFrame = walkUpAnimation.getKeyFrame(stateTime); break;
                case DOWN:
                    currentFrame = walkDownAnimation.getKeyFrame(stateTime); break;
                case LEFT:
                    currentFrame = walkLeftAnimation.getKeyFrame(stateTime); break;
                case RIGHT:
                    currentFrame = walkRightAnimation.getKeyFrame(stateTime); break;
            }
        }
        else {
            switch (direction) {
                case UP:
                    currentFrame = walkUpAnimation.getKeyFrame(0); break;
                case DOWN:
                    currentFrame = walkDownAnimation.getKeyFrame(0); break;
                case LEFT:
                    currentFrame = walkLeftAnimation.getKeyFrame(0); break;
                case RIGHT:
                    currentFrame = walkRightAnimation.getKeyFrame(0); break;
            }
        }

        for(Animal animal : animals){
            animal.updateAnimation(delta);
        }
    }

    public TextureRegion getFace() {
        return face;
    }
    public TextureRegion getCurrentFrame() {
        if(fainted) {
            return faintedAnimation;
        }
        return currentFrame;
    }

    public int calculateScore() {
        int totalAbilityLevels = farmingLevel + miningLevel + foragingLevel + fishingLevel;
        return gold * 2 + (totalAbilityLevels * 15) + (int)(energy);
    }

    public void addNotification(PlayerFriendship.Message message) {
        addMessage(message);
        notifications.add(message);
    }
    public PlayerFriendship.Message readNotification(){
        return notifications.remove();
    }

    public Queue<PlayerFriendship.Message> getNotifications() {
        return notifications;
    }

    public void setNotifiedForMarriage(boolean notifiedForMarriage) {
        this.notifiedForMarriage = notifiedForMarriage;
    }

    public void setMiningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
    }

    public void setForagingLevel(int foragingLevel) {
        this.foragingLevel = foragingLevel;
    }

    public void setFishingLevel(int fishingLevel) {
        this.fishingLevel = fishingLevel;
    }

    public void setFarmingLevel(int farmingLevel) {
        this.farmingLevel = farmingLevel;
    }

    public void setMarriageAsker(Player marriageAsker) {
        this.marriageAsker = marriageAsker;
    }
}

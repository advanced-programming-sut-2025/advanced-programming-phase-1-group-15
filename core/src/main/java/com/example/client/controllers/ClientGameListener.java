package com.example.client.controllers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.client.NetworkClient;
import com.example.client.models.ClientApp;
import com.example.client.views.GameAssetManager;
import com.example.common.Game;
import com.example.common.GroupQuests.GroupQuest;
import com.example.common.GroupQuests.GroupQuestManager;
import com.example.common.Message;
import com.example.common.Player;
import com.example.common.animals.Barn;
import com.example.common.animals.Coop;
import com.example.common.animals.Fish;
import com.example.common.animals.FishType;
import com.example.common.enums.Direction;
import com.example.common.farming.Seed;
import com.example.common.farming.SeedType;
import com.example.common.farming.Tree;
import com.example.common.farming.TreeType;
import com.example.common.foraging.ForagingMineral;
import com.example.common.foraging.ForagingMineralType;
import com.example.common.foraging.Stone;
import com.example.common.map.Farm;
import com.example.common.map.GreenHouse;
import com.example.common.map.Map;
import com.example.common.map.Tile;
import com.example.common.relation.PlayerFriendship;
import com.example.common.stores.GeneralItem;
import com.example.common.stores.GeneralItemsType;
import com.example.common.stores.JojaMartItems;
import com.example.common.tools.BackPackable;
import com.example.common.weather.WeatherOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ClientGameListener {
    private final Game game;
    private final Player player;

    public ClientGameListener(Game game) {
        this.game = game;
        this.player = game.getCurrentPlayer();
        NetworkClient.get().addListener(this::onNetMessage);
    }

    private void onNetMessage(Message msg) {
        String action = msg.getFromBody("action");
        String senderUsername = msg.getFromBody("username");
        if(senderUsername != null && senderUsername.equals(player.getUsername())) {
            return;
        }
        System.out.println("Received message: " + msg.getBody());

        switch (action) {
            case "set_randomizers" -> handleSetRandomizers(msg);
            case "player_movement" -> handlePlayerMovement(msg, senderUsername);
            case "player_stop" -> handlePlayerStop(msg, senderUsername);
            case "generate_trees" -> handleGenerateTrees(msg);
            case "generate_stones" -> handleGenerateStones(msg);
            case "generate_fish" -> handleGenerateFish(msg);
            case "generate_common_minerals" -> handleGenerateCommonMinerals(msg);
            case "generate_special_minerals" -> handleGenerateSpecialMinerals(msg);
            case "predict_weather" -> handleWeatherForecast(msg);
            case "hug" -> handelHug(msg, senderUsername);
            case "flower" -> handelFlower(msg, senderUsername);
            case "gift" -> handleGift(msg);
            case "rateGift" -> handleRateGift(msg);
            case "marry-request" -> handelMarriageRequest(msg);
            case "marry-response" -> handelMarriageResponse(msg);
            case "joinQuest" -> handleJoinQuest(msg);
            case "leaveQuest"-> handleLeaveQuest(msg);
            case "questProgress" -> handleQuestProgress(msg);
            case "questReward" -> handleQuestReward(msg);
            case "questFailed"-> handleQuestFailed(msg);
            case "talk" -> handelTalk(msg);
            case "send_emoji" -> handleSendEmoji(msg);
            case "message" -> handleSendMessage(msg);
            case "score-info" -> handleScoreInfo(msg);
            case "build_greenhouse" -> handleBuildGreenhouse(msg, senderUsername);
            case "build_barn" -> handleBuildBarn(msg, senderUsername);
            case "build_coop" -> handleBuildCoop(msg, senderUsername);
            case "eat_food" -> handleEatFood(msg, senderUsername);
            case "axe_use" -> handleAxeUse(msg, senderUsername);
            case "hoe_use" -> handleHoeUse(msg, senderUsername);
            case "pickaxe_use" -> handlePickaxeUse(msg, senderUsername);
            case "put_in_tile" -> handlePutInTile(msg);
            case "trade" -> handelTrade(msg);
            case "remove" -> handelRemove(msg);
            case "accept_trade" -> handelAccept(msg);
            case "decline" -> handelDecline(msg);
            case "update_jojaMart" -> handelJojaMart(msg);
        }
    }
    public void handelJojaMart(Message msg) {
        String itemName = msg.getFromBody("item");
        int number = (int)Double.parseDouble(msg.getFromBody("number"));
        for (Player player : game.getPlayers()) {
            player.setUpdateJoja(true);
            player.setJojaNumber(number);
            player.setJojaItem(itemName);
        }
    }
    public void handelDecline(Message msg) {
        Player player = game.getPlayerByUsername(msg.getFromBody("username"));
        Player target = game.getPlayerByUsername(msg.getFromBody("target"));
        target.setTradePlayer(null);
        target.getWantedItems().clear();
        target.getItems().clear();
        target.setRefresh(true);
    }
    public void handelAccept(Message msg) {
        Player player = game.getPlayerByUsername(msg.getFromBody("username"));
        Player target = game.getPlayerByUsername(msg.getFromBody("target"));
        for (String s : target.getItems().keySet()) {
            BackPackable temp = target.getInventory().getItemByName(s);
            int num = target.getItems().get(s);
            target.getInventory().removeCountFromBackPack(temp , num);
        }
        for (String s : target.getWantedItems().keySet()) {
            BackPackable temp = game.findItem(s);
            target.getInventory().addToBackPack(temp , target.getWantedItems().get(s));
        }
        target.setTradePlayer(null);
        target.getItems().clear();
        target.getWantedItems().clear();
        target.setRefresh(true);
    }
    public void handelRemove(Message msg) {
        Player player = game.getPlayerByUsername(msg.getFromBody("username"));
        Player target = game.getPlayerByUsername(msg.getFromBody("target"));
        String Type = msg.getFromBody("type");
        if(Type.equals("wanted")) {
            target.getTradePlayer().getWantedItems().remove((String)msg.getFromBody("item"));
        }
        else {
            target.getTradePlayer().getItems().remove((String)msg.getFromBody("item"));
        }
        target.setRefresh(true);
    }
    public void handelTrade(Message msg) {
        Player player = game.getPlayerByUsername(msg.getFromBody("username"));
        Player target = game.getPlayerByUsername(msg.getFromBody("target"));
        target.setNewTrade(true);
        target.setTradePlayer(player);
        String type = msg.getFromBody("type");
        if (type.equals("wanted")) {
            int num = Integer.parseInt(msg.getFromBody("number"));
            target.getTradePlayer().getWantedItems().merge(msg.getFromBody("item") , num , Integer::sum);
        }
        else{
            int num = Integer.parseInt(msg.getFromBody("number"));
            target.getTradePlayer().getItems().merge(msg.getFromBody("item") , num , Integer::sum);
        }
        target.setRefresh(true);
    }
    public void handleScoreInfo(Message msg){
        String username = msg.getFromBody("username");
        Player player = game.getPlayerByUsername(username);
        double gold = msg.getFromBody("gold");
        double energy = msg.getFromBody("energy");
        double farmingLevel = msg.getFromBody("farming-level");
        double miningLevel = msg.getFromBody("mining-level");
        double foragingLevel = msg.getFromBody("foraging-level");
        double fishingLevel = msg.getFromBody("fishing-level");

        player.setGold((int)gold);
        player.setEnergy(energy);
        player.setFarmingLevel((int)farmingLevel);
        player.setMiningLevel((int)miningLevel);
        player.setForagingLevel((int)foragingLevel);
        player.setFishingLevel((int)fishingLevel);
    }

    private void handleSendMessage(Message msg){
        String message = msg.getFromBody("messages");
        for (Player player : game.getPlayers()) {
            player.setMessage(message);
        }
    }
    private void handleSendEmoji(Message msg) {
        String emoji = msg.getFromBody("emoji");
        Sprite sprite;
        sprite = switch (emoji) {
            case "angry" -> GameAssetManager.angry;
            case "cry" -> GameAssetManager.cry;
            case "laugh" -> GameAssetManager.laugh;
            case "love" -> GameAssetManager.love;
            case "party" -> GameAssetManager.party;
            case "smile" -> GameAssetManager.smile;
            case "thumbs_up" -> GameAssetManager.thumbs_up;
            default -> null;
        };
        for (Player player : game.getPlayers()) {
           player.setActiveEmoji(new Sprite(sprite));
        }
    }


    private void handelTalk(Message msg){
        String receiverUsername = msg.getFromBody("receiver");
        if(ClientApp.user.getUsername().equals(receiverUsername)) {
            String senderUsername = msg.getFromBody("username");
            Player receiver = game.getPlayerByUsername(receiverUsername);
            Player sender = game.getPlayerByUsername(senderUsername);
            String message = msg.getFromBody("message");

            PlayerFriendship friendship = new PlayerFriendship(receiver, sender);
            friendship.talk(sender,message);
            receiver.addNotification(new PlayerFriendship.Message(sender,message));
        }
    }

    private void handelMarriageResponse(Message msg) {
        String senderUsername = msg.getFromBody("username");
        String receiverUsername = msg.getFromBody("receiver");
        if(ClientApp.user.getUsername().equals(receiverUsername)){
            Player husband = game.getPlayerByUsername(receiverUsername);
            Player wife = game.getPlayerByUsername(senderUsername);
            String answer = msg.getFromBody("answer");
            PlayerFriendship friendship = ClientApp.currentGame.getFriendshipByPlayers(husband, wife);
            if(answer.equals("accept")) {
                friendship.marry();
                GeneralItem ring = (GeneralItem) husband.getInventory()
                    .getItemByName(GeneralItemsType.WEDDING_RING.getName());
                wife.addToBackPack(ring, 1);
                husband.getInventory().removeCountFromBackPack(ring, 1);
                husband.addNotification(new PlayerFriendship.Message(wife,"I'll Marry you!"));
            }
            else{
                friendship.reject();
                husband.reject(ClientApp.currentGame.getDateAndTime().getDay());
                husband.addNotification(new PlayerFriendship.Message(wife,
                    "your proposal has been rejected :("));
            }
        }
    }

    private void handelMarriageRequest(Message msg) {
        String senderUsername = msg.getFromBody("username");
        String receiverUsername = msg.getFromBody("receiver");
        if(ClientApp.user.getUsername().equals(receiverUsername)) {
            Player receiver = game.getPlayerByUsername(receiverUsername);
            Player sender = game.getPlayerByUsername(senderUsername);
            receiver.setNotifiedForMarriage(true);
            receiver.setMarriageAsker(sender);
        }
    }

    private void handleSetRandomizers(Message msg) {
        for(int i = 0; i < Map.ROWS; i++) {
            for(int j = 0; j < Map.COLS; j++) {
                String key = "(" + i + "," + j + ")";
                int randomizer = msg.getIntFromBody(key);
                game.getTile(i, j).setRandomizer(randomizer);
            }
        }
    }

    private void handleGift(Message msg) {
        String receiverUsername = msg.getFromBody("receiver");
        String senderUsername = msg.getFromBody("username");
        String itemName = msg.getFromBody("item");
        int quantity = msg.getIntFromBody("quantity");
        if (ClientApp.user.getUsername().equals(receiverUsername)) {
            ClientGameController.getGift(senderUsername, itemName, quantity);
            System.out.println("reached this line");
        }
    }

    private void handleJoinQuest(Message msg) {
        String questId = msg.getFromBody("questId");
        String username = msg.getFromBody("username");
        Player joiner = game.getPlayerByUsername(username);

        GroupQuestManager questManager = ClientApp.currentGame.getGroupQuestManager();
        questManager.handleJoinQuest(questId, username);
    }

    private void handleLeaveQuest(Message msg) {
        String questId = msg.getFromBody("questId");
        String username = msg.getFromBody("username");

        GroupQuestManager questManager = ClientApp.currentGame.getGroupQuestManager();
        questManager.handleLeaveQuest(questId, username);
    }

    private void handleQuestProgress(Message msg) {
        String questId = msg.getFromBody("questId");
        String username = msg.getFromBody("username");
        int amount = msg.getIntFromBody("amount");

        GroupQuestManager questManager = ClientApp.currentGame.getGroupQuestManager();
        questManager.handleQuestProgress(questId, username, amount);

        GroupQuest quest = questManager.getQuest(questId);
    }

    private void handleQuestReward(Message msg) {
        String questId = msg.getFromBody("questId");
        String username = msg.getFromBody("username");
        int reward = msg.getIntFromBody("reward");

        if (ClientApp.user.getUsername().equals(username)) {
            GroupQuest quest = ClientApp.currentGame.getGroupQuestManager().getQuest(questId);
            if(quest != null){
                showQuestCompletionNotification(username,quest.getTitle(), reward);
            }
        }
    }

    private void handleQuestFailed(Message msg) {
        String questId = msg.getFromBody("questId");
        String username = msg.getFromBody("username");

        if (ClientApp.user.getUsername().equals(username)) {
            GroupQuest quest = ClientApp.currentGame.getGroupQuestManager().getQuest(questId);
            if(quest != null){
                showQuestFailureNotification(username,quest.getTitle());
            }
        }
    }

    private void showQuestCompletionNotification(String sender,String questTitle, int reward) {
        Player senderPlayer = game.getPlayerByUsername(sender);
        game.getCurrentPlayer().addNotification(new PlayerFriendship.Message(senderPlayer,
            "QUEST COMPLETED: " + questTitle + " - Earned " + reward + " coins!"));
    }

    private void showQuestFailureNotification(String sender,String questTitle) {
        System.out.println("QUEST FAILED: " + questTitle + " - Time limit exceeded!");
        Player senderPlayer = game.getPlayerByUsername(sender);
        game.getCurrentPlayer().addNotification(new PlayerFriendship.Message(senderPlayer,
            "QUEST FAILED: " + questTitle + " - Time limit exceeded!"));
    }

    private void handleRateGift(Message msg) {
        String raterUsername = msg.getFromBody("other");
        String senderUsername = msg.getFromBody("username");
        int giftIndex = msg.getIntFromBody("giftIndex");
        int rating = msg.getIntFromBody("rating");

        Player rater = game.getPlayerByUsername(raterUsername);
        Player sender = game.getPlayerByUsername(senderUsername);

        PlayerFriendship friendship = game.getFriendshipByPlayers(sender, rater);
        if (friendship != null && giftIndex >= 0 && giftIndex < friendship.getGifts(sender).size()) {
            PlayerFriendship.Gift gift = friendship.getGifts(sender).get(giftIndex);
            gift.setRate(rating);
            friendship.rateGift(rating);
            sender.addNotification(new PlayerFriendship.Message(
                rater, rater.getUsername() + " rated your gift (" + gift.getItem().getName() + ") with " + rating + "★"
            ));
        }
    }



    private void handelHug(Message msg, String senderUsername) {
        String receiverName = msg.getFromBody("receiver");

        if(player.getUsername().equals(receiverName)) {
            Player sender = game.getPlayerByUsername(senderUsername);
            player.addNotification(new PlayerFriendship.Message(sender, sender.getNickname() + ": I hugged you :)"));
            game.getFriendshipByPlayers(sender, player).hug();
        }
    }

    private void handelFlower(Message msg, String senderUsername){
        String receiverName = (String) msg.getBody().get("receiver");
        if(ClientApp.user.getUsername().equals(receiverName)) {
            Player sender = game.getPlayerByUsername(senderUsername);
            Player receiver = game.getPlayerByUsername(receiverName);
            ClientApp.currentGame.getCurrentPlayer().addNotification(
                new PlayerFriendship.Message(sender,
                    sender.getNickname() + " : I gave this flower to you :)"));
            game.getFriendshipByPlayers(sender, receiver).flower();
            GeneralItem flower = (GeneralItem) sender.getInventory().getItemByName(GeneralItemsType.BOUQUET.getName());
            receiver.addToBackPack(flower, 1);
            sender.getInventory().removeCountFromBackPack(flower, 1);
        }
    }

    private void handlePlayerMovement(Message msg, String senderUsername) {
        double energy = msg.getFromBody("energy");
        int x = msg.getIntFromBody("x");
        int y = msg.getIntFromBody("y");
        Direction direction = msg.getFromBody("direction", Direction.class);


        Player player = game.getPlayerByUsername(senderUsername);
        player.setWalking(true);
        player.setEnergy(energy);
        player.setPosition(x, y);
        player.setDirection(direction);
    }

    private void handlePlayerStop(Message msg, String senderUsername) {
        Player player = game.getPlayerByUsername(senderUsername);
        player.setWalking(false);
    }

    private void handleGenerateTrees(Message msg) {
        HashMap<String, Object> body = msg.getBody();
        for (java.util.Map.Entry<String, Object> entry : body.entrySet()) {
            String key = entry.getKey();

            if (!key.matches("\\(\\d+,\\d+\\)")) continue;

            String[] parts = key.substring(1, key.length() - 1).split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);

            int treeTypeIndex = msg.getIntFromBody(key);
            TreeType type = TreeType.values()[treeTypeIndex];

            Tile tile = game.getTile(x, y);
            tile.put(new Tree(type));
        }
    }

    private void handleGenerateStones(Message msg) {
        HashMap<String, Object> body = msg.getBody();
        for (java.util.Map.Entry<String, Object> entry : body.entrySet()) {
            String key = entry.getKey();

            if (!key.matches("\\(\\d+,\\d+\\)")) continue;

            String[] parts = key.substring(1, key.length() - 1).split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            Tile tile = game.getTile(x, y);

            int typeIndex = msg.getIntFromBody(key);
            if(typeIndex == 0) {
                tile.put(new Stone());
            }
            else {
                tile.put(new GeneralItem(GeneralItemsType.WOOD));
            }
        }
    }

    private void handleGenerateFish(Message msg) {
        HashMap<String, Object> body = msg.getBody();
        for (java.util.Map.Entry<String, Object> entry : body.entrySet()) {
            String key = entry.getKey();

            if (!key.matches("\\(\\d+,\\d+\\)")) continue;

            String[] parts = key.substring(1, key.length() - 1).split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);

            int fishTypeIndex = msg.getIntFromBody(key);
            FishType type = FishType.values()[fishTypeIndex];

            Tile tile = game.getTile(x, y);
            tile.put(new Fish(type));
        }
    }

    private void handleGenerateCommonMinerals(Message msg) {
        HashMap<String, Object> body = msg.getBody();
        for (java.util.Map.Entry<String, Object> entry : body.entrySet()) {
            String key = entry.getKey();

            if (!key.matches("\\(\\d+,\\d+\\)")) continue;

            String[] parts = key.substring(1, key.length() - 1).split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            Tile tile = game.getTile(x, y);

            int typeIndex = msg.getIntFromBody(key);
            if(typeIndex == 0) {
                tile.put(new ForagingMineral(ForagingMineralType.COAL));
            }
            else if(typeIndex == 1) {
                tile.put(new ForagingMineral(ForagingMineralType.COPPER));
            }
            else {
                tile.put(new ForagingMineral(ForagingMineralType.IRON));
            }
        }
    }

    private void handleGenerateSpecialMinerals(Message msg) {
        HashMap<String, Object> body = msg.getBody();
        for (java.util.Map.Entry<String, Object> entry : body.entrySet()) {
            String key = entry.getKey();

            if (!key.matches("\\(\\d+,\\d+\\)")) continue;

            String[] parts = key.substring(1, key.length() - 1).split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);

            int mineralTypeIndex = msg.getIntFromBody(key);
            ForagingMineralType type = ForagingMineralType.values()[mineralTypeIndex];

            Tile tile = game.getTile(x, y);
            tile.put(new ForagingMineral(type));
        }
    }

    private void handleWeatherForecast(Message msg) {
        WeatherOption tomorrowWeather = msg.getFromBody("tomorrow_weather", WeatherOption.class);
        game.getWeather().setForecast(tomorrowWeather);
    }

    private void handleBuildGreenhouse(Message msg, String senderUsername) {
        Player player = game.getPlayerByUsername(senderUsername);
        GreenHouse greenHouse = player.getFarm().getGreenHouse();

        int gold = msg.getIntFromBody("gold");
        int wood = msg.getIntFromBody("wood");

        greenHouse.buildGreenHouse();
        player.setGold(gold);
        player.setWood(wood);
    }

    private void handleBuildBarn(Message msg, String senderUsername) {
        Player player = game.getPlayerByUsername(senderUsername);

        int x = msg.getIntFromBody("x");
        int y = msg.getIntFromBody("y");

        int gold = msg.getIntFromBody("gold");
        int wood = msg.getIntFromBody("wood");
        int stone = msg.getIntFromBody("stone");

        Farm farm = player.getFarm();
        farm.getInnerAreas().add(new Barn(farm.getSubArea(farm.getTiles(), x, x + Barn.COLS, y, y + Barn.ROWS)));
        player.setGold(gold);
        player.setWood(wood);
        player.setStone(stone);
    }

    private void handleBuildCoop(Message msg, String senderUsername) {
        Player player = game.getPlayerByUsername(senderUsername);

        int x = msg.getIntFromBody("x");
        int y = msg.getIntFromBody("y");

        int gold = msg.getIntFromBody("gold");
        int wood = msg.getIntFromBody("wood");
        int stone = msg.getIntFromBody("stone");

        Farm farm = player.getFarm();
        farm.getInnerAreas().add(new Coop(farm.getSubArea(farm.getTiles(), x, x + Coop.COLS, y, y + Coop.ROWS)));
        player.setGold(gold);
        player.setWood(wood);
        player.setStone(stone);
    }

    private void handleEatFood(Message msg, String senderUsername) {
        Player player = game.getPlayerByUsername(senderUsername);

        String foodName = msg.getFromBody("food_name");
        double energy = msg.getFromBody("energy");

        player.setEnergy(energy);
        player.getInventory().removeCountFromBackPack(player.getInventory().getItemByName(foodName), 1);
    }

    private void handleAxeUse(Message msg, String senderUsername) {
        Player player = game.getPlayerByUsername(senderUsername);

        boolean success = msg.getFromBody("success");
        int xUse = msg.getIntFromBody("x_use");
        int yUse = msg.getIntFromBody("y_use");
        double energy = msg.getFromBody("energy");
        int wood = msg.getIntFromBody("wood");
        int foragingAbility = msg.getIntFromBody("foraging_ability");
        int foragingLevel = msg.getIntFromBody("foraging_level");

        Tile tile = game.getTile(xUse, yUse);
        player.setEnergy(energy);
        player.setWood(wood);
        player.setForagingAbility(foragingAbility);
        player.setForagingLevel(foragingLevel);

        if(success) {
            tile.empty();
            if(msg.getFromBody("seed_type") != null) {
                SeedType seedType = msg.getFromBody("seed_type", SeedType.class);
                int seedCount = msg.getIntFromBody("seed_count");

                player.addToBackPack(new Seed(seedType), seedCount);
            }
        }
    }

    private void handleHoeUse(Message msg, String senderUsername) {
        Player player = game.getPlayerByUsername(senderUsername);

        boolean success = msg.getFromBody("success");
        int xUse = msg.getIntFromBody("x_use");
        int yUse = msg.getIntFromBody("y_use");
        double energy = msg.getFromBody("energy");

        Tile tile = game.getTile(xUse, yUse);
        player.setEnergy(energy);

        if(success) {
            tile.plow();
        }
    }

    private void handlePickaxeUse(Message msg, String senderUsername) {
        Player player = game.getPlayerByUsername(senderUsername);

        String pickaxeAction = msg.getFromBody("pickaxe_action");
        int xUse = msg.getIntFromBody("x_use");
        int yUse = msg.getIntFromBody("y_use");
        double energy = msg.getFromBody("energy");
        int stone = msg.getIntFromBody("stone");
        int miningAbility = msg.getIntFromBody("mining_ability");
        int miningLevel = msg.getIntFromBody("mining_level");

        Tile tile = game.getTile(xUse, yUse);
        player.setEnergy(energy);
        player.setStone(stone);
        player.setMiningAbility(miningAbility);
        player.setMiningLevel(miningLevel);

        if (pickaxeAction.equals("unplow")) {
            tile.unplow();
        }
        else if (pickaxeAction.equals("successful")) {
            tile.empty();
            if (msg.getFromBody("foraging_mineral_type") != null) {
                ForagingMineralType foragingMineralType = msg.getFromBody("foraging_mineral_type", ForagingMineralType.class);
                int foragingMineralCount = msg.getIntFromBody("foraging_mineral_count");

                player.addToBackPack(new ForagingMineral(foragingMineralType), foragingMineralCount);
            }
        }
    }

    private void handlePutInTile(Message msg) {
        int x = msg.getIntFromBody("x");
        int y = msg.getIntFromBody("y");

        String itemName = msg.getFromBody("item_name");

        Tile tile = game.getTile(x, y);
        if(ClientGameController.getItemByName(itemName) != null) {
            tile.put(ClientGameController.getItemByName(itemName));
        }
    }

    public void disconnect() {
        NetworkClient.get().removeListener(this::onNetMessage);
    }
}

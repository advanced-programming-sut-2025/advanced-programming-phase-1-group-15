package com.example.client.controllers;

import com.example.client.NetworkClient;
import com.example.client.models.ClientApp;
import com.example.common.Game;
import com.example.common.Message;
import com.example.common.Player;
import com.example.common.enums.Direction;
import com.example.common.farming.Seed;
import com.example.common.farming.Tree;
import com.example.common.farming.TreeType;
import com.example.common.foraging.Stone;
import com.example.common.map.Map;
import com.example.common.map.Tile;
import com.example.common.relation.PlayerFriendship;
import com.example.common.stores.GeneralItem;
import com.example.common.stores.GeneralItemsType;
import com.example.common.tools.BackPack;
import com.example.common.tools.BackPackable;
import com.example.common.weather.WeatherOption;

import java.util.HashMap;

import static com.example.client.controllers.ClientGameController.*;

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
            case "set_randomizers" -> {
                handleSetRandomizers(msg);
            }
            case "player_movement" -> {
                handlePlayerMovement(msg, senderUsername);
            }
            case "player_stop" -> {
                handlePlayerStop(msg, senderUsername);
            }
            case "generate_trees" -> {
                handleGenerateTrees(msg);
            }
            case "generate_stones" -> {
                handleGenerateStones(msg);
            }
            case "predict_weather" -> {
                handleWeatherForecast(msg);
            }
            case "hug" -> {
                handelHug(msg, senderUsername);
            }
            case "flower" -> {
                handelFlower(msg, senderUsername);
            }
            case "gift" -> {
                handelGift(msg,senderUsername);
            }
            case "marry-request" -> {
                handelMarriageRequest(msg);
            }
            case "marry-response" -> {

            }
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

    private void handelGift(Message msg, String senderUsername) {
        String receiverUsername = msg.getFromBody("receiver");
        String itemName = msg.getFromBody("item");
        int quantity = msg.getIntFromBody("quantity");
        if(ClientApp.user.getUsername().equals(receiverUsername)) {
            Player sender = game.getPlayerByUsername(senderUsername);
            Player receiver = game.getPlayerByUsername(receiverUsername);
            BackPackable item =  sender.getInventory().getItemByName(itemName);
            sender.getInventory().removeCountFromBackPack(item,quantity);
            receiver.getInventory().addToBackPack(item,quantity);
            ClientApp.currentGame.getCurrentPlayer().addNotification(
                new PlayerFriendship.Message(
                    sender, sender.getNickname() +"sent you "+quantity+" number of "+itemName));
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

    private void handleWeatherForecast(Message msg) {
        WeatherOption tomorrowWeather = msg.getFromBody("tomorrow_weather", WeatherOption.class);
        game.getWeather().setForecast(tomorrowWeather);
    }

    public void disconnect() {
        NetworkClient.get().removeListener(this::onNetMessage);
    }
}

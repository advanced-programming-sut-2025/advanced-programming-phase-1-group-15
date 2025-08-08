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
import com.example.common.weather.WeatherOption;

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

    private void handelHug(Message msg, String senderUsername) {
        String receiverName = (String) msg.getBody().get("receiver");
        System.err.println("reached before if");
        System.err.println(receiverName + "   " + ClientApp.user.getUsername());
        if(ClientApp.user.getUsername().equals(receiverName)) {
            Player sender = game.getPlayerByUsername(senderUsername);
            Player receiver = game.getPlayerByUsername(receiverName);
            receiver.addNotification(new PlayerFriendship.Message(sender," I hugged you :)"));
            System.err.println("reached this line");
            game.getFriendshipByPlayers(sender, receiver).hug();
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

package com.example.client.controllers;

import com.example.client.NetworkClient;
import com.example.common.Game;
import com.example.common.Message;
import com.example.common.Player;
import com.example.common.map.Map;

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

    private void handlePlayerMovement(Message msg, String senderUsername) {
        int deltaX = msg.getIntFromBody("delta_x");
        int deltaY = msg.getIntFromBody("delta_y");
        game.getPlayerByUsername(senderUsername).walk(deltaX, deltaY);
    }

    public void disconnect() {
        NetworkClient.get().removeListener(this::onNetMessage);
    }
}

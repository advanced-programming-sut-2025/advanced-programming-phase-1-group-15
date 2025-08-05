package com.example.client.controllers;

import com.example.client.NetworkClient;
import com.example.client.models.ClientApp;
import com.example.client.views.GameView;
import com.example.common.Game;
import com.example.common.Message;
import com.example.common.Player;

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
            case "player_movement" -> {
                int x = msg.getIntFromBody("x");
                int y = msg.getIntFromBody("y");
                game.getPlayerByUsername(senderUsername).walk(x, y);
            }
        }
    }

    public void disconnect() {
        NetworkClient.get().removeListener(this::onNetMessage);
    }
}

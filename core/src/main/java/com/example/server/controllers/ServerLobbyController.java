package com.example.server.controllers;

import com.example.common.Lobby;
import com.example.common.Result;
import com.example.common.User;
import com.example.server.models.ServerApp;

import java.util.UUID;

public class ServerLobbyController {
    public static Result createLobby(String name, String adminUsername, boolean isPublic, String password, boolean isVisible) {
        User admin = ServerApp.getUserByUsername(adminUsername);

        Lobby newLobby = new Lobby(generateShortLobbyId(), name, admin, isPublic, password, isVisible);
        ServerApp.lobbies.add(newLobby);

        return new Result(true, "Lobby created successfully with id " + newLobby.getId() + "!");
    }

    public static String generateShortLobbyId() {
        String raw = UUID.randomUUID().toString().replace("-", "");
        return raw.substring(0, 8).toUpperCase();
    }
}

package com.example.server.controllers;

import com.example.common.Lobby;
import com.example.common.Message;
import com.example.common.Result;
import com.example.common.User;
import com.example.server.models.ServerApp;

import java.util.Map;
import java.util.UUID;

import static com.example.server.controllers.ServerController.*;

public class ServerLobbyController {
    public static Result createLobby(String name, String adminUsername, boolean isPublic, String password, boolean isVisible) {
        User admin = ServerApp.getUserByUsername(adminUsername);

        Lobby newLobby = new Lobby(generateShortLobbyId(), name, admin, isPublic, password, isVisible);
        ServerApp.lobbies.add(newLobby);
        admin.setLobbyId(newLobby.getId());

        return new Result(true, "Lobby created successfully with id " + newLobby.getId() + "!");
    }

    public static String generateShortLobbyId() {
        String raw = UUID.randomUUID().toString().replace("-", "");
        return raw.substring(0, 8).toUpperCase();
    }

    public static void handleJoinLobby(Message req, Map<String,Object> respBody) {
        String lobbyId = req.getFromBody("lobby_id");
        Lobby lobby = ServerApp.getLobbyById(lobbyId);
        String username = req.getFromBody("username");
        User user = ServerApp.getUserByUsername(username);
        String password = req.getFromBody("password");

        if(lobby == null) {
            respBody.put("success", false);
            respBody.put("message", "Refresh the Page. Lobby doesn't exist!");
            return;
        }
        if(lobby.getUsersCount() == 4) {
            respBody.put("success", false);
            respBody.put("message", "Refresh the Page. Lobby has reached the maximum number of users!");
            return;
        }
        if(lobby.isPublic()) {
            lobby.addUser(user);
            user.setLobbyId(lobbyId);

            respBody.put("success", true);
            respBody.put("message", "User joined lobby successfully!");
            respBody.put("username", username);

            informOtherLobbyUsers(respBody, lobby, username);
            informNewJoinLobby();
        }
        else {
           if(lobby.getPassword().equals(password)) {
               lobby.addUser(user);
               user.setLobbyId(lobbyId);

               respBody.put("success", true);
               respBody.put("message", "User joined lobby successfully!");
               respBody.put("username", username);

               informOtherLobbyUsers(respBody, lobby, username);
               informNewJoinLobby();
           }
           else {
               respBody.put("success", false);
               respBody.put("message", "password doesn't match!");
           }
        }
    }

    public static void handleLeaveLobby(Message req, Map<String,Object> respBody) {
        String lobbyId = req.getFromBody("lobby_id");
        Lobby lobby = ServerApp.getLobbyById(lobbyId);
        String username = req.getFromBody("username");
        User user = ServerApp.getUserByUsername(username);

        lobby.removeUser(user);
        if(lobby.getUsersCount() == 0) {
            ServerApp.lobbies.remove(lobby);
        }
        user.setLobbyId(null);

        respBody.put("success", true);
        respBody.put("message", "User leaved lobby successfully!");
        respBody.put("username", username);

        informOtherLobbyUsers(respBody, lobby, username);
        informNewLeaveLobby();
    }

    public static void handleSetMapNumber(Message req, Map<String,Object> respBody) {
        String lobbyId = req.getFromBody("lobby_id");
        Lobby lobby = ServerApp.getLobbyById(lobbyId);
        String username = req.getFromBody("username");
        User user = ServerApp.getUserByUsername(username);
        int mapNumber = req.getIntFromBody("map_number");

        lobby.setMapNumber(user.getUsername(), mapNumber);
        respBody.put("success", true);
        respBody.put("message", "Map number set successfully!");
        respBody.put("username", username);

        informOtherLobbyUsers(respBody, lobby, username);
    }

    public static void handleStartGame(Message req, Map<String,Object> respBody) {
        String lobbyId = req.getFromBody("lobby_id");
        Lobby lobby = ServerApp.getLobbyById(lobbyId);
        String username = req.getFromBody("username");

        respBody.put("success", true);
        respBody.put("message", "Game started successfully!");
        respBody.put("lobby_id", lobbyId);

        informOtherLobbyUsers(respBody, lobby, username);
    }
}

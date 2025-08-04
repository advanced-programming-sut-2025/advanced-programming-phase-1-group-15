package com.example.server.controllers;

import com.example.common.Lobby;
import com.example.common.Message;
import com.example.common.Result;
import com.example.common.User;
import com.example.server.GameServer;
import com.example.server.models.ServerApp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

            informOtherLobbyUsers(respBody, lobby);
        }
        else {
           if(lobby.getPassword().equals(password)) {
               lobby.addUser(user);
               user.setLobbyId(lobbyId);

               respBody.put("success", true);
               respBody.put("message", "User joined lobby successfully!");
               respBody.put("username", username);

               informOtherLobbyUsers(respBody, lobby);
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

        informOtherLobbyUsers(respBody, lobby);
    }

    public static void informOtherLobbyUsers(Map<String,Object> respBody, Lobby lobby) {
        HashMap<String,Object> respBodyHashMap = new HashMap<>(respBody);
        Message resp = new Message(respBodyHashMap, Message.Type.RESPONSE);

        for (GameServer.ClientHandler clientHandler : GameServer.getClientHandlers()) {
            if(lobby.checkIfUserIsInLobby(ServerApp.getUserByAddress(clientHandler.getAddress()))) {
                clientHandler.sendMessage(resp);
            }
        }
    }
}

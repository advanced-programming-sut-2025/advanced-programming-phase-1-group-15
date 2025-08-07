package com.example.client.controllers;

import com.example.client.NetworkClient;
import com.example.client.models.ClientApp;
import com.example.common.Lobby;
import com.example.common.Message;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientLobbyController {
    public static final long timeoutMs = 1000;

    public static void sendCreateLobbyMessage(String adminUsername, String name, boolean isPublic, String password, boolean isVisible) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "create_lobby");
        cmdBody.put("admin_username", adminUsername);
        cmdBody.put("name", name);
        cmdBody.put("is_public", isPublic);
        cmdBody.put("password", password);
        cmdBody.put("is_visible", isVisible);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public static void updateLobbies() {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "get_lobbies");

        try {
            Message resp = NetworkClient.get().sendAndWaitForResponse(new Message(cmdBody, Message.Type.COMMAND), timeoutMs);

            Type lobbyListType = new TypeToken<ArrayList<Lobby>>(){}.getType();
            ClientApp.lobbies = resp.getObjectFromBody("lobbies", lobbyListType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUsernames() {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "get_usernames");

        try {
            Message resp = NetworkClient.get().sendAndWaitForResponse(new Message(cmdBody, Message.Type.COMMAND), timeoutMs);

            Type stringListType = new TypeToken<ArrayList<String>>(){}.getType();
            ClientApp.usernames = resp.getObjectFromBody("usernames", stringListType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isOnline(String username) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "is_online");
        cmdBody.put("username", username);

        try {
            Message resp = NetworkClient.get().sendAndWaitForResponse(new Message(cmdBody, Message.Type.COMMAND), timeoutMs);

            Boolean success = resp.getObjectFromBody("success", Boolean.class);
            return success != null && success;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void sendJoinLobbyMessage(String lobbyId, String username, String password) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "join_lobby");
        cmdBody.put("lobby_id", lobbyId);
        cmdBody.put("username", username);
        cmdBody.put("password", password);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public static void sendLeaveLobbyMessage(String lobbyId, String username) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "leave_lobby");
        cmdBody.put("lobby_id", lobbyId);
        cmdBody.put("username", username);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public static void sendSetMapNumberMessage(String lobbyId, String username, int mapNumber) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "set_map_number");
        cmdBody.put("lobby_id", lobbyId);
        cmdBody.put("username", username);
        cmdBody.put("map_number", mapNumber);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public static void sendStartGameMessage(String lobbyId, String username) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "start_game");
        cmdBody.put("lobby_id", lobbyId);
        cmdBody.put("username", username);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }
}

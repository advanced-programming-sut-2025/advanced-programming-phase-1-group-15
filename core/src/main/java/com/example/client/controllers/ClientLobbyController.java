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
}

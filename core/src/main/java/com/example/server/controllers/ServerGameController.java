package com.example.server.controllers;

import com.example.common.Lobby;
import com.example.common.Message;
import com.example.common.RandomGenerator;
import com.example.server.models.ServerApp;

import java.util.Map;

import static com.example.server.controllers.ServerController.informAllLobbyUsers;
import static com.example.server.controllers.ServerController.informOtherLobbyUsers;

public class ServerGameController {
    public static void handleCommand(Message req, Map<String,Object> respBody) {
        String action = req.getFromBody("action");
        String senderUsername = req.getFromBody("username");
        respBody.put("username", senderUsername);

        switch (action) {
            case "set_randomizers" -> {
                handleSetRandomizers(req, respBody);
            }
            case "player_movement" -> {
                handlePlayerMovement(req, respBody);
            }
        }
    }

    public static void handleSetRandomizers(Message req, Map<String,Object> respBody) {
        String senderUsername = req.getFromBody("username");
        Lobby lobby = ServerApp.getLobbyByUser(senderUsername);
        respBody.put("success", true);
        respBody.put("message", "randomizers setup completed!");

        respBody.putAll(req.getBody());

        informOtherLobbyUsers(respBody, lobby, senderUsername);
    }

    public static void handlePlayerMovement(Message req, Map<String,Object> respBody) {
        String senderUsername = req.getFromBody("username");
        Lobby lobby = ServerApp.getLobbyByUser(senderUsername);
        respBody.put("success", true);
        respBody.put("message", senderUsername + " moved successfully!");
        int deltaX = req.getIntFromBody("delta_x");
        int deltaY = req.getIntFromBody("delta_y");
        respBody.put("delta_x", deltaX);
        respBody.put("delta_y", deltaY);

        informOtherLobbyUsers(respBody, lobby, senderUsername);
    }
}

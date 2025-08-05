package com.example.server.controllers;

import com.example.common.Lobby;
import com.example.common.Message;
import com.example.common.User;
import com.example.server.models.ServerApp;

import java.util.Map;

import static com.example.server.controllers.ServerController.informOtherLobbyUsers;

public class ServerGameController {
    public static void handleCommand(Message req, Map<String,Object> respBody) {
        String action = req.getFromBody("action");
        String senderUsername = req.getFromBody("username");
        respBody.put("username", senderUsername);

        switch (action) {
            case "player_movement" -> {
                handlePlayerMovement(req, respBody);
            }
        }
    }

    public static void handlePlayerMovement(Message req, Map<String,Object> respBody) {
        String senderUsername = req.getFromBody("username");
        Lobby lobby = ServerApp.getLobbyByUser(senderUsername);
        respBody.put("success", true);
        respBody.put("message", senderUsername + " moved successfully!");
        int deltaX = req.getIntFromBody("x");
        int deltaY = req.getIntFromBody("y");
        respBody.put("x", deltaX);
        respBody.put("y", deltaY);

        informOtherLobbyUsers(respBody, lobby, senderUsername);
    }
}

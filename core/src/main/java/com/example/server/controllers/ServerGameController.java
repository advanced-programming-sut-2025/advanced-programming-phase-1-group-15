package com.example.server.controllers;

import com.example.common.Lobby;
import com.example.common.Message;
import com.example.server.models.ServerApp;

import java.util.Map;

import static com.example.server.controllers.ServerController.informOtherLobbyUsers;

public class ServerGameController {
    public static void handleCommand(Message req, Map<String,Object> respBody) {
        String action = req.getFromBody("action");
        String senderUsername = req.getFromBody("username");
        respBody.put("username", senderUsername);

        switch (action) {
            case "set_randomizers", "player_movement", "player_stop", "generate_trees", "generate_stones", "predict_weather","hug" -> {
                reflectMessage(req, respBody);
            }
        }
    }

    public static void reflectMessage(Message req, Map<String,Object> respBody) {
        String senderUsername = req.getFromBody("username");
        Lobby lobby = ServerApp.getLobbyByUser(senderUsername);
        respBody.put("success", true);
        respBody.put("message", "reflection done successfully!");

        respBody.putAll(req.getBody());

        informOtherLobbyUsers(respBody, lobby, senderUsername);
    }
}

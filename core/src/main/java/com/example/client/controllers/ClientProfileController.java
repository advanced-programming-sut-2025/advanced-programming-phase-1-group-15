package com.example.client.controllers;

import com.example.client.NetworkClient;
import com.example.common.Message;

import java.util.HashMap;

public class ClientProfileController {
    public static final long timeoutMs = 1000;

    public static void sendChangeUsernameMessage(String username, String newUsername) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "change_username");
        cmdBody.put("username", username);
        cmdBody.put("new_username", newUsername);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public static void sendChangePasswordMessage(String username, String newPassword) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "change_password");
        cmdBody.put("username", username);
        cmdBody.put("new_password", newPassword);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public static void sendChangeNicknameMessage(String username, String newNickname) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "change_nickname");
        cmdBody.put("username", username);
        cmdBody.put("new_nickname", newNickname);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public static void sendChangeEmailMessage(String username, String newEmail) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "change_email");
        cmdBody.put("username", username);
        cmdBody.put("new_email", newEmail);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public static void sendChangeAvatarKeyMessage(String username, String newKey) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "change_avatar_key");
        cmdBody.put("username", username);
        cmdBody.put("new_key", newKey);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }
}

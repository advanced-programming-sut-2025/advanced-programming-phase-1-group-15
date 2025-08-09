package com.example.server.controllers;

import com.example.common.Lobby;
import com.example.common.Message;
import com.example.common.Result;
import com.example.common.User;
import com.example.common.enums.Gender;
import com.example.server.GameServer;
import com.example.server.models.ServerApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerController {
    public static void handleSignup(Message req, Map<String,Object> respBody, String IP, int port) {
        String username = req.getFromBody("username");
        String pass = req.getFromBody("password");
        String passC = req.getFromBody("passwordConfirm");
        String nick = req.getFromBody("nickname");
        String email = req.getFromBody("email");
        Gender gender = req.getFromBody("gender", Gender.class);

        Result r = ServerLoginController.registerUser(IP + ":" + port, username, pass, passC, nick, email, gender);
        respBody.put("success", r.success());
        respBody.put("message", r.message());
        if(r.success()) {
            informNewSignup();
        }
    }

    public static void handleLogin(Message req, Map<String,Object> respBody, String IP, int port) {
        String username = req.getFromBody("username");
        String pass = req.getFromBody("password");
        Result r = ServerLoginController.loginUser(IP + ":" + port, username, pass, false);

        respBody.put("success", r.success());
        respBody.put("message", r.message());
        if(r.success()) {
            informNewLogin();
        }
    }

    public static void handleLogout(Message req, Map<String,Object> respBody, String IP, int port) {
        String username = req.getFromBody("username");

        String address = IP + ":" + port;
        ServerApp.onlineAddresses.remove(address);

        respBody.put("success", true);
        respBody.put("message", "User logged out successfully.");
        informNewLogout(username);
    }

    public static void getUser(Message req, Map<String,Object> respBody) {
        String IP = req.getFromBody("ip");
        int port = req.getIntFromBody("port");

        respBody.put("user", ServerApp.getUserByAddress(IP + ":" + port));
    }

    public static void handleSecurityQuestion(Message req, Map<String,Object> respBody) {
        String username = req.getFromBody("username");
        String question = req.getFromBody("question");
        String answer = req.getFromBody("answer");
        Result r = ServerLoginController.pickQuestion(username, question, answer);

        respBody.put("success", r.success());
        respBody.put("message", r.message());
    }

    public static void getUserByUsername(Message req, Map<String,Object> respBody) {
        String username = req.getFromBody("username");

        respBody.put("user", ServerApp.getUserByUsername(username));
    }

    public static void handleChangePassword(Message req, Map<String,Object> respBody) {
        String username = req.getFromBody("username");
        String newPassword = req.getFromBody("new_password");
        Result r = ServerLoginController.forgetPassword(username, newPassword);

        respBody.put("success", r.success());
        respBody.put("message", r.message());
    }

    public static void handleChangeUsername(Message req, Map<String,Object> respBody) {
        String username = req.getFromBody("username");
        String newUsername = req.getFromBody("new_username");
        Result r = ServerProfileController.changeUsername(username, newUsername);

        respBody.put("success", r.success());
        respBody.put("message", r.message());
    }

    public static void handleChangeNickname(Message req, Map<String,Object> respBody) {
        String username = req.getFromBody("username");
        String newNickname = req.getFromBody("new_nickname");
        Result r = ServerProfileController.changeNickname(username, newNickname);

        respBody.put("success", r.success());
        respBody.put("message", r.message());
    }

    public static void handleChangeEmail(Message req, Map<String,Object> respBody) {
        String username = req.getFromBody("username");
        String newEmail = req.getFromBody("new_email");
        Result r = ServerProfileController.changeEmail(username, newEmail);

        respBody.put("success", r.success());
        respBody.put("message", r.message());
    }

    public static void handleChangeAvatarKey(Message req, Map<String,Object> respBody) {
        String username = req.getFromBody("username");
        String newKey = req.getFromBody("new_key");
        Result r = ServerProfileController.changeAvatar(username, newKey);

        respBody.put("success", r.success());
        respBody.put("message", r.message());
    }

    public static void handleCreateLobby(Message req, Map<String,Object> respBody) {
        String adminUsername = req.getFromBody("admin_username");
        String name = req.getFromBody("name");
        boolean isPublic = req.getFromBody("is_public");
        String password = req.getFromBody("password");
        boolean isVisible = req.getFromBody("is_visible");
        Result r = ServerLobbyController.createLobby(name, adminUsername, isPublic, password, isVisible);

        respBody.put("success", r.success());
        respBody.put("message", r.message());
        if(r.success()) {
            informNewCreateLobby();
        }
    }

    public static void getLobbies(Message req, Map<String,Object> respBody) {
        respBody.put("lobbies", ServerApp.lobbies);
    }

    public static void getUsernames(Message req, Map<String,Object> respBody) {
        ArrayList<String> usernames = new ArrayList<>();
        for(User user : ServerApp.users.values()) {
            String username = user.getUsername();
            if(!usernames.contains(username)) {
                usernames.add(username);
            }
        }

        respBody.put("usernames", usernames);
    }

    public static void checkOnline(Message req, Map<String,Object> respBody) {
        String username = req.getFromBody("username");
        for(String address : ServerApp.onlineAddresses) {
            User user = ServerApp.getUserByAddress(address);
            if(username.equals(user.getUsername())) {
                respBody.put("success", true);
                return;
            }
        }

        respBody.put("success", false);
    }

    public static void informNewSignup() {
        HashMap<String,Object> respBody = new HashMap<>();
        respBody.put("action", "new_signup");

        informAllUsers(respBody);
    }

    public static void informNewLogin() {
        HashMap<String,Object> respBody = new HashMap<>();
        respBody.put("action", "new_login");

        informAllUsers(respBody);
    }

    public static void informNewLogout(String senderUsername) {
        HashMap<String,Object> respBody = new HashMap<>();
        respBody.put("action", "new_logout");

        informOtherUsers(respBody, senderUsername);
    }

    public static void informNewCreateLobby() {
        HashMap<String,Object> respBody = new HashMap<>();
        respBody.put("action", "new_create_lobby");

        informAllUsers(respBody);
    }

    public static void informNewJoinLobby() {
        HashMap<String,Object> respBody = new HashMap<>();
        respBody.put("action", "new_join_lobby");

        informAllUsers(respBody);
    }

    public static void informNewLeaveLobby() {
        HashMap<String,Object> respBody = new HashMap<>();
        respBody.put("action", "new_leave_lobby");

        informAllUsers(respBody);
    }

    public static void informOtherLobbyUsers(Map<String,Object> respBody, Lobby lobby, String senderUsername) {
        HashMap<String,Object> respBodyHashMap = new HashMap<>(respBody);
        Message resp = new Message(respBodyHashMap, Message.Type.RESPONSE);

        String act = respBodyHashMap.get("action").toString();

        for (GameServer.ClientHandler clientHandler : GameServer.getClientHandlers()) {
            if(!clientHandler.getAddress().equals(ServerApp.getAddressByUser(senderUsername)) &&
                lobby.checkIfUserIsInLobby(ServerApp.getUserByAddress(clientHandler.getAddress()))) {
                clientHandler.sendMessage(resp);
            }
        }
    }

    public static void informAllLobbyUsers(Map<String,Object> respBody, Lobby lobby) {
        HashMap<String,Object> respBodyHashMap = new HashMap<>(respBody);
        Message resp = new Message(respBodyHashMap, Message.Type.RESPONSE);

        for (GameServer.ClientHandler clientHandler : GameServer.getClientHandlers()) {
            if(lobby.checkIfUserIsInLobby(ServerApp.getUserByAddress(clientHandler.getAddress()))) {
                clientHandler.sendMessage(resp);
            }
        }
    }

    public static void informOtherUsers(Map<String,Object> respBody, String senderUsername) {
        HashMap<String,Object> respBodyHashMap = new HashMap<>(respBody);
        Message resp = new Message(respBodyHashMap, Message.Type.RESPONSE);

        for (GameServer.ClientHandler clientHandler : GameServer.getClientHandlers()) {
            if(!clientHandler.getAddress().equals(ServerApp.getAddressByUser(senderUsername))) {
                clientHandler.sendMessage(resp);
            }
        }
    }

    public static void informAllUsers(Map<String,Object> respBody) {
        HashMap<String,Object> respBodyHashMap = new HashMap<>(respBody);
        Message resp = new Message(respBodyHashMap, Message.Type.RESPONSE);

        for (GameServer.ClientHandler clientHandler : GameServer.getClientHandlers()) {
            clientHandler.sendMessage(resp);
        }
    }
}

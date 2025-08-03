package com.example.server.controllers;

import com.example.common.Message;
import com.example.common.Result;
import com.example.common.enums.Gender;
import com.example.server.models.ServerApp;

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
    }

    public static void handleLogin(Message req, Map<String,Object> respBody, String IP, int port) {
        String username = req.getFromBody("username");
        String pass = req.getFromBody("password");
        Result r = ServerLoginController.loginUser(IP + ":" + port, username, pass, false);

        respBody.put("success", r.success());
        respBody.put("message", r.message());
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
}

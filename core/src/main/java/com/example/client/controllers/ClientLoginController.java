package com.example.client.controllers;

import com.example.client.NetworkClient;
import com.example.client.models.ClientApp;
import com.example.common.User;
import com.example.common.enums.Gender;
import com.example.common.Message;

import java.security.SecureRandom;
import java.util.HashMap;

public class ClientLoginController {
    public static final long timeoutMs = 1000;

    public static void sendSignupMessage(String username, String password, String confirmPassword, String nickname, String email, Gender gender) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "signup");
        cmdBody.put("username", username);
        cmdBody.put("password", password);
        cmdBody.put("passwordConfirm", confirmPassword);
        cmdBody.put("nickname", nickname);
        cmdBody.put("email", email);
        cmdBody.put("gender", gender);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public static void sendLoginMessage(String username, String password) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "login");
        cmdBody.put("username", username);
        cmdBody.put("password", password);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public static void updateUser() {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "get_user");

        try {
            Message resp = NetworkClient.get().sendAndWaitForResponse(new Message(cmdBody, Message.Type.COMMAND), timeoutMs);

            ClientApp.user = resp.getObjectFromBody("user", User.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUserByUsername(String username) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "get_user_by_username");
        cmdBody.put("username", username);

        try {
            Message resp = NetworkClient.get().sendAndWaitForResponse(new Message(cmdBody, Message.Type.COMMAND), timeoutMs);

            return resp.getObjectFromBody("user", User.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendSecurityQuestionMessage(String question, String answer) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "security_question");
        cmdBody.put("username", ClientApp.user.getUsername());
        cmdBody.put("question", question);
        cmdBody.put("answer", answer);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public static void sendChangePasswordMessage(String username, String newPassword) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        cmdBody.put("action", "change_password");
        cmdBody.put("username", username);
        cmdBody.put("new_password", newPassword);

        NetworkClient.get().sendMessage(new Message(cmdBody, Message.Type.COMMAND));
    }

    public static String randomPasswordGenerator() {
        final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        final String DIGITS = "0123456789";
        final String SPECIAL_CHARACTERS = "?><,\"';:\\/|][}{+=)(*&^%$#!";
        final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(8);

        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));
        for (int i = 4; i < 8; i++) {
            int index = random.nextInt(ALL_CHARACTERS.length());
            password.append(ALL_CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}

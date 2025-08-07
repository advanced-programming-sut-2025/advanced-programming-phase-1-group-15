package com.example.server.controllers;

import com.example.common.RandomGenerator;
import com.example.common.Result;
import com.example.common.User;
import com.example.common.enums.Gender;
import com.example.common.enums.commands.LoginMenuCommands;
import com.example.server.models.ServerApp;

public class ServerLoginController {
    public static Result registerUser(String address, String username, String password, String passwordConfirm, String nickname,
                                      String email, Gender gender) {
        if(!LoginMenuCommands.checkUsernameFormat(username)) {
            return new Result(false, "username format is invalid!");
        }
        else if (ServerApp.checkUsernameExists(username)) {
            return new Result(false, "this username is already taken!\nYou can use "
                + suggestAlternativeUsername(username) + " instead.");
        }
        else if(password.length() < 8) {
            return new Result(false, "password must be at least 8 characters.");
        }
        else if(!LoginMenuCommands.checkPasswordContainsSpecialCharacter(password)) {
            return new Result(false, "password doesn't have any special characters!");
        }
        else if(!password.equals(passwordConfirm)) {
            return new Result(false, "password confirmation doesn't match!");
        }
        else if(!LoginMenuCommands.checkEmailFormat(email)) {
            return new Result(false, "email format is invalid!");
        }
        else if(gender == null) {
            return new Result(false, "choose between boy or girl.");
        }

        User user = new User(username, password, nickname, email, gender);
        ServerApp.users.put(address, user);
        ServerApp.onlineAddresses.add(address);

        return new Result(true, "User registered successfully!");
    }

    public static Result pickQuestion(String username, String question, String answer) {
        User user = ServerApp.getUserByUsername(username);
        user.setSecurityQuestion(question);
        user.setSecurityQuestionAnswer(answer);

        return new Result(true, "Thanks! your answer has been submitted successfully.");
    }

    public static Result loginUser(String address, String username, String password, boolean stayLoggedIn) {
        User user = ServerApp.getUserByUsername(username);
        if(user == null) {
            return new Result(false, "username not found!");
        }
        else if(!user.getPassword().equals(password)) {
            return new Result(false, "password doesn't match!");
        }

        ServerApp.users.put(address, user);
        ServerApp.onlineAddresses.add(address);

        return new Result(true, "User logged in successfully! You are now in main menu.");
    }

    public static Result forgetPassword(String username, String password) {
        User user = ServerApp.getUserByUsername(username);

        if(password.length() < 8) {
            return new Result(false, "password must be at least 8 characters.");
        }
        else if(LoginMenuCommands.checkPasswordContainsWhitespace(password)) {
            return new Result(false, "password should not contain any whitespace.");
        }
        else if(!LoginMenuCommands.checkPasswordContainsSpecialCharacter(password)) {
            return new Result(false, "password doesn't have any special characters!");
        }

        user.setPassword(password);
        return new Result(true, "Your password has been changed successfully!");
    }

    private static String suggestAlternativeUsername(String originalUsername) {
        String newUsername;
        do {
            int randomSuffix = RandomGenerator.getInstance().randomInt(0, 999);
            newUsername = originalUsername + randomSuffix;
        } while (ServerApp.checkUsernameExists(newUsername));

        return newUsername;
    }
}

package com.example.server.controllers;

import com.example.common.Result;
import com.example.common.enums.commands.LoginMenuCommands;
import com.example.common.User;
import com.example.server.models.ServerApp;

public class ProfileMenuController {
    public static Result changeUsername(String username, String newUsername) {
        User user = ServerApp.getUserByUsername(username);

        if(newUsername.equals(user.getUsername())) {
            return new Result(false, "you should choose a new username!");
        }
        else if(!LoginMenuCommands.checkUsernameFormat(newUsername)) {
            return new Result(false, "username format is invalid!");
        }
        else if (ServerApp.checkUsernameExists(newUsername)) {
            return new Result(false, "this username is already taken!");
        }

        user.setUsername(newUsername);
        return new Result(true, "Username successfully changed!");
    }

    public static Result changeNickname(String username, String newNickname) {
        User user = ServerApp.getUserByUsername(username);

        if(newNickname.equals(user.getNickname())) {
            return new Result(false, "you should choose a new nickname!");
        }

        user.setNickname(newNickname);
        return new Result(true, "Nickname successfully changed!");
    }

    public static Result changeEmail(String username, String newEmail) {
        User user = ServerApp.getUserByUsername(username);

        if(newEmail.equals(user.getEmail())) {
            return new Result(false, "you should choose a new email!");
        }
        else if(!LoginMenuCommands.checkEmailFormat(newEmail)) {
            return new Result(false, "email format is invalid!");
        }

        user.setEmail(newEmail);
        return new Result(true, "Email successfully changed!");
    }

    public static Result changePassword(String username, String newPassword) {
        User user = ServerApp.getUserByUsername(username);

        if(newPassword.equals(user.getPassword())) {
            return new Result(false, "you should choose a new password!");
        }
        else if(newPassword.length() < 8) {
            return new Result(false, "password must be at least 8 characters.");
        }
        else if(!LoginMenuCommands.checkPasswordContainsSpecialCharacter(newPassword)) {
            return new Result(false, "password doesn't have any special characters!");
        }

        user.setPassword(newPassword);
        return new Result(true, "Password successfully changed!");
    }
}

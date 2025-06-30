package com.example.controllers;

import com.example.models.App;
import com.example.models.Result;
import com.example.models.enums.commands.LoginMenuCommands;

public class ProfileMenuController {
    public static Result changeUsername(String newUsername) {
        if(newUsername.equals(App.currentUser.getUsername())) {
            return new Result(false, "you should choose a new username!");
        }
        else if(!LoginMenuCommands.checkUsernameFormat(newUsername)) {
            return new Result(false, "username format is invalid!");
        }
        else if (App.checkUsernameExists(newUsername)) {
            return new Result(false, "this username is already taken!");
        }

        App.currentUser.setUsername(newUsername);
        return new Result(true, "Username successfully changed!");
    }

    public static Result changeNickname(String newNickname) {
        if(newNickname.equals(App.currentUser.getNickname())) {
            return new Result(false, "you should choose a new nickname!");
        }

        App.currentUser.setNickname(newNickname);
        return new Result(true, "Nickname successfully changed!");
    }

    public static Result changeEmail(String newEmail) {
        if(newEmail.equals(App.currentUser.getEmail())) {
            return new Result(false, "you should choose a new email!");
        }
        else if(!LoginMenuCommands.checkEmailFormat(newEmail)) {
            return new Result(false, "email format is invalid!");
        }

        App.currentUser.setEmail(newEmail);
        return new Result(true, "Email successfully changed!");
    }

    public static Result changePassword(String newPassword, String oldPassword) {
        if(!oldPassword.equals(App.currentUser.getPassword())) {
            return new Result(false, "first provide your current password correctly.");
        }
        else if(newPassword.equals(App.currentUser.getPassword())) {
            return new Result(false, "you should choose a new password!");
        }
        else if(newPassword.length() < 8) {
            return new Result(false, "password must be at least 8 characters.");
        }
        else if(!LoginMenuCommands.checkPasswordContainsSpecialCharacter(newPassword)) {
            return new Result(false, "password doesn't have any special characters!");
        }

        App.currentUser.setPassword(newPassword);
        return new Result(true, "Password successfully changed!");
    }
}

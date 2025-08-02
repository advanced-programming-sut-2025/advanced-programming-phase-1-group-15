package com.example.server.models;

import com.example.common.Game;
import com.example.common.Player;
import com.example.common.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerApp {
    public static HashMap<String, User> users = new HashMap<>();

    public static ArrayList<Game> allGames = new ArrayList<>();
    public static ArrayList<Game> currentGames = new ArrayList<>();

    public static boolean checkUsernameExists(String username) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static User getUserByUsername(String username) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static User getUserByAddress(String address) {
        return users.get(address);
    }

    public static boolean checkIsInAnotherGame(User user) {
        for (Game game : ServerApp.currentGames) {
            for (Player player : game.getPlayers()) {
                if (player.getUsername().equals(user.getUsername())) {
                    return true;
                }
            }
        }

        return false;
    }
}

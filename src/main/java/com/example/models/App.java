package com.example.models;

import com.example.views.GameMenu;

import java.util.ArrayList;

public class App {
    public static ArrayList<User> users = new ArrayList<>();
    public static User currentUser;

    public static final String[] securityQuestions = {"1) What is your favorite color?",
    "2) Where were you born?", "3) What is your favorite food?",
    "4) Who is your favorite historical figure?", "5) What is your dream job?"};

    public static ArrayList<Game> recentGames = new ArrayList<>();
    public static Game currentGame;
    public static GameMenu currentGameMenu;

    public static boolean checkUsernameExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static boolean checkIsInAnotherGame(User user) {
        return false;
    }
}

package com.example.client.models;

import com.example.client.views.GameView;
import com.example.common.Game;
import com.example.common.User;

import java.util.ArrayList;

public class ClientApp {
    public static User user;

    public static final String[] securityQuestions = {"1) What is your favorite color?",
    "2) Where were you born?", "3) What is your favorite food?",
    "4) Who is your favorite historical figure?", "5) What is your dream job?"};

    public static ArrayList<Game> recentGames = new ArrayList<>();
    public static Game currentGame;
    public static GameView currentGameView;

    public static GameView getCurrentGameMenu() {
        return currentGameView;
    }

    public static void setCurrentGameMenu(GameView currentGameMenu) {
        ClientApp.currentGameView = currentGameMenu;
    }
}

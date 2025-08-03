package com.example.client.models;

import com.example.client.views.GameView;
import com.example.common.Game;
import com.example.common.Lobby;
import com.example.common.User;

import java.util.ArrayList;

public class ClientApp {
    public static User user;
    public static ArrayList<Lobby> lobbies = new ArrayList<>();

    public static final String[] securityQuestions = {"1) What is your favorite color?",
    "2) Where were you born?", "3) What is your favorite food?",
    "4) Who is your favorite historical figure?", "5) What is your dream job?"};

    public static Game currentGame;
    public static GameView currentGameView;

    public static Lobby getUserLobby() {
        if (user.getLobbyId() == null) {
            return null;
        }
        for (Lobby lobby : lobbies) {
            if (lobby.getId().equals(user.getLobbyId())) {
                return lobby;
            }
        }
        return null;
    }

    public static GameView getCurrentGameMenu() {
        return currentGameView;
    }

    public static void setCurrentGameMenu(GameView currentGameMenu) {
        ClientApp.currentGameView = currentGameMenu;
    }
}

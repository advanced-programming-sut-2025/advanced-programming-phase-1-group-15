package com.example.server.models;

import com.example.common.Lobby;
import com.example.common.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServerApp {
    public static HashMap<String, User> users = new HashMap<>();
    public static Set<String> onlineAddresses = ConcurrentHashMap.newKeySet();
    public static ArrayList<Lobby> lobbies = new ArrayList<>();

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

    public static String getAddressByUser(String username) {
        for (Map.Entry<String, User> entry : users.entrySet()) {
            if (entry.getValue().getUsername().equals(username)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Lobby getLobbyById(String lobbyId) {
        for (Lobby lobby : lobbies) {
            if (lobby.getId().equals(lobbyId)) {
                return lobby;
            }
        }
        return null;
    }

    public static Lobby getLobbyByUser(String username) {
        for (Lobby lobby : lobbies) {
            if (lobby.checkIfUserIsInLobby(username)) {
                return lobby;
            }
        }
        return null;
    }
}

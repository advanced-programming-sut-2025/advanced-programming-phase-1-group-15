package com.example.common;

import java.util.*;
import java.util.stream.Collectors;

public class Lobby {
    private final String id;
    private final String name;
    private User admin;
    private final ArrayList<User> users = new ArrayList<>();
    private final HashMap<String, Integer> usernamesAndMaps = new HashMap<>();
    private final boolean isPublic;
    private final String password;
    private final boolean isVisible;

    public Lobby(String id, String name, User admin, boolean isPublic, String password, boolean isVisible) {
        this.id = id;
        this.name = name;
        this.admin = admin; addUser(admin);
        this.isPublic = isPublic;
        this.password = password;
        this.isVisible = isVisible;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getAdmin() {
        return admin;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    public ArrayList<Integer> getMapNumbers() {
        return new ArrayList<>(usernamesAndMaps.values());
    }

    public int getMapNumber(String username) {
        return usernamesAndMaps.get(username);
    }

    public void setMapNumber(String username, int mapNumber) {
        usernamesAndMaps.put(username, mapNumber);
    }

    public String getUsersString() {
        return getUsers().stream()
            .map(User::getUsername)
            .collect(Collectors.joining(", "));
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getPassword() {
        return password;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public int getUsersCount() {
        return usernamesAndMaps.size();
    }

    public boolean checkIfUserIsInLobby(User user) {
        return getUsers().contains(user);
    }

    public void assignMapNumber(String username) {
        ArrayList<Integer> usedNumbers = getMapNumbers();
        for (int candidate = 1; candidate <= 4; candidate++) {
            if (!usedNumbers.contains(candidate)) {
                usernamesAndMaps.put(username, candidate);
                return;
            }
        }
    }

    public void addUser(User user) {
        users.add(user);
        assignMapNumber(user.getUsername());
    }

    public void removeUser(User user) {
        users.remove(user);
        usernamesAndMaps.remove(user.getUsername());

        if (user.equals(admin)) {
            Iterator<User> it = users.iterator();
            if (it.hasNext()) {
                admin = it.next();
            } else {
                admin = null;
            }
        }
    }
}

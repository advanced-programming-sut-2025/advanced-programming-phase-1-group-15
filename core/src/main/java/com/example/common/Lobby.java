package com.example.common;

import java.util.ArrayList;

public class Lobby {
    private final String id;
    private final String name;
    private User admin;
    private final ArrayList<User> users = new ArrayList<>();
    private final boolean isPublic;
    private final String password;
    private final boolean isVisible;

    public Lobby(String id, String name, User admin, boolean isPublic, String password, boolean isVisible) {
        this.id = id;
        this.name = name;
        this.admin = admin; users.add(admin);
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

    public String getUsersString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < users.size(); i++) {
            builder.append(users.get(i).getUsername());
            if (i != users.size() - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
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
        return users.size();
    }

    public boolean checkIfUserIsInLobby(User user) {
        return users.contains(user);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        if(user.equals(admin)) {
            admin = null;
            if(users.size() >= 2) {
                admin = users.get(1);
            }
        }
        users.remove(user);
    }
}

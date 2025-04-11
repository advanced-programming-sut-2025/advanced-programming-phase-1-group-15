package models;

import java.util.ArrayList;

public class App {
    public static ArrayList<User> users = new ArrayList<>();
    public static User currentUser;

    public static ArrayList<Game> recentGames = new ArrayList<>();
    public static Game currentGame;

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

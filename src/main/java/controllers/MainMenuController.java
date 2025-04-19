package controllers;

import models.*;

import java.util.ArrayList;

public class MainMenuController {
    public static Result startGame(String username1, String username2, String username3) {
        ArrayList<User> gameUsers = new ArrayList<>();

        if(username1 != null) {
            User user1 = App.getUserByUsername(username1);
            gameUsers.add(user1);
        }
        if(username2 != null) {
            User user2 = App.getUserByUsername(username2);
            gameUsers.add(user2);
        }
        if(username3 != null) {
            User user3 = App.getUserByUsername(username3);
            gameUsers.add(user3);
        }

        if(username1 == null && username2 == null && username3 == null) {
            return new Result(false, "flag can not be totally empty!");
        }

        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(App.currentUser));

        for (User user : gameUsers) {
            if (user == null) {
                return new Result(false, "make sure all usernames are valid.");
            }
            else if (user.equals(App.currentUser)) {
                return new Result(false, "make sure not to use usernames more than once.");
            }

            for (Game game : App.recentGames) {
                for (Player player : game.getPlayers()) {
                    if (player.getUsername().equals(user.getUsername())) {
                        return new Result(false, player.getUsername() + " is already in another game!");
                    }
                }
            }

            players.add(new Player(user));
        }

        Game game = new Game(players);
        App.currentGame = game;
        App.recentGames.add(game);

        return new Result(true, "Game started!");
    }

    public static Result loadGame() {
        for (Game game : App.recentGames) {
            for (Player player : game.getPlayers()) {
                if (player.getUsername().equals(App.currentUser.getUsername())) {
                    App.currentGame = game;
                    return new Result(true, "game is now loaded successfully!");
                }
            }
        }

        return new Result(false, "no game loaded!");
    }
}

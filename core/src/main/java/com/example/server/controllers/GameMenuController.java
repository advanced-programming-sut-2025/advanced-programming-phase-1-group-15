package com.example.server.controllers;

import com.example.common.Game;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.User;
import com.example.server.models.ServerApp;

import java.util.ArrayList;

public class GameMenuController {
    public static Result startGame(String username0, String username1, String username2, String username3) {
        ArrayList<User> gameUsers = new ArrayList<>();

        User user0 = ServerApp.getUserByUsername(username0);
        if(username1 != null) {
            User user1 = ServerApp.getUserByUsername(username1);
            gameUsers.add(user1);
        }
        if(username2 != null) {
            User user2 = ServerApp.getUserByUsername(username2);
            gameUsers.add(user2);
        }
        if(username3 != null) {
            User user3 = ServerApp.getUserByUsername(username3);
            gameUsers.add(user3);
        }

        if(username1 == null && username2 == null && username3 == null) {
            return new Result(false, "flag can not be totally empty!");
        }

        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(user0));

        for (User user : gameUsers) {
            if (user == null) {
                return new Result(false, "make sure all usernames are valid.");
            }
            else if (user.equals(user0)) {
                return new Result(false, "make sure not to use usernames more than once.");
            }
            else if (ServerApp.checkIsInAnotherGame(user)) {
                return new Result(false, user.getUsername() + " is already in another game!");
            }

            players.add(new Player(user));
        }

        Game game = new Game(players);
        game.setMainPlayer(players.get(0));
        ServerApp.currentGames.add(game);
        ServerApp.allGames.add(game);

        return new Result(true, "Game started!");
    }

    public static Result loadGame() {
//        for (Game game : ServerApp.currentGames) {
//            for (Player player : game.getPlayers()) {
//                if (player.getUsername().equals(ServerApp.currentUser.getUsername())) {
//                    ServerApp.currentGame = game;
//                    game.setMainPlayer(player);
//                    return new Result(true, "game loaded successfully!");
//                }
//            }
//        }

        return new Result(false, "you are not part of any game!");
    }
}

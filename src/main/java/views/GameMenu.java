package views;

import models.App;
import models.Game;
import models.Player;
import models.Result;
import models.enums.commands.Commands;
import models.enums.commands.GameMenuCommands;

import java.util.Scanner;

public class GameMenu implements AppMenu {
    private final Game game;

    public GameMenu(Game game) {
        this.game = game;
        System.out.println("\"" + game.getCurrentPlayer().getUsername() + "\" it's your turn to begin the game.");
    }

    public Result terminateMenu(Scanner scanner) {
        for(Player player : game.getPlayers()) {
            System.out.println(player.getUsername() + ", do you agree with total game termination? (+/-)");
            String command = scanner.nextLine().trim();

            if(command.equalsIgnoreCase("-")) {
                return new Result(false, "All players must agree to completely terminate the game.");
            }
            else if(!command.equalsIgnoreCase("+")) {
                return new Result(false, "invalid command");
            }
        }

        return new Result(true, "Game terminated successfully.");
    }

    public void run(Scanner scanner) {
        Player currentPlayer = game.getCurrentPlayer();
        String command = scanner.nextLine().trim();

        if(GameMenuCommands.NEXT_TURN_REGEX.matches(command)) {
            game.nextTurn();
            System.out.println("\"" + game.getCurrentPlayer().getUsername() + "\" go on!");
            return;
        }

        if(!currentPlayer.isLocked()) {
            if(Commands.checkShowCurrentMenuRegex(command)) {
                System.out.println("game menu");
            }

            else if(GameMenuCommands.EXIT_GAME_REGEX.matches(command)) {
                if(!currentPlayer.equals(game.getMainPlayer())) {
                    System.out.println("Only the creator can exit the game.");
                }
                else {
                    App.currentGame = null;
                    AppView.currentMenu = new MainMenu();
                    System.out.println("game exited successfully!");
                }
            }

            else if(GameMenuCommands.TERMINATE_GAME_REGEX.matches(command)) {
                Result terminate = terminateMenu(scanner);
                System.out.println(terminate.message());

                if(terminate.success()) {
                    App.recentGames.remove(game);
                    App.currentGame = null;
                    AppView.currentMenu = new MainMenu();
                }
            }

            else {
                System.out.println("invalid command");
            }
        }
        else {
            System.out.println("You are locked! (maximum energy per turn consumed). Use \"next turn\" command to continue.");
        }
    }
}

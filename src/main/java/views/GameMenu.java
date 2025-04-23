package views;

import controllers.CheatCodeController;
import models.App;
import models.Game;
import models.Player;
import models.Result;
import models.enums.commands.CheatCodeCommands;
import models.enums.commands.Commands;
import models.enums.commands.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    private final Game game;

    public GameMenu(Game game) {
        this.game = game;
        game.build();
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

            else if(command.equals("time")) {
                System.out.println(game.getDateAndTime().displayHour());
            }
            else if(command.equals("date")) {
                System.out.println(game.getDateAndTime().displayDate());
            }
            else if(command.equals("datetime")) {
                System.out.println(game.getDateAndTime().displayDateTime());
            }
            else if(command.equals("season")) {
                System.out.println(game.getDateAndTime().displaySeason());
            }
            else if(GameMenuCommands.DAY_OF_WEEK_REGEX.matches(command)) {
                System.out.println(game.getDateAndTime().displayDayOfWeek());
            }
            else if(CheatCodeCommands.ADVANCE_TIME_REGEX.matches(command)) {
                Matcher matcher = CheatCodeCommands.ADVANCE_TIME_REGEX.matcher(command);

                int hours = matcher.matches() ? Integer.parseInt(matcher.group("hours")) : 0;

                Result result = CheatCodeController.cheatAdvanceTime(hours);
                System.out.println(result.message());
            }
            else if(CheatCodeCommands.ADVANCE_DATE_REGEX.matches(command)) {
                Matcher matcher = CheatCodeCommands.ADVANCE_DATE_REGEX.matcher(command);

                int days = matcher.matches() ? Integer.parseInt(matcher.group("days")) : 0;

                Result result = CheatCodeController.cheatAdvanceDate(days);
                System.out.println(result.message());
            }

            else if(command.equals("weather")) {
                System.out.println(game.getWeather().displayWeather());
            }
            else if(GameMenuCommands.WEATHER_FORECAST_REGEX.matches(command)) {
                System.out.println(game.getWeather().displayForecast());
            }
            else if(CheatCodeCommands.WEATHER_SET_REGEX.matches(command)) {
                Matcher matcher = CheatCodeCommands.WEATHER_SET_REGEX.matcher(command);

                String weatherType = matcher.matches() ? matcher.group("weatherType") : "";

                Result result = CheatCodeController.cheatSetWeather(weatherType);
                System.out.println(result.message());
            }

            else if(GameMenuCommands.PRINT_MAP_REGEX.matches(command)) {
                game.getMap().printMap();
            }
            else if(GameMenuCommands.MAP_GUIDE_REGEX.matches(command)) {
                game.getMap().mapGuide();
            }

            else if(GameMenuCommands.SHOW_ENERGY_REGEX.matches(command)) {
                System.out.println(game.getCurrentPlayer().getEnergy());
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

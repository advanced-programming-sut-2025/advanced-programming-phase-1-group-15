package views;

import controllers.MainMenuController;
import models.App;
import models.Player;
import models.Result;
import models.enums.commands.Commands;
import models.enums.commands.MainMenuCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu {
    public static void mapMenu(Scanner scanner) {
        ArrayList<Integer> availableNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4));

        for(Player player : App.currentGame.getPlayers()) {
            while(true) {
                System.out.println("Choosing map for " + player.getUsername() +  ": ");
                String command = scanner.nextLine().trim();

                if(MainMenuCommands.CHOOSE_MAP_REGEX.matches(command)) {
                    Matcher matcher = MainMenuCommands.CHOOSE_MAP_REGEX.matcher(command);
                    int mapNumber = matcher.matches() ? Integer.parseInt(matcher.group("mapNumber")) : 0;

                    if(availableNumbers.contains(mapNumber)) {
                        player.setMapNumber(mapNumber);
                        availableNumbers.remove(Integer.valueOf(mapNumber));
                        break;
                    }
                    else {
                        System.out.println("Invalid map number. you have to choose between " + availableNumbers);
                    }
                }
                else {
                    System.out.println("invalid command");
                }
            }
        }
    }

    public void run(Scanner scanner) {
        String command = scanner.nextLine().trim();

        if(Commands.checkShowCurrentMenuRegex(command)) {
            System.out.println("main menu");
        }

        else if(MainMenuCommands.LOGOUT_REGEX.matches(command)) {
            App.currentUser = null;
            AppView.currentMenu = new LoginMenu();
            System.out.println("User logged out successfully.");
        }

        else if(Commands.checkSwitchRegex(command)) {
            String menuName = Commands.menuName(command);
            if(menuName.equals("profile menu")) {
                AppView.currentMenu = new ProfileMenu();
                System.out.println("You are now in profile menu!");
            }
            else {
                System.out.println("invalid menu name!");
            }
        }

        else if(MainMenuCommands.NEW_GAME_REGEX.matches(command)) {
            Matcher matcher = MainMenuCommands.NEW_GAME_REGEX.matcher(command);

            String username1 = matcher.matches() ? matcher.group("username1") : null;
            String username2 = matcher.group("username2");
            String username3 = matcher.group("username3");

            Result result = MainMenuController.startGame(username1, username2, username3);
            System.out.println(result.message());

            if(result.success()) {
                mapMenu(scanner);
                AppView.currentMenu = new GameMenu(App.currentGame);
            }
        }

        else if(MainMenuCommands.LOAD_GAME_REGEX.matches(command)) {
            Result result = MainMenuController.loadGame();
            System.out.println(result.message());

            if(result.success()) {
                AppView.currentMenu = new GameMenu(App.currentGame);
            }
        }

        else {
            System.out.println("invalid command");
        }
    }
}

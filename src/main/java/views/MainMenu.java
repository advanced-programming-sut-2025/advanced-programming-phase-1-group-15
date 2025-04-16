package views;

import models.App;
import models.enums.commands.Commands;
import models.enums.commands.MainMenuCommands;

import java.util.Scanner;

public class MainMenu implements AppMenu {
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

        else {
            System.out.println("invalid command");
        }
    }
}

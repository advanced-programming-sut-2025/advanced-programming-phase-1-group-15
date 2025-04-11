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
        }

        else if(Commands.checkSwitchRegex(command)) {
            String menuName = Commands.menuName(command);
            if(menuName.equals("profile menu")) {
                AppView.currentMenu = new ProfileMenu();
            }
        }

        else {
            System.out.println("invalid command");
        }
    }
}

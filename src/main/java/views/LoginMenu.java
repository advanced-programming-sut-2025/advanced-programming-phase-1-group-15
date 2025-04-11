package views;

import models.enums.commands.Commands;

import java.util.Scanner;

public class LoginMenu implements AppMenu {
    public void run(Scanner scanner) {
        String command = scanner.nextLine().trim();

        if(Commands.checkShowCurrentMenuRegex(command)) {
            System.out.println("login/register menu");
        }

        else if(Commands.checkExitRegex(command)) {
            AppView.exit = true;
        }

        else if(Commands.checkSwitchRegex(command)) {
            String menuName = Commands.menuName(command);
            AppView.changeMenu(menuName);
            System.out.println("You are now in " + menuName + "!");
        }

        else {
            System.out.println("invalid command");
        }
    }
}

package views;

import models.App;
import models.enums.commands.Commands;
import models.enums.commands.ProfileMenuCommands;

import java.util.Scanner;

public class ProfileMenu implements AppMenu {
    public void run(Scanner scanner) {
        String command = scanner.nextLine().trim();

        if(Commands.checkShowCurrentMenuRegex(command)) {
            System.out.println("profile menu");
        }

        else if(ProfileMenuCommands.USER_INFO_REGEX.matches(command)) {
            System.out.println("Your profile: ");
            System.out.println("-------------------------");
            System.out.println(App.currentUser);
        }

        else {
            System.out.println("invalid command");
        }
    }
}

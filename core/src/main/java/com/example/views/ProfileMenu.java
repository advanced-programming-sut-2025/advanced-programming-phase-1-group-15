package com.example.views;

import com.example.controllers.ProfileMenuController;
import com.example.models.App;
import com.example.models.Result;
import com.example.models.enums.commands.Commands;
import com.example.models.enums.commands.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    public void run(Scanner scanner) {
        String command = scanner.nextLine().trim();

        if(Commands.checkShowCurrentMenuRegex(command)) {
            System.out.println("profile menu");
        }

        else if(Commands.checkSwitchRegex(command)) {
            String menuName = Commands.menuName(command);
            if(menuName.equals("main menu")) {
                AppView.currentMenu = new MainMenu();
                System.out.println("You are now back in main menu!");
            }
            else {
                System.out.println("invalid menu name!");
            }
        }

        else if(ProfileMenuCommands.CHANGE_USERNAME_REGEX.matches(command)) {
            Matcher matcher = ProfileMenuCommands.CHANGE_USERNAME_REGEX.matcher(command);

            String newUsername = matcher.matches() ? matcher.group("username") : null;

            Result result = ProfileMenuController.changeUsername(newUsername);
            System.out.println(result.message());
        }

        else if(ProfileMenuCommands.CHANGE_NICKNAME_REGEX.matches(command)) {
            Matcher matcher = ProfileMenuCommands.CHANGE_NICKNAME_REGEX.matcher(command);

            String newNickname = matcher.matches() ? matcher.group("nickname") : null;

            Result result = ProfileMenuController.changeNickname(newNickname);
            System.out.println(result.message());
        }

        else if(ProfileMenuCommands.CHANGE_EMAIL_REGEX.matches(command)) {
            Matcher matcher = ProfileMenuCommands.CHANGE_EMAIL_REGEX.matcher(command);

            String newEmail = matcher.matches() ? matcher.group("email") : null;

            Result result = ProfileMenuController.changeEmail(newEmail);
            System.out.println(result.message());
        }

        else if(ProfileMenuCommands.CHANGE_PASSWORD_REGEX.matches(command)) {
            Matcher matcher = ProfileMenuCommands.CHANGE_PASSWORD_REGEX.matcher(command);

            String newPassword = matcher.matches() ? matcher.group("newPassword") : null;
            String oldPassword = matcher.group("oldPassword");

            Result result = ProfileMenuController.changePassword(newPassword, oldPassword);
            System.out.println(result.message());
        }

        else if(ProfileMenuCommands.USER_INFO_REGEX.matches(command)) {
            System.out.println("Your profile: ");
            System.out.println("--------------------");
            System.out.println(App.currentUser);
        }

        else {
            System.out.println("invalid command");
        }
    }
}

package views;

import controllers.LoginMenuController;
import models.App;
import models.Result;
import models.enums.Gender;
import models.enums.commands.Commands;
import models.enums.commands.LoginMenuCommands;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    public static String usernameMenu(Scanner scanner, String username) {
        String newUsername;
        do {
            int randomSuffix = new Random().nextInt(1000);
            newUsername = username + randomSuffix;
        } while (App.checkUsernameExists(newUsername));

        System.out.println("Do you want to use " + newUsername + " instead? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            return newUsername;
        }

        return null;
    }

    public static String passwordMenu(Scanner scanner) {
        System.out.println("Do you want to generate a random strong password instead? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            String password = LoginMenuController.randomPasswordGenerator();

            System.out.println("Your new password would be: " + password);
            return password;
        }

        return null;
    }

    public static void securityQuestionMenu(Scanner scanner) {
        System.out.println("Please choose and answer one of the below security questions for further authentication: ");
        for(String question : App.securityQuestions) {
            System.out.println(question);
        }

        Result result = new Result(false, "");
        while(!result.success()) {
            String command = scanner.nextLine().trim();
            if(LoginMenuCommands.PICK_QUESTION_REGEX.matches(command)) {
                Matcher matcher = LoginMenuCommands.PICK_QUESTION_REGEX.matcher(command);
                int questionNumber = matcher.matches() ? Integer.parseInt(matcher.group("questionNumber")) : 0;
                String answer = matcher.group("answer");
                String answerConfirm = matcher.group("answerConfirm");

                result = LoginMenuController.pickQuestion(questionNumber, answer, answerConfirm);
                System.out.println(result.message());
            }
            else {
                System.out.println("invalid command");
            }
        }
    }

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

        else if(LoginMenuCommands.REGISTER_REGEX.matches(command)) {
            Matcher matcher = LoginMenuCommands.REGISTER_REGEX.matcher(command);

            String username = matcher.matches() ? matcher.group("username") : null;
            String password = matcher.group("password");
            String passwordConfirm = matcher.group("passwordConfirm");
            String nickname = matcher.group("nickname");
            String email = matcher.group("email");
            Gender gender = Gender.fromString(matcher.group("gender"));

            Result result = LoginMenuController.registerUser(username, password, passwordConfirm, nickname, email, gender);
            System.out.println(result.message());

            if(!result.success()) {
                if(result.message().equals("this username is already taken!")) {
                    username = usernameMenu(scanner, username);
                    if(username != null) {
                        result = LoginMenuController.registerUser(username, password, passwordConfirm, nickname, email, gender);
                        System.out.println(result.message());

                        if(result.success()) {
                            securityQuestionMenu(scanner);
                        }
                    }
                }
                else if(result.message().contains("characters")) {
                    password = passwordMenu(scanner);
                    if(password != null) {
                        result = LoginMenuController.registerUser(username, password, password, nickname, email, gender);
                        System.out.println(result.message());

                        if(result.success()) {
                            securityQuestionMenu(scanner);
                        }
                    }
                }
            }
            else {
                securityQuestionMenu(scanner);
            }
        }

        else if(LoginMenuCommands.LOGIN_REGEX.matches(command)) {
            Matcher matcher = LoginMenuCommands.LOGIN_REGEX.matcher(command);

            String username = matcher.matches() ? matcher.group("username") : null;
            String password = matcher.group("password");

            Result result = LoginMenuController.loginUser(username, password, false);
            System.out.println(result.message());
        }

        else if(LoginMenuCommands.FORGET_PASSWORD_REGEX.matches(command)) {
            Matcher matcher = LoginMenuCommands.FORGET_PASSWORD_REGEX.matcher(command);

            String username = matcher.matches() ? matcher.group("username") : null;
        }

        else {
            System.out.println("invalid command");
        }
    }
}

package com.example.views;

import com.example.controllers.LoginMenuController;
import com.example.models.App;
import com.example.models.RandomGenerator;
import com.example.models.Result;
import com.example.models.User;
import com.example.models.enums.Gender;
import com.example.models.enums.commands.Commands;
import com.example.models.enums.commands.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    public static String usernameMenu(Scanner scanner, String username) {
        String newUsername;
        do {
            int randomSuffix = RandomGenerator.getInstance().randomInt(0,999);
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

    public static void forgetPasswordMenu(Scanner scanner, String username) {
        User user = App.getUserByUsername(username);
        if(user == null) {
            System.out.println("username not found!");
        }
        else {
            System.out.println(user.getSecurityQuestion());
            String answer = scanner.nextLine().trim();

            if(answer.equals(user.getSecurityQuestionAnswer())) {
                Result result = new Result(false, "");
                while(!result.success()) {
                    System.out.println("Please choose your new password: ");
                    String password = scanner.nextLine().trim();

                    result = LoginMenuController.forgetPassword(password, user);
                    System.out.println(result.message());

                    if(!result.success()) {
                        password = passwordMenu(scanner);
                        if(password != null) {
                            result = LoginMenuController.forgetPassword(password, user);
                            System.out.println(result.message());
                        }
                    }
                }
            }
            else {
                System.out.println("wrong answer! you may try again.");
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
            System.out.println("You can't switch to other menus when you are in register menu!");
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

            forgetPasswordMenu(scanner, username);
        }

        else {
            System.out.println("invalid command");
        }
    }
}

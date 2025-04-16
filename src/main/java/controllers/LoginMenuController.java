package controllers;

import models.App;
import models.Result;
import models.User;
import models.enums.Gender;
import models.enums.commands.LoginMenuCommands;
import views.AppView;
import views.MainMenu;

import java.security.SecureRandom;

public class LoginMenuController {
    public static Result registerUser(String username, String password, String passwordConfirm, String nickname, String email, Gender gender) {
        if(!LoginMenuCommands.checkUsernameFormat(username)) {
            return new Result(false, "username format is invalid!");
        }
        else if (App.checkUsernameExists(username)) {
            return new Result(false, "this username is already taken!");
        }
        else if(password.length() < 8) {
            return new Result(false, "password must be at least 8 characters.");
        }
        else if(!LoginMenuCommands.checkPasswordContainsSpecialCharacter(password)) {
            return new Result(false, "password doesn't have any special characters!");
        }
        else if(!password.equals(passwordConfirm)) {
            return new Result(false, "password confirmation doesn't match!");
        }
        else if(!LoginMenuCommands.checkEmailFormat(email)) {
            return new Result(false, "email format is invalid!");
        }
        else if(gender == null) {
            return new Result(false, "choose between boy or girl.");
        }

        User user = new User(username, password, nickname, email, gender);
        App.users.add(user);
        App.currentUser = user;

        return new Result(true, "User registered successfully!");
    }

    public static String randomPasswordGenerator() {
        final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        final String DIGITS = "0123456789";
        final String SPECIAL_CHARACTERS = "?><,\"';:\\/|][}{+=)(*&^%$#!";
        final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(8);

        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));
        for (int i = 4; i < 8; i++) {
            int index = random.nextInt(ALL_CHARACTERS.length());
            password.append(ALL_CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    public static Result pickQuestion(int questionNumber, String answer, String answerConfirm) {
        if(questionNumber > 5 || questionNumber < 1) {
            return new Result(false, "invalid question number!");
        }
        else if(!answer.equals(answerConfirm)) {
            return new Result(false, "answer confirmation doesn't match!");
        }

        App.currentUser.setSecurityQuestion(App.securityQuestions[questionNumber - 1]);
        App.currentUser.setSecurityQuestionAnswer(answer);
        return new Result(true, "Thanks! your answer has been submitted successfully.");
    }

    public static Result loginUser(String username, String password, boolean stayLoggedIn) {
        User user = App.getUserByUsername(username);
        if(user == null) {
            return new Result(false, "username not found!");
        }
        else if(!password.equals(user.getPassword())) {
            return new Result(false, "password doesn't match!");
        }

        App.currentUser = user;
        AppView.currentMenu = new MainMenu();
        return new Result(true, "User logged in successfully! You are now in main menu.");
    }

    public static Result forgetPassword(String password, User user) {
        if(password.length() < 8) {
            return new Result(false, "password must be at least 8 characters.");
        }
        else if(LoginMenuCommands.checkPasswordContainsWhitespace(password)) {
            return new Result(false, "password should not contain any whitespace.");
        }
        else if(!LoginMenuCommands.checkPasswordContainsSpecialCharacter(password)) {
            return new Result(false, "password doesn't have any special characters!");
        }

        user.setPassword(password);
        return new Result(true, "Your password has been changed successfully!");
    }
}

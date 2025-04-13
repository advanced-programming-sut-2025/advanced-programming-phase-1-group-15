package controllers;

import models.App;
import models.Result;
import models.User;
import models.enums.Gender;
import models.enums.commands.LoginMenuCommands;

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

        return new Result(true, "user registered successfully!");
    }

    public static String randomPasswordGenerator() {
        final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        final String DIGITS = "0123456789";
        final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
        final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(ALL_CHARACTERS.length());
            password.append(ALL_CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    public static Result pickQuestion(int questionNumber, String answer, String confirmedAnswer) {
        return null;
    }

    public static Result loginUser(String username, String password, boolean stayLoggedIn) {
        return null;
    }

    public static Result forgetPassword(String username) {
        return null;
    }
}

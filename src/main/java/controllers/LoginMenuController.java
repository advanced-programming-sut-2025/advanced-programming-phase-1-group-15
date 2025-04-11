package controllers;

import models.App;
import models.Result;
import models.User;
import models.enums.Gender;

public class LoginMenuController {
    public static Result registerUser(String username, String password, String nickname, String email, Gender gender) {
        User user = new User(username, password, nickname, email, gender);
        App.users.add(user);

        return null;
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

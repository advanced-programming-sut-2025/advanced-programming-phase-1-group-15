package models.enums.commands;

public enum LoginMenuCommands implements Commands {
    REGISTER_REGEX("^\\s*register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+(?<passwordConfirm>\\S+)\\s+-n\\s+(?<nickname>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)\\s*$"),
    PICK_QUESTION_REGEX("^\\s*pick\\s+question\\s+-q\\s+(?<questionNumber>\\d+)\\s+-a\\s+(?<answer>.+?)\\s+-c\\s+(?<answerConfirm>.+?)\\s*$"),
    LOGIN_REGEX("^\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s*(–stay-logged-in)?\\s*$"),
    FORGET_PASSWORD_REGEX("^\\s*forget\\s+password\\s+-u\\s+(?<username>\\S+)\\s*$"),
    ANSWER_REGEX("^\\s*answer\\s+-a\\s+(?<answer>.+?)\\s*$");

    private final String regex;
    LoginMenuCommands(String regex) { this.regex = regex; }

    @Override
    public String getRegex() {
        return regex;
    }

    public static boolean checkUsernameFormat(String username) {
        String usernameRegex = "^[a-zA-Z0-9-]+$";
        return username.matches(usernameRegex);
    }

    public static boolean checkPasswordContainsSpecialCharacter(String password) {
        String specialCharacters = "?><,\"';:\\/|][}{+=)(*&^%$#!";
        for(char c : password.toCharArray()) {
            if(specialCharacters.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkPasswordContainsWhitespace(String password) {
        for(char c : password.toCharArray()) {
            if(Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkEmailFormat(String email) {
        String emailRegex = "^(?!.*\\.\\.)[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]{2,})+$";

        String specialCharacters = "?><,\"';:\\/|][}{+=)(*&^%$#!";
        for(char c : email.toCharArray()) {
            if(specialCharacters.indexOf(c) != -1) {
                return false;
            }
        }

        return email.matches(emailRegex);
    }
}
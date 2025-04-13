package models.enums.commands;

public enum LoginMenuCommands implements Commands {
    REGISTER_REGEX("^\\s*register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+(?<passwordConfirm>\\S+)\\s+-n\\s+(?<nickname>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)\\s*$"),
    LOGIN_REGEX("login -u <username> -p <password> –stay-logged-in"),
    FORGET_PASSWORD_REGEX("forget password -u <username>"),
    ANSWER_REGEX("answer -a <answer>");

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
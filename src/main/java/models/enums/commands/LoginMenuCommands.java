package models.enums.commands;

public enum LoginMenuCommands implements Commands {
    REGISTER_REGEX("register -u <username> -p <password> <password_confirm> -n <nickname> -e <email> -g <gender>"),
    LOGIN_REGEX("login -u <username> -p <password> –stay-logged-in"),
    FORGET_PASSWORD_REGEX("forget password -u <username>"),
    ANSWER_REGEX("answer -a <answer>");

    private final String regex;
    LoginMenuCommands(String regex) { this.regex = regex; }

    @Override
    public String getRegex() {
        return regex;
    }
}
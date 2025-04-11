package models.enums.commands;

public enum MainMenuCommands implements Commands {
    LOGOUT_REGEX("user logout");

    private final String regex;
    MainMenuCommands(String regex) { this.regex = regex; }

    @Override
    public String getRegex() {
        return regex;
    }
}

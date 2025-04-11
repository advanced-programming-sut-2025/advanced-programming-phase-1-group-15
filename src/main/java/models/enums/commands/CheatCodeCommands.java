package models.enums.commands;

public enum CheatCodeCommands implements Commands {
    CHANGE_USERNAME_REGEX("change username -u <username>"),;

    private final String regex;
    CheatCodeCommands(String regex) { this.regex = regex; }

    @Override
    public String getRegex() {
        return regex;
    }
}

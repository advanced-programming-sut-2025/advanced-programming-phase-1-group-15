package models.enums.commands;

public enum CheatCodeCommands implements Commands {
    ADVANCE_TIME_REGEX("^\\s*cheat\\s+advance\\s+time\\s+(?<hours>-?\\d+)h\\s*$"),
    ADVANCE_DATE_REGEX("^\\s*cheat\\s+advance\\s+date\\s+(?<days>-?\\d+)d\\s*$"),
    CHANGE_USERNAME_REGEX("change username -u <username>"),;

    private final String regex;
    CheatCodeCommands(String regex) { this.regex = regex; }

    @Override
    public String getRegex() {
        return regex;
    }
}

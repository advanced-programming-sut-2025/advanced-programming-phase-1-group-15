package models.enums.commands;

public enum MainMenuCommands implements Commands {
    LOGOUT_REGEX("^\\s*user\\s+logout\\s*$"),
    NEW_GAME_REGEX("^\\s*game\\s+new\\s+-u(\\s+(?<username1>\\S+))?(\\s+(?<username2>\\S+))?(\\s+(?<username3>\\S+))?\\s*$"),
    CHOOSE_MAP_REGEX("^\\s*game\\s+map\\s+(?<mapNumber>\\d+)\\s*$"),
    LOAD_GAME_REGEX("^\\s*load\\s+game\\s*$");

    private final String regex;
    MainMenuCommands(String regex) { this.regex = regex; }

    @Override
    public String getRegex() {
        return regex;
    }
}

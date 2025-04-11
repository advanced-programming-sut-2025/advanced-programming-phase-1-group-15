package models.enums.commands;

public enum TradeMenuCommands implements Commands {
    CHANGE_USERNAME_REGEX("change username -u <username>"),;

    private final String regex;
    TradeMenuCommands(String regex) { this.regex = regex; }

    @Override
    public String getRegex() {
        return regex;
    }
}

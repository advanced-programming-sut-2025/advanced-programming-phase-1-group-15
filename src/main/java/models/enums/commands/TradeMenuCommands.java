package models.enums.commands;

public enum TradeMenuCommands implements Commands {
    CHANGE_USERNAME_REGEX("\\s*change\\s+username\\s+-u\\s*(?<username>.+)\\s*"),
    TRADE_ITEM("^\\s*trade\\s+-u\\s+(?<username>.+)\\s+-t\\s+(?<type>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\d+)" +
            "\\[-ti\\s+(?<targetItem>.+)\\s+" +
            "-ta\\s+(?<targetAmount>\\d+)\\]\\s*$"),
    TRADE_MONEY("^\\s*trade\\s+-u\\s+(?<username>.+)\\s+-t\\s+(?<type>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\d+)" +
            "\\[-p\\s+(?<price>\\d+)\\]\\s*$"),
    TRADE_LIST("^trade\\s+list$"),
    TRADE_RESPONSE("^trade\\s+response\\s+(?<response>\\S+)\\s+-i\\s+(?<id>\\d+)$"),
    SHOW_TRADE_HISTORY("^trade\\s+history$");
    private final String regex;
    TradeMenuCommands(String regex) { this.regex = regex; }

    @Override
    public String getRegex() {
        return regex;
    }
}

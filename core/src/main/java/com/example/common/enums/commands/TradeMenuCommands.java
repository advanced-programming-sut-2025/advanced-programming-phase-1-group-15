package com.example.common.enums.commands;

public enum TradeMenuCommands implements Commands {
    CHANGE_USERNAME_REGEX("\\s*change\\s+username\\s+-u\\s*(?<username>.+)\\s*"),
    TRADE_ITEM("^trade\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s+" +
            "\\[-ti\\s+(?<targetItem>\\S+)\\s+" +
            "-ta\\s+(?<targetAmount>\\d+)\\]$"),
    TRADE_MONEY("^trade\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s+" +
            "\\[-p\\s+(?<price>\\d+)\\]$"),
    TRADE_LIST("^trade\\s+list$"),
    TRADE_RESPONSE("^trade\\s+response\\s+(?<response>\\S+)\\s+-i\\s+(?<id>\\d+)$"),
    SHOW_TRADE_HISTORY("^trade\\s+history$"),
    BACK("^back$");
    private final String regex;
    TradeMenuCommands(String regex) { this.regex = regex; }

    @Override
    public String getRegex() {
        return regex;
    }
}

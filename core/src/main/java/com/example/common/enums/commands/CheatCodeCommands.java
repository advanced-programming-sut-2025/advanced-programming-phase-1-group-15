package com.example.common.enums.commands;

public enum CheatCodeCommands implements Commands {
    ADVANCE_TIME_REGEX("^\\s*cheat\\s+advance\\s+time\\s+(?<hours>-?\\d+)h\\s*$"),
    ADVANCE_DATE_REGEX("^\\s*cheat\\s+advance\\s+date\\s+(?<days>-?\\d+)d\\s*$"),
    WEATHER_SET_REGEX("^\\s*cheat\\s+weather\\s+set\\s+(?<weatherType>\\S+)\\s*$"),
    ENERGY_SET_REGEX("^\\s*energy\\s+set\\s+-v\\s+(?<value>\\d+)\\s*$"),
    ENERGY_UNLIMITED_REGEX("^\\s*energy\\s+unlimited\\s*$"),
    ANIMAL_FRIENDSHIP_REGEX("^\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<name>.+?)\\s+-c\\s+(?<amount>\\d+)\\s*$"),
    ADD_GOLD_REGEX("^\\s*cheat\\s+add\\s+(?<amount>\\d+)\\s+gold\\s*$"),
    SET_FRIENDSHIP_REGEX("^\\s*cheat\\s+set\\s+friendship\\s+-u\\s+(?<username>\\S+)\\s+-l\\s+(?<level>\\d+)\\s*$"),
    CHANGE_USERNAME_REGEX("change username -u <username>"),
    ADD_RECIPE("^add\\s+recipe\\s+(?<name>\\S+)$"),
    ADD_ITEMS("^add\\s+item\\s+(?<name>\\S+)\\s+(?<count>\\d+)$"),
    CHEAT_THOR("\\s*cheat\\s+[tT]hor\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*$"),
    TELEPORT_CHEAT("\\s*teleport\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*$"),
    SEED("^\\s*plant\\s+-s\\s+(?<seed>.+\\S)\\s+-d\\s+(?<dx>-1|0|1)\\s+(?<dy>-1|0|1)\\s*$");

    private final String regex;
    CheatCodeCommands(String regex) { this.regex = regex; }

    @Override
    public String getRegex() {
        return regex;
    }
}

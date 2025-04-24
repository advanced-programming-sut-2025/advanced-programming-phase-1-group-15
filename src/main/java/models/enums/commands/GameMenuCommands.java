package models.enums.commands;

public enum GameMenuCommands implements Commands {
    EXIT_GAME_REGEX("^\\s*exit\\s+game\\s*$"),
    TERMINATE_GAME_REGEX("^\\s*terminate\\s+game\\s*$"),
    NEXT_TURN_REGEX("^\\s*next\\s+turn\\s*$"),
    DAY_OF_WEEK_REGEX("^\\s*day\\s+of\\s+the\\s+week\\s*$"),
    WEATHER_FORECAST_REGEX("^\\s*weather\\s+forecast\\s*$"),
    PRINT_MAP_REGEX("^\\s*print\\s+map\\s*$"),
    MAP_GUIDE_REGEX("^\\s*help\\s+reading\\s+map\\s*$"),
    SHOW_ENERGY_REGEX("^\\s*energy\\s+show\\s*$"),
    WALK_REGEX("^\\s*walk\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*$"),
    INVENTORY_SHOW_REGEX("^\\s*inventory\\s+show\\s*$"),
    BUILD_BUILDING_REGEX("build -a <building_name> -l <x , y>"),
    BUY_ANIMAL_REGEX("buy animal -a <animal> -n <name>"),
    PET_ANIMAL_REGEX("pet -n <name>"),
    SHOW_ANIMALS_REGEX("animals");

    private final String regex;
    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRegex() {
        return regex;
    }
}

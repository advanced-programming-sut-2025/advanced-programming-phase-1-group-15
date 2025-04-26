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
    INVENTORY_TRASH_REGEX("^\\s*inventory\\s+trash\\s+-i\\s+(?<itemName>.+?)(\\s+-n\\s+(?<count>\\d+))?\\s*$"),
    EQUIP_TOOL_REGEX("^\\s*tools\\s+equip\\s+(?<toolName>.+?)\\s*$"),
    SHOW_CURRENT_TOOL_REGEX("^\\s*tools\\s+show\\s+current\\s*$"),
    SHOW_ALL_TOOLS_REGEX("^\\s*tools\\s+show\\s+available\\s*$"),
    TOOL_UPGRADE_REGEX("^\\s*tools\\s+upgrade\\s+(?<toolName>.+?)\\s*$"),
    TOOL_USE_REGEX("^\\s*tools\\s+use\\s+-d\\s+(?<dx>-1|0|1)\\s+(?<dy>-1|0|1)\\s*$"),
    BUILD_BUILDING_REGEX("build -a <building_name> -l <x , y>"),
    BUY_ANIMAL_REGEX("buy animal -a <animal> -n <name>"),
    PET_ANIMAL_REGEX("pet -n <name>"),
    SHOW_ANIMALS_REGEX("animals"),
    PLANT_SEED("^\\s*plant\\s+-s\\s+(?<seed>.+\\S)\\s+-d\\s+(?<dx>-1|0|1)\\s+(?<dy>-1|0|1)\\s*$"),
    SHOW_PLANT_INFO("^\\s*showplant\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*$"),
    CROP_INFO("^\\s*craftinfo\\s+-n\\s+(?<craft_name>\\S+)\\s*$");

    private final String regex;
    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRegex() {
        return regex;
    }
}

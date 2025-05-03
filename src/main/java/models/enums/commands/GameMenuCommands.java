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
        FRIDGE_REGEX("^\\s*cooking\\s+refrigerator\\s+(?<action>put|pick)\\s+(?<itemName>.+?)\\s*$"),
        SHOW_COOKING_RECIPES_REGEX("^\\s*cooking\\s+show\\s+recipes\\s*$"),
        PREPARE_FOOD_REGEX("^\\s*cooking\\s+prepare\\s+(?<foodName>.+?)\\s*$"),
        EAT_FOOD_REGEX("^\\s*eat\\s+(?<foodName>.+?)\\s*$"),
        BUILD_BUILDING_REGEX("^\\s*build\\s+-a\\s+(?<buildingName>barn|coop)\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*$"),
        BUY_ANIMAL_REGEX("^\\s*buy\\s+animal\\s+-a\\s+(?<animal>\\S+)\\s+-n\\s+(?<name>.+?)\\s*$"),
        PET_ANIMAL_REGEX("^\\s*pet\\s+-n\\s+(?<name>.+?)\\s*$"),
        SHEPHERD_ANIMAL_REGEX("^\\s*shepherd\\s+animals\\s+-n\\s+(?<name>.+?)\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*$"),
        FEED_ANIMAL_REGEX("^\\s*feed\\s+hay\\s+-n\\s+(?<name>.+?)\\s*$"),
        PLANT_SEED("^\\s*plant\\s+-s\\s+(?<seed>.+\\S)\\s+-d\\s+(?<dx>-1|0|1)\\s+(?<dy>-1|0|1)\\s*$"),
        CROP_INFO("^\\s*craft\\s+info\\s+-n\\s+(?<craftName>\\S+)\\s*$"),
        Recipe("^\\s*crafting\\s+show\\s+recipes\\s*^"),
        Crafting("^\\s*crafting\\s+craft\\s+(?<itemName>\\S+)\\s*$"),
        ARTISAN_USE("\\s*artisan\\s+use\\s+(?<artisanName>.*)\\s+(?<itemName>.*)\\s*"),
        GET_ARTISAN("\\s*artisan\\s+get\\s+(?<artisanName>.*)\\s*"),
        PlaceItem("\\s*place\\s+item\\s+-n\\s+(?<itemName>.*)\\s+-d\\s+(?<direction>.*)\n");
        private final String regex;

        GameMenuCommands(String regex) {
            this.regex = regex;
        }

        @Override
        public String getRegex() {
            return regex;
        }
    }

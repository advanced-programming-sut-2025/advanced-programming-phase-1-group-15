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
        SHOW_GOLD_REGEX("^\\s*gold\\s+show\\s*$"),
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
        COLLECT_PRODUCE_REGEX("^\\s*collect\\s+produce\\s+-n\\s+(?<name>.+?)\\s*$"),
        SELL_ANIMAL_REGEX("^\\s*sell\\s+animal\\s+-n\\s+(?<name>.+?)\\s*$"),
        FISHING_REGEX("^\\s*fishing\\s+-p\\s+(?<fishingPole>\\S+)\\s*$"),
        SHOW_STORE_PRODUCTS_REGEX("^\\s*show\\s+all\\s+products\\s*$"),
        SHOW_AVAILABLE_PRODUCTS_REGEX("^\\s*show\\s+all\\s+available\\s+products\\s*$"),
        PURCHASE_PRODUCT_REGEX("^\\s*purchase\\s+(?<productName>.+?)(\\s+-n\\s+(?<count>\\d+))?\\s*$"),
        SELL_PRODUCT_REGEX("^\\s*sell\\s+(?<productName>.+?)(\\s+-n\\s+(?<count>\\d+))?\\s*$"),
        TALK_FRIENDSHIP_REGEX("^\\s*talk\\s+-u\\s+(?<username>\\S+)\\s+-m\\s+(?<message>.+?)\\s*$"),
        TALK_HISTORY_REGEX("^\\s*talk\\s+history\\s+-u\\s+(?<username>\\S+)\\s*$"),
        GIFT_REGEX("^\\s*gift\\s+-u\\s+(?<username>\\S+)\\s+-i\\s+(?<itemName>.+?)\\s+-a\\s+(?<amount>\\d+)\\s*$"),
        GIFT_LIST_REGEX("^\\s*gift\\s+list\\s+-u\\s+(?<username>\\S+)\\s*$"),
        GIFT_RATE_REGEX("^\\s*gift\\s+rate\\s+-u\\s+(?<username>\\S+)\\s+-i\\s+(?<giftNumber>\\d+)\\s+-r\\s+(?<rate>\\d)\\s*$"),
        GIFT_HISTORY_REGEX("^\\s*gift\\s+history\\s+-u\\s+(?<username>\\S+)\\s*$"),
        HUG_REGEX("^\\s*hug\\s+-u\\s+(?<username>\\S+)\\s*$"),
        MARRIAGE_REGEX("^\\s*ask\\s+marriage\\s+-u\\s+(?<username>\\S+)\\s*$"),
        MARRIAGE_RESPOND_REGEX("^\\s*respond\\s+(?<answer>(-accept|-reject))\\s+-u\\s+(?<username>\\S+)\\s*$"),
        FLOWER_REGEX("^\\s*flower\\s+-u\\s+(?<username>\\S+)\\s*$"),
        PLANT_SEED("^\\s*plant\\s+-s\\s+(?<seed>.+\\S)\\s+-d\\s+(?<dx>-1|0|1)\\s+(?<dy>-1|0|1)\\s*$"),
        CROP_INFO("^\\s*craft\\s+info\\s+-n\\s+(?<craftName>.+)\\s*$"),
        Recipe("^crafting\\s+show\\s+recipes$"),
        Crafting("^crafting\\s+craft\\s+(?<itemName>.+?)$"),
        ARTISAN_USE("^artisan\\s+use\\s+(?<artisanName>.+)\\s+(?<itemName>.+)$"),
        GET_ARTISAN("\\s*artisan\\s+get\\s+(?<artisanName>.*)\\s*"),
        PLANT_MIXED_SEED("\\s*plant\\s+mixed\\s+seed\\s+-d\\s+(?<dx>-1|0|1)\\s+(?<dy>-1|0|1)\\s*"),
        HARVEST("\\s*harvest\\s+-d\\s+(?<dx>-1|0|1)\\s+(?<dy>-1|0|1)\\s*"),
        SHOW_PLANT("\\s*show\\s*plant\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*$"),
        PlaceItem("^\\s*place\\s+item\\s+-n\\s+(?<itemName>.?)\\s+-d\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*$");
        private final String regex;

        GameMenuCommands(String regex) {
            this.regex = regex;
        }

        @Override
        public String getRegex() {
            return regex;
        }
    }

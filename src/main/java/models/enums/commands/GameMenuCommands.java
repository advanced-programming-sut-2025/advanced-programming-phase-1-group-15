package models.enums.commands;

public enum GameMenuCommands implements Commands {
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

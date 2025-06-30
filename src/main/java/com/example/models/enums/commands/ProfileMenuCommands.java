package com.example.models.enums.commands;

public enum ProfileMenuCommands implements Commands {
    CHANGE_USERNAME_REGEX("^\\s*change\\s+username\\s+-u\\s+(?<username>\\S+)\\s*$"),
    CHANGE_NICKNAME_REGEX("^\\s*change\\s+nickname\\s+-n\\s+(?<nickname>\\S+)\\s*$"),
    CHANGE_EMAIL_REGEX("^\\s*change\\s+email\\s+-e\\s+(?<email>\\S+)\\s*$"),
    CHANGE_PASSWORD_REGEX("^\\s*change\\s+password\\s+-p\\s+(?<newPassword>\\S+)\\s+-o\\s+(?<oldPassword>\\S+)\\s*$"),
    USER_INFO_REGEX("^\\s*user\\s+info\\s*$");

    private final String regex;
    ProfileMenuCommands(String regex) { this.regex = regex; }

    @Override
    public String getRegex() {
        return regex;
    }
}

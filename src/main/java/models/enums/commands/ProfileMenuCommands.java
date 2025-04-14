package models.enums.commands;

public enum ProfileMenuCommands implements Commands {
    CHANGE_USERNAME_REGEX("change username -u <username>"),
    CHANGE_NICKNAME_REGEX("change nickname -n <nickname>"),
    CHANGE_EMAIL_REGEX("change email -e <email>"),
    CHANGE_PASSWORD_REGEX("change password -p <newPassword> -o <oldPassword>"),
    USER_INFO_REGEX("^\\s*user\\s+info\\s*$");

    private final String regex;
    ProfileMenuCommands(String regex) { this.regex = regex; }

    @Override
    public String getRegex() {
        return regex;
    }
}

package com.example.common.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Commands {
    String getRegex();
    String SHOW_CURRENT_MENU_REGEX = "^\\s*show\\s+current\\s+menu\\s*$";
    String EXIT_MENU_REGEX = "^\\s*menu\\s+exit\\s*$";
    String SWITCH_MENU_REGEX = "^\\s*menu\\s+enter\\s+(?<menuName>.+?)\\s*$";

    default boolean matches(String command) {
        return command.matches(getRegex());
    }
    default Matcher matcher(String command) {
        return Pattern.compile(getRegex()).matcher(command);
    }

    static boolean checkShowCurrentMenuRegex(String command) {
        return command.matches(SHOW_CURRENT_MENU_REGEX);
    }
    static boolean checkExitRegex(String command) {
        return command.matches(EXIT_MENU_REGEX);
    }

    static boolean checkSwitchRegex(String command) {
        return command.matches(SWITCH_MENU_REGEX);
    }
    static String menuName(String command) {
        Matcher matcher = Pattern.compile(SWITCH_MENU_REGEX).matcher(command);
        return matcher.matches() ? matcher.group("menuName") : null;
    }

    static boolean checkUsernameFormat(String username) {
        return false;
    }
    static boolean checkPasswordFormat(String password) {
        return false;
    }
    static boolean checkEmailFormat(String email) {
        return false;
    }
}

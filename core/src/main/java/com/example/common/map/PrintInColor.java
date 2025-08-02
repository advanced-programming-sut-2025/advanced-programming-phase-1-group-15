package com.example.common.map;

public class PrintInColor {
    public static final String RESET = "\u001B[0m";

    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Bright colors
    public static final String BRIGHT_BLACK = "\u001B[90m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";

    // Extended colors (256-color mode)
    public static final String GRAY = "\u001B[38;5;8m";
    public static final String BRIGHT_GRAY = "\u001B[38;5;7m";
    public static final String BROWN = "\u001B[38;5;130m"; // brownish

    public static void printInBlack(Character character) {
        System.out.print(BLACK + character + RESET);
    }

    public static void printInRed(Character character) {
        System.out.print(RED + character + RESET);
    }

    public static void printInGreen(Character character) {
        System.out.print(GREEN + character + RESET);
    }

    public static void printInYellow(Character character) {
        System.out.print(YELLOW + character + RESET);
    }

    public static void printInBlue(Character character) {
        System.out.print(BLUE + character + RESET);
    }

    public static void printInPurple(Character character) {
        System.out.print(PURPLE + character + RESET);
    }

    public static void printInCyan(Character character) {
        System.out.print(CYAN + character + RESET);
    }

    public static void printInWhite(Character character) {
        System.out.print(WHITE + character + RESET);
    }

    // Bright color methods
    public static void printInBrightBlack(Character character) {
        System.out.print(BRIGHT_BLACK + character + RESET);
    }

    public static void printInBrightRed(Character character) {
        System.out.print(BRIGHT_RED + character + RESET);
    }

    public static void printInBrightGreen(Character character) {
        System.out.print(BRIGHT_GREEN + character + RESET);
    }

    public static void printInBrightYellow(Character character) {
        System.out.print(BRIGHT_YELLOW + character + RESET);
    }

    public static void printInBrightBlue(Character character) {
        System.out.print(BRIGHT_BLUE + character + RESET);
    }

    public static void printInBrightPurple(Character character) {
        System.out.print(BRIGHT_PURPLE + character + RESET);
    }

    public static void printInBrightCyan(Character character) {
        System.out.print(BRIGHT_CYAN + character + RESET);
    }

    public static void printInBrightWhite(Character character) {
        System.out.print(BRIGHT_WHITE + character + RESET);
    }

    public static void printInGray(Character character) {
        System.out.print(GRAY + character + RESET);
    }

    public static void printInBrightGray(Character character) {
        System.out.print(BRIGHT_GRAY + character + RESET);
    }

    public static void printInBrown(Character character) {
        System.out.print(BROWN + character + RESET);
    }
}

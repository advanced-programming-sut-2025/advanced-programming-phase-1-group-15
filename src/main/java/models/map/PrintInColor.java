package models.map;

public class PrintInColor {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";

    public static void printInBlue(Character character) {
        System.out.println(RED + character + RESET);
    }
}

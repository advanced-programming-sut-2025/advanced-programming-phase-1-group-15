package com.example.views;

import java.util.Scanner;

public class AppView {
    public static boolean exit = false;
    public static AppMenu currentMenu;

    public static void runMenu(Scanner scanner) {
        currentMenu.run(scanner);
    }
}

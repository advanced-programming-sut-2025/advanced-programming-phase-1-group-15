package views;

import java.util.Scanner;

public class AppView {
    public static boolean exit = false;
    public static AppMenu currentMenu;

    public static void runMenu(Scanner scanner) {
        currentMenu.run(scanner);
    }

    public static void changeMenu(String menuName) {
        switch (menuName) {
            case "login menu":
                AppView.currentMenu = new LoginMenu();
                break;
            case "main menu":
                AppView.currentMenu = new MainMenu();
                break;
            case "game menu":
                AppView.currentMenu = new GameMenu();
                break;
            case "profile menu":
                AppView.currentMenu = new ProfileMenu();
                break;
            default:
                break;
        }
    }
}

import views.AppView;
import views.LoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AppView.currentMenu = new LoginMenu();
        while (!AppView.exit) {
            AppView.runMenu(scanner);
        }
    }
}

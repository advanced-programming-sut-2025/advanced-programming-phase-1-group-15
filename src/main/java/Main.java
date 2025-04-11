import views.AppView;
import views.ProfileMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AppView.currentMenu = new ProfileMenu();
        while (!AppView.exit) {
            AppView.runMenu(scanner);
        }
    }
}

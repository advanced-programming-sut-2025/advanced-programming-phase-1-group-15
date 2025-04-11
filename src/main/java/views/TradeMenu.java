package views;

import models.enums.commands.Commands;

import java.util.Scanner;

public class TradeMenu implements AppMenu {
    public void run(Scanner scanner) {
        String command = scanner.nextLine().trim();

        if(Commands.checkShowCurrentMenuRegex(command)) {
            System.out.println("trade menu");
        }

        else {
            System.out.println("invalid command");
        }
    }
}

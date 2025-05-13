package views;

import controllers.TradeMenuController;
import models.App;
import models.enums.commands.Commands;
import models.enums.commands.TradeMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu implements AppMenu {
    public void run(Scanner scanner) {
        String command = scanner.nextLine().trim();
        if(Commands.checkShowCurrentMenuRegex(command)) {
            System.out.println("trade menu");
        }
        else if(TradeMenuCommands.TRADE_MONEY.matches(command)) {
            Matcher matcher = TradeMenuCommands.TRADE_MONEY.matcher(command);
            matcher.matches();
            System.out.println(TradeMenuController.tradeWithMoney(matcher.group("username"),
                    matcher.group("type"),matcher.group("item"),matcher.group("amount"),matcher.group("price")));
        }
        else if(TradeMenuCommands.TRADE_ITEM.matches(command)) {
            Matcher matcher = TradeMenuCommands.TRADE_ITEM.matcher(command);
            matcher.matches();
            System.out.println(TradeMenuController.tradeWithItem(matcher.group("username"),matcher.group("type"),
                    matcher.group("item"),matcher.group("amount"),matcher.group("targetItem"),matcher.group("targetAmount")));
        }
        else if(TradeMenuCommands.TRADE_LIST.matches(command)) {
            Matcher matcher = TradeMenuCommands.TRADE_LIST.matcher(command);
            matcher.matches();
            TradeMenuController.tradeList();
        }
        else if(TradeMenuCommands.TRADE_RESPONSE.matches(command)) {
            Matcher matcher = TradeMenuCommands.TRADE_RESPONSE.matcher(command);
            matcher.matches();
            System.out.println(TradeMenuController.tradeResponse(matcher.group("response"),matcher.group("id")));
        }
        else if(TradeMenuCommands.SHOW_TRADE_HISTORY.matches(command)) {
            Matcher matcher = TradeMenuCommands.SHOW_TRADE_HISTORY.matcher(command);
            matcher.matches();
            TradeMenuController.showTradeHistory();
        }
        else if (TradeMenuCommands.BACK.matches(command)) {
            Matcher matcher = TradeMenuCommands.BACK.matcher(command);
            AppView.currentMenu = App.currentGameMenu;
        }
        else {
            System.out.println("invalid command");
        }
    }
}

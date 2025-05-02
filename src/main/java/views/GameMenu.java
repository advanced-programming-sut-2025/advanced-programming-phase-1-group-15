package views;

import controllers.CheatCodeController;
import controllers.GameMenuController;
import models.App;
import models.Game;
import models.Player;
import models.Result;
import models.enums.commands.CheatCodeCommands;
import models.enums.commands.Commands;
import models.enums.commands.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    private final Game game;

    public GameMenu(Game game) {
        this.game = game;
        game.build();
        System.out.println("\"" + game.getCurrentPlayer().getUsername() + "\" it's your turn to begin the game.");
    }

    public Result terminateMenu(Scanner scanner) {
        for(Player player : game.getPlayers()) {
            System.out.println(player.getUsername() + ", do you agree with total game termination? (+/-)");
            String command = scanner.nextLine().trim();

            if(command.equalsIgnoreCase("-")) {
                return new Result(false, "All players must agree to completely terminate the game.");
            }
            else if(!command.equalsIgnoreCase("+")) {
                return new Result(false, "invalid command");
            }
        }

        return new Result(true, "Game terminated successfully.");
    }

    public void run(Scanner scanner) {
        Player currentPlayer = game.getCurrentPlayer();
        String command = scanner.nextLine().trim();

        if(GameMenuCommands.NEXT_TURN_REGEX.matches(command)) {
            game.nextTurn();
            System.out.println("\"" + game.getCurrentPlayer().getUsername() + "\" go on!");
            return;
        }

        if(!currentPlayer.isLocked()) {
            if(Commands.checkShowCurrentMenuRegex(command)) {
                System.out.println("game menu");
            }

            else if(GameMenuCommands.EXIT_GAME_REGEX.matches(command)) {
                if(!currentPlayer.equals(game.getMainPlayer())) {
                    System.out.println("Only the creator can exit the game.");
                }
                else {
                    App.currentGame = null;
                    AppView.currentMenu = new MainMenu();
                    System.out.println("game exited successfully!");
                }
            }

            else if(GameMenuCommands.TERMINATE_GAME_REGEX.matches(command)) {
                Result terminate = terminateMenu(scanner);
                System.out.println(terminate.message());

                if(terminate.success()) {
                    App.recentGames.remove(game);
                    App.currentGame = null;
                    AppView.currentMenu = new MainMenu();
                }
            }

            else if(command.equals("time")) {
                System.out.println(game.getDateAndTime().displayHour());
            }
            else if(command.equals("date")) {
                System.out.println(game.getDateAndTime().displayDate());
            }
            else if(command.equals("datetime")) {
                System.out.println(game.getDateAndTime().displayDateTime());
            }
            else if(command.equals("season")) {
                System.out.println(game.getDateAndTime().displaySeason());
            }
            else if(GameMenuCommands.DAY_OF_WEEK_REGEX.matches(command)) {
                System.out.println(game.getDateAndTime().displayDayOfWeek());
            }
            else if(CheatCodeCommands.ADVANCE_TIME_REGEX.matches(command)) {
                Matcher matcher = CheatCodeCommands.ADVANCE_TIME_REGEX.matcher(command);

                int hours = matcher.matches() ? Integer.parseInt(matcher.group("hours")) : 0;

                Result result = CheatCodeController.cheatAdvanceTime(hours);
                System.out.println(result.message());
            }
            else if(CheatCodeCommands.ADVANCE_DATE_REGEX.matches(command)) {
                Matcher matcher = CheatCodeCommands.ADVANCE_DATE_REGEX.matcher(command);

                int days = matcher.matches() ? Integer.parseInt(matcher.group("days")) : 0;

                Result result = CheatCodeController.cheatAdvanceDate(days);
                System.out.println(result.message());
            }

            else if(command.equals("weather")) {
                System.out.println(game.getWeather().displayWeather());
            }
            else if(GameMenuCommands.WEATHER_FORECAST_REGEX.matches(command)) {
                System.out.println(game.getWeather().displayForecast());
            }
            else if(CheatCodeCommands.WEATHER_SET_REGEX.matches(command)) {
                Matcher matcher = CheatCodeCommands.WEATHER_SET_REGEX.matcher(command);

                String weatherType = matcher.matches() ? matcher.group("weatherType") : "";

                Result result = CheatCodeController.cheatSetWeather(weatherType);
                System.out.println(result.message());
            }

            else if(GameMenuCommands.PRINT_MAP_REGEX.matches(command)) {
                game.getMap().printMap();
            }
            else if(GameMenuCommands.MAP_GUIDE_REGEX.matches(command)) {
                game.getMap().mapGuide();
            }

            else if(GameMenuCommands.WALK_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.WALK_REGEX.matcher(command);

                int x = matcher.matches() ? Integer.parseInt(matcher.group("x")) : 0;
                int y = Integer.parseInt(matcher.group("y"));

                Result result = GameMenuController.walk(x, y);
                System.out.println(result.message());

                if(result.success()) {
                    command = scanner.nextLine().trim();
                    if(command.equalsIgnoreCase("y")) {
                        result = GameMenuController.setPosition(x, y);
                        System.out.println(result.message());

                        if(!result.success()) {
                            game.nextTurn();
                        }
                    }
                }
            }

            else if(GameMenuCommands.SHOW_ENERGY_REGEX.matches(command)) {
                System.out.println("energy: " + game.getCurrentPlayer().getEnergy());
            }
            else if(CheatCodeCommands.ENERGY_SET_REGEX.matches(command)) {
                Matcher matcher = CheatCodeCommands.ENERGY_SET_REGEX.matcher(command);

                int value = matcher.matches() ? Integer.parseInt(matcher.group("value")) : 0;

                Result result = CheatCodeController.cheatSetEnergy(value);
                System.out.println(result.message());
            }
            else if(CheatCodeCommands.ENERGY_UNLIMITED_REGEX.matches(command)) {
                Result result = CheatCodeController.cheatUnlimitedEnergy();
                System.out.println(result.message());
            }

            else if(GameMenuCommands.INVENTORY_SHOW_REGEX.matches(command)) {
                System.out.print(game.getCurrentPlayer().getInventory().display());
            }
            else if(GameMenuCommands.INVENTORY_TRASH_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.INVENTORY_TRASH_REGEX.matcher(command);

                String itemName = matcher.matches() ? matcher.group("itemName") : "";
                int number = matcher.group("count") != null ? Integer.parseInt(matcher.group("count")) : -1;

                Result result = GameMenuController.removeFromInventory(itemName, number);
                System.out.println(result.message());
            }

            else if(GameMenuCommands.EQUIP_TOOL_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.EQUIP_TOOL_REGEX.matcher(command);

                String toolName = matcher.matches() ? matcher.group("toolName") : "";

                Result result = GameMenuController.equipTool(toolName);
                System.out.println(result.message());
            }
            else if(GameMenuCommands.SHOW_CURRENT_TOOL_REGEX.matches(command)) {
                Result result = GameMenuController.showCurrentTool();
                System.out.println(result.message());
            }
            else if(GameMenuCommands.SHOW_ALL_TOOLS_REGEX.matches(command)) {
                System.out.print(game.getCurrentPlayer().getInventory().showTools());
            }
            else if(GameMenuCommands.TOOL_UPGRADE_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.TOOL_UPGRADE_REGEX.matcher(command);

                String toolName = matcher.matches() ? matcher.group("toolName") : "";

                Result result = GameMenuController.upgradeTool(toolName);
                System.out.println(result.message());
            }
            else if(GameMenuCommands.TOOL_USE_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.TOOL_USE_REGEX.matcher(command);

                int dx = matcher.matches() ? Integer.parseInt(matcher.group("dx")) : 0;
                int dy = Integer.parseInt(matcher.group("dy"));

                Result result = GameMenuController.useTool(dx, dy);
                System.out.println(result.message());
            }

            else if(GameMenuCommands.FRIDGE_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.FRIDGE_REGEX.matcher(command);

                String action = matcher.matches() ? matcher.group("action") : "";
                String itemName = matcher.group("itemName");

                Result result;
                if(action.equalsIgnoreCase("put")) {
                    result = GameMenuController.putInFridge(itemName);
                }
                else {
                    result = GameMenuController.pickFromFridge(itemName);
                }

                System.out.println(result.message());
            }
            else if(GameMenuCommands.SHOW_COOKING_RECIPES_REGEX.matches(command)) {
                Result result = GameMenuController.showCookingRecipes();
                System.out.println(result.message());
            }
            else if(GameMenuCommands.EAT_FOOD_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.EAT_FOOD_REGEX.matcher(command);

                String foodName = matcher.matches() ? matcher.group("foodName") : "";

                Result result = GameMenuController.eatFood(foodName);
                System.out.println(result.message());
            }

            else if(GameMenuCommands.BUILD_BUILDING_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.BUILD_BUILDING_REGEX.matcher(command);

                String buildingName = matcher.matches() ? matcher.group("buildingName") : "";
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));

                Result result;
                if(buildingName.equals("barn")) {
                    result = GameMenuController.buildBarn(x, y);
                }
                else {
                    result = GameMenuController.buildCoop(x, y);
                }

                System.out.println(result.message());
            }
            else if(GameMenuCommands.BUY_ANIMAL_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.BUY_ANIMAL_REGEX.matcher(command);
            }

            else if(GameMenuCommands.CROP_INFO.matches(command)) {
                Matcher matcher = GameMenuCommands.CROP_INFO.matcher(command);
                matcher.matches();
                System.out.println(GameMenuController.showCropInfo(matcher.group("craft_name")));
            }
            else if(GameMenuCommands.PLANT_SEED.matches(command)) {
                Matcher matcher = GameMenuCommands.PLANT_SEED.matcher(command);
                matcher.matches();
                System.out.println(GameMenuController.plant(matcher.group("seed"),
                        Integer.parseInt(matcher.group("dx")),Integer.parseInt(matcher.group("dy"))));
            }
            else if(GameMenuCommands.Recipe.matches(command)) {
                Matcher matcher = GameMenuCommands.Recipe.matcher(command);
                matcher.matches();
                System.out.println(GameMenuController.ShowRecipe());
            }
            else if(GameMenuCommands.Crafting.matches(command)) {
                Matcher matcher = GameMenuCommands.Crafting.matcher(command);
                matcher.matches();
                System.out.println(GameMenuController.crafting(matcher.group("itemName")));
            }
            else if(CheatCodeCommands.ADD_ITEM.matches(command)) {
                Matcher matcher = CheatCodeCommands.ADD_ITEM.matcher(command);
                matcher.matches();
                Result result = CheatCodeController.cheatAddItem(matcher.group("itemName"),matcher.group("count"));
                System.out.println(result.message());
            }
            else {
                System.out.println("invalid command");
            }
        }
        else {
            System.out.println("You are locked! (maximum energy per turn consumed). Use \"next turn\" command to continue.");
        }
    }
}

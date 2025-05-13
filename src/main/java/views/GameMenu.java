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
import models.relation.PlayerFriendship;

import javax.print.attribute.standard.PresentationDirection;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    private final Game game;

    public GameMenu(Game game) {
        this.game = game;
        game.build();
        App.currentGameMenu = this;
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
            for(PlayerFriendship.Message message : game.getCurrentPlayer().getReceivedMessages()) {
                System.out.println("message from " + message.sender().getUsername() + ":  \"" + message.message() + "\"");
            }
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
            else if(GameMenuCommands.SHOW_GOLD_REGEX.matches(command)) {
                System.out.println("gold: " + game.getCurrentPlayer().getGold());
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
                System.out.print("Available cooking recipes: \n" + game.getCurrentPlayer().showAvailableFoods());
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

                String animal = matcher.matches() ? matcher.group("animal") : "";
                String name = matcher.group("name");

                Result result = GameMenuController.buyAnimal(animal, name);
                System.out.println(result.message());
            }
            else if(GameMenuCommands.PET_ANIMAL_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.PET_ANIMAL_REGEX.matcher(command);

                String name = matcher.matches() ? matcher.group("name") : "";

                Result result = GameMenuController.petAnimal(name);
                System.out.println(result.message());
            }
            else if(CheatCodeCommands.ANIMAL_FRIENDSHIP_REGEX.matches(command)) {
                Matcher matcher = CheatCodeCommands.ANIMAL_FRIENDSHIP_REGEX.matcher(command);

                String name = matcher.matches() ? matcher.group("name") : "";
                int amount = Integer.parseInt(matcher.group("amount"));

                Result result = CheatCodeController.cheatAnimalFriendship(name, amount);
                System.out.println(result.message());
            }
            else if(command.equals("animals")) {
                System.out.print("Animals: \n" + game.getCurrentPlayer().showAnimals());
            }
            else if(GameMenuCommands.SHEPHERD_ANIMAL_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.SHEPHERD_ANIMAL_REGEX.matcher(command);

                String name = matcher.matches() ? matcher.group("name") : "";
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));

                Result result = GameMenuController.shepherdAnimal(name, x, y);
                System.out.println(result.message());
            }
            else if(GameMenuCommands.FEED_ANIMAL_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.FEED_ANIMAL_REGEX.matcher(command);

                String name = matcher.matches() ? matcher.group("name") : "";

                Result result = GameMenuController.feedHayAnimal(name);
                System.out.println(result.message());
            }
            else if(command.equals("produces")) {
                Result result = GameMenuController.showAnimalProducts();
                System.out.println(result.message());
            }
            else if(GameMenuCommands.COLLECT_PRODUCE_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.COLLECT_PRODUCE_REGEX.matcher(command);

                String name = matcher.matches() ? matcher.group("name") : "";

                Result result = GameMenuController.collectProduce(name);
                System.out.println(result.message());
            }
            else if(GameMenuCommands.SELL_ANIMAL_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.SELL_ANIMAL_REGEX.matcher(command);

                String name = matcher.matches() ? matcher.group("name") : "";

                Result result = GameMenuController.sellAnimal(name);
                System.out.println(result.message());
            }
            else if(GameMenuCommands.FISHING_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.FISHING_REGEX.matcher(command);

                String fishingPole = matcher.matches() ? matcher.group("fishingPole") : "";

                Result result = GameMenuController.fishing(fishingPole);
                System.out.println(result.message());
            }

            else if(GameMenuCommands.SHOW_STORE_PRODUCTS_REGEX.matches(command)) {
                Result result = GameMenuController.showStoreProducts();
                System.out.print(result.message());
            }
            else if(GameMenuCommands.SHOW_AVAILABLE_PRODUCTS_REGEX.matches(command)) {
                Result result = GameMenuController.showAvailableStoreProducts();
                System.out.print(result.message());
            }
            else if(GameMenuCommands.PURCHASE_PRODUCT_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.PURCHASE_PRODUCT_REGEX.matcher(command);

                String productName = matcher.matches() ? matcher.group("productName") : "";
                int count = matcher.matches() ? Integer.parseInt(matcher.group("count")) : 1;

                Result result = GameMenuController.purchaseProduct(productName, count);
                System.out.println(result.message());
            }
            else if(CheatCodeCommands.ADD_GOLD_REGEX.matches(command)) {
                Matcher matcher = CheatCodeCommands.ADD_GOLD_REGEX.matcher(command);

                int amount = matcher.matches() ? Integer.parseInt(matcher.group("amount")) : 0;

                Result result = CheatCodeController.cheatAddGold(amount);
                System.out.println(result.message());
            }
            else if(GameMenuCommands.SELL_PRODUCT_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.SELL_PRODUCT_REGEX.matcher(command);

                String productName = matcher.matches() ? matcher.group("productName") : "";
                int count = matcher.matches() ? Integer.parseInt(matcher.group("count")) : -1;

                Result result = GameMenuController.sellProduct(productName, count);
                System.out.println(result.message());
            }

            else if(command.equals("friendships")) {
                Result result = GameMenuController.showFriendships();
                System.out.println("Your Friendships: ");
                System.out.print(result.message());
            }
            else if(GameMenuCommands.TALK_FRIENDSHIP_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.TALK_FRIENDSHIP_REGEX.matcher(command);

                String username = matcher.matches() ? matcher.group("username") : "";
                String message = matcher.group("message");

                Result result = GameMenuController.talkFriendship(username, message);
                System.out.println(result.message());
            }
            else if(GameMenuCommands.TALK_HISTORY_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.TALK_HISTORY_REGEX.matcher(command);

                String username = matcher.matches() ? matcher.group("username") : "";

                Result result = GameMenuController.talkHistory(username);
                System.out.print(result.message());
            }
            else if(GameMenuCommands.GIFT_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.GIFT_REGEX.matcher(command);

                String username = matcher.matches() ? matcher.group("username") : "";
                String itemName = matcher.group("itemName");
                int amount = Integer.parseInt(matcher.group("amount"));

                Result result = GameMenuController.gift(username, itemName, amount);
                System.out.println(result.message());
            }
            else if(GameMenuCommands.GIFT_LIST_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.GIFT_LIST_REGEX.matcher(command);

                String username = matcher.matches() ? matcher.group("username") : "";

                Result result = GameMenuController.giftList(username);
                System.out.print(result.message());
            }
            else if(GameMenuCommands.GIFT_RATE_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.GIFT_RATE_REGEX.matcher(command);

                String username = matcher.matches() ? matcher.group("username") : "";
                int giftNumber = Integer.parseInt(matcher.group("giftNumber"));
                int rate = Integer.parseInt(matcher.group("rate"));

                Result result = GameMenuController.rateGift(username, giftNumber, rate);
                System.out.println(result.message());
            }
            else if(GameMenuCommands.GIFT_HISTORY_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.GIFT_HISTORY_REGEX.matcher(command);

                String username = matcher.matches() ? matcher.group("username") : "";

                Result result = GameMenuController.giftHistory(username);
                System.out.print(result.message());
            }
            else if(GameMenuCommands.HUG_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.HUG_REGEX.matcher(command);

                String username = matcher.matches() ? matcher.group("username") : "";

                Result result = GameMenuController.hug(username);
                System.out.println(result.message());
            }
            else if(GameMenuCommands.FLOWER_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.FLOWER_REGEX.matcher(command);

                String username = matcher.matches() ? matcher.group("username") : "";

                Result result = GameMenuController.flower(username);
                System.out.println(result.message());
            }
            else if(GameMenuCommands.MARRIAGE_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.MARRIAGE_REGEX.matcher(command);

                String username = matcher.matches() ? matcher.group("username") : "";

                Result result = GameMenuController.marry(username);
                System.out.println(result.message());
            }
            else if (GameMenuCommands.MARRIAGE_RESPOND_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.MARRIAGE_RESPOND_REGEX.matcher(command);

                String username = matcher.matches() ? matcher.group("username") : "";
                String answer = matcher.group("answer");

                Result result = GameMenuController.respondMarriage(username, answer);
                System.out.println(result.message());
            }
            else if(CheatCodeCommands.SET_FRIENDSHIP_REGEX.matches(command)) {
                Matcher matcher = CheatCodeCommands.SET_FRIENDSHIP_REGEX.matcher(command);

                String username = matcher.matches() ? matcher.group("username") : "";
                int level = Integer.parseInt(matcher.group("level"));

                Result result = CheatCodeController.cheatSetFriendship(username, level);
                System.out.println(result.message());
            }

            else if(GameMenuCommands.CROP_INFO.matches(command)) {
                Matcher matcher = GameMenuCommands.CROP_INFO.matcher(command);
                matcher.matches();
                System.out.println(GameMenuController.showCropInfo(matcher.group("craftName")));
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
                GameMenuController.ShowRecipe();
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
            else if (GameMenuCommands.PlaceItem.matches(command)) {
                Matcher matcher = GameMenuCommands.PlaceItem.matcher(command);
                matcher.matches();
                System.out.println(GameMenuController.PlaceItem(matcher.group("itemName"), Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            }
            else if(GameMenuCommands.ARTISAN_USE.matches(command)) {
                Matcher matcher = GameMenuCommands.ARTISAN_USE.matcher(command);
                matcher.matches();
                System.out.println(GameMenuController.UseArtisan(matcher.group("artisanName"),matcher.group("itemName")));
            }
            else if (GameMenuCommands.GET_ARTISAN.matches(command)) {
                Matcher matcher = GameMenuCommands.GET_ARTISAN.matcher(command);
                matcher.matches();
                System.out.println(GameMenuController.GetArtisan(matcher.group("artisanName")));
            }
            else if(GameMenuCommands.PLANT_MIXED_SEED.matches(command)) {
                Matcher matcher = GameMenuCommands.PLANT_MIXED_SEED.matcher(command);
                matcher.matches();

                System.out.println(GameMenuController.plantMixedSeed(
                        Integer.parseInt(matcher.group("dx")),Integer.parseInt(matcher.group("dy"))));
            }
            else if(GameMenuCommands.HARVEST.matches(command)) {
                Matcher matcher = GameMenuCommands.HARVEST.matcher(command);
                matcher.matches();
                // TODO : check using tools for harvesting
                System.out.println();
            }
            else if(GameMenuCommands.SHOW_PLANT.matches(command)) {
                Matcher matcher = GameMenuCommands.SHOW_PLANT.matcher(command);
                matcher.matches();
                System.out.println(GameMenuController.showPlant(
                        Integer.parseInt(matcher.group("x")),Integer.parseInt(matcher.group("y"))
                ));
            }
            else if (GameMenuCommands.MEET_NPC.matches(command)) {
                Matcher m = GameMenuCommands.MEET_NPC.matcher(command);
                m.matches();
                System.out.println(
                        GameMenuController.meetNPC(m.group("npcName"))
                );
            }
            else if (GameMenuCommands.GIFT_NPC.matches(command)) {
                Matcher m = GameMenuCommands.GIFT_NPC.matcher(command);
                m.matches();
                System.out.println(
                        GameMenuController.giftNPC(
                                m.group("npcName"),
                                m.group("itemName")
                        )
                );
            }
            else if (GameMenuCommands.FRIEND_LIST.matches(command)) {
                System.out.println(
                        GameMenuController.friendShipNPCList()
                );
            }
            else if (GameMenuCommands.QUEST_LIST.matches(command)) {
                System.out.println(
                        GameMenuController.questLists()
                );
            }
            else if (GameMenuCommands.QUEST_FINISH.matches(command)) {
                Matcher m = GameMenuCommands.QUEST_FINISH.matcher(command);
                m.matches();
                int idx = Integer.parseInt(m.group("index"));
                System.out.println(
                        GameMenuController.finishQuest(idx)
                );
            }
            else if(CheatCodeCommands.ADD_RECIPE.matches(command)) {
                Matcher matcher = CheatCodeCommands.ADD_RECIPE.matcher(command);
                matcher.matches();
                GameMenuController.AddRecipe(matcher.group("name"));
            }
            else if(GameMenuCommands.SHOW_COOKING_RECIPES_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.SHOW_COOKING_RECIPES_REGEX.matcher(command);
                matcher.matches();
            }
            else if(GameMenuCommands.PREPARE_FOOD_REGEX.matches(command)) {
                Matcher matcher = GameMenuCommands.PREPARE_FOOD_REGEX.matcher(command);
                matcher.matches();
            }
            else if(GameMenuCommands.START_TRADE.matches(command)) {
                Matcher matcher = GameMenuCommands.START_TRADE.matcher(command);
                matcher.matches();
                GameMenuController.StartTrade();
                AppView.currentMenu = new TradeMenu();
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

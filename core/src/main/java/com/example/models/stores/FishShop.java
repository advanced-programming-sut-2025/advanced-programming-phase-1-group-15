package com.example.models.stores;

import com.example.models.Player;
import com.example.models.cooking.Food;
import com.example.models.cooking.FoodType;
import com.example.models.crafting.CraftItem;
import com.example.models.crafting.CraftItemType;
import com.example.models.map.AreaType;
import com.example.models.map.Tile;
import com.example.models.tools.FishingPole;
import com.example.models.tools.ToolLevel;

import java.util.ArrayList;

public class FishShop extends Store {
    public static int[] coordinates = {48, 51, 12, 15};

    int[] sold = new int[6];

    public FishShop(ArrayList<ArrayList<Tile>> storeTiles) {
        runner = Runner.WILLIE;
        opensAt = 9;
        closesAt = 17;

        this.areaType = AreaType.STORE;
        this.tiles = storeTiles;

        for(ArrayList<Tile> row : storeTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }

    @Override
    public void build() {

    }

    @Override
    public void resetSoldItems() {
        for(int i = 0; i < 6; i++) {
            sold[i] = 0;
        }
    }

    @Override
    public boolean checkAvailable(String productName) {
        return productName.equalsIgnoreCase("Fish Smoker") || productName.equalsIgnoreCase("Trout Soup")
                || productName.equalsIgnoreCase("Bamboo Pole") || productName.equalsIgnoreCase("Training Rod")
                || productName.equalsIgnoreCase("Fiberglass Rod") || productName.equalsIgnoreCase("Iridium Rod");
    }

    @Override
    public boolean checkAmount(String productName, int amount) {
        if(amount > 1) {
            return false;
        }
        if(productName.equalsIgnoreCase("Fish Smoker")) {
            return sold[0] == 0;
        }
        else if(productName.equalsIgnoreCase("Trout Soup")) {
            return sold[1] == 0;
        }
        else if(productName.equalsIgnoreCase("Bamboo Pole")) {
            return sold[2] == 0;
        }
        else if(productName.equalsIgnoreCase("Training Rod")) {
            return sold[3] == 0;
        }
        else if(productName.equalsIgnoreCase("Fiberglass Rod")) {
            return sold[4] == 0;
        }
        else if(productName.equalsIgnoreCase("Iridium Rod")) {
            return sold[5] == 0;
        }
        return false;
    }

    @Override
    public String sell(Player buyer, String productName, int amount) {
        if(productName.equalsIgnoreCase("Fish Smoker")) {
            if(amount * 10000 > buyer.getGold()) {
                return "not enough gold to buy " + amount + " fish smoker recipe";
            }

            sold[0] += 1;
            buyer.addToAvailableCrafts(new CraftItem(CraftItemType.FISH_SMOKER));
            return "you've bought fish smoker recipe with price 10000";
        }
        else if(productName.equalsIgnoreCase("Trout Soup")) {
            if(amount * 250 > buyer.getGold()) {
                return "not enough gold to buy " + amount + " trout soup";
            }

            sold[1] += 1;
            buyer.addToBackPack(new Food(FoodType.BACKED_FISH), amount);
            return "you've bought trout soup with price 250";
        }
        else if(productName.equalsIgnoreCase("Bamboo Pole")) {
            if(amount * 500 > buyer.getGold()) {
                return "not enough gold to buy " + amount + " bamboo pole";
            }

            sold[2] += 1;
            buyer.addToBackPack(new FishingPole(ToolLevel.BAMBOO), amount);
            return "you've bought bamboo pole with price 500";
        }
        else if(productName.equalsIgnoreCase("Training Rod")) {
            if(amount * 25 > buyer.getGold()) {
                return "not enough gold to buy " + amount + " training rod";
            }

            sold[3] += 1;
            buyer.addToBackPack(new FishingPole(), amount);
            return "you've bought training rod with price 25";
        }
        else if(productName.equalsIgnoreCase("Fiberglass Rod")) {
            if(amount * 1800 > buyer.getGold()) {
                return "not enough gold to buy " + amount + " fiberglass rod";
            }
            if(buyer.getFishingLevel() < 2) {
                return "at least 2 levels of fishing skill required to buy fiberglass rod";
            }

            sold[4] += 1;
            buyer.addToBackPack(new FishingPole(ToolLevel.FIBERGLASS), amount);
            return "you've fiberglass rod pole with price 1800";
        }
        else if(productName.equalsIgnoreCase("Iridium Rod")) {
            if(amount * 7500 > buyer.getGold()) {
                return "not enough gold to buy " + amount + " iridium rod";
            }
            else if(buyer.getFishingLevel() < 4) {
                return "at least 4 levels of fishing skill required to buy iridium rod";
            }

            sold[5] += 1;
            buyer.addToBackPack(new FishingPole(ToolLevel.IRIDIUM), amount);
            return "you've bought iridium rod with price 7500";
        }

        return "";
    }

    @Override
    public String displayItems() {
        return "Name    Description    Price\n" +
                """
                Fish Smoker (Recipe)	"A recipe to make Fish Smoker"	10,000g
                Trout Soup	"Pretty salty."	250g
                Bamboo Pole	"Use in the water to catch fish."	500g
                Training Rod	"It's a lot easier to use than other rods, but can only catch basic fish."	25g
                Fiberglass Rod	"Use in the water to catch fish."	1,800g
                Iridium Rod	"Use in the water to catch fish."	7,500g
                """;
    }

    @Override
    public String displayAvailableItems() {
        StringBuilder display = new StringBuilder();
        display.append("Name    Description    Price\n");

        if(sold[0] == 0) {
            display.append("Fish Smoker (Recipe)\t\"A recipe to make Fish Smoker\"\t10,000g");
        }
        if(sold[1] == 0) {
            display.append("Trout Soup\t\"Pretty salty.\"\t250g");
        }
        if(sold[2] == 0) {
            display.append("Bamboo Pole\t\"Use in the water to catch fish.\"\t500g");
        }
        if(sold[3] == 0) {
            display.append("Training Rod\t\"It's a lot easier to use than other rods, but can only catch basic fish.\"\t25g");
        }
        if(sold[4] == 0) {
            display.append("Fiberglass Rod\t\"Use in the water to catch fish.\"\t1,800g");
        }
        if(sold[5] == 0) {
            display.append("Iridium Rod\tUse in the water to catch fish.\t7,500g");
        }

        return display.toString();
    }
}

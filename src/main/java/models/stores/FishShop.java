package models.stores;

import models.Player;
import models.map.AreaType;
import models.map.Tile;

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
        return false;
    }

    @Override
    public String sell(Player buyer, String productName, int amount) {
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
            display.append("Fiberglass Rod\t\"Use in the water to catch fish.\"\t1,800g");
        }

        return display.toString();
    }
}

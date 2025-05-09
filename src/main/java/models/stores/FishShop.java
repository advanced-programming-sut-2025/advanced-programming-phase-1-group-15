package models.stores;

import models.animals.Fish;
import models.map.AreaType;
import models.map.Tile;
import models.time.DateAndTime;
import models.tools.FishingPole;

import java.util.ArrayList;

public class FishShop extends Store {
    public static int[] coordinates = {48, 51, 12, 15};

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

    }

    @Override
    public String displayItems() {
        return "Name    Description    Price\n" +
                """
                Fish Smoker (Recipe)	A recipe to make Fish Smoker	10,000g
                Trout Soup	Pretty salty.	250g
                Bamboo Pole	Use in the water to catch fish.	500g
                Training Rod	It's a lot easier to use than other rods, but can only catch basic fish.	25g
                Fiberglass Rod	Use in the water to catch fish.	1,800g
                Iridium Rod	Use in the water to catch fish.	7,500g
                """;
    }
}

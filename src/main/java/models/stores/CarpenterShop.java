package models.stores;

import models.Player;
import models.foraging.ForagingMineral;
import models.map.AreaType;
import models.map.Tile;

import java.util.ArrayList;

public class CarpenterShop extends Store {
    public static int[] coordinates = {51, 55, 6, 9};

    public CarpenterShop(ArrayList<ArrayList<Tile>> storeTiles) {
        runner = Runner.ROBIN;
        opensAt = 9;
        closesAt = 20;

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
    public boolean checkAvailable(String productName) {
        return true;
    }

    @Override
    public boolean checkAmount(String productName, int amount) {
        return true;
    }

    @Override
    public String sell(Player buyer, String productName, int amount) {
        return "";
    }

    @Override
    public String displayItems() {
        return "Name    Description    Price\n" +
                """
                Wood	A sturdy, yet flexible plant material with a wide variety of uses.	10g
                Stone	A common material with many uses in crafting and building.	20g
                """ +
                "and farm buildings: \n" +
                """
                Barn	Houses 4 barn-dwelling animals.	6,000g Wood (350) Stone (150)
                Big Barn	Houses 8 barn-dwelling animals. Unlocks goats.	12,000g Wood (450) Stone (200)
                Deluxe Barn	Houses 12 barn-dwelling animals. Unlocks sheep and pigs.	25,000g Wood (550) Stone (300)
                Coop	Houses 4 coop-dwelling animals.	4,000g Wood (300) Stone (100)
                Big Coop	Houses 8 coop-dwelling animals. Unlocks ducks.	10,000g Wood (400) Stone (150)
                Deluxe Coop	Houses 12 coop-dwelling animals. Unlocks rabbits.	20,000g Wood (500) Stone (200)
                Well	Provides a place for you to refill your watering can.	1,000g Stone (75)
                Shipping Bin	Items placed in it will be included in the nightly shipment.	250g Wood (150)
                """;
    }

    @Override
    public String displayAvailableItems() {
        return displayItems();
    }
}

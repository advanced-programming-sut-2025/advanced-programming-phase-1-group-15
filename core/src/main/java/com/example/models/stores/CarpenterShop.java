package com.example.models.stores;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.foraging.Stone;
import com.example.models.map.AreaType;
import com.example.models.map.Tile;
import com.example.models.tools.ShippingBin;
import com.example.views.GameAssetManager;

import java.util.ArrayList;

public class CarpenterShop extends Store {
    public static int[] coordinates = {50, 68, 120, 135};

    public CarpenterShop(ArrayList<ArrayList<Tile>> storeTiles) {
        super(storeTiles);
        this.areaType = AreaType.STORE;

        runner = Runner.ROBIN;
        opensAt = 9;
        closesAt = 20;

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
    public TextureRegion getTexture() {
        return GameAssetManager.carpenter_shop;
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
        if(productName.equalsIgnoreCase("wood")) {
            if(amount * 10 > buyer.getGold()) {
                return "not enough gold to buy " + amount + " wood";
            }

            buyer.subtractGold(amount * 10);
            buyer.addToBackPack(new GeneralItem(GeneralItemsType.WOOD), amount);
            return "you've bought " + amount + " wood with price " + amount * 10;
        }
        else if(productName.equalsIgnoreCase("stone")) {
            if(amount * 20 > buyer.getGold()) {
                return "not enough gold to buy " + amount + " stone";
            }

            buyer.subtractGold(amount * 20);
            buyer.addToBackPack(new Stone(), amount);
            return "you've bought " + amount + " stone with price " + amount * 20;
        }
        else if(productName.equalsIgnoreCase("shipping bin")) {
            if(amount * 250 > buyer.getGold()) {
                return "not enough gold to buy " + amount + " shipping bin";
            }
            if(amount * 150 > buyer.getWood()) {
                return "not enough wood to buy " + amount + " shipping bin";
            }

            buyer.subtractGold(amount * 250);
            buyer.subtractWood(amount * 150);
            ShippingBin shippingBin = new ShippingBin(buyer);
            App.currentGame.getDateAndTime().addObserver(shippingBin);
            buyer.getFarm().place(shippingBin);
            return "you've bought " + amount + " shipping bin with price " + amount * 250;
        }

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

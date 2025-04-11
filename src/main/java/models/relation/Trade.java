package models.relation;

import models.Player;
import models.map.Tilable;

import java.util.HashMap;

public class Trade {
    private int id;
    private Player seller;
    private Player buyer;

    private HashMap<Tilable,Integer> requestedProducts;
    private int requestedGold;
    private HashMap<Tilable,Integer> offeredProduct;

    public Trade(int id, Player seller, Player buyer) {
        this.id = id;
        this.seller = seller;
        this.buyer = buyer;
    }
}

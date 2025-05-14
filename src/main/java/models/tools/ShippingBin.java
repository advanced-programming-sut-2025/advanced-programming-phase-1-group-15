package models.tools;

import models.Player;
import models.map.Tilable;
import models.time.DateAndTime;
import models.time.TimeObserver;

import java.util.HashMap;

public class ShippingBin implements Tilable, TimeObserver {
    Player owner;
    private HashMap<BackPackable, Integer> items = new HashMap<>();

    public ShippingBin(Player owner) {
        this.owner = owner;
    }

    public void addToBin(BackPackable item, int amount) {
        for(BackPackable bp : items.keySet()) {
            if (bp.getName().equalsIgnoreCase(item.getName())) {
                items.put(bp, items.get(bp) + amount);
                return;
            }
        }

        items.put(item, amount);
    }

    public void removeFromBin(BackPackable item) {
        items.remove(item);
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == 9) {
            for(BackPackable item : items.keySet()) {
                owner.addGold(item.getPrice());
            }
            items.clear();
        }
    }
}
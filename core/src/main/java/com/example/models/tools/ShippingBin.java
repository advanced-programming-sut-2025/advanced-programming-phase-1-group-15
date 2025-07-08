package com.example.models.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.Player;
import com.example.models.map.Tilable;
import com.example.models.time.DateAndTime;
import com.example.models.time.TimeObserver;

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
                owner.addGold(items.get(item) * item.getPrice());
            }
            items.clear();
        }
    }


    @Override
    public Sprite getSprite() {
        return null;
    }
}

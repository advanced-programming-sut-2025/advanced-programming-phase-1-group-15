package com.example.models.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.map.Tilable;

import java.util.HashMap;

public class Fridge implements Tilable {
    private HashMap<BackPackable, Integer> items = new HashMap<>();

    public HashMap<BackPackable, Integer> getItems() {
        return items;
    }

    public BackPackable getItemByName(String name) {
        for (BackPackable item : items.keySet()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public int getItemCount(String name) {
        BackPackable item = getItemByName(name);
        return items.getOrDefault(item, 0);
    }

    public void addToFridge(BackPackable item, int amount) {
        for(BackPackable bp : items.keySet()) {
            if (bp.getName().equalsIgnoreCase(item.getName())) {
                items.put(bp, items.get(bp) + amount);
                return;
            }
        }
        items.put(item, amount);
    }

    public void removeFromFridge(BackPackable item) {
        items.remove(item);
    }
    public boolean removeCountFromFridge(BackPackable item, int amount) {
        if (items.get(item) == null){
            return false;
        }
        if(amount == items.get(item)) {
            removeFromFridge(item);
            return true;
        }
        if(amount > items.get(item)) {
            return false;
        }
        items.put(item, items.get(item) - amount);
        return true;
    }
    public String display() {
        if(items.isEmpty()) {
            return "You don't have any items in your fridge\n";
        }

        StringBuilder display = new StringBuilder();
        display.append("Fridge: \n");
        for(BackPackable item : items.keySet()) {
            display.append(item.getName()).append(" ");
            display.append(items.get(item)).append("\n");
        }

        return display.toString();
    }

    @Override
    public Sprite getSprite(){
        return null;
    }
}

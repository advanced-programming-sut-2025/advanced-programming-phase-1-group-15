package models.tools;

import java.util.HashMap;

public class BackPack {
    private boolean big;
    private boolean deluxe;

    private int Capacity;
    private HashMap<BackPackable, Integer> items = new HashMap<>();


    public HashMap<BackPackable, Integer> getItems() {
        return items;
    }

    public int getCapacity() {
        return Capacity;
    }
    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public void upgrade() {

    }

    public String display() {
        if(items.isEmpty()) {
            return "You don't have any items in your inventory";
        }

        StringBuilder display = new StringBuilder();
        display.append("BackPack: \n");
        for(BackPackable item : items.keySet()) {
            display.append(item); display.append(" ");
            display.append(items.get(item)); display.append("\n");
        }

        return display.toString();
    }
}

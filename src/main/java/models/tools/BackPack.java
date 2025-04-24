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

    public void addToBackPack(BackPackable item, int amount) {
        items.put(item, items.getOrDefault(item, 0) + amount);
    }
    public void removeCountFromBackPack(BackPackable item, int amount) {
        items.put(item, items.get(item) - amount);
    }
    public void removeFromBackPack(BackPackable item) {
        items.remove(item);
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
            display.append(item.getName()); display.append(" ");
            display.append(items.get(item)); display.append("\n");
        }

        return display.toString();
    }
}

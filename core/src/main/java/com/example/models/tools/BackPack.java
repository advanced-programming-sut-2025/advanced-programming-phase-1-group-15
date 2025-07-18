package com.example.models.tools;

import com.example.models.Player;
import com.example.models.foraging.Stone;

import java.util.ArrayList;
import java.util.HashMap;

public class BackPack extends Tool {
    private int itemsCount;
    private int capacity;
    private HashMap<BackPackable, Integer> items = new HashMap<>();
    public BackPack() {
        this.toolType = ToolType.BACKPACK;
        this.toolLevel = ToolLevel.NORMAL;
        this.itemsCount = 0;
        this.capacity = 12;
        this.description = "use to carry your inventory.";

        items.put(new Hoe(), 1);
        items.put(new Pickaxe(), 1);
        items.put(new Axe(), 1);
        items.put(new WateringCan(), 1);
        items.put(new Scythe(), 1);
        items.put(new FishingPole(), 1);
    }

    public int getCapacity() {
        return capacity;
    }

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

    public FishingPole getFishingPole(String material) {
        for(BackPackable item : items.keySet()) {
            if(item instanceof FishingPole fishingPole) {
                if(fishingPole.getToolLevel().name().equalsIgnoreCase(material)) {
                    return fishingPole;
                }
            }
        }
        return null;
    }

    public boolean checkFilled() {
        return itemsCount == capacity;
    }
    public void addToBackPack(BackPackable item, int amount) {
        if(item instanceof Tool) {
            items.put(item, 1);
            return;
        }

        for(BackPackable bp : items.keySet()) {
            if (bp.getName().equalsIgnoreCase(item.getName())) {
                items.put(bp, items.get(bp) + amount);
                return;
            }
        }

        items.put(item, amount);
        itemsCount++;
    }

    public boolean removeCountFromBackPack(BackPackable item, int amount) {
        if(amount == items.get(item)) {
            removeFromBackPack(item);
            return true;
        }
        if(amount > items.get(item)) {
            return false;
        }
        items.put(item, items.get(item) - amount);
        return true;
    }
    public void removeFromBackPack(BackPackable item) {
        items.remove(item);
        itemsCount--;
    }

    @Override
    public String upgrade(Player user) {
        switch (toolLevel) {
            case NORMAL -> {
                capacity = 24;
                toolLevel = ToolLevel.LARGE;
            }
            case LARGE -> {
                capacity = Integer.MAX_VALUE;
                toolLevel = ToolLevel.DELUXE;
            }
        }
        return "you can't upgrade this tool.";
    }

    public String display() {
        if(items.isEmpty()) {
            return "You don't have any items in your inventory\n";
        }

        StringBuilder display = new StringBuilder();
        display.append("BackPack: (").append(toolLevel).append(" ").append(itemsCount).append("/").append(capacity).append(")\n");
        for(BackPackable item : items.keySet()) {
            display.append(item.getName()).append(" ");
            display.append(items.get(item)).append("    \"");
            display.append(item.getDescription()).append("\"\n");
        }

        return display.toString();
    }

    public ArrayList<Tool> getTools() {
        ArrayList<Tool> tools = new ArrayList<>();

        for(BackPackable item : items.keySet()) {
            if(item instanceof Tool tool) {
                tools.add(tool);
            }
        }

        return tools;
    }
}

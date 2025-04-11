package models.tools;

import java.util.HashMap;

public class BackPack {
    private boolean big;
    private boolean deluxe;

    private int Capacity;
    private HashMap<BackPackable, Integer> items = new HashMap<>();

    public BackPack(boolean big, boolean deluxe) {

    }


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
}

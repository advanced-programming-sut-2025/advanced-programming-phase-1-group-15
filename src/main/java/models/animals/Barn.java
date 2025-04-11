package models.animals;

import models.map.Position;

import java.util.ArrayList;

public class Barn {
    private int capacity;
    private boolean Big;
    private boolean Deluxe;

    private ArrayList<Animal> animals = new ArrayList<>();

    private Position position;

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isBig() {
        return Big;
    }
    public void setBig(boolean big) {
        Big = big;
    }

    public boolean isDeluxe() {
        return Deluxe;
    }
    public void setDeluxe(boolean deluxe) {
        Deluxe = deluxe;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public Position getPosition() {
        return position;
    }

    public void build() {

    }
}

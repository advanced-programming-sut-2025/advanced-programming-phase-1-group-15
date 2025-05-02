package models.animals;

import models.map.Area;
import models.map.AreaType;
import models.map.Position;
import models.time.DateAndTime;

import java.util.ArrayList;

public class Barn extends Area {
    private int animalCount = 0;
    private int capacity = 4;
    private boolean Big = false;
    private boolean Deluxe = false;

    private ArrayList<Animal> animals = new ArrayList<>();

    public Barn() {
        this.areaType = AreaType.BARN;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isBig() {
        return Big;
    }
    public void setBig() {
        Big = true;
        this.capacity = 8;
    }

    public boolean isDeluxe() {
        return Deluxe;
    }
    public void setDeluxe() {
        Deluxe = true;
        this.capacity = 12;
    }

    public void addAnimal(Animal animal) {
        if(animalCount < capacity) {
            animals.add(animal);
        }
    }
    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    @Override
    public void build() {

    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

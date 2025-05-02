package models.animals;

import models.map.Position;
import models.map.Tilable;
import models.time.DateAndTime;
import models.time.TimeObserver;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Animal implements Tilable, TimeObserver {
    private AnimalType animalType;
    private String name;

    private ArrayList<AnimalProduct> animalProducts = new ArrayList<>();

    private int friendship;
    private boolean Petted;
    private boolean Fed;

    private Position position;

    public Animal(AnimalType animalType, String name) {
        this.animalType = animalType;
        this.name = name;
        this.friendship = 0;
        this.Petted = false;
        this.Fed = false;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }
    public String getAnimalTypeName() {
        return animalType.name().toLowerCase();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return animalType.price;
    }

    public ArrayList<AnimalProduct> getProducts() {
        return animalProducts;
    }
    public void produce() {

    }

    public int getFriendship() {
        return friendship;
    }

    public boolean isPetted() {
        return Petted;
    }

    public boolean isFed() {
        return Fed;
    }

    public void pet() {

    }
    public void shepherd(Position newPosition) {

    }
    public void feed() {

    }

    public static Animal animalFactory(String animalType, String name) {
        for(AnimalType at: AnimalType.values()) {
            if(at.name().toLowerCase().equals(animalType)) {
                return new Animal(at, name);
            }
        }
        return null;
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

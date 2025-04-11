package models.animals;

import models.map.Position;
import models.map.Tilable;
import models.time.DateAndTime;
import models.time.TimeObserver;

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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

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

    private int friendship = 0;
    private boolean petted = false;
    private boolean fed = false;

    private Position position;

    public Animal(AnimalType animalType, String name) {
        this.animalType = animalType;
        this.name = name;
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

    public Maintenance getMaintenance() {
        return animalType.maintenance;
    }
    public int getPrice() {
        return animalType.price;
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public ArrayList<AnimalProduct> getProducts() {
        return animalProducts;
    }
    public void produce() {

    }

    public int getFriendship() {
        return friendship;
    }

    public void pet() {
        petted = true;
        friendship += 15;
    }
    public boolean isPetted() {
        return petted;
    }

    public void feed() {
        fed = true;
        friendship += 8;
    }
    public boolean isFed() {
        return fed;
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
        if(dateAndTime.getHour() == 9) {
            petted = false;
            fed = false;
        }
    }
}

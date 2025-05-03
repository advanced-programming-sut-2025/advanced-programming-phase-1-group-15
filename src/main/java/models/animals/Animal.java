package models.animals;

import models.map.Position;
import models.map.Tilable;
import models.time.DateAndTime;
import models.time.TimeObserver;

import java.util.ArrayList;

public class Animal implements Tilable, TimeObserver {
    private final AnimalType animalType;
    private String name;

    private final ArrayList<AnimalProductType> animalProductTypes = new ArrayList<>();
    private AnimalProduct currentProduct;

    private int friendship = 0;
    private boolean petted = false;
    private boolean fed = false;

    private Position position;

    public Animal(AnimalType animalType, String name) {
        this.animalType = animalType;
        switch (animalType) {
            case CHICKEN -> {
                this.animalProductTypes.add(AnimalProductType.EGG);
                this.animalProductTypes.add(AnimalProductType.LARGE_EGG);
            }
            case DUCK -> {
                this.animalProductTypes.add(AnimalProductType.DUCK_EGG);
                this.animalProductTypes.add(AnimalProductType.DUCK_FEATHER);
            }
            case RABBIT -> {
                this.animalProductTypes.add(AnimalProductType.RABBIT_WOOL);
                this.animalProductTypes.add(AnimalProductType.RABBIT_LEG);
            }
            case DINOSAUR -> this.animalProductTypes.add(AnimalProductType.DINOSAUR_EGG);
            case COW -> {
                this.animalProductTypes.add(AnimalProductType.COW_MILK);
                this.animalProductTypes.add(AnimalProductType.COW_LARGE_MILK);
            }
            case GOAT -> {
                this.animalProductTypes.add(AnimalProductType.GOAT_MILK);
                this.animalProductTypes.add(AnimalProductType.GOAT_LARGE_MILK);
            }
            case SHEEP -> this.animalProductTypes.add(AnimalProductType.SHEEP_WOOL);
            case PIG -> this.animalProductTypes.add(AnimalProductType.TRUFFLE);
        }
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
    public int getBasePrice() {
        return animalType.price;
    }
    public int getPrice() {
        return (int) (animalType.price * (((double) friendship /1000) + 0.3));
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public int getFriendship() {
        return friendship;
    }
    public void setFriendship(int friendship) {
        this.friendship = friendship;
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

    public ProductQuality calculateQuality() {
        double random = Math.random();
        double quality = ((double) friendship /1000) * (0.5 + 0.5 * random);

        if(quality <= 0.5) {
            return ProductQuality.NORMAL;
        }
        else if(quality <= 0.7) {
            return ProductQuality.SILVER;
        }
        else if(quality <= 0.9) {
            return ProductQuality.GOLD;
        }
        else {
            return ProductQuality.IRIDIUM;
        }
    }
    public void produce() {
        if(friendship > 100 && animalProductTypes.size() > 1) {
            double random = 0.5 + Math.random();
            double probability = (friendship + 150 * random) / 1500;

            if(probability > 0.5) {
                currentProduct = new AnimalProduct(animalProductTypes.get(1), calculateQuality());
            }
            else {
                currentProduct = new AnimalProduct(animalProductTypes.get(0), calculateQuality());
            }
        }
        else {
            currentProduct = new AnimalProduct(animalProductTypes.get(0), calculateQuality());
        }
    }
    public AnimalProduct getCurrentProduct() {
        return currentProduct;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == 9) {
            if(fed && dateAndTime.getDay() % animalType.period == 0) {
                produce();
            }
            else {
                currentProduct = null;
            }
            petted = false;
            fed = false;
        }
    }
}

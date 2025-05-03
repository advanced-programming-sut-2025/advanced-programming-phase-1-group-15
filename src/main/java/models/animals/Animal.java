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
    private AnimalProduct currentProduct;

    private int friendship = 0;
    private boolean petted = false;
    private boolean fed = false;

    private Position position;

    public Animal(AnimalType animalType, String name) {
        this.animalType = animalType;
        switch (animalType) {
            case CHICKEN -> {
                this.animalProducts.add(new AnimalProduct(AnimalProductType.EGG));
                this.animalProducts.add(new AnimalProduct(AnimalProductType.LARGE_EGG));
            }
            case DUCK -> {
                this.animalProducts.add(new AnimalProduct(AnimalProductType.DUCK_EGG));
                this.animalProducts.add(new AnimalProduct(AnimalProductType.DUCK_FEATHER));
            }
            case RABBIT -> {
                this.animalProducts.add(new AnimalProduct(AnimalProductType.RABBIT_WOOL));
                this.animalProducts.add(new AnimalProduct(AnimalProductType.RABBIT_LEG));
            }
            case DINOSAUR -> {
                this.animalProducts.add(new AnimalProduct(AnimalProductType.DINOSAUR_EGG));
            }
            case COW -> {
                this.animalProducts.add(new AnimalProduct(AnimalProductType.COW_MILK));
                this.animalProducts.add(new AnimalProduct(AnimalProductType.COW_LARGE_MILK));
            }
            case GOAT -> {
                this.animalProducts.add(new AnimalProduct(AnimalProductType.GOAT_MILK));
                this.animalProducts.add(new AnimalProduct(AnimalProductType.GOAT_LARGE_MILK));
            }
            case SHEEP -> {
                this.animalProducts.add(new AnimalProduct(AnimalProductType.SHEEP_WOOL));
            }
            case PIG -> {
                this.animalProducts.add(new AnimalProduct(AnimalProductType.TRUFFLE));
            }
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
        if(friendship > 100) {

        }
        else {
            currentProduct = new AnimalProduct(animalProducts.get(0).getProductType(), 1, calculateQuality());
        }
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == 9) {
            if(fed) {
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

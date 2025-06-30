package com.example.models.animals;

public enum AnimalProductType{
    EGG(50),
    LARGE_EGG(95),
    DUCK_EGG(95),
    DUCK_FEATHER(250),
    RABBIT_WOOL(340),
    RABBIT_LEG(565),
    DINOSAUR_EGG(350),
    COW_MILK(125),
    COW_LARGE_MILK(190),
    GOAT_MILK(225),
    GOAT_LARGE_MILK(345),
    SHEEP_WOOL(340),
    TRUFFLE(625);
    public final int price;

    AnimalProductType(int price) {
        this.price = price;
    }
    public String getName(){
        return this.name().toLowerCase().replaceAll("_", " ");
    }
}

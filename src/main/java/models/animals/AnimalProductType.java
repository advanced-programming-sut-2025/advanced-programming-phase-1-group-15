package models.animals;

public enum AnimalProductType {
    EGG(50),
    LARGE_EGG(95),
    DUCK_EGG(95),
    DUCK_FEATHER(250),
    RABBIT_FLUFF(340),
    RABBIT_FOOT(565),
    DINOSAUR_EGG(1400),
    COW_MILK(125),
    COW_LARGE_MILK(190),
    GOAT_MILK(225),
    GOAT_LARGE_MILK(345),
    SHEEP_FLUFF(340),
    TRUFFLE(625);
    public final int price;

    AnimalProductType(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}

package models.animals;

public enum AnimalProductType {
    EGG(50);

    public final int price;

    AnimalProductType(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}

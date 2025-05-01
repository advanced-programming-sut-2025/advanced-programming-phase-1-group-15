package models.animals;

public enum AnimalType {
    CHICKEN(Maintenance.COOP, 800),
    DUCK(Maintenance.COOP, 1200),
    RABBIT(Maintenance.COOP, 8000),
    DINOSAUR(Maintenance.COOP, 14000),
    COW(Maintenance.BARN, 1500),
    GOAT(Maintenance.BARN, 4000),
    SHEEP(Maintenance.BARN, 8000),
    PIG(Maintenance.BARN, 16000);

    public final Maintenance maintenance;
    public final int price;

    AnimalType(Maintenance maintenance, int price) {
        this.maintenance = maintenance;
        this.price = price;
    }
}

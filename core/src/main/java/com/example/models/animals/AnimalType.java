package com.example.models.animals;

public enum AnimalType {
    CHICKEN(Maintenance.COOP, 800, 1),
    DUCK(Maintenance.COOP, 1200, 2),
    RABBIT(Maintenance.COOP, 8000, 4),
    DINOSAUR(Maintenance.COOP, 14000, 7),
    COW(Maintenance.BARN, 1500, 1),
    GOAT(Maintenance.BARN, 4000, 2),
    SHEEP(Maintenance.BARN, 8000, 3),
    PIG(Maintenance.BARN, 16000, 1);

    public final Maintenance maintenance;
    public final int price;
    public final int period;

    AnimalType(Maintenance maintenance, int price, int period) {
        this.maintenance = maintenance;
        this.price = price;
        this.period = period;
    }
}

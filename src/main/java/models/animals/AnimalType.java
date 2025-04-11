package models.animals;

public enum AnimalType {
    CHICKEN("Coop", 800),
    DUCK("Coop", 1200),
    RABBIT("Coop", 8000),
    DINOSAUR("Coop", 14000),
    COW("Barn", 1500),
    GOAT("Barn", 4000),
    SHEEP("Barn", 8000),
    PIG("Barn", 16000);

    public final String maintenance;
    public final int price;

    AnimalType(String maintenance, int price) {
        this.maintenance = maintenance;
        this.price = price;
    }

    public String getTypeName() {
        return switch (this) {
            case CHICKEN -> "Chicken";
            case DUCK -> "Duck";
            case RABBIT -> "Rabbit";
            case DINOSAUR -> "Dinosaur";
            case COW -> "Cow";
            case GOAT -> "Goat";
            case SHEEP -> "Sheep";
            case PIG -> "Pig";
        };
    }
}

package models.farming;

public interface Harvestable {
    public void harvest();

    public int getDaysUntilHarvest();
    public String printInfo();
}

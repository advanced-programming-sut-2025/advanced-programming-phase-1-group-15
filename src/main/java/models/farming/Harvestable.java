package models.farming;

import java.util.ArrayList;

public interface Harvestable {
    public void harvest();
    public ArrayList<Integer> getStages();

    public int getDaysUntilHarvest();
    public String printInfo();
}

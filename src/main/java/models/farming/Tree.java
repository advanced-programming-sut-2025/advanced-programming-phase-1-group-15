package models.farming;

import models.map.Tilable;
import models.time.DateAndTime;
import models.time.TimeObserver;
import models.tools.BackPackable;

public class Tree implements Tilable, TimeObserver , Harvestable{
    private TreeType treeType;

    private GiantProducts giantProducts;

    private int DaysUntilHarvest;

    public int getDaysUntilHarvest() {
        return DaysUntilHarvest;
    }
    public TreeType getTreeType() {
        return treeType;
    }
    public SeedType getSeedType() {
        return treeType.seedType;
    }
    public void setTreeType(TreeType treeType) {
        this.treeType = treeType;
    }
    public Tree(){
    }  // TODO: should be checked
    @Override
    public void update(DateAndTime dateAndTime) {

    }

    @Override
    public void harvest() {

    }

    @Override
    public String printInfo() {
        return " ";
    }
}

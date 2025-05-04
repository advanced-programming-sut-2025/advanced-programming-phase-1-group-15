package models.farming;

import models.map.Tilable;
import models.time.DateAndTime;
import models.time.TimeObserver;

import java.util.ArrayList;

public class Tree extends Harvestable implements Tilable, TimeObserver {
    private TreeType treeType;

    public TreeType getTreeType() {
        return treeType;
    }
    public SeedType getSeedType() {
        return treeType.seedType;
    }
    public void setTreeType(TreeType treeType) {
        this.treeType = treeType;
        this.daysUntilHarvest = treeType.getTotalHarvestTime();
    }

    public Tree(TreeType treeType) {
        this.treeType = treeType;
        this.daysUntilHarvest = treeType.getTotalHarvestTime();
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }

    @Override
    public void harvest() {
        if (daysUntilHarvest > 0) {
            return;
        }
    }

    @Override
    public String printInfo() {
        return "";
    }

    @Override
    public ArrayList<Integer> getStages() {
        return null;
    }

    @Override
    public boolean isOneTime() {
        return false;
    }
}

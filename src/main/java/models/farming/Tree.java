package models.farming;

import models.App;
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
    public void harvest(int number) {
        App.currentGame.getCurrentPlayer().addToBackPack(new Fruit(this),number);
    }

    @Override
    public ArrayList<Integer> getStages() {
        return treeType.getStages();
    }

    @Override
    public boolean isOneTime() {
        return false;
    }

    public String getName(){
        return treeType.name().toLowerCase().replaceAll("_", " ");
    }
}

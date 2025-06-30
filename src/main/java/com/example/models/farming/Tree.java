package com.example.models.farming;

import com.example.models.App;
import com.example.models.map.Tilable;
import com.example.models.time.TimeObserver;

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
        return null;
    }

    @Override
    public boolean isOneTime() {
        return false;
    }

    public String getName(){
        return treeType.name().toLowerCase().replaceAll("_", " ");
    }
}

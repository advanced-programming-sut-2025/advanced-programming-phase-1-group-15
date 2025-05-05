package models.farming;

import models.tools.BackPackable;

public class Fruit implements BackPackable {
    private final FruitType fruitType;
    private final Tree sourceTree;

    public Fruit(Tree sourceTree) {
        this.sourceTree = sourceTree;
        this.fruitType   = sourceTree.getTreeType().getFruitType();
    }

    public FruitType getFruitType() {
        return fruitType;
    }

    public Tree getSourceTree() {
        return sourceTree;
    }

    public String getName() {
        return fruitType.name().toLowerCase();
    }

    @Override
    public String getDescription() {
        return fruitType.name().toLowerCase() + " fruit";
    }

    @Override
    public int getPrice() {
        return fruitType.getPrice();
    }

    @Override
    public String toString() {
        return String.format("%s", getName());
    }

}

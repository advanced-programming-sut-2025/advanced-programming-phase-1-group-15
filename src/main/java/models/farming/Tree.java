package models.farming;

import models.map.Tilable;
import models.time.DateAndTime;
import models.time.TimeObserver;

public class Tree implements Tilable, TimeObserver {
    private TreeType treeType;

    public TreeType getTreeType() {
        return treeType;
    }
    public void setTreeType(TreeType treeType) {
        this.treeType = treeType;
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

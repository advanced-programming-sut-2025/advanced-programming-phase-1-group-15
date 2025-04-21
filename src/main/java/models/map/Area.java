package models.map;

import java.util.ArrayList;

public abstract class Area {
    protected ArrayList<Tile> tiles;

    protected ArrayList<Area> innerAreas;

    public abstract void build();
}

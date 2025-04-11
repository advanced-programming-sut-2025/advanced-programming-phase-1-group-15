package models.map;

import java.util.ArrayList;

public abstract class Area {
    protected ArrayList<Tile> tiles = new ArrayList<>();

    protected ArrayList<Area> innerAreas = new ArrayList<>();

    public abstract void build();
}

package models.map;

public class Tile {
    private Position position;
    private Tilable objectInTile;
    private Area area;

    public Position getPosition() {
        return position;
    }

    public void put(Tilable object) {
        objectInTile = object;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isAdjacent(Tile tile) {
        return false;
    }
}

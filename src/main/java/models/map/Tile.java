package models.map;

public class Tile {
    private final Position position;
    private Tilable objectInTile;
    private Area area;

    public Tile(int x, int y) {
        this.position = new Position(x, y);
        this.objectInTile = null;
        this.area = null;
    }

    public Position getPosition() {
        return position;
    }

    public void put(Tilable object) {
        objectInTile = object;
    }
    public boolean isEmpty() {
        if (objectInTile == null) {
            return true;
        }
        return false;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    public Area getArea() {
        return area;
    }

    public boolean isAdjacent(Tile tile) {
        return false;
    }

    public char print() {
        if(objectInTile == null) {
            return '.';
        }
        return '*';
    }
}

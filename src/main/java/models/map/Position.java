package models.map;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean equals(Position position) {
        return x == position.x && y == position.y;
    }

    public boolean outOfBounds() {
        return x < 0 || y < 0 || x > Map.COLS || y > Map.ROWS;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

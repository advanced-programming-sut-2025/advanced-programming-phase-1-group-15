package models.map;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public boolean equals(Position position) {
        return x == position.x && y == position.y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

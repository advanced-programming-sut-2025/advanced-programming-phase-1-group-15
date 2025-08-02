package com.example.common.map;

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

    public boolean isAdjacent(Position otherPosition) {
        return (Math.abs(x - otherPosition.x) <= 1) && (Math.abs(y - otherPosition.y) <= 1);
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

    public Position getRight(){
        return new Position(x+1, y);
    }

    public Position getLeft(){
        return new Position(x-1, y);
    }

    public Position getUp(){
        return new Position(x,y-1);
    }

    public Position getDown(){
        return new Position(x,y+1);
    }
}

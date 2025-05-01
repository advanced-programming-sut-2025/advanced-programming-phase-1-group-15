package models.map;

import models.App;
import models.farming.GeneralPlants.PloughedTile;
import models.farming.Tree;
import models.foraging.ForagingMineral;
import models.foraging.Stone;
import models.tools.Fridge;

import java.util.ArrayList;

public class Tile {
    private final Position position;

    public void setObjectInTile(Tilable objectInTile) {
        this.objectInTile = objectInTile;
    }

    private Tilable objectInTile;
    private Area area;

    boolean plowed = false;
    boolean watered = false;

    public Tile(int x, int y) {
        this.position = new Position(x, y);
        this.objectInTile = null;
        this.area = null;
    }

    public static ArrayList<ArrayList<Tile>> buildMapTiles() {
        ArrayList<ArrayList<Tile>> mapTiles = new ArrayList<>();

        for (int row = 0; row < Map.ROWS; row++) {
            mapTiles.add(new ArrayList<>());
            for (int col = 0; col < Map.COLS; col++) {
                mapTiles.get(row).add(new Tile(col, row));
            }
        }

        return mapTiles;
    }

    public Position getPosition() {
        return position;
    }

    public Tilable getObjectInTile() {
        return objectInTile;
    }
    public void put(Tilable object) {
        objectInTile = object;
    }
    public void empty() {
        objectInTile = null;
    }
    public boolean isEmpty() {
        return objectInTile == null;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    public Area getArea() {
        return area;
    }
    public AreaType getAreaType() {
        return area.areaType;
    }

    public boolean isWalkable(){
        AreaType type = getAreaType();

        if(type.equals(AreaType.LAKE)) {
            return false;
        }

        return objectInTile == null;
    }

    public boolean isAdjacent(Tile otherTile) {
        return (Math.abs(position.x - otherTile.position.x) <= 1) && (Math.abs(position.y - otherTile.position.y) <= 1);
    }

    public void plow() {
        plowed = true;
        objectInTile = new PloughedTile(App.currentGame.getMap(),this.position);
    }
    public void unplow() {
        plowed = false;
        objectInTile = null;
    }
    public boolean isPlowed() {
        return plowed;
    }

    public void water() {
        watered = true;
    }
    public boolean isWatered() {
        return watered;
    }

    public void print() {
        if(area.areaType.equals(AreaType.LAKE)) {
            PrintInColor.printInBlue('~');
        }
        else if(area.areaType.equals(AreaType.HOUSE)) {
            if(objectInTile instanceof House) {
                PrintInColor.printInCyan('F');
            }
            else {
                System.out.print('H');
            }
        }
        else if(area.areaType.equals(AreaType.GREENHOUSE)) {
            PrintInColor.printInGreen('G');
        }
        else if(area.areaType.equals(AreaType.QUARRY)) {
            PrintInColor.printInBrown('Q');
        }
        else if(objectInTile != null) {
            if(objectInTile instanceof Tree) {
                PrintInColor.printInBrightGreen('T');
            }
            else if(objectInTile instanceof Stone) {
                PrintInColor.printInBrightGray('O');
            }
            else if(objectInTile instanceof ForagingMineral) {
                PrintInColor.printInBrightYellow('*');
            }
            else if(objectInTile instanceof Fridge) {
                PrintInColor.printInBlue('F');
            }
        }
        else {
            if(plowed) {
                System.out.print('X');
            }
            else {
                System.out.print('.');
            }
        }
    }
}
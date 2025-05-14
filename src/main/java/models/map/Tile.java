package models.map;

import models.App;
import models.animals.Animal;
import models.animals.Fish;
import models.crafting.CraftItem;
import models.farming.GeneralPlants.*;
import models.farming.Tree;
import models.foraging.ForagingMineral;
import models.foraging.Stone;
import models.stores.*;
import models.tools.Fridge;
import models.tools.ShippingBin;
import models.weather.WeatherManagement;

import java.util.ArrayList;

public class Tile {
    private final Position position;

    public void setObjectInTile(Tilable objectInTile) {
        this.objectInTile = objectInTile;
    }

    private Tilable objectInTile;
    private Area area;

    public void setWatered(boolean watered) {
        this.watered = watered;
    }

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
    public boolean isBuildable() {
        AreaType type = getAreaType();

        return type.equals(AreaType.FARM) && objectInTile == null;
    }

    public boolean isAdjacent(Tile otherTile) {
        return (Math.abs(position.x - otherTile.position.x) <= 1) && (Math.abs(position.y - otherTile.position.y) <= 1);
    }

    public void plow() {
        plowed = true;
        objectInTile = new PloughedPlace(this);
        App.currentGame.getDateAndTime().addObserver((PloughedPlace) objectInTile);
        App.currentGame.getWeather().addObserver((PloughedPlace) objectInTile);
    }
    public void unplow() {
        plowed = false;
        if(objectInTile != null) {
            if(objectInTile instanceof PloughedPlace) {
                PloughedPlace p = (PloughedPlace) objectInTile;
                App.currentGame.getDateAndTime().removeObserver(p);
                App.currentGame.getWeather().removeObserver(p);
            }
        }
        objectInTile = null;
    }
    public boolean isPlowed() {
        return plowed;
    }

    public void water() {
        watered = true;
        if(objectInTile != null) {
            if(objectInTile instanceof PloughedPlace) {
                PloughedPlace p = (PloughedPlace) objectInTile;
                p.getCurrentState().water();
            }
        }
    }
    public boolean isWatered() {
        return watered;
    }

    public void print() {
        if(objectInTile instanceof Animal) {
            Animal animal = (Animal) objectInTile;
            switch (animal.getAnimalType()) {
                case COW -> System.out.print('C');
                case GOAT -> System.out.print('G');
                case SHEEP -> System.out.print('S');
                case PIG -> PrintInColor.printInBrightPurple('P');
                case CHICKEN -> PrintInColor.printInYellow('C');
                case DUCK -> System.out.print('D');
                case RABBIT -> System.out.print('R');
                case DINOSAUR -> PrintInColor.printInGreen('D');
            }
            return;
        }
        if(area.areaType.equals(AreaType.LAKE)) {
            if(objectInTile instanceof Fish) {
                PrintInColor.printInRed('Ɔ');
            }
            else {
                PrintInColor.printInBlue('~');
            }
        }
        else if(area.areaType.equals(AreaType.HOUSE)) {
            if(objectInTile instanceof Fridge) {
                PrintInColor.printInCyan('F');
            }
            else {
                System.out.print('H');
            }
        }
        else if(area instanceof GreenHouse greenHouse) {
            if(greenHouse.isBuilt()) {
                PrintInColor.printInGreen('G');
            }
            else {
                System.out.print('/');
            }
        }
        else if(area.areaType.equals(AreaType.QUARRY)) {
            PrintInColor.printInBrown('#');
        }
        else if(area.areaType.equals(AreaType.BARN)) {
            System.out.print('_');
        }
        else if(area.areaType.equals(AreaType.COOP)) {
            PrintInColor.printInGray('□');
        }
        else if(area.areaType.equals(AreaType.STORE)) {
            if(area instanceof Blacksmith) {
                System.out.print('b');
            }
            else if(area instanceof JojaMart) {
                System.out.print('j');
            }
            else if(area instanceof PierreGeneralStore) {
                System.out.print('p');
            }
            else if(area instanceof CarpenterShop) {
                System.out.print('c');
            }
            else if(area instanceof FishShop) {
                PrintInColor.printInBlue('f');
            }
            else if(area instanceof MarnieRanch) {
                System.out.print('m');
            }
            else if(area instanceof StarDropSaloon) {
                PrintInColor.printInBrightPurple('s');
            }
        }
        else if(area.areaType.equals(AreaType.VILLAGE)) {
            PrintInColor.printInCyan('⌂');
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
            else if(objectInTile instanceof CraftItem) {
                PrintInColor.printInBrightGray('I');
            }
            else if(objectInTile instanceof ShippingBin) {
                PrintInColor.printInPurple('█');
            }
            else if(plowed){
                PloughedPlace p = (PloughedPlace) objectInTile;
                if(p.getCurrentState() instanceof PloughedState){
                    PrintInColor.printInBrown('X');
                }
                else if(p.getCurrentState() instanceof SeededState){
                    PrintInColor.printInGreen('S');
                }
                else if(p.getCurrentState() instanceof WateredState){
                    PrintInColor.printInBlue('W');
                }
                else if(p.getCurrentState() instanceof RestState){
                    PrintInColor.printInGreen('R');
                }
                else {
                    PrintInColor.printInBrightPurple('N');
                }
            }
        }
        else {
            System.out.print('.');
        }
    }
}
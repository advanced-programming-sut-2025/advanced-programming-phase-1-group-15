package models.map;

import models.time.DateAndTime;
import models.time.TimeObserver;
import models.weather.WeatherObserver;
import models.weather.WeatherOption;

import java.util.ArrayList;

public class Map extends Area implements TimeObserver, WeatherObserver {
    private final ArrayList<ArrayList<Tile>> mapTiles;
    public static final int ROWS = 50;
    public static final int COLS = 100;

    public Map() {
        mapTiles = new ArrayList<>();
        tiles = new ArrayList<>();
        for(int i = 0; i < ROWS; i++){
            mapTiles.add(new ArrayList<>());
            for(int j = 0; j < COLS; j++){
                mapTiles.get(i).add(new Tile(j, i));
            }
            tiles.addAll(mapTiles.get(i));
        }
    }

    public void build() {
        for(int i = 0; i < ROWS * COLS; i++){
            tiles.get(i).setArea(this);
        }
        innerAreas = new ArrayList<>();
        innerAreas.add(new Farm(1));
        innerAreas.add(new Farm(2));
        innerAreas.add(new Farm(3));
        innerAreas.add(new Farm(4));

        for(Area area : innerAreas){
            area.build();
        }
    }

    public ArrayList<ArrayList<Tile>> getMapTiles() {
        return mapTiles;
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
    @Override
    public void update(WeatherOption weatherOption) {

    }

    public void printMap() {
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                System.out.print(mapTiles.get(i).get(j).print());
            }
            System.out.println();
        }
    }

    public void mapGuide() {
        System.out.println("Map Guide: ");
        System.out.println("--------------------");
        System.out.println("empty tile .");
    }
}

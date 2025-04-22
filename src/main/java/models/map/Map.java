package models.map;

import models.time.DateAndTime;
import models.time.TimeObserver;
import models.weather.WeatherObserver;
import models.weather.WeatherOption;

import java.util.ArrayList;


/*
  Our coordinates are like this :
  (0,0) (1,0) (2,0) (3,0)
  (0,1) (1,1) (2,1) (3,1)
  (0,2) (1,2) (2,2) (3,2)
  (0,3) (1,3) (2,3) (3,3)

  x : culoumn
  y : row

*/


public class Map extends Area implements TimeObserver, WeatherObserver {
    public static int ROWS = 50;
    public static int COLS = 100;

    public Map(ArrayList<ArrayList<Tile>> mapTiles , int firstCol, int lastCol, int firstRow, int lastRow) {
        tiles = mapTiles;
        for(int row = firstRow; row <= lastRow; row++) {
            for(int col = firstCol; col <= lastCol; col++) {
                Tile tile = mapTiles.get(col).get(row);
                tile.setArea(this);
            }
        }
    }

    public Map(){}

    public void build() {

        int[] firstIndex = {1,70};
        int[] lastIndex = {1,70};

        innerAreas = new ArrayList<>();
        for(int i=0; i<1; i++){
            for(int j=0; j<1; j++){
                int firstCol = firstIndex[i];
                int lastCol = lastIndex[i];
                int firstRow = firstIndex[j];;
                int lastRow = lastIndex[j];
                innerAreas.add(new Farm(getSubArea(tiles,firstCol,lastCol,firstRow,lastRow),firstCol,lastCol,firstRow,lastRow));
            }
        }

        for(Area area : innerAreas){
            //area.build();
        }
    }
//
//    public ArrayList<ArrayList<Tile>> getMapTiles() {
//        return mapTiles;
//    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
    @Override
    public void update(WeatherOption weatherOption) {

    }

//    public void printMap() {
//        for(int i = 0; i < ROWS; i++){
//            for(int j = 0; j < COLS; j++){
//                System.out.print(mapTiles.get(i).get(j).print());
//            }
//            System.out.println();
//        }
//    }

    public void mapGuide() {
        System.out.println("Map Guide: ");
        System.out.println("--------------------");
        System.out.println("empty tile .");
    }
}

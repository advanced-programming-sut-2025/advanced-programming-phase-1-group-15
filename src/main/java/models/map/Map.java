package models.map;

import models.App;
import models.Player;
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

  x : column
  y : row

*/


public class Map extends Area implements TimeObserver, WeatherObserver {
    public static int ROWS = 50;
    public static int COLS = 100;

    public Map(ArrayList<ArrayList<Tile>> mapTiles) {
        this.areaType = AreaType.MAP;
        this.tiles = mapTiles;
        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                Tile tile = tiles.get(row).get(col);
                tile.setArea(this);
            }
        }
    }

    public void build() {
        innerAreas = new ArrayList<>();

        for (int i = 0; i < Farm.coordinates.length; i++) {
            innerAreas.add(new Farm(getSubArea(tiles, Farm.coordinates[i][0], Farm.coordinates[i][1], Farm.coordinates[i][2], Farm.coordinates[i][3]), i + 1));
        }

        for(Area innerArea : innerAreas){
            innerArea.build();
        }
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
    @Override
    public void update(WeatherOption weatherOption) {

    }

    public void printMap() {
        System.out.print(' ');
        for(int i =0; i < 100; i++){
            System.out.print('_');
        }
        System.out.println();

        for(int i = 0; i < ROWS; i++){
            System.out.print('|');
            for(int j = 0; j < COLS; j++){
                Tile tile = tiles.get(i).get(j);
                boolean playerFound = false;

                for(int p = 0; p < App.currentGame.getPlayers().size(); p++) {
                    Player player = App.currentGame.getPlayers().get(p);
                    if(player.getPosition().equals(tile.getPosition())) {
                        System.out.print((char) ('a' + p));
                        playerFound = true;
                        break;
                    }
                }
                if(!playerFound) {
                    tile.print();
                }
            }
            System.out.print('|');
            System.out.println();
        }

        System.out.print(' ');
        for(int i =0; i < 100; i++){
            System.out.print('_');
        }
        System.out.println();
    }

    public void mapGuide() {
        System.out.println("Map Guide: ");
        System.out.println("(players are shown by characters 'a' to 'd' based on their original order in game)");
        System.out.println("--------------------");
        System.out.println("horizontal borders _");
        System.out.println("vertical borders |");
        System.out.println("empty tile .");
        System.out.println("house H");
        System.out.println("lake ~");
        System.out.println("greenhouse G");
        System.out.println("quarry #");
        System.out.println("tree T");
        System.out.println("stone O");
    }
}

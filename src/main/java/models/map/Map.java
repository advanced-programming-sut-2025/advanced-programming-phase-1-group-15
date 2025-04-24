package models.map;

import models.App;
import models.Player;
import models.time.DateAndTime;
import models.time.TimeObserver;
import models.weather.WeatherObserver;
import models.weather.WeatherOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


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

    public Tile getTile(Position pos) {
        return tiles.get(pos.getX()).get(pos.getY());
    }

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

        for(int i = 0; i < Farm.coordinates.length; i++) {
            innerAreas.add(new Farm(getSubArea(tiles, Farm.coordinates[i][0], Farm.coordinates[i][1], Farm.coordinates[i][2],
                    Farm.coordinates[i][3]), i + 1));
        }

        for(Area innerArea : innerAreas){
            innerArea.build();
            innerArea.setParentArea(this);
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

    private static boolean isValidPosition(int y, int x) {
        return x >= 0 && x < COLS && y >= 0 && y < ROWS;
    }

    private static boolean isValidPosition(Position pos) {
        return isValidPosition(pos.getY(), pos.getX());
    }

    public int findShortestPath (Position start,Position end){
        if(!isValidPosition(start)) return -1;
        if(!isValidPosition(end)) return -1;
        if(start.equals(end)) return 0;

        Tile startTile = getTile(start);
        Tile endTile = getTile(end);

        if (!startTile.isWalkable() || !endTile.isWalkable()) {
            return -1;
        }

        final int[] deltaX = {-1,0,1};
        final int[] deltaY = {1,0,-1};

        int[][] distance = new int[ROWS][COLS];
        for (int[] row : distance) {
            Arrays.fill(row, -1); // -1 means unvisited
        }
        distance[start.getY()][start.getX()] = 0;
        Queue<Position> toBeChecked = new LinkedList<>();
        toBeChecked.add(start);

        while(!toBeChecked.isEmpty()) {
            Position current = toBeChecked.poll();
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(deltaX[i] == 0 && deltaY[j] == 0) continue;
                    int newX = current.getX() + deltaX[i];
                    int newY = current.getY() + deltaY[j];

                    if(newX == end.getX() && newY == end.getY()) {
                        return distance[current.getY()][current.getX()]+1;
                    }

                    if (isValidPosition(newY, newX) && distance[newY][newX] == -1) {
                        Tile neighbor = getTile(new Position(newX, newY));
                        if (neighbor.isWalkable()) {
                            distance[newY][newX] = distance[current.getY()][current.getX()] + 1;
                            toBeChecked.add(new Position(newX, newY));
                        }
                    }
                }
            }
        }
        return -1;
    }

}
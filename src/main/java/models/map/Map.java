package models.map;

import models.App;
import models.Player;
import models.stores.*;
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


public class Map extends Area {
    public static int ROWS = 50;
    public static int COLS = 100;

    public Tile getTile(Position pos) {
        return tiles.get(pos.getY()).get(pos.getX());
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

        innerAreas.add(new Blacksmith(getSubArea(tiles, Blacksmith.coordinates[0], Blacksmith.coordinates[1], Blacksmith.coordinates[2], Blacksmith.coordinates[3])));
        innerAreas.add(new JojaMart(getSubArea(tiles, JojaMart.coordinates[0], JojaMart.coordinates[1], JojaMart.coordinates[2], JojaMart.coordinates[3])));
        innerAreas.add(new PierreGeneralStore(getSubArea(tiles, PierreGeneralStore.coordinates[0], PierreGeneralStore.coordinates[1], PierreGeneralStore.coordinates[2], PierreGeneralStore.coordinates[3])));
        innerAreas.add(new CarpenterShop(getSubArea(tiles, CarpenterShop.coordinates[0], CarpenterShop.coordinates[1], CarpenterShop.coordinates[2], CarpenterShop.coordinates[3])));
        innerAreas.add(new FishShop(getSubArea(tiles, FishShop.coordinates[0], FishShop.coordinates[1], FishShop.coordinates[2], FishShop.coordinates[3])));
        innerAreas.add(new MarnieRanch(getSubArea(tiles, MarnieRanch.coordinates[0], MarnieRanch.coordinates[1], MarnieRanch.coordinates[2], MarnieRanch.coordinates[3])));
        innerAreas.add(new StarDropSaloon(getSubArea(tiles, StarDropSaloon.coordinates[0], StarDropSaloon.coordinates[1], StarDropSaloon.coordinates[2], StarDropSaloon.coordinates[3])));

        for(Area innerArea : innerAreas){
            innerArea.setParentArea(this);
            innerArea.build();
        }
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        for(Area innerArea : innerAreas){
            innerArea.update(dateAndTime);
        }
    }

    public void printMap() {
        System.out.print(' ');
        for(int i =0; i < COLS; i++){
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

    public static boolean isBoundValid(Position pos) {
        return pos.x >= 0 && pos.x < COLS && pos.y >= 0 && pos.y < ROWS;
    }

    public int findShortestPath(Player player, Position start, Position end){
        if(start.equals(end)) return 0;

        Tile startTile = getTile(start);
        Tile endTile = getTile(end);

        final int[] deltaX = {-1,0,1};
        final int[] deltaY = {1,0,-1};

        int[][] distance = new int[ROWS][COLS];
        for (int[] row : distance) {
            Arrays.fill(row, -1); // -1 means unvisited
        }

        distance[start.y][start.x] = 0;
        Queue<Position> toBeChecked = new LinkedList<>();
        toBeChecked.add(start);

        while(!toBeChecked.isEmpty()) {
            Position current = toBeChecked.poll();
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(deltaX[i] == 0 && deltaY[j] == 0) continue;
                    int newX = current.x + deltaX[i];
                    int newY = current.y + deltaY[j];
                    Position newPosition = new Position(newX, newY);

                    if(newX == end.x && newY == end.y) {
                        return distance[current.getY()][current.getX()] + 1;
                    }

                    if(isBoundValid(newPosition) && distance[newY][newX] == -1) {
                        Tile neighbor = getTile(newPosition);
                        if(neighbor.isWalkable()) {
                            Player owner = neighbor.getArea().getOwner();
                            if(owner == null || owner.equals(player)) {
                                distance[newY][newX] = distance[current.getY()][current.getX()] + 1;
                                toBeChecked.add(newPosition);
                            }
                        }
                    }
                }
            }
        }

        return -1;
    }
}
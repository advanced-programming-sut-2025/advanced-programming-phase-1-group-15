package com.example.models.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.stores.*;
import com.example.models.time.DateAndTime;

import java.util.*;


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
    public static int ROWS = 270;
    public static int COLS = 270;

    public Tile getTile(int row, int col) {
        return tiles.get(row).get(col);
    }

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

//        innerAreas.add(new Blacksmith(getSubArea(tiles, Blacksmith.coordinates[0], Blacksmith.coordinates[1], Blacksmith.coordinates[2], Blacksmith.coordinates[3])));
//        innerAreas.add(new JojaMart(getSubArea(tiles, JojaMart.coordinates[0], JojaMart.coordinates[1], JojaMart.coordinates[2], JojaMart.coordinates[3])));
//        innerAreas.add(new PierreGeneralStore(getSubArea(tiles, PierreGeneralStore.coordinates[0], PierreGeneralStore.coordinates[1], PierreGeneralStore.coordinates[2], PierreGeneralStore.coordinates[3])));
//        innerAreas.add(new CarpenterShop(getSubArea(tiles, CarpenterShop.coordinates[0], CarpenterShop.coordinates[1], CarpenterShop.coordinates[2], CarpenterShop.coordinates[3])));
//        innerAreas.add(new FishShop(getSubArea(tiles, FishShop.coordinates[0], FishShop.coordinates[1], FishShop.coordinates[2], FishShop.coordinates[3])));
//        innerAreas.add(new MarnieRanch(getSubArea(tiles, MarnieRanch.coordinates[0], MarnieRanch.coordinates[1], MarnieRanch.coordinates[2], MarnieRanch.coordinates[3])));
//        innerAreas.add(new StarDropSaloon(getSubArea(tiles, StarDropSaloon.coordinates[0], StarDropSaloon.coordinates[1], StarDropSaloon.coordinates[2], StarDropSaloon.coordinates[3])));
//        innerAreas.add(new NPCVillage(getSubArea(tiles, NPCVillage.coordinates[0][0], NPCVillage.coordinates[0][1], NPCVillage.coordinates[0][2],NPCVillage.coordinates[0][3])));
//        innerAreas.add(new NPCVillage(getSubArea(tiles, NPCVillage.coordinates[1][0], NPCVillage.coordinates[1][1], NPCVillage.coordinates[1][2],NPCVillage.coordinates[1][3])));

        for(Area innerArea : innerAreas){
            innerArea.setParentArea(this);
            innerArea.build();
        }
    }

    @Override
    public TextureRegion getTexture() {
        return null;
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
        for(int i =0; i < COLS; i++){
            System.out.print('_');
        }
        System.out.println();
    }

    public static boolean isBoundValid(Position pos) {
        return pos.x >= 0 && pos.x < COLS && pos.y >= 0 && pos.y < ROWS;
    }

    private  Position[][] nextTileInPath = new Position[ROWS][COLS];

    public int findShortestPath(Player player, Position start, Position end) {
        if (start.equals(end)) return 0;

        final int[] deltaX = {-1, 0, 1};
        final int[] deltaY = { 1, 0,-1};

        int[][] distance = new int[ROWS][COLS];
        for (int[] row : distance) {
            Arrays.fill(row, -1);
        }
        distance[start.y][start.x] = 0;

        nextTileInPath = new Position[ROWS][COLS];

        Queue<Position> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            int currentDistance = distance[current.y][current.x];

            for (int dx : deltaX) {
                for (int dy : deltaY) {
                    if (dx == 0 && dy == 0) continue;

                    int nx = current.x + dx, ny = current.y + dy;
                    Position np = new Position(nx, ny);

                    if (nx == end.x && ny == end.y) {
                        nextTileInPath[ny][nx] = current;
                        return currentDistance + 1;
                    }

                    if (isBoundValid(np) && distance[ny][nx] == -1) {
                        Tile neighbor = getTile(np);
                        if (neighbor.isWalkable()) {
                            Player owner = neighbor.getArea().getOwner();
                            if (owner == null
                                    || owner.equals(player)
                                    || owner.equals(player.getCouple())) {

                                distance[ny][nx] = currentDistance + 1;

                                nextTileInPath[ny][nx] = current;
                                queue.add(np);
                            }
                        }
                    }
                }
            }
        }

        return -1;
    }


    public int calculatePath(Position start, Position end) {

        List<Position> path = new ArrayList<>();
        Position current = end;
        while (current != null && !current.equals(start)) {
            path.add(current);
            current = nextTileInPath[current.y][current.x];
        }
        if (current == null) {
            return -1;
        }
        path.add(start);

        Collections.reverse(path);

        int tiles = path.size() - 1;
        int corners = 0;
        for (int i = 2; i < path.size(); i++) {
            Position p0 = path.get(i - 2);
            Position p1 = path.get(i - 1);
            Position p2 = path.get(i);
            int dx1 = p1.x - p0.x, dy1 = p1.y - p0.y;
            int dx2 = p2.x - p1.x, dy2 = p2.y - p1.y;
            if (dx1 != dx2 || dy1 != dy2) corners++;
        }

        return tiles + 10 * corners;
    }
}

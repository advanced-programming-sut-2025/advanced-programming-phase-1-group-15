package models.map;

import models.App;
import models.Player;
import models.farming.Tree;
import models.foraging.Stone;

import java.util.ArrayList;
import java.util.Random;

public class Farm extends Area {
    public static int[][] coordinates = {
            {0, 40, 0, 20},   //MAP 1
            {60, 100, 0, 20}, //MAP 2
            {60, 100, 30, 50}, //MAP 3
            {0, 40, 30, 50}   //MAP 4
    };

    private final int number;

    public Farm(ArrayList<ArrayList<Tile>> farmTiles, int number) {
        this.areaType = AreaType.FARM;
        this.number = number;
        this.tiles = farmTiles;

        for (Player player : App.currentGame.getPlayers()) {
            if (player.getMapNumber() == number) {
                player.setHome(new Position(House.playerCoordinates[number - 1][0], House.playerCoordinates[number - 1][1]));
                player.setFarm(this);

                this.owner = player;
            }
        }

        for(ArrayList<Tile> row : farmTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }



    public void build() {
        innerAreas = new ArrayList<>();

        innerAreas.add(new Lake(getSubArea(tiles, Lake.coordinates[number - 1][0], Lake.coordinates[number - 1][1], Lake.coordinates[number - 1][2], Lake.coordinates[number - 1][3])));
        innerAreas.add(new House(getSubArea(tiles, House.coordinates[number - 1][0], House.coordinates[number - 1][1], House.coordinates[number - 1][2], House.coordinates[number - 1][3])));
        innerAreas.add(new GreenHouse(getSubArea(tiles, GreenHouse.coordinates[number - 1][0], GreenHouse.coordinates[number - 1][1], GreenHouse.coordinates[number - 1][2], GreenHouse.coordinates[number - 1][3])));
        innerAreas.add(new Quarry(getSubArea(tiles, Quarry.coordinates[number - 1][0], Quarry.coordinates[number - 1][1], Quarry.coordinates[number - 1][2], Quarry.coordinates[number - 1][3])));

        for(Area innerArea : innerAreas){
            innerArea.build();
            innerArea.setParentArea(this);
        }

        Random rand = new Random();
        int treesCount = rand.nextInt(6) + 7;
        int stonesCount = rand.nextInt(6) + 7;
        for(int i = 0; i < treesCount; i++) {
            while(true) {
                int randomRow = rand.nextInt(tiles.size());
                int randomCol = rand.nextInt(tiles.get(randomRow).size());
                Tile randomTile = tiles.get(randomRow).get(randomCol);
                if(randomTile.getArea().areaType.equals(AreaType.FARM) && randomTile.isEmpty()) {
                    randomTile.put(new Tree());
                    break;
                }
            }
        }
        for(int i = 0; i < stonesCount; i++) {
            while(true) {
                int randomRow = rand.nextInt(tiles.size());
                int randomCol = rand.nextInt(tiles.get(randomRow).size());
                Tile randomTile = tiles.get(randomRow).get(randomCol);
                if(randomTile.getArea().areaType.equals(AreaType.FARM) && randomTile.isEmpty()) {
                    randomTile.put(new Stone());
                    break;
                }
            }
        }
    }
}
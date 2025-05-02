package models.map;

import models.App;
import models.Player;
import models.farming.Tree;
import models.farming.TreeType;
import models.foraging.ForagingMineral;
import models.foraging.ForagingMineralType;
import models.foraging.Stone;
import models.time.DateAndTime;
import models.time.Season;
import models.tools.Fridge;

import java.util.ArrayList;
import java.util.Random;

public class Farm extends Area {
    public static int[][] coordinates = {
            {0, 40, 0, 20},   //MAP 1
            {60, 100, 0, 20}, //MAP 2
            {60, 100, 30, 50}, //MAP 3
            {0, 40, 30, 50}   //MAP 4
    };

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

    private void randomTreeGenerator(Season season) {
        Random rand = new Random();
        int treesCount = rand.nextInt(6) + 7;

        ArrayList<TreeType> validTreeTypes = new ArrayList<>();
        for (TreeType treeType : TreeType.values()) {
            if (treeType.season.contains(season)) {
                validTreeTypes.add(treeType);
            }
        }

        for (int i = 0; i < treesCount; i++) {
            while (true) {
                int randomRow = rand.nextInt(tiles.size());
                int randomCol = rand.nextInt(tiles.get(randomRow).size());
                Tile randomTile = tiles.get(randomRow).get(randomCol);

                if (randomTile.getArea().areaType.equals(AreaType.FARM) && randomTile.isEmpty()) {
                    TreeType randomTreeType = validTreeTypes.get(rand.nextInt(validTreeTypes.size()));

                    randomTile.put(new Tree(randomTreeType));
                    break;
                }
            }
        }
    }

    private void randomMineralGenerator() {
        Random rand = new Random();
        int mineralsCount = rand.nextInt(6) + 7;

        for (int i = 0; i < mineralsCount; i++) {
            while (true) {
                int randomRow = rand.nextInt(tiles.size());
                int randomCol = rand.nextInt(tiles.get(randomRow).size());
                Tile randomTile = tiles.get(randomRow).get(randomCol);

                if(rand.nextBoolean()) {
                    if (randomTile.getArea().areaType.equals(AreaType.FARM) && randomTile.isEmpty()) {
                        randomTile.put(new Stone());
                        break;
                    }
                }
                else {
                    if (randomTile.getArea().areaType.equals(AreaType.FARM) && randomTile.isEmpty()) {
                        ForagingMineralType randomMineralType = ForagingMineralType.values()[rand.nextInt(ForagingMineralType.values().length)];

                        randomTile.put(new ForagingMineral(randomMineralType));
                        break;
                    }
                }
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
            innerArea.setParentArea(this);
            innerArea.number = number;
            innerArea.build();
        }

        randomTreeGenerator(Season.SPRING);
        randomMineralGenerator();
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getDay() == 1 || dateAndTime.getDay() == 14) {
            randomTreeGenerator(dateAndTime.getSeason());
            randomMineralGenerator();
        }

        for(Area innerArea : innerAreas) {
            innerArea.update(dateAndTime);
        }
    }
}
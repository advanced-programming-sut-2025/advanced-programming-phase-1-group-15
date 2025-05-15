package models.map;

import models.App;
import models.Player;
import models.RandomGenerator;
import models.animals.Animal;
import models.animals.Barn;
import models.animals.Coop;
import models.animals.Maintenance;
import models.farming.Tree;
import models.farming.TreeType;
import models.foraging.ForagingMineral;
import models.foraging.ForagingMineralType;
import models.foraging.Stone;
import models.time.DateAndTime;
import models.time.Season;
import models.tools.Fridge;

import java.util.ArrayList;

public class Farm extends Area {
    public static int[][] coordinates = {
            {0, 40, 0, 20},   //MAP 1
            {60, 100, 0, 20}, //MAP 2
            {60, 100, 30, 50}, //MAP 3
            {0, 40, 30, 50}   //MAP 4
    };

    private GreenHouse greenHouse;
    boolean hasScareCrow = false;

    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public boolean doesHaveScareCrow() {
        return hasScareCrow;
    }

    public void setHasScareCrow(boolean hasScareCrow) {
        this.hasScareCrow = hasScareCrow;
    }

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
        int treesCount = RandomGenerator.getInstance().randomInt(7,12);

        ArrayList<TreeType> validTreeTypes = new ArrayList<>();
        for (TreeType treeType : TreeType.values()) {
            if (treeType.season.contains(season)) {
                validTreeTypes.add(treeType);
            }
        }

        for (int i = 0; i < treesCount; i++) {
            while (true) {
                int randomRow = RandomGenerator.getInstance().randomInt(0,tiles.size());
                int randomCol = RandomGenerator.getInstance().randomInt(0,tiles.get(randomRow).size());
                Tile randomTile = tiles.get(randomRow).get(randomCol);

                if (randomTile.getArea().areaType.equals(AreaType.FARM) && randomTile.isEmpty()) {
                    TreeType randomTreeType = validTreeTypes.get(RandomGenerator.getInstance().randomInt(0,validTreeTypes.size()));

                    randomTile.put(new Tree(randomTreeType));
                    break;
                }
            }
        }
    }

    private void randomMineralGenerator() {
        int mineralsCount = RandomGenerator.getInstance().randomInt(7,12);

        for (int i = 0; i < mineralsCount; i++) {
            while (true) {
                int randomRow = RandomGenerator.getInstance().randomInt(0,tiles.size());
                int randomCol = RandomGenerator.getInstance().randomInt(0,tiles.get(randomRow).size());
                Tile randomTile = tiles.get(randomRow).get(randomCol);

                if(RandomGenerator.getInstance().randomBoolean()) {
                    if (randomTile.getArea().areaType.equals(AreaType.FARM) && randomTile.isEmpty()) {
                        randomTile.put(new Stone());
                        break;
                    }
                }
                else {
                    if (randomTile.getArea().areaType.equals(AreaType.FARM) && randomTile.isEmpty()) {
                        ForagingMineralType randomMineralType = ForagingMineralType.values()
                                [RandomGenerator.getInstance().randomInt(0,ForagingMineralType.values().length)];

                        randomTile.put(new ForagingMineral(randomMineralType));
                        break;
                    }
                }
            }
        }
    }

    public void build() {
        innerAreas = new ArrayList<>();

        innerAreas.add(new Lake(getSubArea(tiles, Lake.coordinates[number - 1][0], Lake.coordinates[number - 1][1],
                Lake.coordinates[number - 1][2], Lake.coordinates[number - 1][3])));
        innerAreas.add(new House(getSubArea(tiles, House.coordinates[number - 1][0], House.coordinates[number - 1][1],
                House.coordinates[number - 1][2], House.coordinates[number - 1][3])));
        greenHouse = new GreenHouse(getSubArea(tiles, GreenHouse.coordinates[number - 1][0],
                GreenHouse.coordinates[number - 1][1], GreenHouse.coordinates[number - 1][2], GreenHouse.coordinates[number - 1][3]));
        innerAreas.add(greenHouse);
        innerAreas.add(new Quarry(getSubArea(tiles, Quarry.coordinates[number - 1][0],
                Quarry.coordinates[number - 1][1], Quarry.coordinates[number - 1][2], Quarry.coordinates[number - 1][3])));

        for(Area innerArea : innerAreas){
            innerArea.setParentArea(this);
            innerArea.number = number;
            innerArea.build();
        }

        randomTreeGenerator(Season.SPRING);
        randomMineralGenerator();
    }

    public boolean place(Animal animal) {
        if(animal.getMaintenance().equals(Maintenance.BARN)) {
            for(ArrayList<Tile> row : tiles) {
                for(Tile tile : row) {
                    if((tile.getArea() instanceof Barn barn) && tile.isEmpty()) {
                        switch(animal.getAnimalType()) {
                            case COW -> {
                                animal.setPosition(tile.getPosition());
                                tile.put(animal);
                                return true;
                            }
                            case GOAT -> {
                                if(barn.isBig() || barn.isDeluxe()) {
                                    animal.setPosition(tile.getPosition());
                                    tile.put(animal);
                                    return true;
                                }
                            }
                            case SHEEP, PIG -> {
                                if(barn.isDeluxe()) {
                                    animal.setPosition(tile.getPosition());
                                    tile.put(animal);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            for(ArrayList<Tile> row : tiles) {
                for(Tile tile : row) {
                    if((tile.getArea() instanceof Coop coop) && tile.isEmpty()) {
                        switch(animal.getAnimalType()) {
                            case CHICKEN -> {
                                animal.setPosition(tile.getPosition());
                                tile.put(animal);
                                return true;
                            }
                            case DUCK, DINOSAUR -> {
                                if(coop.isBig() || coop.isDeluxe()) {
                                    animal.setPosition(tile.getPosition());
                                    tile.put(animal);
                                    return true;
                                }
                            }
                            case RABBIT -> {
                                if(coop.isDeluxe()) {
                                    animal.setPosition(tile.getPosition());
                                    tile.put(animal);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean place(Tilable tilable) {
        for(ArrayList<Tile> row : tiles) {
            for(Tile tile : row) {
                if(tile.getAreaType().equals(AreaType.FARM) && tile.isEmpty()) {
                    tile.put(tilable);
                    return true;
                }
            }
        }

        return false;
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
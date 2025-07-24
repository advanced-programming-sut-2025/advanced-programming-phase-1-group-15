package com.example.models.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.RandomGenerator;
import com.example.models.animals.Animal;
import com.example.models.animals.Barn;
import com.example.models.animals.Coop;
import com.example.models.animals.Maintenance;
import com.example.models.farming.Tree;
import com.example.models.farming.TreeType;
import com.example.models.foraging.Stone;
import com.example.models.stores.GeneralItem;
import com.example.models.stores.GeneralItemsType;
import com.example.models.time.DateAndTime;
import com.example.models.time.Season;

import java.util.ArrayList;

public class Farm extends Area {
    public static int[][] coordinates = {
            {0, 90, 0, 90},   //MAP 1
            {180, 270, 0, 90}, //MAP 2
            {0, 90, 180, 270}, //MAP 3
            {180, 270, 180, 270}   //MAP 4
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
        int treesCount = RandomGenerator.getInstance().randomInt(50, 75);

        ArrayList<TreeType> validTreeTypes = new ArrayList<>();
        for (TreeType treeType : TreeType.values()) {
            if (treeType.season.contains(season)) {
                validTreeTypes.add(treeType);
            }
        }

        for (int i = 0; i < treesCount; i++) {
            while (true) {
                int randomRow = RandomGenerator.getInstance().randomInt(0, tiles.size() - 1);
                int randomCol = RandomGenerator.getInstance().randomInt(0, tiles.get(randomRow).size() - 1);
                Tile randomTile = tiles.get(randomRow).get(randomCol);

                if (randomTile.getArea().areaType.equals(AreaType.FARM) && randomTile.isEmpty()) {
                    TreeType randomTreeType = validTreeTypes.get(RandomGenerator.getInstance().randomInt(0, validTreeTypes.size() - 1));

                    randomTile.put(new Tree(randomTreeType));
                    break;
                }
            }
        }
    }

    private void randomStoneAndWoodGenerator() {
        int totalCount = RandomGenerator.getInstance().randomInt(50, 75);

        for (int i = 0; i < totalCount; i++) {
            while (true) {
                int randomRow = RandomGenerator.getInstance().randomInt(0, tiles.size() - 1);
                int randomCol = RandomGenerator.getInstance().randomInt(0, tiles.get(randomRow).size() - 1);
                Tile randomTile = tiles.get(randomRow).get(randomCol);

                if (randomTile.getArea().areaType.equals(AreaType.FARM) && randomTile.isEmpty()) {
                    if(RandomGenerator.getInstance().randomBoolean()) {
                        randomTile.put(new Stone());
                    }
                    else {
                        randomTile.put(new GeneralItem(GeneralItemsType.WOOD));
                    }
                    break;
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
        randomStoneAndWoodGenerator();
    }

    @Override
    public TextureRegion getTexture() {
        return null;
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
        if((dateAndTime.getDay() % 7 == 1) && dateAndTime.getHour() == 9) {
            randomTreeGenerator(dateAndTime.getSeason());
            randomStoneAndWoodGenerator();
        }

        for(Area innerArea : innerAreas) {
            innerArea.update(dateAndTime);
        }
    }
}

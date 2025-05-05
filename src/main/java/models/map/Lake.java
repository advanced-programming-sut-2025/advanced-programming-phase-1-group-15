package models.map;

import models.animals.Fish;
import models.animals.FishType;
import models.time.DateAndTime;
import models.time.Season;

import java.util.ArrayList;
import java.util.Random;

public class Lake extends Area {
    public static int[][] coordinates = {
            {2, 13, 14, 19},   //MAP 1
            {25, 32, 1, 9}, //MAP 2
            {10, 15, 13, 18}, //MAP 3
            {5, 15, 10, 15}   //MAP 4
    };

    FishType todaysFishType;

    public Lake(ArrayList<ArrayList<Tile>> lakeTiles) {
        this.areaType = AreaType.LAKE;
        this.tiles = lakeTiles;

        for(ArrayList<Tile> row : lakeTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }

    private void emptyLake() {
        for(ArrayList<Tile> row : tiles) {
            for(Tile tile : row) {
                tile.empty();
            }
        }
    }
    private void randomFishGenerator(Season season) {
        Random rand = new Random();
        int fishesCount = rand.nextInt(2) + 1;

        ArrayList<FishType> validFishTypes = new ArrayList<>();
        for (FishType fishType : FishType.values()) {
            if(fishType.season.equals(season)) {
                validFishTypes.add(fishType);
            }
        }

        FishType randomFishType = validFishTypes.get(rand.nextInt(validFishTypes.size()));
        todaysFishType = randomFishType;
        for (int i = 0; i < fishesCount; i++) {
            int randomRow = rand.nextInt(tiles.size());
            int randomCol = rand.nextInt(tiles.get(randomRow).size());
            Tile randomTile = tiles.get(randomRow).get(randomCol);

            randomTile.put(new Fish(randomFishType));
        }
    }

    public FishType getTodaysFishType() {
        return todaysFishType;
    }

    public void build() {
        randomFishGenerator(Season.SPRING);
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == 9) {
            emptyLake();
            randomFishGenerator(dateAndTime.getSeason());
        }
    }
}

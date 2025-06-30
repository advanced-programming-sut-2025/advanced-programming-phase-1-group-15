package com.example.models.map;

import com.example.models.RandomGenerator;
import com.example.models.animals.Fish;
import com.example.models.animals.FishType;
import com.example.models.time.DateAndTime;
import com.example.models.time.Season;

import java.util.ArrayList;

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
        int fishesCount = RandomGenerator.getInstance().randomInt(1,2);

        ArrayList<FishType> validFishTypes = new ArrayList<>();
        for (FishType fishType : FishType.values()) {
            if(fishType.season.equals(season)) {
                validFishTypes.add(fishType);
            }
        }

        FishType randomFishType = validFishTypes.get(RandomGenerator.getInstance().randomInt(0,validFishTypes.size()-1));
        todaysFishType = randomFishType;
        for (int i = 0; i < fishesCount; i++) {
            int randomRow = RandomGenerator.getInstance().randomInt(0,tiles.size()-1);
            int randomCol = RandomGenerator.getInstance().randomInt(0,tiles.get(randomRow).size()-1);
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

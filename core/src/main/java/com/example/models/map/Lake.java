package com.example.models.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.RandomGenerator;
import com.example.models.animals.Fish;
import com.example.models.animals.FishType;
import com.example.models.time.DateAndTime;
import com.example.models.time.Season;
import com.example.views.GameAssetManager;

import java.util.ArrayList;

public class Lake extends Area {
    public static int[][] coordinates = {
            {5, 25, 30, 45},   //MAP 1
            {25, 40, 45, 65}, //MAP 2
            {50, 70, 50, 70}, //MAP 3
            {5, 25, 10, 25}   //MAP 4
    };

    FishType todaysFishType;

    public Lake(ArrayList<ArrayList<Tile>> lakeTiles) {
        super(lakeTiles);
        this.areaType = AreaType.LAKE;

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
        int fishesCount = RandomGenerator.getInstance().randomInt(1, 4);

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
    public TextureRegion getTexture() {
        return GameAssetManager.lake;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == 9) {
            emptyLake();
            randomFishGenerator(dateAndTime.getSeason());
        }
    }
}

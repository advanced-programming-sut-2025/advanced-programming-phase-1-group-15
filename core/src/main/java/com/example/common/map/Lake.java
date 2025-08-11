package com.example.common.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.client.controllers.ClientGameController;
import com.example.client.models.ClientApp;
import com.example.common.RandomGenerator;
import com.example.common.animals.Fish;
import com.example.common.animals.FishType;
import com.example.common.time.DateAndTime;
import com.example.common.time.Season;
import com.example.client.views.GameAssetManager;

import java.util.ArrayList;
import java.util.HashMap;

public class Lake extends Area {
    public static int[][] coordinates = {
            {5, 25, 30, 45},   //MAP 1
            {25, 40, 45, 65}, //MAP 2
            {50, 70, 50, 70}, //MAP 3
            {5, 25, 10, 25}   //MAP 4
    };

    ArrayList<FishType> todayFishTypes = new ArrayList<>();

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
        todayFishTypes.clear();
    }
    private void randomFishGenerator(Season season) {
        HashMap<String,Object> cmdBody = new HashMap<>();
        int fishesCount = RandomGenerator.getInstance().randomInt(1, 4);
        cmdBody.put("fish_count", fishesCount);

        ArrayList<FishType> validFishTypes = new ArrayList<>();
        for (FishType fishType : FishType.values()) {
            if(fishType.season.equals(season)) {
                validFishTypes.add(fishType);
            }
        }

        for (int i = 0; i < fishesCount; i++) {
            FishType randomFishType = validFishTypes.get(RandomGenerator.getInstance().randomInt(0, validFishTypes.size() - 1));
            todayFishTypes.add(randomFishType);

            int randomRow = RandomGenerator.getInstance().randomInt(1, tiles.size() - 2);
            int randomCol = RandomGenerator.getInstance().randomInt(1, tiles.get(randomRow).size() - 2);
            Tile randomTile = tiles.get(randomRow).get(randomCol);

            randomTile.put(new Fish(randomFishType));

            cmdBody.put("(" + randomTile.getPosition().x + "," + randomTile.getPosition().y + ")", randomFishType.ordinal());
        }

        ClientGameController.sendGenerateFishMessage(cmdBody);
    }

    public FishType getTodaysFishType() {
        return todayFishTypes.get(RandomGenerator.getInstance().randomInt(0, todayFishTypes.size() - 1));
    }

    public void build() {
        if(ClientApp.currentGame.isAdmin()) {
            randomFishGenerator(Season.SPRING);
        }
    }

    @Override
    public TextureRegion getTexture() {
        return GameAssetManager.lake;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == 9) {
            if(ClientApp.currentGame.isAdmin()) {
                emptyLake();
                randomFishGenerator(dateAndTime.getSeason());
            }
        }
    }
}

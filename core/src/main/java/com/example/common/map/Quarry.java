package com.example.common.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.client.controllers.ClientGameController;
import com.example.client.models.ClientApp;
import com.example.common.RandomGenerator;
import com.example.common.foraging.ForagingMineral;
import com.example.common.foraging.ForagingMineralType;
import com.example.common.time.DateAndTime;

import java.util.ArrayList;
import java.util.HashMap;

public class Quarry extends Area {
    public static int[][] coordinates = {
            {65, 80, 3, 18},   //MAP 1
            {65, 80, 65, 80}, //MAP 2
            {65, 80, 10, 25}, //MAP 3
            {10, 25, 65, 80}   //MAP 4
    };

    public Quarry(ArrayList<ArrayList<Tile>> quarryTiles) {
        super(quarryTiles);
        this.areaType = AreaType.QUARRY;

        for(ArrayList<Tile> row : quarryTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }

    public void build() {
        if(ClientApp.currentGame.isAdmin()) {
            randomCommonMineralGenerator();
            randomSpecialMineralGenerator();
        }
    }

    @Override
    public TextureRegion getTexture() {
        return null;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if((dateAndTime.getDay() % 3 == 1) && dateAndTime.getHour() == 9) {
            if(ClientApp.currentGame.isAdmin()) {
                randomCommonMineralGenerator();
            }
        }
        if((dateAndTime.getDay() % 7 == 1) && dateAndTime.getHour() == 9) {
            if(ClientApp.currentGame.isAdmin()) {
                randomSpecialMineralGenerator();
            }
        }
    }

    private void randomCommonMineralGenerator() {
        HashMap<String,Object> cmdBody = new HashMap<>();
        int mineralsCount = RandomGenerator.getInstance().randomInt(25, 50);
        cmdBody.put("minerals_count", mineralsCount);

        for (int i = 0; i < mineralsCount; i++) {
            while (true) {
                int randomRow = RandomGenerator.getInstance().randomInt(0, tiles.size() - 1);
                int randomCol = RandomGenerator.getInstance().randomInt(0, tiles.get(randomRow).size() - 1);
                Tile randomTile = tiles.get(randomRow).get(randomCol);

                if (randomTile.isEmpty()) {
                    int randomInt = RandomGenerator.getInstance().randomInt(0, 9);
                    if(randomInt % 2 == 0) {
                        randomTile.put(new ForagingMineral(ForagingMineralType.COAL));
                        cmdBody.put("(" + randomTile.getPosition().x + "," + randomTile.getPosition().y + ")", 0);
                    }
                    else if (randomInt % 3 == 0) {
                        randomTile.put(new ForagingMineral(ForagingMineralType.COPPER));
                        cmdBody.put("(" + randomTile.getPosition().x + "," + randomTile.getPosition().y + ")", 1);
                    }
                    else if(randomInt % 5 == 0) {
                        randomTile.put(new ForagingMineral(ForagingMineralType.IRON));
                        cmdBody.put("(" + randomTile.getPosition().x + "," + randomTile.getPosition().y + ")", 2);
                    }
                    break;
                }
            }
        }

        ClientGameController.sendGenerateCommonMineralsMessage(cmdBody);
    }

    private void randomSpecialMineralGenerator() {
        HashMap<String,Object> cmdBody = new HashMap<>();
        int mineralsCount = RandomGenerator.getInstance().randomInt(10, 15);
        cmdBody.put("minerals_count", mineralsCount);

        for (int i = 0; i < mineralsCount; i++) {
            while (true) {
                int randomRow = RandomGenerator.getInstance().randomInt(0, tiles.size() - 1);
                int randomCol = RandomGenerator.getInstance().randomInt(0, tiles.get(randomRow).size() - 1);
                Tile randomTile = tiles.get(randomRow).get(randomCol);

                if (randomTile.isEmpty()) {
                    ForagingMineralType randomMineralType = ForagingMineralType.values()
                        [RandomGenerator.getInstance().randomInt(0, ForagingMineralType.values().length - 1)];

                    randomTile.put(new ForagingMineral(randomMineralType));

                    cmdBody.put("(" + randomTile.getPosition().x + "," + randomTile.getPosition().y + ")", randomMineralType.ordinal());
                    break;
                }
            }
        }

        ClientGameController.sendGenerateSpecialMineralsMessage(cmdBody);
    }
}

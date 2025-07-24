package com.example.models.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.RandomGenerator;
import com.example.models.foraging.ForagingMineral;
import com.example.models.foraging.ForagingMineralType;
import com.example.models.time.DateAndTime;

import java.util.ArrayList;

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
        randomCommonMineralGenerator();
        randomSpecialMineralGenerator();
    }

    @Override
    public TextureRegion getTexture() {
        return null;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if((dateAndTime.getDay() % 3 == 1) && dateAndTime.getHour() == 9) {
            randomCommonMineralGenerator();
        }
        if((dateAndTime.getDay() % 7 == 1) && dateAndTime.getHour() == 9) {
            randomSpecialMineralGenerator();
        }
    }

    private void randomCommonMineralGenerator() {
        int mineralsCount = RandomGenerator.getInstance().randomInt(25, 50);

        for (int i = 0; i < mineralsCount; i++) {
            while (true) {
                int randomRow = RandomGenerator.getInstance().randomInt(0, tiles.size() - 1);
                int randomCol = RandomGenerator.getInstance().randomInt(0, tiles.get(randomRow).size() - 1);
                Tile randomTile = tiles.get(randomRow).get(randomCol);

                if (randomTile.isEmpty()) {
                    int randomInt = RandomGenerator.getInstance().randomInt(0, 9);
                    if(randomInt % 2 == 0) {
                        randomTile.put(new ForagingMineral(ForagingMineralType.COAL));
                    }
                    else if (randomInt % 3 == 0) {
                        randomTile.put(new ForagingMineral(ForagingMineralType.COPPER));
                    }
                    else if(randomInt % 5 == 0) {
                        randomTile.put(new ForagingMineral(ForagingMineralType.IRON));
                    }
                    break;
                }
            }
        }
    }

    private void randomSpecialMineralGenerator() {
        int mineralsCount = RandomGenerator.getInstance().randomInt(10, 15);

        for (int i = 0; i < mineralsCount; i++) {
            while (true) {
                int randomRow = RandomGenerator.getInstance().randomInt(0, tiles.size() - 1);
                int randomCol = RandomGenerator.getInstance().randomInt(0, tiles.get(randomRow).size() - 1);
                Tile randomTile = tiles.get(randomRow).get(randomCol);

                if (randomTile.isEmpty()) {
                    ForagingMineralType randomMineralType = ForagingMineralType.values()
                        [RandomGenerator.getInstance().randomInt(0, ForagingMineralType.values().length - 1)];

                    randomTile.put(new ForagingMineral(randomMineralType));
                    break;
                }
            }
        }
    }
}

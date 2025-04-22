package models;

import models.map.Position;

import java.util.Random;

public class RandomGenerator {
    public int randomInt(Random rand, int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public Position randomArea(Random rand, int minCol, int maxCol, int minRow, int maxRow) {
        int x = randomInt(rand, minCol, maxCol);
        int y = randomInt(rand, minRow, maxRow);
        return new Position(x, y);
    }
}

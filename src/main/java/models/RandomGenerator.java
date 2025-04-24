package models;

import java.util.Random;

public class RandomGenerator {
    private static final RandomGenerator instance = new RandomGenerator();

    public static RandomGenerator getInstance() {
        return instance;
    }

    private final Random rand = new Random();

    public int randomInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}

package models.map;

import models.App;
import models.Player;

import java.util.ArrayList;
import java.util.Random;

public class Farm extends Area {
    public static int[][] coordinates = {
            {0, 40, 0, 20},   //MAP 1
            {60, 100, 0, 20}, //MAP 2
            {60, 100, 30, 50}, //MAP 3
            {0, 40, 30, 50}   //MAP 4
    };


    private Player player;
    private final int number;

    public Farm(ArrayList<ArrayList<Tile>> farmTiles, int number) {
        this.number = number;
        this.tiles = farmTiles;

        Random rand = new Random();
        for (Player player : App.currentGame.getPlayers()) {
            if (player.getMapNumber() == number) {
                player.setFarm(this);
                System.out.println(player.getFarm());
                this.player = player;

                int randomRow = rand.nextInt(farmTiles.size());
                int randomCol = rand.nextInt(farmTiles.get(randomRow).size());
                Tile randomTile = farmTiles.get(randomRow).get(randomCol);
                player.setPosition(randomTile.getPosition());
                System.out.println(randomTile.getPosition().x + ", " + randomTile.getPosition().y);
            }
        }

        for(ArrayList<Tile> row : farmTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }
    }



    public void build() {
        innerAreas = new ArrayList<>();

        innerAreas.add(new Lake());
        innerAreas.add(new GreenHouse());
        innerAreas.add(new House());
        innerAreas.add(new Quarry());
    }
}

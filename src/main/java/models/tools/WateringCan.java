package models.tools;

import models.Player;
import models.foraging.ForagingMineral;
import models.map.Area;
import models.map.AreaType;
import models.map.Tile;

import java.awt.image.AreaAveragingScaleFilter;

public class WateringCan extends Tool {
    private int waterAmount;
    private int capacity;

    public WateringCan() {
        this.toolType = ToolType.WATERING_CAN;
        this.toolLevel = ToolLevel.NORMAL;
        this.waterAmount = 0;
        this.capacity = 40;
        this.description = "don't forget to water your plants everyday.";
    }

    public boolean successfulAttempt(Tile tile) {
        return (waterAmount < capacity && tile.getAreaType().equals(AreaType.LAKE)) ||
                (waterAmount > 0 && (tile.getAreaType().equals(AreaType.FARM) || tile.getAreaType().equals(AreaType.GREENHOUSE)));
    }

    @Override
    public int calculateEnergyConsume(Tile tile, Player user) {
        if(successfulAttempt(tile)) {
            return switch (toolLevel) {
                case NORMAL -> user.getFarmingLevel() == 4 ? 4 : 5;
                case COOPER -> user.getFarmingLevel() == 4 ? 3 : 4;
                case IRON -> user.getFarmingLevel() == 4 ? 2 : 3;
                case GOLD -> user.getFarmingLevel() == 4 ? 1 : 2;
                case IRIDIUM -> user.getFarmingLevel() == 4 ? 0 : 1;
                default -> 0;
            };
        }
        else {
            return switch (toolLevel) {
                case NORMAL -> user.getFarmingLevel() == 4 ? 3 : 4;
                case COOPER -> user.getFarmingLevel() == 4 ? 2 : 3;
                case IRON -> user.getFarmingLevel() == 4 ? 1 : 2;
                case GOLD -> user.getFarmingLevel() == 4 ? 0 : 1;
                case IRIDIUM -> 0;
                default -> 0;
            };
        }
    }

    @Override
    public void upgrade() {

    }

    @Override
    public String use(Tile tile, Player user) {
        int energyConsume = calculateEnergyConsume(tile, user);
        if(energyConsume > user.getEnergy()) {
            return "you do not have enough energy to use this tool.";
        }

        if(waterAmount == capacity && tile.getAreaType().equals(AreaType.LAKE)) {
            return "can already filled!";
        }
        else if(waterAmount == 0 && (tile.getAreaType().equals(AreaType.FARM) || tile.getAreaType().equals(AreaType.GREENHOUSE))) {
            return "fill the can first!";
        }
        else if(waterAmount < capacity && tile.getAreaType().equals(AreaType.LAKE)) {
            user.subtractEnergy(energyConsume);
            waterAmount = capacity;
            return "can filled. " + energyConsume + " energy has been consumed.";
        }
        else if(waterAmount > 0 && (tile.getAreaType().equals(AreaType.FARM) || tile.getAreaType().equals(AreaType.GREENHOUSE))) {
            user.subtractEnergy(energyConsume);
            waterAmount--;
            tile.water();
            return "tile " + tile.getPosition() + " watered.\n" + energyConsume + " energy has been consumed.";
        }
        else {
            user.subtractEnergy(energyConsume);
            return "unsuccessful attempt! " + energyConsume + " energy has been consumed.";
        }
    }
}

package models.tools;

import models.Player;
import models.foraging.ForagingMineral;
import models.map.AreaType;
import models.map.Tile;

public class FishingPole extends Tool {
    public FishingPole() {
        this.toolType = ToolType.FISHING_POLE;
        this.toolLevel = ToolLevel.TRAINING;
        this.description = "a long, flexible rod used to catch fish.";
    }

    public boolean successfulAttempt(Tile tile) {
        return tile.getAreaType().equals(AreaType.LAKE);
    }

    @Override
    public int calculateEnergyConsume(Tile tile, Player user) {
        return switch (toolLevel) {
            case TRAINING -> user.getFishingLevel() == 4 ? 7 : 8;
            case BAMBOO -> user.getFishingLevel() == 4 ? 7 : 8;
            case FIBERGLASS -> user.getFishingLevel() == 4 ? 5 : 6;
            case IRIDIUM -> user.getFishingLevel() == 4 ? 3 : 4;
            default -> 0;
        };
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

        if(!successfulAttempt(tile)) {
            return "you can use fishing pole only on a lake tile.";
        }
        else {
            user.subtractEnergy(energyConsume);
            return "successful attempt!";
        }
    }
}

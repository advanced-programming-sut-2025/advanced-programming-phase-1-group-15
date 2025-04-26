package models.tools;

import models.Player;
import models.animals.Animal;
import models.farming.Crop;
import models.farming.ForagingCrop;
import models.map.Tile;

public class MilkPail extends Tool {
    public MilkPail() {
        this.toolType = ToolType.MILK_PAIL;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "gather milk from your animals.";
    }

    public boolean successfulAttempt(Tile tile) {
        if(tile.isEmpty()) {
            return false;
        }
        else return tile.getObjectInTile() instanceof Animal;
    }

    @Override
    public String use(Tile tile, Player user) {
        int energyConsume = 4;
        if(energyConsume > user.getEnergy()) {
            return "you do not have enough energy to use this tool.";
        }

        user.subtractEnergy(energyConsume);
        if(successfulAttempt(tile)) {
            return "You have successfully used this tool.";
        }
        else {
            return "unsuccessful attempt! " + energyConsume + " energy has been consumed.";
        }
    }
}

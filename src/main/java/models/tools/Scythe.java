package models.tools;

import models.Player;
import models.farming.GeneralPlants.PloughedPlace;
import models.map.Tile;

public class Scythe extends Tool {
    public Scythe() {
        this.toolType = ToolType.SCYTHE;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "used for harvesting crops.";
    }

    public boolean successfulAttempt(Tile tile) {
        if(tile.isEmpty()) {
            return false;
        }
        else return tile.getObjectInTile() instanceof PloughedPlace;
    }

    @Override
    public String upgrade(Player user) {
        return "you can't upgrade this tool.";
    }

    @Override
    public String use(Tile tile, Player user) {
        int energyConsume = 2;
        if(energyConsume > user.getEnergy()) {
            return "you do not have enough energy to use this tool.";
        }

        user.subtractEnergy(energyConsume);
        if(successfulAttempt(tile)) {
            user.upgradeFarmingAbility(5);
            PloughedPlace p = (PloughedPlace) tile.getObjectInTile();
            return p.getCurrentState().harvest().getMessage();
        }
        else {
            return "unsuccessful attempt! " + energyConsume + " energy has been consumed.";
        }
    }
}

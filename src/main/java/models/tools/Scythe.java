package models.tools;

import models.Player;
import models.farming.Crop;
import models.farming.ForagingCrop;
import models.map.Tile;

public class Scythe extends Tool {
    public Scythe() {
        this.toolType = ToolType.SCYTHE;
        this.toolLevel = ToolLevel.COOPER;
        this.description = "used for harvesting crops.";
    }

    public boolean successfulAttempt(Tile tile) {
        if(tile.isEmpty()) {
            return true;
        }
        else return tile.getObjectInTile() instanceof Crop || tile.getObjectInTile() instanceof ForagingCrop;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public String use(Tile tile, Player user) {
        int energyConsume = 2;
        if(energyConsume > user.getEnergy()) {
            return "you do not have enough energy to use this tool.";
        }

        user.subtractEnergy(energyConsume);
        if(successfulAttempt(tile)) {
            if(tile.getObjectInTile() instanceof Crop c) {
                user.addToBackPack(c, 1);
                user.upgradeFarmingAbility(5);
                tile.empty();

                return 1 + " " + c.getName() + " added to your inventory.\n" + energyConsume + " energy has been consumed.";
            }
            else {
                ForagingCrop fc = (ForagingCrop) tile.getObjectInTile();
                user.addToBackPack(fc, 1);
                user.upgradeFarmingAbility(5);
                tile.empty();

                return 1 + " " + fc.getName() + " added to your inventory.\n" + energyConsume + " energy has been consumed.";
            }
        }
        else {
            return "unsuccessful attempt! " + energyConsume + " energy has been consumed.";
        }
    }
}

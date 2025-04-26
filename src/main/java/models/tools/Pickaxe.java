package models.tools;

import models.Player;
import models.foraging.ForagingMineral;
import models.map.Tile;

public class Pickaxe extends Tool {
    public Pickaxe() {
        this.toolType = ToolType.PICKAXE;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "can break small rocks anywhere.";
    }

    public boolean successfulAttempt(Tile tile) {
        if(tile.isEmpty()) {
            return tile.isPlowed();
        }
        else return tile.getObjectInTile() instanceof ForagingMineral;
    }

    @Override
    public int calculateEnergyConsume(Tile tile, Player user) {
        if(successfulAttempt(tile)) {
            return switch (toolLevel) {
                case NORMAL -> user.getMiningLevel() == 4 ? 4 : 5;
                case COOPER -> user.getMiningLevel() == 4 ? 3 : 4;
                case IRON -> user.getMiningLevel() == 4 ? 2 : 3;
                case GOLD -> user.getMiningLevel() == 4 ? 1 : 2;
                case IRIDIUM -> user.getMiningLevel() == 4 ? 0 : 1;
                default -> 0;
            };
        }
        else {
            return switch (toolLevel) {
                case NORMAL -> user.getMiningLevel() == 4 ? 3 : 4;
                case COOPER -> user.getMiningLevel() == 4 ? 2 : 3;
                case IRON -> user.getMiningLevel() == 4 ? 1 : 2;
                case GOLD -> user.getMiningLevel() == 4 ? 0 : 1;
                case IRIDIUM -> 0;
                default -> 0;
            };
        }
    }

    @Override
    public void upgrade() {
        switch (toolLevel) {
            case NORMAL -> toolLevel = ToolLevel.COOPER;
            case COOPER -> toolLevel = ToolLevel.IRON;
            case IRON -> toolLevel = ToolLevel.GOLD;
            case GOLD -> toolLevel = ToolLevel.IRIDIUM;
        }
    }

    @Override
    public String use(Tile tile, Player user) {
        int energyConsume = calculateEnergyConsume(tile, user);
        if(energyConsume > user.getEnergy()) {
            return "you do not have enough energy to use this tool.";
        }

        user.subtractEnergy(energyConsume);
        if(successfulAttempt(tile)) {
            if(tile.isPlowed()) {
                tile.unplow();
                return "tile " + tile.getPosition() + " unplowed.\n" + energyConsume + " energy has been consumed.";
            }
            else if(tile.getObjectInTile() instanceof ForagingMineral fm) {
                int count = user.getMiningLevel() >= 2 ? 2 : 1;

                user.addToBackPack(fm, count);
                user.upgradeMiningAbility(10);
                tile.empty();

                return count + " " + fm.getName() + " added to your inventory.\n" + energyConsume + " energy has been consumed.";
            }
        }
        else {
            return "unsuccessful attempt! " + energyConsume + " energy has been consumed.";
        }

        return "unsuccessful attempt!";
    }
}

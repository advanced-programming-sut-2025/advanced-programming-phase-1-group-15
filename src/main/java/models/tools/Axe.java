package models.tools;

import models.Player;
import models.farming.ForagingSeeds;
import models.farming.Seed;
import models.farming.Tree;
import models.map.Tile;

public class Axe extends Tool {
    public Axe() {
        this.toolType = ToolType.AXE;
        this.toolLevel = ToolLevel.NORMAL;
        this.description = "chop, split, and shape woods!";
    }

    public boolean successfulAttempt(Tile tile) {
        if(tile.isEmpty()) {
            return false;
        }
        else return tile.getObjectInTile() instanceof Tree;
    }

    @Override
    public int calculateEnergyConsume(Tile tile, Player user) {
        if(successfulAttempt(tile)) {
            return switch (toolLevel) {
                case NORMAL -> user.getForagingLevel() == 4 ? 4 : 5;
                case COOPER -> user.getForagingLevel() == 4 ? 3 : 4;
                case IRON -> user.getForagingLevel() == 4 ? 2 : 3;
                case GOLD -> user.getForagingLevel() == 4 ? 1 : 2;
                case IRIDIUM -> user.getForagingLevel() == 4 ? 0 : 1;
                default -> 0;
            };
        }
        else {
            return switch (toolLevel) {
                case NORMAL -> user.getForagingLevel() == 4 ? 3 : 4;
                case COOPER -> user.getForagingLevel() == 4 ? 2 : 3;
                case IRON -> user.getForagingLevel() == 4 ? 1 : 2;
                case GOLD -> user.getForagingLevel() == 4 ? 0 : 1;
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
            Tree tr = (Tree) tile.getObjectInTile();
            Seed s = new Seed(tr.getSeedType());
            int count = user.getForagingLevel() >= 2 ? 2 : 1;


            user.addToBackPack(s, count);
            user.upgradeForagingAbility(10);
            tile.empty();

            return count + " " + s.getName() + " added to your inventory.\n" + energyConsume + " energy has been consumed.";
        }
        else {
            return "unsuccessful attempt! " + energyConsume + " energy has been consumed.";
        }
    }
}

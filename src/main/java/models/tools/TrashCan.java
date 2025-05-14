package models.tools;

import models.Player;

public class TrashCan extends Tool {
    double returnPercentage;

    public TrashCan() {
        this.toolType = ToolType.TRASH_CAN;
        this.toolLevel = ToolLevel.NORMAL;
        returnPercentage = 0;
        this.description = "used to delete items from the inventory menu.";
    }

    @Override
    public void upgrade(Player user) {
        switch (toolLevel) {
            case NORMAL -> {
                returnPercentage = 0.15;
                toolLevel = ToolLevel.COOPER;
            }
            case COOPER -> {
                returnPercentage = 0.3;
                toolLevel = ToolLevel.IRON;
            }
            case IRON -> {
                returnPercentage = 0.45;
                toolLevel = ToolLevel.GOLD;
            }
            case GOLD -> {
                returnPercentage = 0.6;
                toolLevel = ToolLevel.IRIDIUM;
            }
        }
    }

    public int use(BackPackable item, int amount, Player player) {
        int returnAmount = (int) (returnPercentage * amount * item.getPrice());
        player.addGold(returnAmount);
        return returnAmount;
    }
}

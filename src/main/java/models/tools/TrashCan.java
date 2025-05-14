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
    public String upgrade(Player user) {
        switch (toolLevel) {
            case NORMAL -> {
                if(user.getInventory().getItemCount("copper") < 5) {
                    return "not enough cooper!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("copper"), 5);
                toolLevel = ToolLevel.COPPER;
                returnPercentage = 0.15;
                return "your trash can upgraded to COPPER!";
            }
            case COPPER -> {
                if(user.getInventory().getItemCount("iron") < 5) {
                    return "not enough iron!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iron"), 5);
                toolLevel = ToolLevel.IRON;
                returnPercentage = 0.3;
                return "your trash can upgraded to IRON!";
            }
            case IRON -> {
                if(user.getInventory().getItemCount("gold") < 5) {
                    return "not enough gold!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("gold"), 5);
                toolLevel = ToolLevel.GOLD;
                returnPercentage = 0.45;
                return "your trash can upgraded to GOLD!";
            }
            case GOLD -> {
                if(user.getInventory().getItemCount("iridium") < 5) {
                    return "not enough iridium!";
                }
                user.getInventory().removeCountFromBackPack(user.getInventory().getItemByName("iridium"), 5);
                toolLevel = ToolLevel.IRIDIUM;
                returnPercentage = 0.6;
                return "your trash can upgraded to IRIDIUM!";
            }
        }
        return "you can't upgrade this tool.";
    }

    public int use(BackPackable item, int amount, Player player) {
        int returnAmount = (int) (returnPercentage * amount * item.getPrice());
        player.addGold(returnAmount);
        return returnAmount;
    }
}

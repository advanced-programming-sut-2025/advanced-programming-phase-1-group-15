package com.example.common.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.farming.GeneralPlants.PloughedPlace;
import com.example.common.map.Tile;
import com.example.client.views.GameAssetManager;

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
        return "this tool is not upgradable.";
    }

    @Override
    public Result use(Tile tile, Player user) {
        int energyConsume = 2;
        if(energyConsume > user.getEnergy()) {
            return new Result(false, "you do not have enough energy to use this tool.");
        }

        user.subtractEnergy(energyConsume);
        if(successfulAttempt(tile)) {
            user.upgradeFarmingAbility(5);
            PloughedPlace p = (PloughedPlace) tile.getObjectInTile();
            return new Result(true, p.getCurrentState().harvest().getMessage());
        }
        else {
            return new Result(false, "unsuccessful attempt! " + energyConsume + " energy has been consumed.");
        }
    }

    @Override
    public Sprite getSprite() {
        return GameAssetManager.scythe;
    }
}

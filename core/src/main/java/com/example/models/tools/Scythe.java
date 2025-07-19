package com.example.models.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.Player;
import com.example.models.Result;
import com.example.models.farming.GeneralPlants.PloughedPlace;
import com.example.models.map.Tile;
import com.example.views.GameAssetManager;

import java.awt.geom.RectangularShape;

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

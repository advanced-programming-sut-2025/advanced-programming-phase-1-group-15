package com.example.models.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.models.Player;
import com.example.models.Result;
import com.example.models.animals.Fish;
import com.example.models.animals.ProductQuality;
import com.example.models.map.Lake;
import com.example.models.map.Tile;
import com.example.models.weather.WeatherOption;
import com.example.views.GameAssetManager;

public class FishingPole extends Tool {
    public FishingPole() {
        this.toolType = ToolType.FISHING_POLE;
        this.toolLevel = ToolLevel.TRAINING;
        this.description = "a long, flexible rod used to catch fish.";
    }
    public FishingPole(ToolLevel toolLevel) {
        this.toolType = ToolType.FISHING_POLE;
        this.toolLevel = toolLevel;
        this.description = "a long, flexible rod used to catch fish.";
    }

    public int calculateEnergyConsume(Player user) {
        return switch (toolLevel) {
            case TRAINING, BAMBOO -> user.getFishingLevel() == 4 ? 7 : 8;
            case FIBERGLASS -> user.getFishingLevel() == 4 ? 5 : 6;
            case IRIDIUM -> user.getFishingLevel() == 4 ? 3 : 4;
            default -> 0;
        };
    }

    public ToolLevel getToolLevel() {
        return toolLevel;
    }

    @Override
    public String upgrade(Player user) {
        return "this tool is not upgradable.";
    }

    public String use(Lake lake, Player user, WeatherOption weather) {
        int energyConsume = calculateEnergyConsume(user);
        user.subtractEnergy(energyConsume);
        user.upgradeFishingAbility(5);

        double R = Math.random();
        double M = switch (weather) {
            case SUNNY -> 1.5;
            case RAINY -> 1.2;
            case STORM -> 0.5;
            default -> 1;
        };
        int count = Math.min((int) (R * M * (user.getFishingLevel() + 2)), 6);
        if(count == 0) {
            return "Oops you couldn't catch any fish!\n" + energyConsume + " energy has been consumed.";
        }

        double P = switch (toolLevel) {
            case TRAINING -> 0.1;
            case BAMBOO -> 0.5;
            case FIBERGLASS -> 0.9;
            case IRIDIUM -> 1.2;
            default -> 0;
        };
        double q = (R * (user.getFishingLevel() + 2) * P) / (7 - M);
        ProductQuality quality;
        if(q <= 0.5) {
            quality = ProductQuality.NORMAL;
        }
        else if(q <= 0.7) {
            quality = ProductQuality.SILVER;
        }
        else if(q <= 0.9) {
            quality = ProductQuality.GOLD;
        }
        else {
            quality = ProductQuality.IRIDIUM;
        }

        Fish caughtFish = new Fish(lake.getTodaysFishType(), quality);
        user.addToBackPack(caughtFish, count);

        return "You caught " + count + " " + caughtFish.getName() + ".\n" + energyConsume + " energy has been consumed.";
    }

    @Override
    public Result use(Tile tile, Player user) {
        return new Result(false, "this command is not applicable to fishing pole.");
    }

    @Override
    public Sprite getSprite() {
        switch (toolLevel) {
            case BAMBOO -> {
                return GameAssetManager.bamboo_rod;
            }
            case FIBERGLASS -> {
                return GameAssetManager.fiberglass_rod;
            }
            case IRIDIUM -> {
                return GameAssetManager.iridium_rod;
            }
            default -> {
                return GameAssetManager.training_rod;
            }
        }
    }
}

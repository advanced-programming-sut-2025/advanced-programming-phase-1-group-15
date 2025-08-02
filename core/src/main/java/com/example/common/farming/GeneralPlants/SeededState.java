package com.example.common.farming.GeneralPlants;

import com.example.common.Result;
import com.example.common.farming.Crop;
import com.example.common.farming.Fertilizer;
import com.example.common.farming.Seedable;
import com.example.common.farming.Tree;

public class SeededState implements PlantState {
    PloughedPlace tile;
    boolean isWatered = false;

    public SeededState(PloughedPlace tile) {
        this.tile = tile;
        if(tile.getFertilizer() != null){
            if(tile.getFertilizer().equals(Fertilizer.Growth)){
                daysWatered ++;
            }
        }
    }
    private int daysNotWatered = 0 ;
    private int daysWatered = 0;
    private int growthLevel = 0;

    @Override
    public Result fertilize(Fertilizer fertilizer) {
        return new Result(false,"this tile is already fertilized");
    }

    @Override
    public Result water() {
        isWatered = true;
        daysWatered++;
        daysNotWatered = 0;
        if(growthLevel <= 3) {
            if(tile.getHarvestable() instanceof Crop){
                Crop crop = (Crop) tile.getHarvestable();
                if(daysWatered > crop.getStages().get(growthLevel)){
                    daysWatered = 0;
                    growthLevel++;
                }
            }
            else if(tile.getHarvestable() instanceof Tree){
                Tree tree = (Tree) tile.getHarvestable();
                if(daysWatered > tree.getStages().get(growthLevel)){
                    daysWatered = 0;
                    growthLevel++;
                }
            }
        }
        else{
            tile.setState(new WateredState(tile));
        }
        return new Result(true,"successfully watered this plant!");
    }

    @Override
    public Result harvest() {
        if(growthLevel > 3 ){
            tile.harvest();
            System.out.println("test");
        }
        return new Result(false,"you should water this tile first");
    }

    public int getGrowthLevel() {
        return growthLevel;
    }

    @Override
    public Result updateByTime() {
        daysNotWatered++;
        Fertilizer fertilizer = tile.getFertilizer();
        isWatered = false;
        if(fertilizer != null){
            if(fertilizer.equals(Fertilizer.Water)){
                return null;
            }
        }
        if(daysNotWatered >= 2 && growthLevel > 3){
            tile.unPlough();
        }
        return null;
    }

    @Override
    public Result takeRest() {
        return null;
    }

    @Override
    public Result seed(Seedable seed) {
        return new Result(false,"this tile is already seeded");
    }
}

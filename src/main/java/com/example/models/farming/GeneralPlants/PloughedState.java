package com.example.models.farming.GeneralPlants;

import com.example.models.Result;
import com.example.models.farming.*;

public class PloughedState implements PlantState {

    PloughedPlace tile;

    public PloughedState(PloughedPlace tile) {
        this.tile = tile;
    }




    @Override
    public Result updateByTime() {
        return null;
    }

    @Override
    public Result seed(Seedable seed) {
        if(seed instanceof CropSeeds cropSeed) {
            tile.setCropSeed(cropSeed);
            try{tile.setHarvestable(new Crop(CropSeeds.cropOfThisSeed(cropSeed)));}
            catch(Exception e){
                return new Result(false,"bug in getting crop of this seed");
            }
        }
        else if(seed instanceof SeedType treeSeed) {
            tile.setSeed(treeSeed);
            try{tile.setHarvestable(new Tree(SeedType.getTreeOfSeedType(treeSeed)));}
            catch(Exception e){
                return new Result(false,"bug in getting tree of this seed");
            }
        }
        tile.setState(new SeededState(tile));
        return new Result(true,"congratulations! you seeded this tile!");
    }

    @Override
    public Result fertilize(Fertilizer fertilizer) {
        if(fertilizer.equals(Fertilizer.Water)){
            tile.setFertilizer(Fertilizer.Water);
            return new Result(true,"successfully fertilized waterFertilizer!");
        }
        else if(fertilizer.equals(Fertilizer.Growth)){
            tile.setFertilizer(Fertilizer.Growth);
            return new Result(true,"successfully fertilized growthFertilizer!");
        }
        else return new Result(false,"invalid fertilizer");
    }

    @Override
    public Result water() {
        return new Result(false,"you should seed this tile first");
    }

    @Override
    public Result harvest() {
        return new Result(false,"you should seed this tile first");
    }

    @Override
    public Result takeRest() {
        return null;
    }
}

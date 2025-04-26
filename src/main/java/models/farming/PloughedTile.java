package models.farming;

import models.map.Map;
import models.map.Position;
import models.map.Tilable;
import models.map.Tile;
import models.time.DateAndTime;
import models.time.TimeObserver;

public class PloughedTile implements Tilable, TimeObserver {
    Position position;
    Map map;
    Tile tile;

    Harvestable harvestable;

    public boolean hasTreeOrCrop(){
        return harvestable != null;
    }

    public PloughedTile(Map map,Position position) {
        tile = map.getTile(position);
    }

    public void seed(SeedType seedType) {
        harvestable = new Tree(SeedType.getTreeOfSeedType(seedType));
    }

    public void seed(CropSeeds seed){
        harvestable = new Crop(CropSeeds.cropOfThisSeed(seed));
        // TODO: check for getting Giant
    }

    @Override
    public void update(DateAndTime dateAndTime) {

    }
}

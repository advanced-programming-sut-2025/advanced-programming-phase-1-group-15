package models.farming;

import models.map.Map;
import models.map.Position;
import models.map.Tilable;
import models.map.Tile;

public class PloughedTile implements Tilable {
    Position position;
    Map map;
    Tile tile;

    public PloughedTile(Map map,Position position) {
        tile = map.getTile(position);
    }

    public void seed(SeedType seedType) {
        tile.setObjectInTile(new Tree(SeedType.getTreeOfSeedType(seedType)));
    }

    public void seed(CropSeeds seed){
        tile.setObjectInTile(new Crop(CropSeeds.cropOfThisSeed(seed)));
        // TODO: check for getting Giant
    }

}

package models.farming.GeneralPlants;

import models.Result;
import models.farming.SeedType;

public interface PlantState {
    Result seed(PloughedTile tile, SeedType seed);
    Result fertilize(PloughedTile tile);
    Result water(PloughedTile tile);
    Result harvest(PloughedTile tile);
    Result updateByTime(PloughedTile tile);
}

package models.farming.GeneralPlants;

import models.Result;
import models.farming.CropSeeds;
import models.farming.SeedType;

public interface PlantState {
    Result seed(PloughedPlace tile, CropSeeds seed);
    //Result seed(PloughedPlace tile, CropSeeds seed);
    Result fertilize(PloughedPlace tile);
    Result water(PloughedPlace tile);
    Result harvest(PloughedPlace tile);
    Result updateByTime(PloughedPlace tile);
}

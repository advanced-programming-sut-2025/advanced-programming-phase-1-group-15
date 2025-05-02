package models.farming.GeneralPlants;

import models.Result;
import models.farming.CropSeeds;
import models.farming.SeedType;

public interface PlantState {
    Result seed(CropSeeds seed);
    //Result seed(CropSeeds seed);
    Result fertilize();
    Result water();
    Result harvest();
    Result takeRest();
    Result updateByTime();
}

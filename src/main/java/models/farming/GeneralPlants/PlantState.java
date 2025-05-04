package models.farming.GeneralPlants;

import models.Result;
import models.farming.CropSeeds;
import models.farming.MixedSeedCrop;
import models.farming.SeedType;
import models.farming.Seedable;

public interface PlantState {
    Result seed(Seedable seed);
    Result fertilize();
    Result water();
    Result harvest();
    Result takeRest();
    Result updateByTime();
}

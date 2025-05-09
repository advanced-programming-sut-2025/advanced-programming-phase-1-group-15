package models.farming.GeneralPlants;

import models.Result;
import models.farming.*;

public interface PlantState {
    Result seed(Seedable seed);
    Result fertilize(Fertilizer fertilizer);
    Result water();
    Result harvest();
    Result takeRest();
    Result updateByTime();
}

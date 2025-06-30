package com.example.models.farming.GeneralPlants;

import com.example.models.Result;
import com.example.models.farming.*;

public interface PlantState {
    Result seed(Seedable seed);
    Result fertilize(Fertilizer fertilizer);
    Result water();
    Result harvest();
    Result takeRest();
    Result updateByTime();
}

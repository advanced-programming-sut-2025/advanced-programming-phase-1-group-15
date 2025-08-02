package com.example.common.farming.GeneralPlants;

import com.example.common.Result;
import com.example.common.farming.Fertilizer;
import com.example.common.farming.Seedable;

public interface PlantState {
    Result seed(Seedable seed);
    Result fertilize(Fertilizer fertilizer);
    Result water();
    Result harvest();
    Result takeRest();
    Result updateByTime();
}

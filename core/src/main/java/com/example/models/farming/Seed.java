package com.example.models.farming;

import com.example.models.map.Tilable;
import com.example.models.tools.BackPackable;

public class Seed implements Tilable, BackPackable {
    SeedType seedType;

    public Seed(SeedType seedType) {
        this.seedType = seedType;
    }

    @Override
    public String getName() {
        return seedType.name().toLowerCase().replaceAll("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getPrice() {
        return 0;
    }
}

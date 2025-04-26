package models.farming;

import models.map.Tilable;
import models.tools.BackPackable;

public class Seed implements Tilable, BackPackable {
    SeedType seedType;

    public Seed(SeedType seedType) {
        this.seedType = seedType;
    }

    @Override
    public String getName() {
        return seedType.name().toLowerCase().replace("_", " ");
    }

    @Override
    public String getDescription() {
        return "";
    }
}

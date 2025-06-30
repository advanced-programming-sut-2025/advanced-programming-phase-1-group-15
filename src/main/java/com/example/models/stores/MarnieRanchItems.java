package com.example.models.stores;

import com.example.models.tools.ToolType;

public enum MarnieRanchItems {
    MILK_PAIL(ToolType.MILK_PAIL, 1000, 1),
    SHEARS(ToolType.SHEAR, 1000, 1);

    public final ToolType toolType;
    public final int price;
    public final int dailyLimit;
    MarnieRanchItems(ToolType toolType, int price, int dailyLimit) {
        this.toolType = toolType;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }

    public String getName() {
        return name().toLowerCase().replaceAll("_", " ");
    }
}

package com.example.models.tools;

import com.example.models.map.Tilable;

public interface BackPackable extends Tilable {
    public String getName();
    public String getDescription();
    public int getPrice();
}

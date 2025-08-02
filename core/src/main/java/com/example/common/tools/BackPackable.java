package com.example.common.tools;

import com.example.common.map.Tilable;

public interface BackPackable extends Tilable {
    public String getName();
    public String getDescription();
    public int getPrice();
}

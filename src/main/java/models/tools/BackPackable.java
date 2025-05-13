package models.tools;

import models.map.Tilable;

public interface BackPackable extends Tilable {
    public String getName();
    public String getDescription();
    public int getPrice();
}

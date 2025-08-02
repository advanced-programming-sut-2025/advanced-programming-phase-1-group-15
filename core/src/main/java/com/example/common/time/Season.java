package com.example.common.time;

public enum Season {
    SPRING, SUMMER, AUTUMN, WINTER, ALL;

    public String displaySeason() {
        return switch (this) {
            case SPRING -> "Spring";
            case SUMMER -> "Summer";
            case AUTUMN -> "Autumn";
            case WINTER -> "Winter";
            case ALL -> "All";
        };
    }
}

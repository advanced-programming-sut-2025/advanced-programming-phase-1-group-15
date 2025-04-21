package models.time;

public enum Season {
    SPRING, SUMMER, AUTUMN, WINTER;

    public String displaySeason() {
        return switch (this) {
            case SPRING -> "Spring";
            case SUMMER -> "Summer";
            case AUTUMN -> "Autumn";
            case WINTER -> "Winter";
        };
    }
}

package models.weather;

public enum WeatherOption {
    SUNNY, RAINY, STORM, SNOW;

    public String displayWeather() {
        return switch (this) {
            case SUNNY -> "Sunny";
            case RAINY -> "Rainy";
            case STORM -> "Stormy";
            case SNOW -> "Snowy";
        };
    }
}

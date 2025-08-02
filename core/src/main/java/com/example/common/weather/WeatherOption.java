package com.example.common.weather;

public enum WeatherOption {
    SUNNY, RAINY, STORM, SNOW;

    public String displayWeather() {
        return switch (this) {
            case SUNNY -> "Sunny";
            case RAINY -> "Rain";
            case STORM -> "Storm";
            case SNOW -> "Snow";
        };
    }
    public static WeatherOption getWeather(String weather) {
        switch (weather) {
            case "sunny" -> {
                return SUNNY;
            }
            case "rain" -> {
                return RAINY;
            }
            case "storm" -> {
                return STORM;
            }
            case "snow" -> {
                return SNOW;
            }
        }

        return null;
    }
}

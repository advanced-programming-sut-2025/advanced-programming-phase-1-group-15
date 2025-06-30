package com.example.models.enums;

public enum Gender {
    BOY, GIRL;

    public static Gender fromString(String gender) {
        return switch (gender) {
            case "boy" -> BOY;
            case "girl" -> GIRL;
            default -> null;
        };
    }
}

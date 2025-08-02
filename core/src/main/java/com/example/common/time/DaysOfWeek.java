package com.example.common.time;

public enum DaysOfWeek {
    MONDAY, TUESDAY, WEDNESDAY,THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public String displayDayOfWeek() {
        return switch (this) {
            case MONDAY -> "Mon";
            case TUESDAY -> "Tue";
            case WEDNESDAY -> "Wed";
            case THURSDAY -> "Thur";
            case FRIDAY -> "Fri";
            case SATURDAY -> "Sat";
            case SUNDAY -> "Sun";
        };
    }
}

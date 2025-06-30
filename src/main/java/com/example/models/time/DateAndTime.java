package com.example.models.time;

import java.util.ArrayList;
import java.util.List;

public class DateAndTime {
    private final List<TimeObserver> observers = new ArrayList<>();

    public void addObserver(TimeObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(TimeObserver observer) {
        observers.remove(observer);
    }
    private void notifyObservers() {
        List<TimeObserver> snapshot = new ArrayList<>(observers);
        for (TimeObserver obs : snapshot) {
            obs.update(this);
        }
    }

    private int year = 0;
    private Season season = Season.SPRING;
    private DaysOfWeek dayOfWeek = DaysOfWeek.MONDAY;
    private int day = 1;
    private int hour = 9;

    public void nextYear(){
        year++;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void nextSeason() {
        if(season == Season.SUMMER) {
            season = Season.AUTUMN;
        } else if(season == Season.AUTUMN) {
            season = Season.WINTER;
        } else if(season == Season.WINTER) {
            season = Season.SPRING;
            nextYear();
        } else if(season == Season.SPRING) {
            season = Season.SUMMER;
        }
    }

    public void nextDayOfWeek() {
        if (dayOfWeek == DaysOfWeek.MONDAY) {
            dayOfWeek = DaysOfWeek.TUESDAY;
        } else if (dayOfWeek == DaysOfWeek.TUESDAY) {
            dayOfWeek = DaysOfWeek.WEDNESDAY;
        } else if (dayOfWeek == DaysOfWeek.WEDNESDAY) {
            dayOfWeek = DaysOfWeek.THURSDAY;
        } else if (dayOfWeek == DaysOfWeek.THURSDAY) {
            dayOfWeek = DaysOfWeek.FRIDAY;
        } else if (dayOfWeek == DaysOfWeek.FRIDAY) {
            dayOfWeek = DaysOfWeek.SATURDAY;
        } else if (dayOfWeek == DaysOfWeek.SATURDAY) {
            dayOfWeek = DaysOfWeek.SUNDAY;
        } else if (dayOfWeek == DaysOfWeek.SUNDAY) {
            dayOfWeek = DaysOfWeek.MONDAY;
        }
    }

    public void nextDay() {
        if(day == 28) {
            day = 1;
            nextSeason();
        }
        else {
            this.day++;
        }

        this.hour = 9;
        nextDayOfWeek();
        notifyObservers();
    }
    public void nextNDays(int n) {
        for(int i = 0; i < n; i++) {
            nextDay();
        }
    }

    public void nextHour(){
        if(hour == 23) {
            hour = 9;
            nextDay();
        }
        else {
            this.hour++;
        }
        notifyObservers();
    }
    public void nextNHours(int n){
        for(int i = 0; i < n; i++) {
            nextHour();
        }
    }

    public int getYear() {
        return year;
    }
    public String displayYear() {
        return "year: " + year;
    }

    public Season getSeason() {
        return season;
    }
    public String displaySeason() {
        return "season: " + season.displaySeason();
    }

    public int getDay() {
        return day;
    }
    public String displayDay() {
        return "day: " + day;
    }
    public String displayDayOfWeek() {
        return dayOfWeek.displayDayOfWeek();
    }

    public int getHour() {
        return hour;
    }
    public String displayHour() {
        return hour + ":00";
    }

    public String displayDate() {
        return displayDay() + " " + displaySeason() + " " + displayYear();
    }
    public String displayDateTime() {
        return displayHour() + " " + displayDate();
    }
}

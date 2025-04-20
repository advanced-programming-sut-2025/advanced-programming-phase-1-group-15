package models.time;

import java.util.ArrayList;
import java.util.List;

public class DateAndTime {
    private static final DateAndTime instance = new DateAndTime(0,Season.SPRING, DaysOfWeek.MONDAY,1,9);
    private List<TimeObserver> observers = new ArrayList<>();

    public void addObserver(TimeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TimeObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (TimeObserver observer : observers) {
            observer.update(this);
        }
    }

    private int year;
    private Season season;
    private DaysOfWeek daysOfWeek;
    private int day;
    private int hour;

    private DateAndTime(int year, Season season, DaysOfWeek daysOfWeek, int day, int hour) {
        this.year = year;
        this.season = season;
        this.daysOfWeek = daysOfWeek;
        this.day = day;
        this.hour = hour;
    }

    public static DateAndTime getInstance() {
        return instance;
    }

    public void nextYear(){
        this.year++;
    }

    public void nextSeason() {
        if(this.season == Season.SUMMER) {
            this.season = Season.AUTUMN;
        }
        else if(this.season == Season.AUTUMN) {
            this.season = Season.WINTER;
        }
        else if(this.season == Season.WINTER) {
            this.season = Season.SPRING;
            nextYear();
        }
        else {
            this.season = Season.SUMMER;
        }
    }

    public void nextDayOfWeek() {

    }

    public void nextDay() {
        this.day++;
        nextDayOfWeek();
    }
    public void nextNDays(int n) {

    }

    public void nextHour(){
        this.hour++;
    }
    public void nextNHours(int n){
        this.hour += n;
    }

    public int getYear() {
        return year;
    }
    public Season getSeason() {
        return season;
    }
    public int getDay() {
        return day;
    }
    public int getHour() {
        return hour;
    }
}

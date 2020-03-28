package com.osamaelsh3rawy.otlop.helper;

public class DateModel {
    private String day, month, year;
    private String date_txt;

    public DateModel(String day, String month, String year, String date_txt) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.date_txt = date_txt;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate_txt() {
        return date_txt;
    }

    public void setDate_txt(String date_txt) {
        this.date_txt = date_txt;
    }
}

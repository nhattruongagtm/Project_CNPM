package com.example.project_cnpm.Model;

public class DateTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    //  Constructor nhận vào String
    public DateTime(String dateTime) {
        String[] dateTimes = dateTime.split(" ");
        String[] dates = dateTimes[0].split("-");
        String[] times = dateTimes[1].split(":");
        this.year = Integer.parseInt(dates[0]);
        this.month = Integer.parseInt(dates[1]);
        this.day = Integer.parseInt(dates[2]);
        this.hour = Integer.parseInt(times[0]);
        this.minute = Integer.parseInt(times[1]);
        this.second = (int) (Double.parseDouble(times[2]));
    }
    public DateTime(){

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
    }
}

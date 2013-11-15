package com.dacer.androidcharts;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Dacer on 10/24/13.
 */
public class SimpleDate {
    private int year;
    private int month;
    private int day;
    private String nameOfWeek;
    private TimeZone timeZone;

    public SimpleDate(Calendar calendar,Context context){
        initDate(calendar,context);
    }

    public SimpleDate(int year, int month, int day,Context context){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day,0,0,0);
        initDate(calendar,context);
    }

    private void initDate(Calendar calendar,Context context){
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        timeZone = calendar.getTimeZone();
        nameOfWeek = "week";
    }

    public boolean isFuture(){
        Calendar todayCal = Calendar.getInstance();
        todayCal.set(todayCal.get(Calendar.YEAR),
                todayCal.get(Calendar.MONTH),
                todayCal.get(Calendar.DATE),0,0,0);
        if(getCalendar().getTimeInMillis()>todayCal.getTimeInMillis()){
            return true;
        }
        return false;
    }

    public Calendar getCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(getYear(),getMonth(),getDay(),0,0,0);
        calendar.setTimeZone(getTimeZone());
        return calendar;
    }

    public boolean equals(SimpleDate simpleDate){
        if(this.getDay()==simpleDate.getDay()&&
                this.getMonth()==simpleDate.getMonth()&&
                this.getYear()==simpleDate.getYear()){
            return true;
        }
        return false;
    }

    public String toString(){
        return "Year:"+getYear()+"; Month:"+(getMonth()+1)+"; Day"+getDay();
    }

    public String toStringWithoutYear(){
        return getMonthStr()+"-"+getDayStr();
    }



    public ArrayList<String> getThisMonthStrList(){
        Calendar c = getCalendar();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DATE, -1);
        int totalDay = c.get(Calendar.DAY_OF_MONTH);
        ArrayList<String> result = new ArrayList<String>();
        for (int i=1;i<=totalDay;i++){
            setDay(i);
            result.add(toStringWithoutYear());
        }
        return result;
    }

    public String getDayStr(){
        if(getDay()<10){
            return "0"+getDay();
        }else{
            return String.valueOf(getDay());
        }
    }

    /**
     *
     * @return The month between 1 and 12 !
     */
    public String getMonthStr(){
        if(getMonth()<9){
            return "0"+(getMonth()+1);
        }else{
            return String.valueOf(getMonth()+1);
        }
    }

    public void setDay(int day){
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setMonth(int month){
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setYear(int year){
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public String getNameOfWeek() {
        return nameOfWeek;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }
}

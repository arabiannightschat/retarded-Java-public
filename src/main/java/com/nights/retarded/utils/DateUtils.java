package com.nights.retarded.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static Date add(Date date , int num, int timeType){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(timeType, num);
        return c.getTime();
    }

    public static Date sdfParse(String string){
        Date date = null;
        try {
            date = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
	
	public static Date addDay(Date date ,int day) {
        return add(date, day, Calendar.DATE);
	}

    public static Date addMonth(Date date, int month) {
        return add(date, month, Calendar.MONTH);
    }

    public static Date addHour(Date date, int hour) {
        return add(date, hour, Calendar.HOUR);
    }

    public static Date addMinute(Date date, int minute) {
        return add(date, minute, Calendar.MINUTE);
    }

    public static Date addSecond(Date date, int second) {
        return add(date, second, Calendar.SECOND);
    }

    public static Date addMilliSecond(Date date, int milliSecond) {
        return add(date, milliSecond, Calendar.MILLISECOND);
    }

    public static final SimpleDateFormat daySdf = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sdf_999 = new SimpleDateFormat("yyyy-MM-dd 23:59:59.999");
    public static final SimpleDateFormat sdf_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");

	public static Date toDaySdf(Date date) {
	    String day = daySdf.format(date);
        try {
            return daySdf.parse(day);
        } catch (ParseException e) {
            return null;
        }
    }

    // 从传入日期到月末剩余天数
	public static int dayToNextMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int d = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int now = c.get(Calendar.DAY_OF_MONTH);
        return d - now + 1;
    }

    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static String toCategories(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("M-dd");
        return sdf.format(date);
    }

    public static String toCategoriesDay(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("d");
        return sdf.format(dt);
    }

    public static Date dayBegin(Date date){
        return toDaySdf(date);
    }

    public static Date monthBegin(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(toDaySdf(date));
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date yearBegin(Date date){
        try {
            return yearSdf.parse(yearSdf.format(date));
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date dayEnd(Date date){
        try {
            return sdf_SSS.parse(sdf_999.format(date));
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date monthEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, monthDays(date));
        try {
            return sdf_SSS.parse(sdf_999.format(c.getTime()));
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date yearEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, 11);
        c.set(Calendar.DAY_OF_MONTH, 31);
        try {
            return sdf_SSS.parse(sdf_999.format(c.getTime()));
        } catch (ParseException e) {
            return null;
        }

    }

    public static Date lastMonthFirstDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(toDaySdf(new Date()));
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date currMonthFirstDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(toDaySdf(new Date()));
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Integer diff(Date date1, Date date2){
        long diff = date1.getTime() - date2.getTime();
        return (int) (diff / (24 * 60 * 60 * 1000));
    }

    public static boolean isSameMonth(Date date, Date dt) {
        if(getMonth(date) == getMonth(dt)){
            return true;
        } else {
            return false;
        }
    }

    public static int monthDays(Date dt) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static boolean isEqual( Date date1 ,Date date2) {
	    if(date1 == null || date2 == null) {
	        return false;
        }
	    if(date1.getTime() == date2.getTime()){
	        return true;
        }
        return false;
    }

    public static Date setSecondZero(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static String formatDate(Date date, String pattern) {
	    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	    return sdf.format(date);
    }

    public static String formatDate(Date date) {
        return sdf.format(date);
    }

    public static String formatDateSSS(Date date) {
        return sdf_SSS.format(date);
    }

    public static Date yearFirstMonth(Date year) {
        Calendar c = Calendar.getInstance();
        c.setTime(monthBegin(year));
        c.set(Calendar.MONTH, 0);
        return c.getTime();

    }

    public static Date yearLastMonth(Date year) {
        Calendar c = Calendar.getInstance();
        c.setTime(toDaySdf(year));
        c.set(Calendar.MONTH, 11);
        c.set(Calendar.DAY_OF_MONTH, monthDays(c.getTime()));
        Date a = c.getTime();
        try {
            return sdf_SSS.parse(sdf_999.format(a));
        } catch (ParseException e) {
            return null;
        }
    }

}

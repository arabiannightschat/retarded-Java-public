package com.nights.retarded.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static Date addDay(Date date ,int day) {
		Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        return c.getTime();
	}

    public static Date addMonth(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    public static final SimpleDateFormat daySdf = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat daySdf2 = new SimpleDateFormat("yyyyMMdd");

	public static Date toDaySdf(Date date) {
	    String day = daySdf.format(date);
        try {
            return daySdf.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

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
        return c.get(Calendar.DAY_OF_MONTH) + 1;
    }

    public static String toCategories(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("M-dd");
        return sdf.format(date);
    }

    public static String toCategoriesDay(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("d");
        return sdf.format(dt);
    }

    public static String getDayOfWeekChinese(Date date) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    public static Date monthFirstDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date lastMonthFirstDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date currMonthFirstDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
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

    public static Date monthLastDay(Date dt) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.set(Calendar.DAY_OF_MONTH, monthDays(dt));
        return c.getTime();
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

}

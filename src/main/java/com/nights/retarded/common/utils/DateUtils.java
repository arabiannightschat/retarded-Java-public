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
        return d - now;
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

    public static String toCategories(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("M-dd");
        return sdf.format(date);
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
}

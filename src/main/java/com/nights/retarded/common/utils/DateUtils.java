package com.nights.retarded.common.utils;

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

	public static int dayToNextMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int d = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int now = c.get(Calendar.DAY_OF_MONTH);
        return d - now;
    }

}

package com.nights.retarded.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static Date addDay(Date date ,int day) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
	}

    public static final SimpleDateFormat daySdf = new SimpleDateFormat("yyyy-MM-dd");

}

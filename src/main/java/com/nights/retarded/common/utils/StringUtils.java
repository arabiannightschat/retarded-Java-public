package com.nights.retarded.common.utils;

public class StringUtils {

    public static boolean isBlank(String string){
        return org.springframework.util.StringUtils.isEmpty(string);
    }

    public static boolean isNotBlank(String string){
        return !isBlank(string);
    }
}

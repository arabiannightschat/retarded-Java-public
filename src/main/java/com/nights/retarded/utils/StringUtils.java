package com.nights.retarded.utils;

public class StringUtils {

    public static boolean isEmpty(String string){
        if(string == null || string.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(String string){
        return !isEmpty(string);
    }
}

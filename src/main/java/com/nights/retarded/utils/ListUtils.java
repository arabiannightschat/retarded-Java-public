package com.nights.retarded.utils;

import java.util.List;

public class ListUtils {

    public static <T> T getIndexZero (List<T> list){
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    public static boolean isEmpty(List list) {
        if(list == null || list.size() == 0){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(List list){
        return !isEmpty(list);
    }

    public static <T> List<T> listToList(List source, Class<T> clazz){
        return JsonUtils.jsonToList(JsonUtils.objectToJson(source), clazz);
    }

}

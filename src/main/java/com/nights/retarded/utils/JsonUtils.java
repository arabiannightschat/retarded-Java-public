package com.nights.retarded.utils;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	private static ObjectMapper json = new ObjectMapper();

    static {
        json.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }
	
	/**
	 * 参数为 String 型的 wxopenId = {"session_key":"oII\/RFa6E\/QMA9ulB2lzAQ==","openId":"opxLy5I39n4oZyPg_CUysPgemVec"}
	 * @return openId
	 */
	public static String toOpenId(String wxOpenId) {
		if(StringUtils.isEmpty(wxOpenId)){
			return null;
		}
		try {
			Map<String, String> wxopenIdMap;
			wxopenIdMap = json.readValue(wxOpenId, HashMap.class);
			return wxopenIdMap.get("openid");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String mapToJson(Map<?, ?> map) {
		try {
			return json.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String objectToJson(Object object) {
		try {
			return json.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static ArrayList<Object> jsonToList(String string){
		
		ArrayList<Object> list = null;
		json.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		try {
			list = json.readValue(string, ArrayList.class);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

    public static <T> ArrayList<T>jsonToList(String string, Class<T> classs){
        List<Object> list = jsonToList(string);
        ArrayList<T> result = new ArrayList<>();
        for (Object object: list){
            T t = objectToClass(object, classs);
            result.add(t);
        }
        return result;
    }
	
	public static ObjectMapper getJson() {
		return json;
	}
	
	public static <T> T jsonToClass(String string,Class<T> classs) {
		try {
			return json.readValue(string, classs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T objectToClass(Object object,Class<T> classs) {
		try {
			return json.readValue(JsonUtils.objectToJson(object), classs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String requestToOpenId(HttpServletRequest request) {
		String sessionId = request.getHeader("sessionId");
		String wxopenId = RedisUtils.get(sessionId);
		String openId = JsonUtils.toOpenId(wxopenId);
		return openId;
	}

	public static <T> T getIndexZero (List<T> list){
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
	
}

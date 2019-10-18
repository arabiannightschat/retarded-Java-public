package com.nights.retarded.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	private static ObjectMapper json = new ObjectMapper();
	
	/**
	 * 参数为String型的wxopenId = {"session_key":"oII\/RFa6E\/QMA9ulB2lzAQ==","openId":"opxLy5I39n4oZyPg_CUysPgemVec"}
	 * @return openId
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static String toOpenId(String wxopenId) {
		
		try {
			Map<String, String> wxopenIdMap;
			wxopenIdMap = json.readValue(wxopenId, HashMap.class);
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
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> jsonToStringMap(String string){
		
		Map<String,String> map = null;
		try {
			map = json.readValue(string, HashMap.class);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> jsonToList(String string){
		
		ArrayList<Object> list = null;
		json.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		try {
			list = json.readValue(string, ArrayList.class);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
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
	
}

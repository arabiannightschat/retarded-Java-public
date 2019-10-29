package com.nights.retarded.common.utils;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
	
	private static RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		RedisUtils.redisTemplate = redisTemplate;
	}
	
	public static void set(String key,String value) {
		redisTemplate.opsForValue().set(key, value , 30, TimeUnit.DAYS);
	}
	
	public static String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	public static void del(String key) {
		redisTemplate.opsForValue().getOperations().delete(key);
	}
}
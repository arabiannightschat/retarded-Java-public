package com.nights.retarded.utils;

import java.util.concurrent.TimeUnit;

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
	
	public static void set30Days(String key,String value) {
		set(key, value , 30, TimeUnit.DAYS);
	}

    /**
     * 写入Redis，默认没有过期时间
     */
    public static void set(String key,String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 写入Redis
     * @param exTime 过期时间（秒）
     */
    public static void set(String key,String value,Integer exTime) {
        set(key, value, exTime, TimeUnit.SECONDS);
    }

    /**
     * 写入Redis
     * @param exTime 过期时间
     * @param timeUnit 时间单位
     */
    public static void set(String key, String value, Integer exTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, exTime, timeUnit);
    }

    public static String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	public static void del(String key) {
		redisTemplate.opsForValue().getOperations().delete(key);
	}
}
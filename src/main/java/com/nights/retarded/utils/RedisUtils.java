package com.nights.retarded.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
	
	private static RedisTemplate<String, String> redisTemplate;
    private static RedisTemplate<String, Object> redisTemplateObject;

    @Autowired
    public void setRedisTemplateObject(RedisTemplate<String, Object> redisTemplateObject) {
        RedisUtils.redisTemplateObject = redisTemplateObject;
    }

    @Autowired
	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		RedisUtils.redisTemplate = redisTemplate;
	}
	
    /**
     * 写入Redis，默认没有过期时间
     */
    public static void set(String key,String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static void setObject(String key, Object value) {
        redisTemplateObject.opsForValue().set(key, value);
    }

    public static Object getObject(String key) {
        return redisTemplateObject.opsForValue().get(key);
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
	
	public static void delete(String key) {
		redisTemplate.opsForValue().getOperations().delete(key);
	}

	// 发布消息
	public static void publish(String topic, String message){
        redisTemplate.convertAndSend(topic, message);
    }

    public static void leftPush(String key, Object value){
        redisTemplateObject.opsForList().leftPush(key, value);
    }

    public static void leftPushAll(String key, List<Object> values) {
        redisTemplateObject.opsForList().leftPushAll(key, values);
    }

    public static Object rightPop(String key) {
        return redisTemplateObject.opsForList().rightPop(key);
    }

    public static Object rightPeek(String key) {
        List list = redisTemplateObject.opsForList().range(key, -1, -1);
        return ListUtils.getIndexZero(list);
    }

    public static List<String> range(String key, long start, long length){
        List<String> list = redisTemplate.opsForList().range(key, start, length);
        return list;
    }

    public static List<String> rangeAll(String key) {
        return range(key, 0, -1);
    }

    public static Object getHash(String hash, String key){
        return redisTemplate.opsForHash().get(hash, key);
    }

    public static void putHash(String hash, String key, String value){
        redisTemplate.opsForHash().put(hash, key, value);
    }

    public static boolean isHash(String hash){
        if(redisTemplate.opsForHash().size(hash) > 0){
            return true;
        }
        return false;
    }

}
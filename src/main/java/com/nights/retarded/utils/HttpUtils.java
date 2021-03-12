package com.nights.retarded.utils;

import com.nights.retarded.base.interceptor.KnownException;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * HttpPost 携带 Cookie 请求头使用 x-www-form-urlencoded
     * @param s Class<T> 对象
     * @param cookie List<String> 从登录请求的 SetCookie 字段取得 参考 postForCookie 方法
     */
    public static <T> T postWithCookie(String url, MultiValueMap params, Class<T> s, List<String> cookie) throws KnownException {
        HttpHeaders httpHeaders = jsonHeaders();
        httpHeaders.put("Cookie", cookie);
        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<T> res = restTemplate.postForEntity(url, httpEntity, s);
        if(res.getStatusCode() != HttpStatus.OK) {
            throw new KnownException("Http 请求失败，url = " + url + "，Code = " + res.getStatusCodeValue());
        }
        return res.getBody();
    }

    /**
     * 用于登录方法获取 cookie，返回值是 cookieList
     */
    public static List<String> postForCookie(String url, MultiValueMap params){
        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(params, jsonHeaders());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class );
        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        return cookies;
    }

    public static ResponseEntity<String> post(String url, MultiValueMap params){
        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(params, jsonHeaders());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class );
        return responseEntity;
    }

    public static ResponseEntity<String> get(String url, Map<String, ?> params){
        url += "?";
        for (String key: params.keySet()) {
            url += (key + "={" + key + "}&");
        }
        url = url.substring(0, url.length() - 1 );
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, params );
        return responseEntity;
    }

    public static String getBody(String url, Map<String, ?> params){
        return get(url, params).getBody();
    }

    /**
     * 获取 x-www-form-urlencoded 请求头设置后的 HttpHeaders
     */
    private static HttpHeaders formHeaders() {
        //添加请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        //form表单提交 application/x-www-form-urlencoded
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return httpHeaders;
    }

    private static HttpHeaders jsonHeaders() {
        //添加请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

}


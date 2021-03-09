package com.nights.retarded.base.interceptor;

import com.nights.retarded.utils.JsonUtils;
import com.nights.retarded.utils.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口鉴权拦截器，对请求检查 openId 是否合法
 * @author jc
 * @time 2019-10-23 10:38:23
 */
@Aspect
@Repository
public class TokenInterceptor {

    @Autowired
    HttpServletRequest request;

    @Pointcut("execution(* com.nights.retarded.*.controller.*Controller.*(..)) && !execution(* com.nights.retarded.sys.controller.LoginController.*(..))")
    public void pointcut(){}

    @Before("pointcut()")
    public void before() throws KnownException {

        String openId = JsonUtils.requestToOpenId(request);
        if(StringUtils.isEmpty(openId)){
            throw new KnownException("鉴权失败，请重新登录");
        }
    }

}

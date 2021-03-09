package com.nights.retarded.base.interceptor;

import com.nights.retarded.base.baseController.BaseController;
import com.nights.retarded.base.baseController.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 全局异常拦截
 * @author jc
 * @time 2019-10-14 17:03:24
 */
@ControllerAdvice
public class ExceptionIntercept {

    Logger logger = LoggerFactory.getLogger("ExceptionIntercept");

    /**
     * 全局已知异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = KnownException.class)
    public Result knownErrorHandler(KnownException ex) {
        String message = ex.getMessage() == null ? ex.getCause().getMessage() : ex.getMessage();
        logger.error("发生已知异常! " + message);
        return BaseController.Error(message);
    }

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception ex) {
        String message;
        try {
            message = ex.getMessage() == null ? ex.getCause().getMessage() : ex.getMessage();
        } catch (Exception e) {
            ex.printStackTrace();
            message = "Unknown Info";
        }
        ex.printStackTrace();
        logger.error("发生未知异常! " + message);
        return BaseController.UnknownError("发生异常! " + message);
    }
}
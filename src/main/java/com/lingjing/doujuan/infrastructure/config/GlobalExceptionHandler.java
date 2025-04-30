package com.lingjing.doujuan.infrastructure.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.lingjing.doujuan.infrastructure.utils.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotLoginException.class)
    public ApiResult<String> handleNotLoginException(HttpServletRequest request, NotLoginException e) {
        log.error("未登录异常", e);
        return ApiResult.failed(401,String.valueOf(request.getAttribute("traceId")), "未登录异常");
    }

    @ExceptionHandler(NotPermissionException.class)
    public ApiResult<String> handleNotPermissionException(HttpServletRequest request,NotPermissionException e) {
        log.error("未授权异常", e);
        return ApiResult.failed(403,String.valueOf(request.getAttribute("traceId")), "未授权异常");
    }


    @ExceptionHandler(Exception.class)
    public ApiResult<String> handleException(HttpServletRequest request,Exception e) {
        log.error(e.getMessage(), e);
        return ApiResult.failed(500,String.valueOf(request.getAttribute("traceId")), "未知异常");
    }


}

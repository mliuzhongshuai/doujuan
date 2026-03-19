package com.lingjing.doujuan.infrastructure.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.lingjing.doujuan.infrastructure.constants.ErrorMessage;
import com.lingjing.doujuan.infrastructure.enums.ResultCode;
import com.lingjing.doujuan.infrastructure.utils.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<String> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error(ErrorMessage.VALIDATION_FAILED + ": {}", message);
        return ApiResult.failed(ResultCode.BAD_REQUEST.getCode(), null, message);
    }

    @ExceptionHandler(NotLoginException.class)
    public ApiResult<String> handleNotLoginException(HttpServletRequest request, NotLoginException e) {
        log.error(ErrorMessage.NOT_LOGGED_IN, e);
        String traceId = String.valueOf(request.getAttribute("traceId"));
        return ApiResult.failed(ResultCode.UNAUTHORIZED.getCode(), traceId, ErrorMessage.NOT_LOGGED_IN);
    }

    @ExceptionHandler(NotPermissionException.class)
    public ApiResult<String> handleNotPermissionException(HttpServletRequest request,NotPermissionException e) {
        log.error(ErrorMessage.NOT_AUTHORIZED, e);
        String traceId = String.valueOf(request.getAttribute("traceId"));
        return ApiResult.failed(ResultCode.FORBIDDEN.getCode(), traceId, ErrorMessage.NOT_AUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<String> handleException(HttpServletRequest request,Exception e) {
        log.error(e.getMessage(), e);
        String traceId = String.valueOf(request.getAttribute("traceId"));
        return ApiResult.failed(ResultCode.SERVER_ERROR.getCode(), traceId, ErrorMessage.UNKNOWN_ERROR);
    }

}

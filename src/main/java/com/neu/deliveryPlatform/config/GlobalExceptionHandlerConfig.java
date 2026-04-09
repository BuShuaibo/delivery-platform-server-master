package com.neu.deliveryPlatform.config;


import com.neu.deliveryPlatform.config.exception.BizException;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author： JiuYou
 * @Date： 2023/3/17
 * @Description：全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandlerConfig {
    public static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandlerConfig.class);

    @ExceptionHandler(NullPointerException.class)
    public Response handleNullPointerException() {
        return Response.error(ErrorCode.NULL_POINT_EXCEPTION);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        bindingResult.getFieldErrors().forEach(item -> stringBuilder.append(item.getField()).append(": ").append(item.getDefaultMessage()).append(";"));
        return Response.error(ErrorCode.ILLEGAL_ARGUMENT.getErrCode(),
                stringBuilder.toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Response handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException:{}", e.getMessage());
        return Response.error(ErrorCode.ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Response handelDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException:{}", e.getMessage());
        return Response.error(ErrorCode.ILLEGAL_ARGUMENT.getErrCode(),
                "外键连接异常，请检查数据");
    }

    @ExceptionHandler(BizException.class)
    public Response handleBizException(BizException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        log.error("\n业务异常:\n异常定位:{}\n异常类型:{}\n异常信息:{}",
                stackTrace.length == 0 ? "" : stackTrace[0], e.getClass(), e.getErrDesc());
        return Response.error(e.getErrCode(), e.getErrDesc());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Response handelAccessDeniedException(Exception e) {
        log.error("权限不足");
        return Response.error(ErrorCode.AUTH_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public Response handleOtherException(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        log.error("\n其他异常:\n异常定位:{}\n异常类型:{}\n异常信息:{}",
                stackTrace.length == 0 ? "" : stackTrace[0], e.getClass(), e.getMessage());
        return Response.error(ErrorCode.UNKNOWN_ERROR);
    }

}

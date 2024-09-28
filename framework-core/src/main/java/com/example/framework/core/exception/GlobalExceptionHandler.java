package com.example.framework.core.exception;

import com.example.framework.core.resp.Resp;
import com.example.framework.core.resp.RespUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.StringJoiner;

/**
 * @ClassName: GlobalExceptionHandler
 * @Description: 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static String DUPLICATE_KEY_CODE = "1001";
    private static String PARAM_FAIL_CODE = "1002";
    private static String VALIDATION_CODE = "1003";
    private static String METHOD_NOT_SUPPORTED = "1004";

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BizException.class)
    public Resp handleRRException(BizException e) {
        logger.error(e.getMessage(), e);
        return RespUtil.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public Resp handleBindException(BindException e) {
        logger.error(e.getMessage(), e);
        return RespUtil.fail(PARAM_FAIL_CODE, e.getMessage());
    }

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Resp handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        // 按需重新封装需要返回的错误信息 解析原错误信息，封装后返回，此处返回非法的字段名称error.getField()，原始值error.getRejectedValue()，错误信息
        StringJoiner sj = new StringJoiner(";");
        e.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
        return RespUtil.fail(PARAM_FAIL_CODE, sj.toString());
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public Resp handleValidationException(ValidationException e) {
        logger.error(e.getMessage(), e);
        return RespUtil.fail(VALIDATION_CODE, e.getCause().getMessage());
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Resp handleConstraintViolationException(ConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        StringJoiner sj = new StringJoiner(";");
        e.getConstraintViolations().forEach(x -> sj.add(x.getMessageTemplate()));
        return RespUtil.fail(PARAM_FAIL_CODE, sj.toString());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Resp handlerNoFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return RespUtil.fail("404", "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Resp handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage(), e);
        return RespUtil.fail(METHOD_NOT_SUPPORTED, "不支持'" + e.getMethod() + "'请求方法");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Resp handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return RespUtil.fail(DUPLICATE_KEY_CODE, "数据重复，请检查后提交");
    }

    @ExceptionHandler(Exception.class)
    public Resp handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return RespUtil.fail("500", "系统繁忙，请稍后再试");
    }
}

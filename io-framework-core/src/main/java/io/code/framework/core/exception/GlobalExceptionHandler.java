package io.code.framework.core.exception;

import io.code.framework.common.entity.ApiResponse;
import io.code.framework.common.entity.ApiResponseUtil;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

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
    public ApiResponse handleRRException(BizException e) {
        logger.error(e.getMessage(), e);
        return ApiResponseUtil.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ApiResponse handleBindException(BindException e) {
        logger.error(e.getMessage(), e);
        return ApiResponseUtil.fail(PARAM_FAIL_CODE, e.getMessage());
    }

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        // 按需重新封装需要返回的错误信息 解析原错误信息，封装后返回，此处返回非法的字段名称error.getField()，原始值error.getRejectedValue()，错误信息
        StringJoiner sj = new StringJoiner(";");
        e.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
        return ApiResponseUtil.fail(PARAM_FAIL_CODE, sj.toString());
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public ApiResponse handleValidationException(ValidationException e) {
        logger.error(e.getMessage(), e);
        return ApiResponseUtil.fail(VALIDATION_CODE, e.getCause().getMessage());
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse handleConstraintViolationException(ConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        StringJoiner sj = new StringJoiner(";");
        e.getConstraintViolations().forEach(x -> sj.add(x.getMessageTemplate()));
        return ApiResponseUtil.fail(PARAM_FAIL_CODE, sj.toString());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResponse handlerNoFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return ApiResponseUtil.fail("404", "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage(), e);
        return ApiResponseUtil.fail(METHOD_NOT_SUPPORTED, "不支持'" + e.getMethod() + "'请求方法");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ApiResponse handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return ApiResponseUtil.fail(DUPLICATE_KEY_CODE, "数据重复，请检查后提交");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return ApiResponseUtil.fail("500", "系统繁忙，请稍后再试");
    }
}

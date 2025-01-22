package io.code.framework.common.enums;

/**
 * @Author: zongz
 * @Date: 2024/9/26
 */
public enum StatusEnum {

    SUCCESS("0000", "成功"),
    FAIL("9999", "失败"),
    PARAM_ERROR("0002", "参数错误"),
    SYSTEM_ERROR("0003", "系统错误"),
    NOT_FOUND("0004", "未找到"),
    TIMEOUT("0005", "超时"),
    UNKNOWN("0006", "未知异常"),
    ;

    private String code;
    private String message;

    StatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

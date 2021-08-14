package com.zkzong.framework.core.exception;

import lombok.Data;

@Data
public class BizException extends RuntimeException {

    private Integer code;
    private String msg;

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BizException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(int code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
    }

}

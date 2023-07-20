package com.example.framework.core.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Resp<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public Resp(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

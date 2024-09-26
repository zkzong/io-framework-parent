package com.example.framework.core.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Resp<T> implements Serializable {

    private String code;
    private String message;
    private T data;

    public Resp(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

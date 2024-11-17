package io.code.framework.core.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private String code;
    private String message;
    private T data;

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

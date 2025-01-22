package io.code.framework.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {

    private String code;
    private String message;
    private T data;

    public ApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

package com.zkzong.framework.example.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DemoReq {

    @NotBlank(message = "userId不能为空")
    private String userId;

    @NotNull(message = "money不能为空")
    private Integer money;

}

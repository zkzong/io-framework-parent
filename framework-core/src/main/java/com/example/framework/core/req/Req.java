package com.example.framework.core.req;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Req implements Serializable {

    @NotBlank(message = "userId不能为空")
    private String userId;

    @NotNull(message = "money不能为空")
    private Integer money;

    @NotNull(message = "金额不能为空")
    @DecimalMin(value = "0.1")
    @Digits(integer = 14, fraction = 1, message = "数字的值超出了允许范围(只允许在14位整数和1位小数范围内)")
    private BigDecimal amount;

    //@NotBlank(message = "身份证号不能为空")
    //@IdentityCardNumber(message = "身份证信息有误,请核对后提交")
    //private String idNo;

}

package io.code.framework.example.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserVo implements Serializable {

    private String name;

    private Integer age;

    private BigDecimal amount;

}

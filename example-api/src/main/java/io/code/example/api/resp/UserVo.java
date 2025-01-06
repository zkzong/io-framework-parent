package io.code.example.api.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserVo implements Serializable {

    private String name;

    private Integer age;

    private BigDecimal amount;

}

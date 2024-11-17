package io.code.framework.core.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Resp implements Serializable {

    private String name;

    private Integer age;

    private BigDecimal amount;

}

package io.code.example.spring.util.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zong on 2017/2/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class More {

    private Integer id;
    private String name;
    private Integer age;
    private String sex;
    private Long amount;

}

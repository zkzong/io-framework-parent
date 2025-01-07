package io.code.example.spring.util;

import io.code.example.spring.util.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

/**
 * @Author: zongz
 * @Date: 2024-12-20
 */
public class ObjectUtilsTest {

    @Test
    public void test() {
        Employee employee = new Employee();
        employee.setRealName("zhangsan");
        boolean empty = ObjectUtils.isEmpty(employee);
        System.out.println("isEmpty:" + empty);

        Employee[] employees = {employee};
        boolean empty1 = ObjectUtils.isEmpty(employees);
        System.out.println("isEmpty(arrar):" + empty1);

        boolean array = ObjectUtils.isArray(employees);
        System.out.println("isArray:" + array);

        Employee tar = new Employee();
        tar.setRealName("zhangsan");
        boolean b = ObjectUtils.containsElement(employees, tar);
        System.out.println("containsElement:" + b);

        boolean b1 = ObjectUtils.nullSafeEquals(employee, tar);
        System.out.println("nullSafeEquals:" + b1);

        Employee lisi = new Employee();
        lisi.setRealName("lisi");
        Employee[] employees1 = ObjectUtils.addObjectToArray(employees, lisi);
        System.out.println("addObjectToArray:" + employees1);
        String string = ObjectUtils.nullSafeToString(employees1);
        System.out.println("nullSafeToString:" + string);
    }
}

package io.code.framework.example.spring.util;

import io.code.framework.example.spring.util.entity.Less;
import io.code.framework.example.spring.util.entity.More;
import io.code.framework.example.spring.util.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class BeanUtilsTest {

    // 字段类型不一样不能赋值
    // 原生类型和包装类型可以赋值
    @Test
    void test() {
        More more = new More(1, "zong", 1, "1", 100L);
        System.out.println(more);
        Less less = new Less();
        BeanUtils.copyProperties(more, less);
        System.out.println(less);

        // todo 属性值少的复制给属性值多的，没有被复制到的属性就是该类型的默认值。 结果1......xy......null

        // null覆盖有值字段
        More more1 = new More(1, "zong", 1, "1", 100L);
        System.out.println(more1);
        Less less1 = new Less();
        BeanUtils.copyProperties(less1, more1);
        System.out.println(more1);

    }

    @Test
    void list2list() throws InvocationTargetException, IllegalAccessException {
        Person p1 = new Person("zong", 30);
        Person p2 = new Person("ma", 25);
        Person p3 = new Person("liu", 20);
        List<Person> sList = new ArrayList<Person>(3);
        sList.add(p1);
        sList.add(p2);
        sList.add(p3);

        // list的泛型不能使用copyProperties
        List<Person> tList = new ArrayList<Person>(3);
        BeanUtils.copyProperties(sList, tList);
        System.out.println(tList.size());

        // 需要遍历copyProperties
        for (int i = 0; i < sList.size(); i++) {
            Person p = new Person();
            BeanUtils.copyProperties(sList.get(i), p);
            tList.add(p);
        }
        System.out.println(tList.size());
    }

    @Test
    void nullProperty() {
        Person p1 = new Person();
        p1.setName("");
        p1.setAge(10);

        Person p2 = new Person();
        p2.setName("ma");
        p2.setAge(30);

        System.out.println("===改变前===");
        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);

        BeanUtils.copyProperties(p1, p2, getNullPropertyNames(p1));

        System.out.println("===改变后===");
        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
    }

    public String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || "".equals(srcValue)) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}

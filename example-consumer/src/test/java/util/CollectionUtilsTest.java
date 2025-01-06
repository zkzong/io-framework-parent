package util;

import util.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zongz
 * @Date: 2024-12-20
 */
public class CollectionUtilsTest {

    @Test
    public void test() {
        List<Employee> list = new ArrayList<>();
        boolean b = CollectionUtils.isEmpty(list);
        System.out.println(b);
        list.add(new Employee());
        b = CollectionUtils.isEmpty(list);
        System.out.println(b);

        Map<String, String> map = new HashMap<>();
        b = CollectionUtils.isEmpty(map);
        System.out.println(b);
        map.put("name", "java");
        b = CollectionUtils.isEmpty(map);
        System.out.println(b);
    }
}

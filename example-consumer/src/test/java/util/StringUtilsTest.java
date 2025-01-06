package util;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

/**
 * @Author: zongz
 * @Date: 2024-12-20
 */
public class StringUtilsTest {

    @Test
    public void test() {
        String s = "";
        String ss = " ";

        boolean b = StringUtils.isEmpty(s);
        boolean bb = StringUtils.isEmpty(ss);
        System.out.println(b);
        System.out.println(bb);

        // 类似isNotEmpty
        b = StringUtils.hasLength(s);
        bb = StringUtils.hasLength(ss);
        System.out.println(b);
        System.out.println(bb);

        // 类似isNotBlank
        b = StringUtils.hasText(s);
        bb = StringUtils.hasText(ss);
        System.out.println(b);
        System.out.println(bb);
    }
}

package org.example.framework.datasource.toolkit;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源上下文
 **/
@Slf4j
public class DataSourceContextHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    private DataSourceContextHolder() {
    }

    public static void switchTo(String name) {
        log.debug("switch datasource to [{}]", name);
        CONTEXT_HOLDER.set(name);
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

    public static String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

}

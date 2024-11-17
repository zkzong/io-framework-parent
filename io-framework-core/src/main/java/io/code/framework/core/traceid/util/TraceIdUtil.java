package io.code.framework.core.traceid.util;

import java.util.UUID;

/**
 * traceId生成工具类
 */
public class TraceIdUtil {
    public static String getTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}

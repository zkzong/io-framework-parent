package com.example.framework.core.utils;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class ModuleConverter extends ClassicConverter {

    private static final int MAX_LENGTH = 20;

    @Override
    public String convert(ILoggingEvent event) {
        if (event.getLoggerName().length() > MAX_LENGTH) {
            return "";
        } else {
            return event.getLoggerName();
        }
    }
}

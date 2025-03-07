package io.code.framework.core.interceptor;

import io.code.framework.core.annotation.Delete;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

/**
 * @Author: zong
 * @Date: 2021/8/20
 */
@Slf4j
public class DeleteInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取注解位置，切入点
        Delete annotation;
        if (handler instanceof HandlerMethod handlerMethod) {

            // 入参使用@RequestParam
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                String[] parameterValues = request.getParameterValues(name);
                log.info("{} = {}", name, parameterValues);
                log.info(name + " " + parameterValues[0]);
            }

            annotation = handlerMethod.getMethodAnnotation(Delete.class);
            if (annotation != null) {
                log.info("该方法有Delete注解");
            }

            StringBuilder sb = new StringBuilder("使用Interceptor获取请求信息[");

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 时间
            sb.append(now.format(formatter)).append("|");

            final String className = handlerMethod.getBeanType().getName();
            final String methodName = handlerMethod.getMethod().getName();
            String uri = request.getRequestURI();

            // 请求方式
            sb.append(request.getMethod()).append("|")
                    // URI
                    .append(uri).append("|")
                    // 类名、方法名
                    .append(className).append(".").append(methodName).append("]");
            log.info(sb.toString());
        } else {
            return true;
        }
        if (annotation == null) {
            return true;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}

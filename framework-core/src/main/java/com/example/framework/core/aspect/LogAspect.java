package com.example.framework.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @Author: zong
 * @Date: 2021/8/2
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private Environment environment;

    /**
     * 使用annotation方式
     */
    //@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    //public void log() {
    //}

    /**
     * 使用execution方式
     */
    @Pointcut("execution(public * com..*.controller.*.*(..)))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws UnknownHostException {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        final Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;

            final String hostAddress = InetAddress.getLocalHost().getHostAddress();
            final String port = environment.getProperty("server.port");

            StringBuilder sb = new StringBuilder("使用Aspect[");

            // 请求方ip
            sb.append(request.getRemoteAddr()).append("|")
                    // 目标方ip和port
                    .append(hostAddress).append(":").append(port).append("|")
                    // 请求方式：POST、GET...
                    .append(request.getMethod()).append("|")
                    // URI
                    .append(request.getRequestURI()).append("|")
                    // 类名、方法名
                    .append(methodSignature.getDeclaringTypeName()).append(".").append(methodSignature.getName()).append("|")
                    // 入参
                    .append(Arrays.toString(joinPoint.getArgs()))
                    .append("]");
            log.info(sb.toString());
        }

        // 记录下请求内容
        //log.info("URL : " + request.getRequestURL().toString());
        //log.info("URI : " + request.getRequestURI());
        //log.info("HTTP_METHOD : " + request.getMethod());
        //log.info("IP : " + request.getRemoteAddr());
        //log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));


    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
    }

}

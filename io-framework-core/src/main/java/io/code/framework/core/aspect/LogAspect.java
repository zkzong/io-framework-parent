package io.code.framework.core.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.code.framework.core.annotation.Delete;
import jakarta.servlet.http.HttpServletRequest;
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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 使用annotation方式
     */
    //@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    //public void log() {
    //}

    /**
     * 使用execution方式
     */
    @Pointcut("execution(public * io.code..*.controller.*.*(..)))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws UnknownHostException {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        final Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature methodSignature) {

            final String hostAddress = InetAddress.getLocalHost().getHostAddress();
            final String port = environment.getProperty("server.port");

            Delete annotation = methodSignature.getMethod().getAnnotation(Delete.class);
            if (annotation != null) {
                log.info("该方法有Delete注解");
            }

            String paramStr = "";

            // 如果入参是form方式，joinPoint.getArgs()只能获取到值，不能获取key
            Map<String, String[]> parameterMap = request.getParameterMap();
            int size = parameterMap.size();
            if (size > 0) {
                Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
                //entries.forEach(entry -> {
                //    log.info(entry.getKey() + "=" + Arrays.stream(entry.getValue()).collect(Collectors.joining(",")));
                //});
                for (Map.Entry<String, String[]> entry : entries) {
                    paramStr += entry.getKey() + "=" + Arrays.stream(entry.getValue()).collect(Collectors.joining(",")) + ", ";
                }
            }
            Object[] args = joinPoint.getArgs();
            int length = args.length;
            if (length > 0) {
                try {
                    paramStr = objectMapper.writeValueAsString(args);
                } catch (JsonProcessingException e) {
                    log.error("解析入参错误");
                }
            }

            // 请求方ip
            String sb = "使用Aspect获取请求信息[" + request.getRemoteAddr() + "|" +
                    // 目标方ip和port
                    hostAddress + ":" + port + "|" +
                    // 请求方式：POST、GET...
                    request.getMethod() + "|" +
                    // URI
                    request.getRequestURI() + "|" +
                    // 类名、方法名
                    methodSignature.getDeclaringTypeName() + "." + methodSignature.getName() + "|" +
                    // 入参
                    paramStr +
                    "]";
            log.info(sb);
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
        try {
            log.info("RESPONSE : " + objectMapper.writeValueAsString(ret));
        } catch (JsonProcessingException e) {
            log.error("解析返回值错误");
        }
    }

}

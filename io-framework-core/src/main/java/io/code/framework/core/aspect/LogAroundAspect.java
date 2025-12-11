package io.code.framework.core.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.code.framework.core.annotation.Delete;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
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
public class LogAroundAspect {

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

    @Around("log()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws UnknownHostException {
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

            // 入参是form表单，joinPoint.getArgs()和request.getParameterMap()都有值
            // 但是joinPoint.getArgs()只能获取到值，不能获取key
            // 入参是body，joinPoint.getArgs()有值，request.getParameterMap()为空
            Object[] args = joinPoint.getArgs();
            int length = args.length;
            if (length > 0) {
                try {
                    paramStr = objectMapper.writeValueAsString(args);
                } catch (JsonProcessingException e) {
                    log.error("解析入参错误");
                }
            }

            Map<String, String[]> parameterMap = request.getParameterMap();
            int size = parameterMap.size();
            if (size > 0) {
                paramStr = "";
                Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
                //entries.forEach(entry -> {
                //    log.info(entry.getKey() + "=" + Arrays.stream(entry.getValue()).collect(Collectors.joining(",")));
                //});
                for (Map.Entry<String, String[]> entry : entries) {
                    paramStr += entry.getKey() + "=" + Arrays.stream(entry.getValue()).collect(Collectors.joining(",")) + ", ";
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

        Object result;
        try {
            result = joinPoint.proceed();
            log.info("Around response : {}", objectMapper.writeValueAsString(result));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}

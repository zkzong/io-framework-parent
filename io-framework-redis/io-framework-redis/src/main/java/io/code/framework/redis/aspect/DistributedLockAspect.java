package io.code.framework.redis.aspect;

import io.code.framework.redis.annotation.DistributedLock;
import io.code.framework.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static io.code.framework.redis.constant.StringConstant.REDIS_KEY_CONNECTION_LINE;
import static io.code.framework.redis.constant.StringConstant.REDIS_SPLIT_KEY_CONNECTION_LINE;
import static io.code.framework.redis.constant.StringConstant.REDIS_SPLIT_LINE;


/**
 * 分布式锁解析器
 */
@Aspect
@Component
@Slf4j
public class DistributedLockAspect {

    @Pointcut("@annotation(io.code.framework.redis.annotation.DistributedLock)")
    public void lockPoint() {
    }

    @Autowired
    RedisUtils redisUtils;

    private final ExpressionParser parser = new SpelExpressionParser();

    // spring boot 2使用LocalVariableTableParameterNameDiscoverer
    private final StandardReflectionParameterNameDiscoverer discoverer = new StandardReflectionParameterNameDiscoverer();

    @Around("lockPoint()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
        String prefix = distributedLock.prefix();
        String key = distributedLock.key();
        Object[] args = pjp.getArgs();
        String keyName = prefix + parse(key, method, args);
        log.info("Redis分布式锁的key为 : {}", keyName);
        RLock lock = getLock(keyName, distributedLock);
        log.info("[开始]执行RedisLock环绕通知,获取Redis分布式锁开始");
        if (lock.tryLock(distributedLock.waitTime(), distributedLock.expireTime(), distributedLock.unit())) {
            try {
                log.info("获取Redis分布式锁[成功]，加锁完成，开始执行业务逻辑...");
                return pjp.proceed();
            } finally {
                try {
                    lock.unlock();
                } catch (Exception ex) {
                    log.error("释放Redis分布式锁[异常]", ex);
                }
                log.info("释放Redis分布式锁[成功]，解锁完成，结束业务逻辑...");
            }
        } else {
            log.warn("获取Redis分布式锁[失败]");
        }
        log.info("[结束]执行RedisLock环绕通知");
        return null;
    }

    /**
     * @description 解析spring EL表达式
     */
    private String parse(String key, Method method, Object[] args) {
        if (StringUtils.isBlank(key)) {
            return key;
        }
        String[] params = discoverer.getParameterNames(method);
        if (params == null || params.length <= 0) {
            return "";
        }
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < params.length; i++) {
            context.setVariable(params[i], args[i]);
        }
        StringBuilder sb = new StringBuilder();
        String[] keys = new String[]{key};
        if (key.contains(REDIS_KEY_CONNECTION_LINE)) {
            keys = key.split(REDIS_SPLIT_KEY_CONNECTION_LINE);
        }
        for (String s : keys) {
            sb.append(REDIS_SPLIT_LINE).append(parser.parseExpression(s).getValue(context, String.class));
        }
        return sb.toString();
    }

    private RLock getLock(String key, DistributedLock distributedLock) {
        switch (distributedLock.lockType()) {
            case REENTRANT_LOCK:
                return redisUtils.getLock(key);
            case FAIR_LOCK:
                return redisUtils.getFairLock(key);
            case READ_LOCK:
                return redisUtils.getReadWriteLock(key).readLock();
            case WRITE_LOCK:
                return redisUtils.getReadWriteLock(key).writeLock();
            default:
                throw new RuntimeException("do not support lock type:" + distributedLock.lockType().name());
        }
    }
}

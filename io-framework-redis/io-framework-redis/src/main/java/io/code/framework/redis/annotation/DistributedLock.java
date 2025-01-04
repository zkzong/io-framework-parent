package io.code.framework.redis.annotation;

import io.code.framework.redis.enums.LockType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DistributedLock {

    /**
     * 锁的资源，key。支持spring El表达式
     */
    String prefix() default "lock";

    /**
     * 分布式锁名称: prefix+key
     */
    String key() default "";

    /**
     * 获取锁等待时间，默认3秒
     */
    long waitTime() default 3L;

    /**
     * 锁超时时间,默认60秒
     */
    int expireTime() default 60;

    /**
     * 锁超时时间单位,默认为秒
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 锁类型 默认为可重入锁
     */
    LockType lockType() default LockType.REENTRANT_LOCK;
}

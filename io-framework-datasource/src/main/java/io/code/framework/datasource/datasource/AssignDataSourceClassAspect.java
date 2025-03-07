package io.code.framework.datasource.datasource;

import io.code.framework.datasource.toolkit.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

/**
 * 指定数据源AOP
 **/
@Aspect
@Order(-6)
@Slf4j
public class AssignDataSourceClassAspect {

    @Before("@within(targetDataSource)")
    public void changeDataSource1(JoinPoint point, AssignDataSource targetDataSource) {
        //获取当前的指定的数据源;
        String dataSourceName = targetDataSource.value();
        DataSourceContextHolder.switchTo(dataSourceName);
        log.debug("切换数据源: [{}]", dataSourceName);
    }

    @After("@within(targetDataSource)")
    public void restoreDataSource1(JoinPoint point, AssignDataSource targetDataSource) {
        DataSourceContextHolder.clear();
        log.debug("回收数据源: [{}] > [{}]", targetDataSource.value(), point.getSignature());
    }

}

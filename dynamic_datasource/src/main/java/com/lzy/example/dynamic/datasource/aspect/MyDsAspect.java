package com.lzy.example.dynamic.datasource.aspect;

import com.lzy.example.dynamic.datasource.utils.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>
 * 动态数据源aop切面实现
 * </p>
 *
 * @author lzy
 * @since 2023/9/14 16:31
 */
@Aspect
@Component
@Slf4j
public class MyDsAspect {

    @Pointcut("@annotation(com.lzy.example.dynamic.datasource.aspect.MyDs)")
    public void dynamicDataSource(){}

    @Around("dynamicDataSource()")
    public Object dataSourceAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        MyDs ds = method.getAnnotation(MyDs.class);
        if (Objects.nonNull(ds)){
            DataSourceContextHolder.setDataSource(ds.value());
        }
        try {
            return point.proceed();
        } finally {
            DataSourceContextHolder.removeDataSource();
        }
    }
}

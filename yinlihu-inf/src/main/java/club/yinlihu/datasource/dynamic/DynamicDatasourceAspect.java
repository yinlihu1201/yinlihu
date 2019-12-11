package club.yinlihu.datasource.dynamic;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 动态数据源切面配置
 */
@Aspect
@Component
public class DynamicDatasourceAspect {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicDatasourceAspect.class);

    @Pointcut("execution(* club.yinlihu..mapper.*.*(..))")
    public void datasourcePoint(){}

    @Before("datasourcePoint()")
    public void before(JoinPoint joinPoint) {
        try {
            Object target = joinPoint.getTarget();

            // 类上切换数据源
            if (target.getClass().getInterfaces()[0].isAnnotationPresent(YinlihuDatasource.class)) {
                YinlihuDatasource annotation = target.getClass().getInterfaces()[0].getAnnotation(YinlihuDatasource.class);
                DynamicDatasourceContextHolder.setDatasourceType(annotation.value());
                return;
            }

            // 方法上切换数据源
            String method = joinPoint.getSignature().getName();
            Class<?>[] clazz = target.getClass().getInterfaces();
            Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();

            Method m = clazz[0].getMethod(method, parameterTypes);
            //如果方法上存在切换数据源的注解，则根据注解内容进行数据源切换
            if (m != null && m.isAnnotationPresent(YinlihuDatasource.class)) {
                YinlihuDatasource data = m.getAnnotation(YinlihuDatasource.class);
                DynamicDatasourceContextHolder.setDatasourceType(data.value());
            }
        } catch (Exception e) {
            LOG.info("切换数据源异常", e);
        }
    }

    @After("datasourcePoint()")
    public void after(JoinPoint joinPoint) {
        DynamicDatasourceContextHolder.clearDatasourceType();
    }
}

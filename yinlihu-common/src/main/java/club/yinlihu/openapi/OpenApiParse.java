package club.yinlihu.openapi;

import club.yinlihu.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Open-Api方法解析
 *
 * BeanPostProcessor: spring-ioc提供的开方接口
 * spring-IOC实例化Bean操作：
 * 调用BeanPostProcessor 的 postProcessBeforeInitialization
 * 调用Bean的初始化方法
 * 调用BeanPostProcessor 的 postProcessAfterInitialization
 */
@Component
public class OpenApiParse implements BeanPostProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(OpenApiParse.class);

    @Autowired
    private OpenApiRepository openApiRepository;

    private static javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    /**
     * 初始化方法：将初始化的方法注入到仓库中
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        if (declaredMethods == null) {
            return bean;
        }
        for (Method method : declaredMethods) {
            if (!method.isAnnotationPresent(OpenApi.class)) {
                continue;
            }
            // 校验入参
            checkParam(clazz,method);
            // 校验出参
            checkReturnParam(clazz,method);
            // 注册api
            openApiRepository.register(method, clazz);
        }
        return bean;
    }

    /**
     * 校验请求参数
     */
    private void checkParam(Class<?> clazz, Method method){
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            throw new BizException("class " + clazz.getSimpleName() + ",method " + method.getName() + ", no parameter");
        }
        if (parameterTypes.length > 1) {
            throw new BizException("class " + clazz.getSimpleName() + ",method " + method.getName() + ", has more than one parameter");
        }
    }

    /**
     * 校验出参
     * @param clazz
     * @param method
     */
    private void checkReturnParam(Class<?> clazz, Method method) {
        Class<?> returnType = method.getReturnType();
        if (!ResultMap.class.isAssignableFrom(returnType)) {
            throw new BizException("class " + clazz.getSimpleName() + ",method " + method.getName() + ", return Type must be ResultMap.class");
        }
    }

    /**
     * 执行任务
     * @return
     */
    public ResultMap excute(HttpServletRequest request, OpenApiRequestParam param){
        LOG.info("request method {},param {}",methodName , data);

        OpenMethod openMethod = methodMap.get(methodName);

        // 方法名错误
        if (openMethod == null) {
            LOG.error("request method {} not found", methodName);
            return SNCloudCallResult.fail("请求方法校验错误");
        }

        // Json->Java校验
        Object businessData;
        try {
            if (openMethod.getParamType().isAssignableFrom(String.class)) {
                businessData = data;
            } else {
                // 平台标识
                request.setAttribute("sourcePlatform", snCloudApiJobRequestParam.getPlatform());
                businessData = JSONObject.parseObject(data, openMethod.getParamType());
                // businessData = objectMapper.readValue(data, openMethod.getParamType());
            }
        } catch (Exception e) {
            LOG.error("requestParamStr {} parse exception,", data, e);
            return SNCloudCallResult.fail("您传入的业务参数有误,参数为json对象或者字符串");
        }

        // 统一必填校验
        Set<ConstraintViolation<Object>> violations = validator.validate(businessData);
        if (violations != null && violations.size() > 0) {
            return SNCloudCallResult.fail(violations.iterator().next().getMessage());
        }

        // 反射：调用方法
        Method method = openMethod.getMethod();
        try {
            method.setAccessible(true);
            return (SNCloudCallResult) method.invoke(openMethod.getInstance(), businessData);
        } catch (IllegalAccessException e) {
            LOG.error("method invoke exception {},", e.getCause(), e);
            return SNCloudCallResult.fail("业务方法执行异常");
        } catch (InvocationTargetException e) {
            LOG.error("method invoke exception {},", e.getCause(), e);
            return SNCloudCallResult.fail("业务方法执行异常");
        }
        return ResultMap.ok();
    }
}

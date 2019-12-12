package club.yinlihu.openapi;

import club.yinlihu.constants.CommonConstants;
import club.yinlihu.entity.ResultMap;
import club.yinlihu.exception.BizException;
import club.yinlihu.exception.ErrorCode;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

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
            throw new BizException(clazz.getSimpleName() + "--" + method.getName() + ", no parameter");
        }
        if (parameterTypes.length > 1) {
            throw new BizException(clazz.getSimpleName() + "--" + method.getName() + ", has more than one parameter");
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
            throw new BizException(clazz.getSimpleName() + " return Type must be ResultMap.class");
        }
    }

    /**
     * 执行任务
     * @return
     */
    public ResultMap execute(HttpServletRequest request, OpenApiRequestParam param){
        long start = System.currentTimeMillis();
        OpenApiVersion version = OpenApiVersion.getVersion(param.getVersion());
        if (version == null) {
            return ResultMap.fail(ErrorCode.OPEN_API_VERSION_ERROR);
        }

        OpenApiMethod openApi = openApiRepository.getOpenApi(version, param.getApi());
        if (openApi == null) {
            return ResultMap.fail(ErrorCode.OPEN_API_NOT_EXISTS_ERROR);
        }

        // TODO: 增加接口限流

        LOG.info("request api class {} method {}",openApi.getInstance().getClass().getSimpleName(), openApi.getMethod().getName());
        request.setAttribute(CommonConstants.OPEN_API_CLASS_NAME, openApi.getInstance().getClass().getSimpleName());
        request.setAttribute(CommonConstants.OPEN_API_METHOD_NAME, openApi.getMethod().getName());

        // Json->Java校验
        Object businessData;
        try {
            businessData = JSONObject.parseObject(param.getData(), openApi.getParamType());
        } catch (Exception e) {
            LOG.error("requestParamStr {} parse exception,", param.getData(), e);
            return ResultMap.fail(ErrorCode.OPEN_API_PARAM_ERROR);
        }

        // 反射：调用方法
        Method method = openApi.getMethod();
        try {
            method.setAccessible(true);
            return (ResultMap) method.invoke(openApi.getInstance(), businessData);
        }  catch (Exception e) {
            LOG.error("method invoke exception!", e);
            return ResultMap.fail(ErrorCode.BIZ_ERROR);
        }
    }
}

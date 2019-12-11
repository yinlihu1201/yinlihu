package club.yinlihu.openapi;

import club.yinlihu.exception.BizException;
import club.yinlihu.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OpenApi存储仓库
 * ApplicationContextAware: 获取IOC控制的上下文
 */
@Component
public class OpenApiRepository implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(OpenApiRepository.class);

    // 版本对应的open类型
    private static final Map<OpenApiVersion,Map<String, OpenApiMethod>> OPEN_API_MAP = new ConcurrentHashMap<>();
    // 应用上下文
    private ApplicationContext context;

    /**
     * 注入open-api
     */
    public synchronized void register(Method method, Class<?> clazz) {
        OpenApi openApi = method.getAnnotation(OpenApi.class);
        String apiName = openApi.value();
        OpenApiVersion version = openApi.version();

        if (OPEN_API_MAP.get(version) == null) {
            OPEN_API_MAP.put(version,new HashMap<>());
        }

        Map<String, OpenApiMethod> openApiMethodMap = OPEN_API_MAP.get(version);
        if (openApiMethodMap.containsKey(apiName)) {
            throw new BizException(ErrorCode.OPEN_API_REPEAT_ERROR);
        }

        OpenApiMethod openApiMethod = new OpenApiMethod();
        openApiMethod.setMethod(method);
        openApiMethod.setParamType(method.getParameterTypes()[0]);
        Object instance;
        try {
            instance = context.getBean(clazz);
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
        openApiMethod.setInstance(instance);

        openApiMethodMap.put(apiName, openApiMethod);
    }

    /**
     * 获取方法
     * @return
     */
    public OpenApiMethod getOpenApi(OpenApiVersion version, String apiName){
        Map<String, OpenApiMethod> openApiMethod = OPEN_API_MAP.get(version);
        if (openApiMethod != null) {
            return openApiMethod.get(apiName);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

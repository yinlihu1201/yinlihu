package club.yinlihu.openapi;

import java.lang.reflect.Method;

/**
 * OpenApi的方法封装
 */
public class OpenApiMethod {

    // 请求参数类型
    private Class<?> paramType;
    // 注解方法
    private Method method;
    // 注解对象
    private Object instance;

    public Class<?> getParamType() {
        return paramType;
    }
    public void setParamType(Class<?> paramType) {
        this.paramType = paramType;
    }
    public Method getMethod() {
        return method;
    }
    public void setMethod(Method method) {
        this.method = method;
    }
    public Object getInstance() {
        return instance;
    }
    public void setInstance(Object instance) {
        this.instance = instance;
    }
}

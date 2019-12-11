package club.yinlihu.openapi;

import java.lang.annotation.*;

/**
 * OpenApi注解
 * 参数只可进行接收一个参数，post接口方式进行调用
 * 返回参数必须定义为ResultMap
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenApi {
    String value();

    OpenApiVersion version() default OpenApiVersion.V1;
}

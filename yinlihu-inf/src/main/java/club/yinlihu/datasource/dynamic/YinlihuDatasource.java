package club.yinlihu.datasource.dynamic;

import java.lang.annotation.*;

/**
 * 数据源配置
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface YinlihuDatasource {
    DatasourceType value() default DatasourceType.MASTER;
}

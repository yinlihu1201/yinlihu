package club.yinlihu.datasource.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据源上下文
 * 使用ThreadLocal线程安全，原子性
 */
public class DynamicDatasourceContextHolder {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicDatasourceContextHolder.class);

    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    public static void setDatasourceType(DatasourceType datasourceType) {
        LOG.info("切换数据源：{}",datasourceType.getType());
        HOLDER.set(datasourceType.getType());
    }

    public static String getDatasourceType() {
        return HOLDER.get();
    }

    public static void clearDatasourceType() {
        HOLDER.remove();
    }
}

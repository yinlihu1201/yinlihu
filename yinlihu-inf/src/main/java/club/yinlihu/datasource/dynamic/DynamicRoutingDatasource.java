package club.yinlihu.datasource.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 实现AbstractRoutingDataSource接口，动态配置数据源
 * 路由到哪个数据源
 */
public class DynamicRoutingDatasource extends AbstractRoutingDataSource {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicRoutingDatasource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String datasourceType = DynamicDatasourceContextHolder.getDatasourceType();
        LOG.info("当前数据源:{}",datasourceType);
        return datasourceType;
    }
}

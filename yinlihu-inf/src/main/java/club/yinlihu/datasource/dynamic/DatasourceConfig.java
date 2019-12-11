package club.yinlihu.datasource.dynamic;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "club.yinlihu.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DatasourceConfig {

    @Bean(name="masterDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    @Primary
    public DataSource masterDatasource() {
        DataSource master = DataSourceBuilder.create().build();
        return master;
    }

    /**
     * 数据源配置
     * @return
     */
    @Bean(name="clusterDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.cluster")
    public DataSource clusterDatasource() {
        DataSource cluster = DataSourceBuilder.create().build();
        return cluster;
    }

    /**
     * 动态数据源配置
     * @return
     */
    @Bean(name = "dynamicDatasource")
    public DataSource dynamicDatasource(@Qualifier("masterDatasource")DataSource masterDatasource, @Qualifier("clusterDatasource")DataSource clusterDatasource) {
        DynamicRoutingDatasource dynamicRoutingDatasource = new DynamicRoutingDatasource();
        //配置多数据源
        Map<Object, Object> dataSourceMap = new HashMap<>(3);
        dataSourceMap.put(DatasourceType.MASTER.getType(), masterDatasource);
        dataSourceMap.put(DatasourceType.CLUSTER.getType(), clusterDatasource);
        // 将 master 数据源作为默认指定的数据源
        dynamicRoutingDatasource.setDefaultTargetDataSource(masterDatasource());
        // 将 master 和 slave 数据源作为指定的数据源
        dynamicRoutingDatasource.setTargetDataSources(dataSourceMap);
        return dynamicRoutingDatasource;
    }

    /**
     * session控制
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("dynamicDatasource") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 设置mybatis的xml所在位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/**/*.xml"));
        bean.setTypeAliasesPackage("club.yinlihu.entity");
        return bean.getObject();
    }

    /**
     * 事务控制
     * @return
     */
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("dynamicDatasource")DataSource dynamicDatasource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager(dynamicDatasource);
        return manager;
    }

    /**
     * 配置事务的传播特性
     */
    @Bean(name = "txAdvice")
    public TransactionInterceptor txAdvice(@Qualifier("transactionManager")PlatformTransactionManager transactionManager){
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(transactionManager);
        Properties transactionAttributes = new Properties();
        //使用transactionAttributes.setProperty()配置传播特性
        interceptor.setTransactionAttributes(transactionAttributes);
        return interceptor;
    }

    @Bean(name = "txAdviceAdvisor")
    public Advisor txAdviceAdvisor(@Qualifier("txAdvice") TransactionInterceptor txAdvice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        String transactionExecution = "execution(* club.yinlihu..service.*.*(..))";
        pointcut.setExpression(transactionExecution);
        return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }
}
